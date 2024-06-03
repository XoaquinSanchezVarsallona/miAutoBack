package entities.condicionales;

public class FiltroAceite implements Condicionales {
    public float limite = 20000;

    @Override
    public boolean verificado(float kilometraje) {
        return kilometraje - limite < 0;
    }

    @Override
    public void sumarAlLimite(float kilometraje) {
        limite += kilometraje;
    }
}
