package DTOs;

import entities.User;

import java.util.List;
import java.util.Map;

public class UserDTO {
    private final List<Integer> familias;
    public String email;
    private String username;
    private Double domicilioLongitude;
    private Double domicilioLatitude;
    private String surname;
    private String name;
    private String userId;
    private final Map<Integer, String> familiasMapa;

    public UserDTO(User user) {
        this.email = user.getEmail();
        this.userId = user.getUserID();
        this.username = user.getUsername();
        this.domicilioLongitude = user.getDomicilioLongitude();
        this.domicilioLatitude = user.getDomicilioLatitude();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.familias = user.getFamiliasId();
        this.familiasMapa = user.getFamiliasMap();
    }
}

