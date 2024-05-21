package DTOs;

import entities.Car;
import entities.User;

public class PapersToDisplay {

    public final String dniCara;
    public final String dniContraCara;
    public final String registration;
    public final UserDTO user;
    public final CarDTO car;

    public PapersToDisplay(User user, Car car, String dniCara, String dniContraCara, String registration) {
        this.user = new UserDTO(user);
        this.car = new CarDTO(car);
        this.dniCara = dniCara;
        this.dniContraCara = dniContraCara;
        this.registration = registration;
    }
}