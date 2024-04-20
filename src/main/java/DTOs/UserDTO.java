package DTOs;

import entities.User;

import java.util.List;
import java.util.Map;

public class UserDTO {
    private final List<Integer> familias;
    private String email;
    private String username;
    private final Map<Integer, String> familiasMapa;
    public UserDTO(User user) {
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.familias = user.getFamiliasId();
        this.familiasMapa = user.getFamiliasMap();
    }
}

