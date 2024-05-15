package DTOs;

import entities.Car;
import entities.User;

public class PapersToDisplay {

    public final byte[] dniCara;
    public final byte[] dniContraCara;
    public final byte[] registration;
    public final UserDTO user;
    public final CarDTO car;

    public PapersToDisplay(User user, Car car, byte[] dniCara, byte[] dniContraCara, byte[] registration) {
        this.user = new UserDTO(user);
        this.car = new CarDTO(car);
        this.dniCara = dniCara;
        this.dniContraCara = dniContraCara;
        this.registration = registration;
    }
}