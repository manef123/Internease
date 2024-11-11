package tn.esprit.artifact.dto;

import java.util.Objects;

public class UserDTO {
    private Long id;
    private String identifiantUser;
    private String email;
    private Long number;
    private String nom;
    private String prenom;
    private String mdp;  // Consider whether to include this for security reasons
    private String type; // If UserType is an enum, you can handle it differently
    private Double notePoste;

    // Getters and setters for each field

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentifiantUser() {
        return identifiantUser;
    }

    public void setIdentifiantUser(String identifiantUser) {
        this.identifiantUser = identifiantUser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getNotePoste() {
        return notePoste;
    }

    public void setNotePoste(Double notePoste) {
        this.notePoste = notePoste;
    }

    // Override equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDTO)) return false;
        UserDTO userDTO = (UserDTO) o;
        return id != null && id.equals(userDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
