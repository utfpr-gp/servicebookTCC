package br.edu.utfpr.servicebook.service;

import br.edu.utfpr.servicebook.model.entity.Professional;
import br.edu.utfpr.servicebook.model.entity.ProfessionalExpertise;
import br.edu.utfpr.servicebook.model.repository.ProfessionalExpertiseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfessionalExpertiseService {

    @Autowired
    private ProfessionalExpertiseRepository professionalExpertiseRepository;

    public List<ProfessionalExpertise> findByProfessional(Professional professional) {
        return this.professionalExpertiseRepository.findByProfessional(professional);
    }

}