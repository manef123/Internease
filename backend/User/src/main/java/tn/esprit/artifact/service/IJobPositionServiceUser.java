package tn.esprit.artifact.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import tn.esprit.artifact.dto.JobPositionDTO;

import java.util.List;

@FeignClient(name = "jobposition")
public interface IJobPositionServiceUser {

    @GetMapping("/jobposition")
    List<JobPositionDTO> getAllJobPositions();

    @GetMapping("/api/jobposition/{jobpositionId}")
    JobPositionDTO getJobPositionById(@PathVariable("jobpositionId") Long id);
}
