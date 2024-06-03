package entities.condicionales;

public class FiltroHabitaculo implements Condicionales {
    public float limite = 15000;

    @Override
    public boolean verificado(float kilometraje) {
        return kilometraje - limite < 0;
    }

    @Override
    public void sumarAlLimite(float kilometraje) {
        limite += kilometraje;
    }
}
