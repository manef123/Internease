package tn.esprit.artifact.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.artifact.entity.Formation;

import java.util.List;

@Repository
public interface FormationRepository extends JpaRepository<Formation,Long> {
    @Query(value = "SELECT * FROM formation f JOIN formation_users u ON f.id = u.formation_id WHERE u.users = :userId", nativeQuery = true)
    List<Formation> findFormationsByUserId(@Param("userId") Long userId);


}
