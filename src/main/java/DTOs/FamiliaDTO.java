package DTOs;

import entities.Familia;

public class FamiliaDTO {
    private String surname;

    public FamiliaDTO(Familia familia) {
        this.surname = familia.getApellido();
    }

    public String getSurname() {
        return surname;
    }
}