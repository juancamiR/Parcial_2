package co.edu.uniquindio.techsolutions.model.factory;

import co.edu.uniquindio.techsolutions.model.composite.Servicio;

public class RecuperacionInformacionFactory extends ServicioFactory {

    /**
     * metodo que crea un servicio de recuperacion de informacion
     * @param codigo
     * @param nombre
     * @param descripcion
     * @param atencionPresencial
     * @return
     */
    @Override
    public Servicio crearServicio(String codigo, String nombre, String descripcion, boolean atencionPresencial) {
        return new RecuperacionInformacion(codigo, nombre, descripcion, atencionPresencial);
    }
}
