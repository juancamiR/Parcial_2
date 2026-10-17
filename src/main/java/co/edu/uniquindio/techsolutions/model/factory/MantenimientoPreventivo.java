package co.edu.uniquindio.techsolutions.model.factory;

import co.edu.uniquindio.techsolutions.model.TipoServicio;
import co.edu.uniquindio.techsolutions.model.composite.Servicio;

public class MantenimientoPreventivo extends Servicio {

    public static final double COSTO_BASE = 80000.0;

    /**
     * Constructor de la clase MantenimientoPreventivo
     * @param codigo
     * @param nombre
     * @param descripcion
     */
    public MantenimientoPreventivo(String codigo, String nombre, String descripcion) {
        super(codigo, nombre, descripcion, COSTO_BASE, false);
    }

    /**
     * Constructor de la clase MantenimientoPreventivo con atencion presencial
     * @param codigo
     * @param nombre
     * @param descripcion
     * @param atencionPresencial
     */
    public MantenimientoPreventivo(String codigo, String nombre, String descripcion, boolean atencionPresencial) {
        super(codigo, nombre, descripcion, COSTO_BASE, atencionPresencial);
    }

    @Override
    public TipoServicio getTipoServicio() {
        return TipoServicio.MANTENIMIENTO_PREVENTIVO;
    }
}
