package br.edu.utfpr.servicebook.service;

import br.edu.utfpr.servicebook.model.entity.*;
import br.edu.utfpr.servicebook.model.repository.CategoryRepository;
import br.edu.utfpr.servicebook.model.repository.ProfessionalServiceOfferingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class ProfessionalServiceOfferingService {

    @Autowired
    private ProfessionalServiceOfferingRepository professionalServiceOfferingRepository;

    /**
     * Busca uma oferta de serviço de um profissional
     * @param id
     * @return
     */
    public Optional<ProfessionalServiceOffering> findById(Long id) {
        return professionalServiceOfferingRepository.findById(id);
    }

    /**
     * Salva uma oferta de serviço de um profissional
     * @param professionalServiceOffering
     */
    public void save(ProfessionalServiceOffering professionalServiceOffering){
        professionalServiceOfferingRepository.save(professionalServiceOffering);
    }

    /**
     * Deleta uma oferta de serviço de um profissional
     */
    public void delete(ProfessionalServiceOffering professionalServiceOffering) {
        professionalServiceOfferingRepository.delete(professionalServiceOffering);
    }

    /**
     * Busca todas as ofertas de serviços de um profissional
     * @param user
     * @return
     */
    public List<ProfessionalServiceOffering> findProfessionalServiceOfferingByUser(User user){
        return professionalServiceOfferingRepository.findProfessionalServiceOfferingByUser(user);
    }

    public List<ProfessionalServiceOffering> findFirst3ProfessionalServiceOfferingByUserAndType(User user, Enum type){
        return professionalServiceOfferingRepository.findFirst3ByUserAndType(user, type);
    }

    public List<ProfessionalServiceOffering> findProfessionalServiceOfferingByUserAndType(User user, Enum type){
        return professionalServiceOfferingRepository.findByUserAndType(user, type);
    }
    /**
     * Busca todas as ofertas de serviços de um profissional
     * @param id
     * @return
     */
    public List<ProfessionalServiceOffering> findProfessionalServiceOfferingByUserId(Long id){
        return professionalServiceOfferingRepository.findProfessionalServiceOfferingByUserId(id);
    }

    /**
     * Busca todos os serviços de um profissional por serviço cadastrado pelo administrador.
     * @param service
     * @return
     */
    public List<ProfessionalServiceOffering> findProfessionalServiceOfferingByService(Service service){
        return professionalServiceOfferingRepository.findProfessionalServiceOfferingByService(service);
    }

    public Page<ProfessionalServiceOffering> findDistinctByTermIgnoreCaseWithPagination(String searchTerm, Integer page, Integer size){
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return this.professionalServiceOfferingRepository.findDistinctByTermIgnoreCaseWithPagination(searchTerm, pageRequest);
    }

    /**
     * Busca todas os serviços de um profissional por serviço cadastrado pelo administrador.
     */
    public List<ProfessionalServiceOffering> findProfessionalServiceOfferingByServiceId(Long id){
        return professionalServiceOfferingRepository.findProfessionalServiceOfferingByServiceId(id);
    }

    /**
     * Busca todas as ofertas de serviços de um profissional e especialidade
     */
    public List<ProfessionalServiceOffering> findProfessionalServiceOfferingByUserAndExpertise(Long userId, Long expertiseId){
        return professionalServiceOfferingRepository.findProfessionalServiceOfferingByUserAndExpertise(userId, expertiseId);
    }

    /**
     * Busca todas as ofertas de serviços de um profissional e especialidade
     */
    public List<ProfessionalServiceOffering> findProfessionalServiceOfferingByUserAndService_Expertise(User user, Expertise expertise){
        return professionalServiceOfferingRepository.findProfessionalServiceOfferingByUserAndService_Expertise(user, expertise);
    }

    /**
     * Busca o serviço pelo nome.
     * @param name
     * @return
     */
    public Optional<ProfessionalServiceOffering> findProfessionalServiceOfferingByName(String name){
        return professionalServiceOfferingRepository.findProfessionalServiceOfferingByName(name);
    }

    public Page<ProfessionalServiceOffering> findAllIndividualsByService(Service searchTerm, Integer page, Integer size){
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return this.professionalServiceOfferingRepository.findAllIndividualsByService(searchTerm, pageRequest);
    }


    /**
     * Busca o serviço pelo id.
     * @param id
     * @return
     */
    public Optional<ProfessionalServiceOffering> findProfessionalServiceOfferingById(Long id){
        return professionalServiceOfferingRepository.findProfessionalServiceOfferingById(id);
    }

    public List<ProfessionalServiceOffering> findAllByProfessionalServicePackageOfferingUser(User professionalServicePackageOffering){
        return professionalServiceOfferingRepository.findProfessionalServiceOfferingAdItemsByUser(professionalServicePackageOffering);
    }

    public List<ProfessionalServiceOffering> findAll(){
        return professionalServiceOfferingRepository.findDistinctProfessionalServiceOfferings();
    }

    public Optional<ProfessionalServiceOffering> findProfessionalServiceOfferingByExpertiseAAndUser(Long user, Long expertise){
        return professionalServiceOfferingRepository.findProfessionalServiceOfferingByExpertiseAAndUser(user, expertise);
    }

    public List<ProfessionalServiceOffering> findProfessionalServiceOfferingByServiceAndUser(Long service, Long user) {
        return professionalServiceOfferingRepository.findProfessionalServiceOfferingByServiceAndUser(service, user);
    }

    public Optional<ProfessionalServiceOffering> findProfessionalServiceOfferingByIdAndUser(Long id, Long user) {
        return professionalServiceOfferingRepository.findProfessionalServiceOfferingByIdAndUser(id, user);
    }
}
