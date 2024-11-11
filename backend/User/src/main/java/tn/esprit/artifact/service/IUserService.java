package tn.esprit.artifact.service;

import tn.esprit.artifact.dto.JobPositionDTO;
import tn.esprit.artifact.dto.ServiceEqDTO;
import tn.esprit.artifact.entity.User;

import java.util.List;

public interface IUserService {
    User createUser(User user);

    User updateUser(Long id, User user);

    List<User> getAllUsers();

    User getUserById(Long id);

    User deleteUser(Long id);

     User login(String identifiant, String password);

     List<User> findUsersByServiceEq(Long id);

     ServiceEqDTO getServiceEqByUserId(Long userId);

     List<User> getChefsWithoutServiceEq();

     List<User> getUsersWithoutServiceEq();

     JobPositionDTO getJobPositionFromUserId(Long userId);

     void updateNotePoste(Long userId, double notePoste);

    public void deleteServiceEq(Long id);
}
