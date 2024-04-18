package DTOs;

import entities.User;

import java.util.List;

public class UserDTO {
    private final List<Integer> familias;
    private String email;
    private String username;
    public UserDTO(User user) {
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.familias = user.getFamiliasId();
    }
}

