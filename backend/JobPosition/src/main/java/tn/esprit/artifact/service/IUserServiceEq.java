package tn.esprit.artifact.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import tn.esprit.artifact.dto.UserDTO;

import java.util.List;

@FeignClient(name = "user")
public interface IUserServiceEq {

    @GetMapping("/api/user")
    List<UserDTO> getAllUsers();

    @GetMapping("/api/user/{userId}")
    UserDTO getUserById(@PathVariable("userId") Long userId);
}
