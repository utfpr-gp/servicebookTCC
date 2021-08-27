package br.edu.utfpr.servicebook.model.mapper;
import br.edu.utfpr.servicebook.model.dto.JobCandidateDTO;
import br.edu.utfpr.servicebook.model.entity.JobCandidate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JobCandidateMapper {
    @Autowired
    private ModelMapper mapper;

    public JobCandidateDTO toDto(JobCandidate entity){
        JobCandidateDTO dto = mapper.map(entity, JobCandidateDTO.class);
        return dto;
    }

    public JobCandidate toEntity(JobCandidateDTO dto){
        JobCandidate entity = mapper.map(dto, JobCandidate.class);
        return entity;
    }
}
