package co.edu.uniquindio.techsolutions.model.decorator;

import co.edu.uniquindio.techsolutions.model.composite.IComponenteServicio;

public class AtencionPrioritaria extends ComplementoDecorator {

    public static final double COSTO_ADICIONAL = 35000.0;

    /**
     * constructor de la clase AtencionPrioritaria
     * @param servicioEnvuelto
     */
    public AtencionPrioritaria(IComponenteServicio servicioEnvuelto) {
        super(servicioEnvuelto);
    }

    @Override
    public String getNombre() {
        return servicioEnvuelto.getNombre() + " + Atención Prioritaria";
    }

    /**
     * metodo que suma el costo adicional de atencion prioritaria al costo del servicio
     * @return
     */
    @Override
    public double calcularCosto() {
        return servicioEnvuelto.calcularCosto() + COSTO_ADICIONAL;
    }
}
