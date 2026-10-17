package co.edu.uniquindio.techsolutions.model.decorator;

import co.edu.uniquindio.techsolutions.model.composite.IComponenteServicio;

public class Soporte30Dias extends ComplementoDecorator {

    public static final double COSTO_ADICIONAL = 50000.0;

    /**
     * constructor de la clase Soporte30Dias
     * @param servicioEnvuelto
     */
    public Soporte30Dias(IComponenteServicio servicioEnvuelto) {
        super(servicioEnvuelto);
    }

    @Override
    public String getNombre() {
        return servicioEnvuelto.getNombre() + " + Soporte 30 Días";
    }

    /**
     * metodo que suma el costo adicional de soporte 30 dias al costo del servicio
     * @return
     */
    @Override
    public double calcularCosto() {
        return servicioEnvuelto.calcularCosto() + COSTO_ADICIONAL;
    }
}
