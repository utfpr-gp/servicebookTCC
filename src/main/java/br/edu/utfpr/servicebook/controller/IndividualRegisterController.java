package br.edu.utfpr.servicebook.controller;

import br.edu.utfpr.servicebook.model.dto.*;
import br.edu.utfpr.servicebook.model.entity.City;
import br.edu.utfpr.servicebook.model.entity.Individual;
import br.edu.utfpr.servicebook.model.entity.User;
import br.edu.utfpr.servicebook.model.entity.UserCode;
import br.edu.utfpr.servicebook.model.mapper.CityMapper;
import br.edu.utfpr.servicebook.model.mapper.IndividualMapper;
import br.edu.utfpr.servicebook.model.mapper.UserCodeMapper;
import br.edu.utfpr.servicebook.service.*;
import br.edu.utfpr.servicebook.util.WizardSessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import java.util.Optional;


@Controller
@Slf4j
@RequestMapping("/cadastrar-se")
@SessionAttributes("wizard")
public class IndividualRegisterController {

    @Autowired
    private WizardSessionUtil<IndividualDTO> wizardSessionUtil;

    @Autowired
    private IndividualService individualService;

    @Autowired
    private IndividualMapper individualMapper;

    @Autowired
    private UserCodeService userCodeService;

    @Autowired
    private UserCodeMapper userCodeMapper;

    @Autowired
    private CityService cityService;

    @Autowired
    private CityMapper cityMapper;

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private SmartValidator validator;

    @Autowired
    private AuthenticationCodeGeneratorService authenticationCodeGeneratorService;

    private String userRegistrationErrorForwarding(String step, IndividualDTO dto, Model model, BindingResult errors) {
        model.addAttribute("dto", dto);
        model.addAttribute("errors", errors.getAllErrors());

        return "visitor/user-registration/wizard-step-0" + step;
    }

    private String userCodeErrorForwarding(String step, UserCodeDTO dto, Model model, BindingResult errors) {
        model.addAttribute("dto", dto);
        model.addAttribute("errors", errors.getAllErrors());

        return "visitor/user-registration/wizard-step-0" + step;
    }

    private String userAddressRegistrationErrorForwarding(String step, AddressDTO dto, Model model, BindingResult errors) {
        model.addAttribute("dto", dto);
        model.addAttribute("errors", errors.getAllErrors());

        return "visitor/user-registration/wizard-step-0" + step;
    }

    @GetMapping
    public String showUserRegistrationWizard(
            @RequestParam(value = "passo", required = false, defaultValue = "1") Long step,
            HttpSession httpSession,
            Model model
    ) {

        if (step < 1 || step > 8) {
            step = 1L;
        }

        IndividualDTO dto = wizardSessionUtil.getWizardState(httpSession, IndividualDTO.class, WizardSessionUtil.KEY_WIZARD_USER);
        model.addAttribute("dto", dto);
        return "visitor/user-registration/wizard-step-0" + step;
    }

    @PostMapping("/passo-1")
    public String saveUserEmail(
            HttpSession httpSession,
            @Validated(IndividualDTO.RequestUserEmailInfoGroupValidation.class) IndividualDTO dto,
            BindingResult errors,
            RedirectAttributes redirectAttributes,
            Model model
    ) throws MessagingException {

        if (errors.hasErrors()) {
            return this.userRegistrationErrorForwarding("1", dto, model, errors);
        }

        Optional<Individual> oUser = individualService.findByEmail(dto.getEmail());

        if (oUser.isPresent()) {
            errors.rejectValue("email", "error.dto", "Email já cadastrado! Por favor, insira um email não cadastrado.");
        }

        if (errors.hasErrors()) {
            return this.userRegistrationErrorForwarding("1", dto, model, errors);
        }

        Optional<UserCode> oUserCode = userCodeService.findByEmail(dto.getEmail());

        if (!oUserCode.isPresent()) {
            String code = authenticationCodeGeneratorService.generateAuthenticationCode();

            UserCodeDTO userCodeDTO = new UserCodeDTO(dto.getEmail(), code);
            UserCode userCode = userCodeMapper.toEntity(userCodeDTO);

            userCodeService.save(userCode);
            emailSenderService.sendEmailToServer(dto.getEmail(), "Servicebook: Código de autenticação.", "Código de autenticação:" + "\n\n\n" + code);
        } else {
            emailSenderService.sendEmailToServer(dto.getEmail(), "Servicebook: Código de autenticação.", "Código de autenticação:" + "\n\n\n" + oUserCode.get().getCode());
        }

        IndividualDTO sessionDTO = wizardSessionUtil.getWizardState(httpSession, IndividualDTO.class, WizardSessionUtil.KEY_WIZARD_USER);
        sessionDTO.setEmail(dto.getEmail());

        return "redirect:/cadastrar-se?passo=2";
    }

    @PostMapping("/passo-2")
    public String saveUserEmailCode(
            HttpSession httpSession,
            @Validated(UserCodeDTO.RequestUserCodeInfoGroupValidation.class) UserCodeDTO dto,
            BindingResult errors,
            RedirectAttributes redirectAttributes,
            Model model
    ) {

        if (errors.hasErrors()) {
            return this.userCodeErrorForwarding("2", dto, model, errors);
        }

        IndividualDTO sessionDTO = wizardSessionUtil.getWizardState(httpSession, IndividualDTO.class, WizardSessionUtil.KEY_WIZARD_USER);
        Optional<UserCode> oUserCode = userCodeService.findByEmail(sessionDTO.getEmail());

        if (!oUserCode.isPresent()) {
            errors.rejectValue("code", "error.dto", "Código inválido! Por favor, insira o código de autenticação.");
        }

        if (errors.hasErrors()) {
            return this.userCodeErrorForwarding("2", dto, model, errors);
        }

        if (!dto.getCode().equals(oUserCode.get().getCode())) {
            errors.rejectValue("code", "error.dto", "Código inválido! Por favor, insira o código de autenticação.");
        }

        if (errors.hasErrors()) {
            return this.userCodeErrorForwarding("2", dto, model, errors);
        }

        sessionDTO.setEmailVerified(true);

        redirectAttributes.addFlashAttribute("msg", "Email verificado com sucesso!");

        return "redirect:/cadastrar-se?passo=3";
    }

    @PostMapping("/passo-3")
    public String saveUserPassword(
            HttpSession httpSession,
            @Validated(IndividualDTO.RequestUserPasswordInfoGroupValidation.class) IndividualDTO dto,
            BindingResult errors,
            RedirectAttributes redirectAttributes,
            Model model
    ) {

        if (errors.hasErrors()) {
            return this.userRegistrationErrorForwarding("3", dto, model, errors);
        }

        if(!dto.getPassword().equals(dto.getRepassword())){
            errors.rejectValue("password", "error.dto", "As senhas não correspondem. Por favor, tente novamente.");
        }

        if (errors.hasErrors()) {
            return this.userRegistrationErrorForwarding("3", dto, model, errors);
        }

        IndividualDTO sessionDTO = wizardSessionUtil.getWizardState(httpSession, IndividualDTO.class, WizardSessionUtil.KEY_WIZARD_USER);
        sessionDTO.setPassword(dto.getPassword());
        sessionDTO.setRepassword(dto.getRepassword());

        return "redirect:/cadastrar-se?passo=4";
    }

    @PostMapping("/passo-4")
    public String saveUserPhone(
            HttpSession httpSession,
            @Validated(IndividualDTO.RequestUserPhoneInfoGroupValidation.class) IndividualDTO dto,
            BindingResult errors,
            RedirectAttributes redirectAttributes,
            Model model
    ) {

        if (errors.hasErrors()) {
            return this.userRegistrationErrorForwarding("4", dto, model, errors);
        }

        Optional<Individual> oUser = individualService.findByPhoneNumber(dto.getPhoneNumber());

        if (oUser.isPresent()) {
            errors.rejectValue("phoneNumber", "error.dto", "Telefone já cadastrado! Por favor, insira um número de telefone não cadastrado.");
        }

        if (errors.hasErrors()) {
            return this.userRegistrationErrorForwarding("4", dto, model, errors);
        }

        ///// Enviar código de autenticação para telefone.

        IndividualDTO sessionDTO = wizardSessionUtil.getWizardState(httpSession, IndividualDTO.class, WizardSessionUtil.KEY_WIZARD_USER);
        sessionDTO.setPhoneNumber(dto.getPhoneNumber());

        return "redirect:/cadastrar-se?passo=5";
    }

    @PostMapping("/passo-5")
    public String saveUserPhoneCode(
            HttpSession httpSession,
            @Validated(UserCodeDTO.RequestUserCodeInfoGroupValidation.class) UserCodeDTO dto,
            BindingResult errors,
            RedirectAttributes redirectAttributes,
            Model model
    ) {

        if (errors.hasErrors()) {
            return this.userCodeErrorForwarding("5", dto, model, errors);
        }

        IndividualDTO sessionDTO = wizardSessionUtil.getWizardState(httpSession, IndividualDTO.class, WizardSessionUtil.KEY_WIZARD_USER);
        Optional<UserCode> oUserCode = userCodeService.findByEmail(sessionDTO.getEmail());

        if (!oUserCode.isPresent()) {
            errors.rejectValue("code", "error.dto", "Código inválido! Por favor, insira o código de autenticação.");
        }

        if (errors.hasErrors()) {
            return this.userCodeErrorForwarding("5", dto, model, errors);
        }

        if (!dto.getCode().equals(oUserCode.get().getCode())) {
            errors.rejectValue("code", "error.dto", "Código inválido! Por favor, insira o código de autenticação.");
        }

        if (errors.hasErrors()) {
            return this.userCodeErrorForwarding("5", dto, model, errors);
        }

        sessionDTO.setPhoneVerified(true);

        redirectAttributes.addFlashAttribute("msg", "Telefone verificado com sucesso!");

        return "redirect:/cadastrar-se?passo=6";
    }

    @PostMapping("/passo-6")
    public String saveUserNameAndCPF(
            HttpSession httpSession,
            @Validated(IndividualDTO.RequestUserNameAndCPFInfoGroupValidation.class) IndividualDTO dto,
            BindingResult errors,
            RedirectAttributes redirectAttributes,
            Model model
    ) {

        if (errors.hasErrors()) {
            return this.userRegistrationErrorForwarding("6", dto, model, errors);
        }

        Optional<Individual> oUser = individualService.findByCpf(dto.getCpf());

        if (oUser.isPresent()) {
            errors.rejectValue("cpf", "error.dto", "CPF já cadastrado! Por favor, insira um CPF não cadastrado.");
        }

        if (errors.hasErrors()) {
            return this.userRegistrationErrorForwarding("6", dto, model, errors);
        }

        IndividualDTO sessionDTO = wizardSessionUtil.getWizardState(httpSession, IndividualDTO.class, WizardSessionUtil.KEY_WIZARD_USER);
        sessionDTO.setName(dto.getName());
        sessionDTO.setCpf(dto.getCpf());

        return "redirect:/cadastrar-se?passo=7";
    }

    @PostMapping("/passo-7")
    public String saveUserAddress(
            HttpSession httpSession,
            @Validated(AddressDTO.RequestUserAddressInfoGroupValidation.class) AddressDTO dto,
            BindingResult errors,
            RedirectAttributes redirectAttributes,
            Model model
    ) {

        if (errors.hasErrors()) {
            return this.userAddressRegistrationErrorForwarding("7", dto, model, errors);
        }

        Optional<City> oCity = cityService.findByName(dto.getCity());

        if (!oCity.isPresent()) {
            errors.rejectValue("city", "error.dto", "Cidade não cadastrada! Por favor, insira uma cidade cadastrada.");
        }

        if (errors.hasErrors()) {
            return this.userAddressRegistrationErrorForwarding("7", dto, model, errors);
        }

        CityMidDTO cityMidDTO = cityMapper.toMidDto(oCity.get());

        AddressFullDTO addressFullDTO = new AddressFullDTO();
        addressFullDTO.setStreet(dto.getStreet());
        addressFullDTO.setNumber(dto.getNumber());
        addressFullDTO.setPostalCode(dto.getPostalCode());
        addressFullDTO.setNeighborhood(dto.getNeighborhood());
        addressFullDTO.setCity(cityMidDTO);

        IndividualDTO sessionDTO = wizardSessionUtil.getWizardState(httpSession, IndividualDTO.class, WizardSessionUtil.KEY_WIZARD_USER);
        sessionDTO.setAddress(addressFullDTO);
        sessionDTO.setProfileVerified(true);

        return "redirect:/cadastrar-se?passo=8";
    }

    @PostMapping("/passo-8")
    public String saveUser(
            HttpSession httpSession,
            IndividualDTO dto,
            BindingResult errors,
            Model model,
            RedirectAttributes redirectAttributes,
            SessionStatus status
    ) {

        IndividualDTO sessionDTO = wizardSessionUtil.getWizardState(httpSession, IndividualDTO.class, WizardSessionUtil.KEY_WIZARD_USER);

        validator.validate(sessionDTO, errors, new Class[]{
                IndividualDTO.RequestUserEmailInfoGroupValidation.class,
                IndividualDTO.RequestUserPasswordInfoGroupValidation.class,
                IndividualDTO.RequestUserPhoneInfoGroupValidation.class,
                IndividualDTO.RequestUserNameAndCPFInfoGroupValidation.class,
                AddressDTO.RequestUserAddressInfoGroupValidation.class
        });

        if (errors.hasErrors()) {
            return this.userRegistrationErrorForwarding("8", dto, model, errors);
        }

        Individual user = individualMapper.toEntity(sessionDTO);
        individualService.save(user);

        redirectAttributes.addFlashAttribute("msg", "Usuário cadastrado com sucesso! Realize o login no Servicebook!");
        status.setComplete();

        return "redirect:/entrar";
    }

}