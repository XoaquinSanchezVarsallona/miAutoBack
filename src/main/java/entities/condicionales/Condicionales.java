package entities.condicionales;

public interface Condicionales {
    float limite = 0;
    public boolean verificado(float kilometraje);
    // Esto permite que al hacer el service, se pueda ajustar el nuevo límite.
    public void sumarAlLimite(float kilometraje);
}
