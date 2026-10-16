package co.edu.uniquindio.techsolutions.model.decorator;

import co.edu.uniquindio.techsolutions.model.composite.IComponenteServicio;

public class AtencionDomicilio extends ComplementoDecorator {

    public static final double COSTO_ADICIONAL = 25000.0;

    public AtencionDomicilio(IComponenteServicio servicioEnvuelto) {
        super(servicioEnvuelto);
    }

    @Override
    public String getNombre() {
        return servicioEnvuelto.getNombre() + " + Atención a Domicilio";
    }

    @Override
    public double calcularCosto() {
        return servicioEnvuelto.calcularCosto() + COSTO_ADICIONAL;
    }

    @Override
    public boolean requiereAtencionPresencial() {
        return true;
    }
}
