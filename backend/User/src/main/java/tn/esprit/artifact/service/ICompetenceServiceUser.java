package tn.esprit.artifact.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import tn.esprit.artifact.dto.CompetenceDTO;

import java.util.List;

@FeignClient(name = "competance")
public interface ICompetenceServiceUser {

    @GetMapping("/api/competence")
    List<CompetenceDTO> getAllCompetences();


    @GetMapping("/api/competence/{competenceId}")
    CompetenceDTO getCompetenceById(@PathVariable("competenceId") Long id);
}
