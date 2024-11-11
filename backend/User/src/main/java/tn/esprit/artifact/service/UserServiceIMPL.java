package tn.esprit.artifact.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.artifact.dto.CompetenceDTO;
import tn.esprit.artifact.dto.EvaluationDTO;
import tn.esprit.artifact.dto.JobPositionDTO;
import tn.esprit.artifact.dto.ServiceEqDTO;
import tn.esprit.artifact.entity.*;
import tn.esprit.artifact.repository.UserRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceIMPL implements IUserService {

    /*@Autowired
    private EvaluationRepository evaluationRepository;*/

   /* @Autowired
    private static final String SERVICE_EQ_URL = "http://localhost:8081/serviceEq/api/serviceEq/";
    @Autowired
    private final RestTemplate restTemplate;*/

    @Autowired
    private ICompetenceServiceUser competenceService;

    @Autowired
    private IEvaluationServiceUser evaluationService;

    @Autowired
    private IServiceEquipeUser serviceEquipeService;

    @Autowired
    private IJobPositionServiceUser serviceJobPosition;


    @Autowired
    private UserRepository userRepository;



    @Override
    public User createUser(User user) {
        user.setNotePoste(0.0);
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long id, User user) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();

            // Update fields only if they are not null

            if (user.getMdp() != null) {
                existingUser.setMdp(user.getMdp());
            }
            if (user.getEmail() != null) {
                existingUser.setEmail(user.getEmail());
            }
            if (user.getNumber() != null) {
                existingUser.setNumber(user.getNumber());
            }
            if (user.getNom() != null) {
                existingUser.setNom(user.getNom());
            }
            if (user.getPrenom() != null) {
                existingUser.setPrenom(user.getPrenom());
            }
            if (user.getType() != null) {
                existingUser.setType(user.getType());
            }

           /* if (user.getEvaluations() != null) {
                existingUser.setEvaluations(user.getEvaluations());
            }else{
                existingUser.setEvaluations(new HashSet<>());
            }
*/
            if (user.getServiceEq() != null) {
                existingUser.setServiceEq(user.getServiceEq());
            }else{
                existingUser.setServiceEq(null);

            }if (user.getFormation() != null) {
                existingUser.setFormation(user.getFormation());
            } else {
                existingUser.setFormation(new HashSet<>()); // Or handle as appropriate if null should be explicitly set
            }

           /* if (user.getChefEquipeService() != null) {
                existingUser.setChefEquipeService(user.getChefEquipeService());
            }else{
                existingUser.setServiceEq(null);

            }*/


            // Save the updated user entity
            return userRepository.save(existingUser);
        } else {
            // Handle the case where the user with the given ID is not found
            throw new IllegalArgumentException("User not found with ID: " + id);
        }
    }


    @Override
    public List<User> getAllUsers() {
        Iterable<User> usersIterable = userRepository.findAll();
        List<User> usersList = new ArrayList<>();
        for (User user : usersIterable) {
            usersList.add(user);
        }
        return usersList;
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User deleteUser(Long id) {
        try{
            Optional<User> optionalUser = userRepository.findById(id);



            // If the user exists, retrieve it
            User userToDelete = optionalUser.get();

            // Delete the user by its ID
            userRepository.deleteById(id);

            // Return the deleted stage
            return userToDelete;
        } catch(Exception e) {
            // If the stage does not exist, throw an exception or handle it in any other appropriate way
            throw new IllegalArgumentException("user not found");
        }

    }

    @Override
    public User login(String identifiant, String password) {
        User user = userRepository.findUserByIdentifiantUser(identifiant);
        if (user != null && user.getMdp().equals(password)) {
            return user;
        }
        return null;
    }

    @Override
    public List<User> findUsersByServiceEq(Long id) {
        List<User> user = userRepository.findUsersByserviceEq(id);
        if (user != null ) {
            return user;
        }
        return null;
    }
    @Override
    public ServiceEqDTO getServiceEqByUserId(Long userId) {
        // Recherchez l'utilisateur par son ID
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        // Check if the user has an associated ServiceEq ID
        Long serviceEqId = user.getServiceEq();
        if (serviceEqId == null) {
            throw new RuntimeException("ServiceEq not assigned to User with ID: " + userId);
        }
        ServiceEqDTO serviceEq = serviceEquipeService.getServiceEqById(serviceEqId);

        // Renvoyer l'objet ServiceEq associé
        return serviceEq;
    }
    @Override
    public List<User> getChefsWithoutServiceEq() {
        List<User> chefs = userRepository.findByType(UserType.CHEF_EQUIPE); // Ensure this method exists in your repository
        List<ServiceEqDTO> serviceEqs = serviceEquipeService.getAllServiceEqs();
        Set<Long> chefIdsWithServiceEq = serviceEqs.stream()
                .map(ServiceEqDTO::getChefEquipe)
                .collect(Collectors.toSet());

        // Filter out chefs who are not in the serviceEqs
        return chefs.stream()
                .filter(chef -> !chefIdsWithServiceEq.contains(chef.getId()))
                .collect(Collectors.toList());


    }
    @Override
    public List<User> getUsersWithoutServiceEq() {
        return userRepository.findUsersWithoutServiceEq(UserType.EMPLOYE);
    }


    @Override
    public JobPositionDTO getJobPositionFromUserId(Long userId) {
        // Retrieve all evaluations of type FORUSER for the given user
        List<EvaluationDTO> evaluations = evaluationService.getEvaluationsByUserIdAndType(userId, "FORUSER");

        if (evaluations.isEmpty()) {
            // Handle the case where no evaluations are found
            return null; // or return new JobPosition();
        }

        // Create a new JobPosition object to return
        JobPositionDTO jobPositionToReturn = new JobPositionDTO();

        // Create a Set to hold all unique competences related to the evaluations
        Set<Long> filteredCompetences = new HashSet<>();

        double totalNotes = 0.0;
        int noteCount = 0;

        // Iterate over all evaluations
        for (EvaluationDTO evaluation : evaluations) {
            Long competence = evaluation.getCompetence();
            if (competence != null) {
                filteredCompetences.add(competence);

                // Set the basic job position details from the first evaluation's job position
                if (jobPositionToReturn.getId() == null) {

                    CompetenceDTO competenceDTO = competenceService.getCompetenceById(competence) ;
                    Long IDoriginalJobPosition = competenceDTO.getJobPosition();
                    JobPositionDTO originalJobPosition = serviceJobPosition.getJobPositionById(IDoriginalJobPosition);
                    if (originalJobPosition != null) {
                        jobPositionToReturn.setId(originalJobPosition.getId());
                        jobPositionToReturn.setNom(originalJobPosition.getNom());
                        jobPositionToReturn.setDescription(originalJobPosition.getDescription());
                    }
                }

                // Accumulate the notes for the competencies
                Double note = evaluation.getNote();
                if (note != null) {
                    totalNotes += note;
                    noteCount++;
                }
            }
        }

        // Set the collected competences to the JobPosition object
        jobPositionToReturn.setCompetencesRequises(filteredCompetences);

        // Calculate the average note
        double averageNote = (noteCount > 0) ? totalNotes / noteCount : 0.00;

        // Assign the calculated average note to user.notePoste
        User user = new User();
        user.setId(evaluations.get(0).getUserId());
        if (user != null) {
            user.setNotePoste(averageNote); // Assuming User has a setNotePoste method
            userRepository.save(user); // Persist the changes to the user
        }

        return jobPositionToReturn;
    }


    @Override
    public void updateNotePoste(Long userId, double notePoste) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setNotePoste(notePoste);
            userRepository.save(user);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    @Override
    @Transactional
    public void deleteServiceEq(Long id) {


        // Step 1: Find all users associated with this ServiceEq ID
        List<User> users = userRepository.findUsersByserviceEq(id);

        // Step 2: Update each user to set serviceEq to null
        for (User user : users) {
            user.setServiceEq(null);
            userRepository.save(user);
        }


    }



}
