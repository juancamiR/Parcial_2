package co.edu.uniquindio.techsolutions.model.decorator;

import co.edu.uniquindio.techsolutions.model.composite.IComponenteServicio;

public class AtencionDomicilio extends ComplementoDecorator {

    public static final double COSTO_ADICIONAL = 25000.0;

    /**
     * constructor de la clase AtencionDomicilio
     * @param servicioEnvuelto
     */
    public AtencionDomicilio(IComponenteServicio servicioEnvuelto) {
        super(servicioEnvuelto);
    }

    @Override
    public String getNombre() {
        return servicioEnvuelto.getNombre() + " + Atención a Domicilio";
    }

    /**
     * metodo que suma el costo adicional de atencion a domicilio al costo del servicio
     * @return
     */
    @Override
    public double calcularCosto() {
        return servicioEnvuelto.calcularCosto() + COSTO_ADICIONAL;
    }

    /**
     * metodo que indica que la atencion a domicilio siempre requiere atencion presencial
     * @return
     */
    @Override
    public boolean requiereAtencionPresencial() {
        return true;
    }
}
