package tn.esprit.artifact.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.artifact.entity.Evaluation;
import tn.esprit.artifact.entity.EvaluationType;

import java.util.List;
import java.util.Optional;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation,Long>{

    List<Evaluation> findByUserAndEval(Long userId, EvaluationType evalType);

    //List<Evaluation> findByUserIdAndEval(Long userId, EvaluationType eval);

    List<Evaluation> findByUserAndCompetenceAndEval(Long userId, Long competenceId, EvaluationType eval);
    List<Evaluation> findByUserAndCompetence(Long userId, Long competenceId);

    void deleteByUser(Long userId);

    @Query("SELECT e FROM Evaluation e WHERE e.user = :userId AND e.competence = :competenceId AND e.eval <> 'FORUSER'")
    List<Evaluation> findAllByUserIdAndCompetenceIdAndEvalNotForUser(@Param("userId") Long userId, @Param("competenceId") Long competenceId);


}
