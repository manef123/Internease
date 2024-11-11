package tn.esprit.artifact.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ServiceEqDTO {
    private Long id;
    private String nom;
    private Long chefEquipe; // Consider if you need the full UserDTO for this
    private Set<Long> employes; // You may want to change this to a List<UserDTO> if you need more info about users


}
