package entities.condicionales;

public class FiltroCombustible implements Condicionales {
    public float limite = 60000;

    @Override
    public boolean verificado(float kilometraje) {
        return kilometraje - limite < 0;
    }

    @Override
    public void sumarAlLimite(float kilometraje) {
        limite += kilometraje;
    }
}
