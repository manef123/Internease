package tn.esprit.artifact.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import tn.esprit.artifact.dto.EvaluationDTO;

import java.util.List;

@FeignClient(name = "evaluation")
public interface IEvaluationServiceUser {

    @GetMapping("/api/evaluation")
    List<EvaluationDTO> getAllEvaluations();

    @GetMapping("/api/evaluation/{evaluationId}")
    EvaluationDTO getEvaluationById(@PathVariable("evaluationId") Long id);

    @GetMapping("/api/user/{userId}/type/{evalType}")
    List<EvaluationDTO> getEvaluationsByUserIdAndType(@PathVariable("userId") Long userId, @PathVariable("evalType") String evalType);


}
