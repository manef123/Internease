package tn.esprit.artifact.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tn.esprit.artifact.dto.CompetenceDTO;
import tn.esprit.artifact.dto.EvaluationDTO;
import tn.esprit.artifact.dto.UserDTO;

import tn.esprit.artifact.entity.JobPosition;
import tn.esprit.artifact.repository.JobPositionRepository;

import java.io.Console;
import java.util.*;

@Service
@AllArgsConstructor
@Slf4j
public class JobPositionIMPL implements IJobPositionService{
    @Autowired
    private JobPositionRepository jobpositionRepository;

    /*@Autowired
    private EvaluationRepository evaluationRepository;*/

    @Autowired
    private ICompetenceService competenceService;

    @Autowired
    private IEvaluationService evaluationService;

    @Autowired
    private final IUserServiceEq userService;


// Spring's RestTemplate to make REST calls

    private static final String USER_SERVICE_URL = "http://localhost:8081/api/user"; // Replace with Eureka URL if using service discovery



    @Override
    public JobPosition createJobPosition(JobPosition jobposition) {
        return jobpositionRepository.save(jobposition);
    }

    @Override
    public JobPosition updateJobPosition(Long id, JobPosition jobposition) {
        Optional<JobPosition> optionalJobPosition = jobpositionRepository.findById(id);

        if (optionalJobPosition.isPresent()) {
            JobPosition existingJobPosition = optionalJobPosition.get();

            // Update fields only if they are not null
            if (jobposition.getDescription() != null) {
                existingJobPosition.setDescription(jobposition.getDescription());
            }else {
                existingJobPosition.setDescription(null); // Or handle as appropriate if null should be explicitly set
            }
            if (jobposition.getNom() != null) {
                existingJobPosition.setNom(jobposition.getNom());
            }else {
                existingJobPosition.setNom(null); // Or handle as appropriate if null should be explicitly set
            }

            if (jobposition.getCompetencesRequises() != null) {
                existingJobPosition.setCompetencesRequises(jobposition.getCompetencesRequises());
            } else {
                existingJobPosition.setCompetencesRequises(new HashSet<>()); // Or handle as appropriate if null should be explicitly set
            }


            // Save the updated job position entity
            return jobpositionRepository.save(existingJobPosition);
        } else {
            // Handle the case where the job position with the given ID is not found
            throw new IllegalArgumentException("JobPosition not found with ID: " + id);
        }
    }


    @Override
    public List<JobPosition> getAllJobPositions() {
        Iterable<JobPosition> jobpositionsIterable = jobpositionRepository.findAll();
        List<JobPosition> jobpositionsList = new ArrayList<>();
        for (JobPosition jobposition : jobpositionsIterable) {
            jobpositionsList.add(jobposition);
        }
        return jobpositionsList;
    }

    @Override
    public JobPosition getJobPositionById(Long id) {
        return jobpositionRepository.findById(id).orElse(null);
    }

    @Override
    public JobPosition deleteJobPosition(Long id) {
        try{
            Optional<JobPosition> optionalJobPosition = jobpositionRepository.findById(id);



            // If the jobposition exists, retrieve it
            JobPosition jobpositionToDelete = optionalJobPosition.get();

            // Delete the jobposition by its ID
            jobpositionRepository.deleteById(id);

            // Return the deleted stage
            return jobpositionToDelete;
        } catch(Exception e) {
            // If the stage does not exist, throw an exception or handle it in any other appropriate way
            throw new IllegalArgumentException("jobposition not found");
        }

    }



   /* @Override
    public JobPosition getJobPositionFromUserId(Long userId) {
        System.out.println(userId);
        List<Evaluation> evaluations = evaluationRepository.findByUserIdAndEval(userId, EvaluationType.FORUSER);
        System.out.println(evaluations);

        if (evaluations.isEmpty()) {
            return null;
        }

        JobPosition jobPositionToReturn = new JobPosition();
        Set<Competence> filteredCompetences = new HashSet<>();

        double totalNotes = 0.0;
        int noteCount = 0;

        for (Evaluation evaluation : evaluations) {
            Competence competence = evaluation.getCompetence();
            if (competence != null) {
                filteredCompetences.add(competence);

                if (jobPositionToReturn.getId() == null) {
                    JobPosition originalJobPosition = competence.getJobPosition();
                    if (originalJobPosition != null) {
                        jobPositionToReturn.setId(originalJobPosition.getId());
                        jobPositionToReturn.setNom(originalJobPosition.getNom());
                        jobPositionToReturn.setDescription(originalJobPosition.getDescription());
                    }
                }

                Double note = evaluation.getNote();
                if (note != null) {
                    totalNotes += note;
                    noteCount++;
                }
            }
        }

        jobPositionToReturn.setCompetencesRequises(filteredCompetences);

        double averageNote = (noteCount > 0) ? totalNotes / noteCount : 0.00;

        // Update UserDTO in MySQL project
        String updateUrl = "http://localhost:8085/api/user/" + userId + "/note?notePoste=" + averageNote; // Replace with Eureka if needed
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.put(updateUrl, null);

        return jobPositionToReturn;
    }*/

    public Set<UserDTO> getUsersForJobPosition(Long posteId) {
        // Fetch the JobPosition
        JobPosition jobPosition = jobpositionRepository.findById(posteId)
                .orElseThrow(() -> new RuntimeException("JobPosition not found"));

        Set<Long> competences = jobPosition.getCompetencesRequises(); // Assuming you are using Competence objects
        Set<UserDTO> users = new HashSet<>();

        // Debugging
        System.out.println("JobPosition: " + jobPosition.getNom());
        System.out.println("Competences: " + competences);

        // Iterate through the competences
        for (Long competence : competences) {
            // Fetch the CompetenceDTO using Feign Client (or service call)
            CompetenceDTO competenceDTO = competenceService.getCompetenceById(competence);
            System.out.println("CompetenceDTO: " + competenceDTO.getNom());

            // Iterate through evaluations for each competence
            for (Long evaluationId : competenceDTO.getEvaluations()) {
                // Fetch evaluation details
                EvaluationDTO evaluation = evaluationService.getEvaluationById(evaluationId);
                System.out.println("EvaluationDTO: " + evaluation);

                // Fetch user using userId from the evaluation
                Long userId = evaluation.getUserId();
                UserDTO user = userService.getUserById(userId);
                System.out.println("UserDTO: " + user);

                // If user is not null, add to the set
                if (user != null) {
                    users.add(user);
                }
            }
        }

        // Return the set of users
        return users;
    }


}
