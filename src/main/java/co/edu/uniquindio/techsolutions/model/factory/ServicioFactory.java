package co.edu.uniquindio.techsolutions.model.factory;

import co.edu.uniquindio.techsolutions.model.TipoServicio;
import co.edu.uniquindio.techsolutions.model.composite.Servicio;

public class ServicioFactory {

    public static Servicio crearServicio(TipoServicio tipo, String codigo, String nombre, String descripcion) {
        return crearServicio(tipo, codigo, nombre, descripcion, false);
    }

    public static Servicio crearServicio(
            TipoServicio tipo,
            String codigo,
            String nombre,
            String descripcion,
            boolean atencionPresencial) {

        if (tipo == null) {
            throw new IllegalArgumentException("El tipo de servicio es obligatorio.");
        }

        return switch (tipo) {
            case MANTENIMIENTO_PREVENTIVO -> new MantenimientoPreventivo(codigo, nombre, descripcion, atencionPresencial);
            case MANTENIMIENTO_CORRECTIVO -> new MantenimientoCorrectivo(codigo, nombre, descripcion, atencionPresencial);
            case INSTALACION_REDES -> new InstalacionRedes(codigo, nombre, descripcion, atencionPresencial);
            case RECUPERACION_INFORMACION -> new RecuperacionInformacion(codigo, nombre, descripcion, atencionPresencial);
        };
    }
}
