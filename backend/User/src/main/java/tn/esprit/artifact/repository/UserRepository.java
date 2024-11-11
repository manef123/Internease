package tn.esprit.artifact.repository;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.artifact.entity.User;
import tn.esprit.artifact.entity.UserType;

import java.util.List;
import java.util.Map;
@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findUserByIdentifiantUser(String identifiant);
    List<User> findUsersByserviceEq(Long serviceEqId);

    List<User> findByType(UserType type);

    @Query("SELECT u FROM User u WHERE u.type = :type AND u.serviceEq IS NULL")
    List<User> findUsersWithoutServiceEq(@Param("type") UserType type);

}
