package co.edu.uniquindio.techsolutions.model.decorator;

import co.edu.uniquindio.techsolutions.model.composite.IComponenteServicio;

public class AtencionPrioritaria extends ComplementoDecorator {

    public static final double COSTO_ADICIONAL = 35000.0;

    public AtencionPrioritaria(IComponenteServicio servicioEnvuelto) {
        super(servicioEnvuelto);
    }

    @Override
    public String getNombre() {
        return servicioEnvuelto.getNombre() + " + Atención Prioritaria";
    }

    @Override
    public double calcularCosto() {
        return servicioEnvuelto.calcularCosto() + COSTO_ADICIONAL;
    }
}
