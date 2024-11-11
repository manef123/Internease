package tn.esprit.artifact.dto;

import java.util.Objects;
import java.util.Set;

public class CompetenceDTO {
    private Long id;
    private String nom;
    private String description;
    private Long jobPosition;  // ID of the JobPosition to which this competence belongs
    private Set<Long> evaluations;  // Set of Evaluation IDs related to this competence

    // Getters and setters for each field

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getJobPosition() {
        return jobPosition;
    }

    public void setJobPosition(Long jobPositionId) {
        this.jobPosition = jobPositionId;
    }

    public Set<Long> getEvaluations() {
        return evaluations;
    }

    public void setEvaluations(Set<Long> evaluationIds) {
        this.evaluations = evaluationIds;
    }

    // Override equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CompetenceDTO)) return false;
        CompetenceDTO that = (CompetenceDTO) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
