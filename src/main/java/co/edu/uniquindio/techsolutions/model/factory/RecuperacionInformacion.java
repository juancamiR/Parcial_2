package co.edu.uniquindio.techsolutions.model.factory;

import co.edu.uniquindio.techsolutions.model.TipoServicio;
import co.edu.uniquindio.techsolutions.model.composite.Servicio;

public class RecuperacionInformacion extends Servicio {

    public static final double COSTO_BASE = 150000.0;

    /**
     * constructor de la clase RecuperacionInformacion
     * @param codigo
     * @param nombre
     * @param descripcion
     */
    public RecuperacionInformacion(String codigo, String nombre, String descripcion) {
        super(codigo, nombre, descripcion, COSTO_BASE, false);
    }

    /**
     * constructor de la clase RecuperacionInformacion con atencion presencial
     * @param codigo
     * @param nombre
     * @param descripcion
     * @param atencionPresencial
     */
    public RecuperacionInformacion(String codigo, String nombre, String descripcion, boolean atencionPresencial) {
        super(codigo, nombre, descripcion, COSTO_BASE, atencionPresencial);
    }

    @Override
    public TipoServicio getTipoServicio() {
        return TipoServicio.RECUPERACION_INFORMACION;
    }
}
