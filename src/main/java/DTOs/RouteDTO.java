package DTOs;

import entities.Route;

public class RouteDTO {
    private String patente;
    private String username;
    private String kilometraje;
    private String date;
    private String duration;

    public RouteDTO(Route route) {
        this.patente = route.getPatente();
        this.username = route.getUser().getUsername();
        this.kilometraje = route.getKilometraje();
        this.date = route.getFecha();
        this.duration = route.getDuration();
    }
}
