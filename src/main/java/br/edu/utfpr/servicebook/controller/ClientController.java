package br.edu.utfpr.servicebook.controller;

import br.edu.utfpr.servicebook.model.dto.JobRequestDTO;
import br.edu.utfpr.servicebook.model.entity.JobRequest;
import br.edu.utfpr.servicebook.model.mapper.ClientMapper;
import br.edu.utfpr.servicebook.model.mapper.JobRequestMapper;
import br.edu.utfpr.servicebook.service.*;
import br.edu.utfpr.servicebook.util.CurrentUserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import br.edu.utfpr.servicebook.model.entity.Client;
import org.springframework.web.servlet.ModelAndView;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestMapping("/minha-conta/requisicoes")
@Controller
public class ClientController {

    public static final Logger log =
            LoggerFactory.getLogger(ClientController.class);

    @Autowired
    private ClientService clientService;

    @Autowired
    private ClientMapper clientMapper;

    @Autowired
    private JobRequestService jobRequestService;

    @Autowired
    private JobRequestMapper jobRequestMapper;

    @GetMapping
    public ModelAndView show() {
        ModelAndView mv = new ModelAndView("client/my-requests");

        Optional<Client> client =  clientService.findById(CurrentUserUtil.getCurrentUserId());
        List<JobRequest> jobs = jobRequestService.findByClient(client.get());
        List<JobRequestDTO> jobRequestDTOs = jobs.stream()
                .map(s -> jobRequestMapper.toDto(s))
                .collect(Collectors.toList());
        mv.addObject("jobRequest", jobRequestDTOs);

        return mv;
    }

}