package co.edu.uniquindio.techsolutions.model.factory;

import co.edu.uniquindio.techsolutions.model.TipoServicio;
import co.edu.uniquindio.techsolutions.model.composite.Servicio;

import java.util.EnumMap;
import java.util.Map;


public abstract class ServicioFactory {

    private static final Map<TipoServicio, ServicioFactory> registroFactorias = new EnumMap<>(TipoServicio.class);

    static {
        registrarFactoria(TipoServicio.MANTENIMIENTO_PREVENTIVO, new MantenimientoPreventivoFactory());
        registrarFactoria(TipoServicio.MANTENIMIENTO_CORRECTIVO, new MantenimientoCorrectivoFactory());
        registrarFactoria(TipoServicio.INSTALACION_REDES, new InstalacionRedesFactory());
        registrarFactoria(TipoServicio.RECUPERACION_INFORMACION, new RecuperacionInformacionFactory());
    }

    public static void registrarFactoria(TipoServicio tipo, ServicioFactory factoria) {
        if (tipo == null || factoria == null) {
            throw new IllegalArgumentException("El tipo y la factoría no pueden ser nulos.");
        }
        registroFactorias.put(tipo, factoria);
    }

    public static ServicioFactory obtenerFactoria(TipoServicio tipo) {
        ServicioFactory factoria = registroFactorias.get(tipo);
        if (factoria == null) {
            throw new IllegalArgumentException("No hay una factoría registrada para el tipo de servicio: " + tipo);
        }
        return factoria;
    }

    public static Servicio crearServicio(TipoServicio tipo, String codigo, String nombre, String descripcion) {
        return crearServicio(tipo, codigo, nombre, descripcion, false);
    }

    public static Servicio crearServicio(
            TipoServicio tipo,
            String codigo,
            String nombre,
            String descripcion,
            boolean atencionPresencial) {
        return obtenerFactoria(tipo).crearServicio(codigo, nombre, descripcion, atencionPresencial);
    }

    
    public abstract Servicio crearServicio(String codigo, String nombre, String descripcion, boolean atencionPresencial);

    public Servicio crearServicio(String codigo, String nombre, String descripcion) {
        return crearServicio(codigo, nombre, descripcion, false);
    }
}
