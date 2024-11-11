package tn.esprit.artifact.dto;

import java.util.Objects;
import java.util.Set;

public class JobPositionDTO {
    private Long id;
    private String nom;
    private String description;
    private Set<Long> competencesRequises;  // Assuming this is a Set of competence IDs or another relevant DTO

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

    public Set<Long> getCompetencesRequises() {
        return competencesRequises;
    }

    public void setCompetencesRequises(Set<Long> competencesRequises) {
        this.competencesRequises = competencesRequises;
    }

    // Override equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JobPositionDTO)) return false;
        JobPositionDTO that = (JobPositionDTO) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
