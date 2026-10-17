package co.edu.uniquindio.techsolutions.model.decorator;

import co.edu.uniquindio.techsolutions.model.composite.IComponenteServicio;

public abstract class ComplementoDecorator implements IComponenteServicio {

    protected IComponenteServicio servicioEnvuelto;

    /**
     * constructor de la clase ComplementoDecorator
     * @param servicioEnvuelto
     */
    public ComplementoDecorator(IComponenteServicio servicioEnvuelto) {
        if (servicioEnvuelto == null) {
            throw new IllegalArgumentException("El servicio a decorar no puede ser nulo.");
        }
        this.servicioEnvuelto = servicioEnvuelto;
    }

    public IComponenteServicio getServicioEnvuelto() {
        return servicioEnvuelto;
    }

    @Override
    public String getCodigo() {
        return servicioEnvuelto.getCodigo();
    }

    @Override
    public String getNombre() {
        return servicioEnvuelto.getNombre();
    }

    /**
     * metodo que delega el calculo de costo al servicio envuelto
     * @return
     */
    @Override
    public double calcularCosto() {
        return servicioEnvuelto.calcularCosto();
    }

    /**
     * metodo que delega la verificacion de atencion presencial al servicio envuelto
     * @return
     */
    @Override
    public boolean requiereAtencionPresencial() {
        return servicioEnvuelto.requiereAtencionPresencial();
    }

    @Override
    public String toString() {
        return getCodigo() + " - " + getNombre() + " ($" + calcularCosto() + ")";
    }
}
