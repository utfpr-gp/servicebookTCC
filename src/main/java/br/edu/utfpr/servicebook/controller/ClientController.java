package br.edu.utfpr.servicebook.controller;

import br.edu.utfpr.servicebook.exception.InvalidParamsException;
import br.edu.utfpr.servicebook.model.dto.*;
import br.edu.utfpr.servicebook.model.entity.*;
import br.edu.utfpr.servicebook.model.mapper.*;
import br.edu.utfpr.servicebook.service.*;
import br.edu.utfpr.servicebook.util.CurrentUserUtil;
import br.edu.utfpr.servicebook.util.pagination.PaginationDTO;
import br.edu.utfpr.servicebook.util.pagination.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestMapping("/minha-conta/meus-pedidos")
@Controller
public class ClientController {

    public static final Logger log =
            LoggerFactory.getLogger(ClientController.class);

    @Autowired
    private ClientService clientService;

    @Autowired
    private ClientMapper clientMapper;

    @Autowired
    private JobCandidateService jobCandidateService;

    @Autowired
    private JobRequestService jobRequestService;

    @Autowired
    private JobCandidateMapper jobCandidateMapper;

    @Autowired
    private JobRequestMapper jobRequestMapper;

    @Autowired
    private ExpertiseService expertiseService;

    @Autowired
    private ExpertiseMapper expertiseMapper;

    @Autowired
    private JobContractedService jobContractedService;

    @Autowired
    private JobContractedMapper jobContractedMapper;

    @GetMapping
    public ModelAndView show() throws Exception {
        ModelAndView mv = new ModelAndView("client/my-requests");

        Optional<Client> client = Optional.ofNullable(clientService.findByEmailAddress(CurrentUserUtil.getCurrentClientUser()));

        if (!client.isPresent()) {
            throw new Exception("Usuário não autenticado! Por favor, realize sua autenticação no sistema.");
        }

        ClientDTO clientDTO = clientMapper.toDto(client.get());
        mv.addObject("client", clientDTO);

        List<JobRequest> jobRequests = jobRequestService.findByClientOrderByDateCreatedDesc(client.get());

        List<JobRequestMinDTO> jobRequestDTOs = jobRequests.stream()
                .map(job -> {
                    Optional <Long> amountOfCandidates = jobCandidateService.countByJobRequest(job);

                    if(amountOfCandidates.isPresent()){
                        return jobRequestMapper.toMinDto(job, amountOfCandidates);
                    }
                    return jobRequestMapper.toMinDto(job, Optional.ofNullable(0L));
                })
                .collect(Collectors.toList());

        mv.addObject("jobRequests", jobRequestDTOs);

        return mv;
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) throws IOException {

        Optional<Client> client = Optional.ofNullable(clientService.findByEmailAddress(CurrentUserUtil.getCurrentClientUser()));

        if (!client.isPresent()) {
            throw new IOException("Usuário não autenticado! Por favor, realize sua autenticação no sistema.");
        }

        Optional<JobRequest> jobRequest = this.jobRequestService.findById(id);

        if(!jobRequest.isPresent()) {
            throw new EntityNotFoundException("Solicitação não foi encontrada pelo id informado.");
        }

        Long jobRequestClientId = jobRequest.get().getClient().getId();
        Long clientId = client.get().getId();

        if(jobRequestClientId != clientId){
            throw new EntityNotFoundException("Você não ter permissão para deletar essa solicitação.");
        }

        this.jobRequestService.delete(id);
        redirectAttributes.addFlashAttribute("msg", "Solicitação deletada!");

        return "redirect:/minha-conta/meus-pedidos";

    }

    @GetMapping("/{id}")
    public ModelAndView showDetailsRequest(@PathVariable Optional<Long> id) throws Exception {
        ModelAndView mv = new ModelAndView("client/details-request");

        Optional<Client> client = Optional.ofNullable(clientService.findByEmailAddress(CurrentUserUtil.getCurrentClientUser()));

        if (!client.isPresent()) {
            throw new Exception("Usuário não autenticado! Por favor, realize sua autenticação no sistema.");
        }
        ClientDTO clientDTO = clientMapper.toDto(client.get());
        mv.addObject("client", clientDTO);

        Optional<JobRequest> job = jobRequestService.findById(id.get());

        if (!job.isPresent()) {
            throw new EntityNotFoundException("Solicitação de serviço não encontrado. Por favor, tente novamente.");
        }

        JobRequestFullDTO jobDTO = jobRequestMapper.toFullDto(job.get());
        mv.addObject("jobRequest", jobDTO);

        Long expertiseId = job.get().getExpertise().getId();

        Optional<Expertise> expertise = expertiseService.findById(expertiseId);

        if (!expertise.isPresent()) {
            throw new EntityNotFoundException("A especialidade não foi encontrada. Por favor, tente novamente.");
        }

        ExpertiseMinDTO expertiseDTO = expertiseMapper.toMinDto(expertise.get());
        mv.addObject("expertise", expertiseDTO);

        List<JobCandidate> jobCandidates = jobCandidateService.findByJobRequest(job.get());

        List<JobCandidateDTO> jobCandidatesDTOs = jobCandidates.stream()
                .map(candidate -> jobCandidateMapper.toDto(candidate))
                .collect(Collectors.toList());

        mv.addObject("candidates", jobCandidatesDTOs);


        return mv;
    }

    @GetMapping("/disponiveis")
    public ModelAndView showAvailableJobs(
            HttpServletRequest request,
            @RequestParam(value = "pag", defaultValue = "1") int page,
            @RequestParam(value = "siz", defaultValue = "3") int size,
            @RequestParam(value = "ord", defaultValue = "id") String order,
            @RequestParam(value = "dir", defaultValue = "ASC") String direction
    ) throws Exception {

        Optional<Client> client = Optional.ofNullable(clientService.findByEmailAddress(CurrentUserUtil.getCurrentClientUser()));

        if (!client.isPresent()) {
            throw new Exception("Usuário não autenticado! Por favor, realize sua autenticação no sistema.");
        }

        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by("dateExpired").ascending());
        Page<JobRequest> jobRequestPage = null;
        List<JobRequestFullDTO> jobRequestFullDTOs = null;

        jobRequestPage = jobRequestService.findByStatusAndClient(JobRequest.Status.AVAILABLE, client.get(), pageRequest);

        jobRequestFullDTOs = jobRequestPage.stream()
                .map(jobRequest -> {
                    Optional<Long> totalCandidates = jobCandidateService.countByJobRequest(jobRequest);

                    if (totalCandidates.isPresent()) {
                        return jobRequestMapper.toFullDto(jobRequest, totalCandidates);
                    }

                    return jobRequestMapper.toFullDto(jobRequest, Optional.ofNullable(0L));
                }).collect(Collectors.toList());

        PaginationDTO paginationDTO = PaginationUtil.getPaginationDTO(jobRequestPage, "/minha-conta/meus-pedidos/disponiveis");

        ModelAndView mv = new ModelAndView("client/job-request/tabs/available-jobs-report");
        mv.addObject("pagination", paginationDTO);
        mv.addObject("jobs", jobRequestFullDTOs);

        return mv;
    }

    @GetMapping("/para-orcamento")
    public ModelAndView showDisputedJobs(
            HttpServletRequest request,
            @RequestParam(value = "pag", defaultValue = "1") int page,
            @RequestParam(value = "siz", defaultValue = "3") int size,
            @RequestParam(value = "ord", defaultValue = "id") String order,
            @RequestParam(value = "dir", defaultValue = "ASC") String direction
    ) throws Exception {

        Optional<Client> client = Optional.ofNullable(clientService.findByEmailAddress(CurrentUserUtil.getCurrentClientUser()));

        if (!client.isPresent()) {
            throw new Exception("Usuário não autenticado! Por favor, realize sua autenticação no sistema.");
        }

        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by("date").descending());
        Page<JobCandidate> jobCandidatePage = null;
        List<JobCandidateMinDTO> jobCandidateDTOs = null;

        jobCandidatePage = jobCandidateService.findByJobRequest_StatusAndJobRequest_Client(JobRequest.Status.BUDGET, client.get(),pageRequest);

        jobCandidateDTOs = jobCandidatePage.stream()
                .map(jobCandidate -> {
                    Optional<Long> totalCandidates = jobCandidateService.countByJobRequest(jobCandidate.getJobRequest());

                    if (totalCandidates.isPresent()) {
                        return jobCandidateMapper.toMinDto(jobCandidate, totalCandidates);
                    }

                    return jobCandidateMapper.toMinDto(jobCandidate, Optional.ofNullable(0L));
                }).collect(Collectors.toList());

        PaginationDTO paginationDTO = PaginationUtil.getPaginationDTO(jobCandidatePage, "/minha-conta/meus-pedidos/em-orcamento");

        ModelAndView mv = new ModelAndView("client/job-request/tabs/disputed-jobs-report");
        mv.addObject("pagination", paginationDTO);
        mv.addObject("jobs", jobCandidateDTOs);

        return mv;
    }

    @GetMapping("/para-fazer")
    public ModelAndView showTodoJobs(
            HttpServletRequest request,
            @RequestParam(value = "pag", defaultValue = "1") int page,
            @RequestParam(value = "siz", defaultValue = "3") int size,
            @RequestParam(value = "ord", defaultValue = "id") String order,
            @RequestParam(value = "dir", defaultValue = "ASC") String direction
    ) throws Exception {

        Optional<Client> client = Optional.ofNullable(clientService.findByEmailAddress(CurrentUserUtil.getCurrentClientUser()));

        if (!client.isPresent()) {
            throw new Exception("Usuário não autenticado! Por favor, realize sua autenticação no sistema.");
        }

        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by("date").descending());
        Page<JobCandidate> jobCandidatePage = null;
        List<JobCandidateMinDTO> jobCandidateDTOs = null;

         jobCandidatePage = jobCandidateService.findByJobRequest_StatusAndJobRequest_Client(JobRequest.Status.TO_DO, client.get(),pageRequest);

        jobCandidateDTOs = jobCandidatePage.stream()
                .map(jobCandidate -> {
                    Optional<Long> totalCandidates = jobCandidateService.countByJobRequest(jobCandidate.getJobRequest());

                    if (totalCandidates.isPresent()) {
                        return jobCandidateMapper.toMinDto(jobCandidate, totalCandidates);
                    }

                    return jobCandidateMapper.toMinDto(jobCandidate, Optional.ofNullable(0L));
                }).collect(Collectors.toList());

        PaginationDTO paginationDTO = PaginationUtil.getPaginationDTO(jobCandidatePage, "/minha-conta/meus-pedidos/para-fazer");

        ModelAndView mv = new ModelAndView("client/job-request/tabs/todo-jobs-report");
        mv.addObject("pagination", paginationDTO);
        mv.addObject("jobs", jobCandidateDTOs);

        return mv;
    }

    @GetMapping("/executados")
    public ModelAndView showJobsPerformed(
            HttpServletRequest request,
            @RequestParam(value = "pag", defaultValue = "1") int page,
            @RequestParam(value = "siz", defaultValue = "3") int size,
            @RequestParam(value = "ord", defaultValue = "id") String order,
            @RequestParam(value = "dir", defaultValue = "ASC") String direction
    ) throws Exception {

        Optional<Client> client = Optional.ofNullable(clientService.findByEmailAddress(CurrentUserUtil.getCurrentClientUser()));

        if (!client.isPresent()) {
            throw new Exception("Usuário não autenticado! Por favor, realize sua autenticação no sistema.");
        }

        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by("id").descending());
        Page<JobContracted> jobContractedPage = null;
        List<JobContractedFullDTO> jobContractedDTOs = null;

        jobContractedPage = jobContractedService.findByJobRequest_StatusAndJobRequest_Client(JobRequest.Status.CLOSED, client.get(), pageRequest);

        jobContractedDTOs = jobContractedPage.stream()
                .map(jobContracted -> {
                    Optional<Long> totalCandidates = jobCandidateService.countByJobRequest(jobContracted.getJobRequest());

                    if (totalCandidates.isPresent()) {
                        return jobContractedMapper.toFullDto(jobContracted, totalCandidates);
                    }

                    return jobContractedMapper.toFullDto(jobContracted, Optional.ofNullable(0L));
                })
                .collect(Collectors.toList());

        PaginationDTO paginationDTO = PaginationUtil.getPaginationDTO(jobContractedPage, "/minha-conta/meus-pedidos/executados");

        ModelAndView mv = new ModelAndView("client/job-request/tabs/executed-jobs-report");

        mv.addObject("pagination", paginationDTO);
        mv.addObject("jobs", jobContractedDTOs);

        return mv;
    }

//    public ModelAndView save(@Validated JobRequestDTO dto, Errors errors, RedirectAttributes redirectAttributes) {
}


