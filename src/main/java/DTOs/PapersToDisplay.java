package DTOs;

import entities.Car;
import entities.User;

public class PapersToDisplay {

    public final String dniFront;
    public final String dniBack;
    public final String registration;
    public final UserDTO user;
    public final CarDTO car;

    public PapersToDisplay(User user, Car car, String dniCara, String dniContraCara, String registration) {
        this.user = new UserDTO(user);
        this.car = new CarDTO(car);
        this.dniFront = dniCara;
        this.dniBack = dniContraCara;
        this.registration = registration;
    }
}