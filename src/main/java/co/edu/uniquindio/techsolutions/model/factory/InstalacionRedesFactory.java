package co.edu.uniquindio.techsolutions.model.factory;

import co.edu.uniquindio.techsolutions.model.composite.Servicio;

public class InstalacionRedesFactory extends ServicioFactory {

    @Override
    public Servicio crearServicio(String codigo, String nombre, String descripcion, boolean atencionPresencial) {
        return new InstalacionRedes(codigo, nombre, descripcion, atencionPresencial);
    }
}
