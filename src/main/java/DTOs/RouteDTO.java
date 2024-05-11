package DTOs;

import entities.Route;

public class RouteDTO {
    private String patente;
    private String username;
    private String kilometraje;
    private String date;
    private String duration;
    private String routeId;

    public RouteDTO(Route route) {
        this.routeId = String.valueOf(route.getRouteId());
        this.patente = route.getPatente();
        this.username = route.getUser().getUsername();
        this.kilometraje = route.getKilometraje();
        this.date = route.getFecha();
        this.duration = route.getDuration();
    }
}
