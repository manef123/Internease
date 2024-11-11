package tn.esprit.artifact.dto;

import java.util.Date;

public class EvaluationDTO {

    private Long id;
    private String eval;  // Store enum as String (for better presentation)
    private Long competence;
    private Long user;
    private Double note;
    private Date dateEvalu;

    // Getters and setters for each field

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEval() {
        return eval;
    }

    public void setEval(String eval) {
        this.eval = eval;
    }

    public Long getCompetence() {
        return competence;
    }

    public void setCompetence(Long competence) {
        this.competence = competence;
    }

    public Long getUserId() {
        return user;
    }

    public void setUserId(Long userId) {
        this.user = userId;
    }

    public Double getNote() {
        return note;
    }

    public void setNote(Double note) {
        this.note = note;
    }

    public Date getDateEvalu() {
        return dateEvalu;
    }

    public void setDateEvalu(Date dateEvalu) {
        this.dateEvalu = dateEvalu;
    }
}
