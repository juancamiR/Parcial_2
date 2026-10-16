package co.edu.uniquindio.techsolutions.model.decorator;

import co.edu.uniquindio.techsolutions.model.composite.IComponenteServicio;

public abstract class ComplementoDecorator implements IComponenteServicio {

    protected IComponenteServicio servicioEnvuelto;

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

    @Override
    public double calcularCosto() {
        return servicioEnvuelto.calcularCosto();
    }

    @Override
    public boolean requiereAtencionPresencial() {
        return servicioEnvuelto.requiereAtencionPresencial();
    }

    @Override
    public String toString() {
        return getCodigo() + " - " + getNombre() + " ($" + calcularCosto() + ")";
    }
}
