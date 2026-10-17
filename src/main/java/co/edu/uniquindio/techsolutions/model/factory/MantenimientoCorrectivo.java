package co.edu.uniquindio.techsolutions.model.factory;

import co.edu.uniquindio.techsolutions.model.TipoServicio;
import co.edu.uniquindio.techsolutions.model.composite.Servicio;

public class MantenimientoCorrectivo extends Servicio {

    public static final double COSTO_BASE = 120000.0;

    /**
     * constructor de la clase MantenimientoCorrectivo
     * @param codigo
     * @param nombre
     * @param descripcion
     */
    public MantenimientoCorrectivo(String codigo, String nombre, String descripcion) {
        super(codigo, nombre, descripcion, COSTO_BASE, false);
    }

    /**
     * constructor de la clase MantenimientoCorrectivo con atencion presencial
     * @param codigo
     * @param nombre
     * @param descripcion
     * @param atencionPresencial
     */
    public MantenimientoCorrectivo(String codigo, String nombre, String descripcion, boolean atencionPresencial) {
        super(codigo, nombre, descripcion, COSTO_BASE, atencionPresencial);
    }

    @Override
    public TipoServicio getTipoServicio() {
        return TipoServicio.MANTENIMIENTO_CORRECTIVO;
    }
}
