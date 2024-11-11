package tn.esprit.artifact.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.artifact.dto.UserDTO;
import tn.esprit.artifact.entity.JobPosition;
import tn.esprit.artifact.service.IJobPositionService;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class JobPositionController {
    @Autowired
    IJobPositionService jobpositionService;

    @PostMapping("/jobposition")
    public ResponseEntity<JobPosition> addJobPosition(@RequestBody JobPosition jobposition) {
        JobPosition addedJobPosition = jobpositionService.createJobPosition(jobposition);
        return new ResponseEntity<>(addedJobPosition, HttpStatus.CREATED);
    }

    @GetMapping("/jobposition/{jobpositionId}")
    public ResponseEntity<JobPosition> showJobPositionByid(@PathVariable Long jobpositionId) {
        JobPosition jobposition = jobpositionService.getJobPositionById(jobpositionId);
        if (jobposition != null) {
            return ResponseEntity.ok(jobposition);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/jobposition")
    public ResponseEntity<List<JobPosition>> getAllJobPositions() {
        List<JobPosition> jobpositions = jobpositionService.getAllJobPositions();
        return ResponseEntity.ok(jobpositions);
    }

    @PutMapping("/jobposition/{jobpositionId}")
    public ResponseEntity<JobPosition> updateJobPosition(@PathVariable("jobpositionId") Long jobpositionId, @RequestBody JobPosition updatedJobPosition) {
        try {
            JobPosition updated = jobpositionService.updateJobPosition(jobpositionId, updatedJobPosition);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/jobposition/{jobpositionId}")
    public ResponseEntity<Void> deleteJobPosition(@PathVariable Long jobpositionId) {
        try {
          jobpositionService.deleteJobPosition(jobpositionId);

            // Create the response message
            String message = "JobPosition with ID " + jobpositionId + " deleted successfully.";

            // Create the response headers
            HttpHeaders headers = new HttpHeaders();
            headers.add("Message", message);
            // Return the response entity with the response object and status OK
            return new ResponseEntity<>(headers, HttpStatus.OK);
        }

        catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }
    /*@GetMapping("/user/jobposition/{userId}")
    public ResponseEntity<JobPosition> getJobPositionByUserId(@PathVariable Long userId) {
        JobPosition jobPosition = jobpositionService.getJobPositionFromUserId(userId);

        if (jobPosition == null) {
            // Création d'une instance de JobPosition avec une valeur par défaut
            JobPosition defaultPosition = new JobPosition();
            defaultPosition.setNom("N/A"); // Valeur par défaut pour le nom du poste
            // Vous pouvez également définir d'autres attributs par défaut si nécessaire

            return ResponseEntity.ok(defaultPosition); // Retourne 200 avec la position par défaut
        }

        return ResponseEntity.ok(jobPosition); // Retourne 200 avec les données de la position trouvée
    }*/

    @PostMapping("/jobposition/users/{posteId}")
    public Set<UserDTO> getUsersForJobPosition(@PathVariable Long posteId) {
        return jobpositionService.getUsersForJobPosition(posteId);
    }
}
