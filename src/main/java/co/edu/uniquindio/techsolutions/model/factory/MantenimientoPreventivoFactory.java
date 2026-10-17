package co.edu.uniquindio.techsolutions.model.factory;

import co.edu.uniquindio.techsolutions.model.composite.Servicio;

public class MantenimientoPreventivoFactory extends ServicioFactory {

    /**
     * metodo que crea un servicio de mantenimiento preventivo
     * @param codigo
     * @param nombre
     * @param descripcion
     * @param atencionPresencial
     * @return
     */
    @Override
    public Servicio crearServicio(String codigo, String nombre, String descripcion, boolean atencionPresencial) {
        return new MantenimientoPreventivo(codigo, nombre, descripcion, atencionPresencial);
    }
}
