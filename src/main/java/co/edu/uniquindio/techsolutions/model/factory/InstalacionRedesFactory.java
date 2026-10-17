package co.edu.uniquindio.techsolutions.model.factory;

import co.edu.uniquindio.techsolutions.model.composite.Servicio;

public class InstalacionRedesFactory extends ServicioFactory {

    /**
     * Metodo que crea un servicio de instalacion de redes
     * @param codigo
     * @param nombre
     * @param descripcion
     * @param atencionPresencial
     * @return
     */
    @Override
    public Servicio crearServicio(String codigo, String nombre, String descripcion, boolean atencionPresencial) {
        return new InstalacionRedes(codigo, nombre, descripcion, atencionPresencial);
    }
}
