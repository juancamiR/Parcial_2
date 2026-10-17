package co.edu.uniquindio.techsolutions.model.factory;

import co.edu.uniquindio.techsolutions.model.TipoServicio;
import co.edu.uniquindio.techsolutions.model.composite.Servicio;

import java.util.EnumMap;
import java.util.Map;

/**
 * Creador abstracto del patrón Factory Method (GoF).
 * Define la interfaz del método fábrica 'crearServicio' y permite extender nuevos tipos
 * de servicios registrando creadores concretos sin modificar el código existente,
 * cumpliendo estrictamente con el principio Open/Closed (OCP).
 */
public abstract class ServicioFactory {

    private static final Map<TipoServicio, ServicioFactory> registroFactorias = new EnumMap<>(TipoServicio.class);

    static {
        registrarFactoria(TipoServicio.MANTENIMIENTO_PREVENTIVO, new MantenimientoPreventivoFactory());
        registrarFactoria(TipoServicio.MANTENIMIENTO_CORRECTIVO, new MantenimientoCorrectivoFactory());
        registrarFactoria(TipoServicio.INSTALACION_REDES, new InstalacionRedesFactory());
        registrarFactoria(TipoServicio.RECUPERACION_INFORMACION, new RecuperacionInformacionFactory());
    }

    /**
     * metodo que permite registrar una factoria concreta para un tipo de servicio
     * @param tipo
     * @param factoria
     */
    public static void registrarFactoria(TipoServicio tipo, ServicioFactory factoria) {
        if (tipo == null || factoria == null) {
            throw new IllegalArgumentException("El tipo y la factoría no pueden ser nulos.");
        }
        registroFactorias.put(tipo, factoria);
    }

    /**
     * metodo que retorna la factoria asociada al tipo de servicio
     * @param tipo
     * @return
     */
    public static ServicioFactory obtenerFactoria(TipoServicio tipo) {
        ServicioFactory factoria = registroFactorias.get(tipo);
        if (factoria == null) {
            throw new IllegalArgumentException("No hay una factoría registrada para el tipo de servicio: " + tipo);
        }
        return factoria;
    }

    /**
     * metodo estatico que permite crear un servicio usando el Factory Method
     * @param tipo
     * @param codigo
     * @param nombre
     * @param descripcion
     * @return
     */
    public static Servicio crearServicio(TipoServicio tipo, String codigo, String nombre, String descripcion) {
        return crearServicio(tipo, codigo, nombre, descripcion, false);
    }

    /**
     * metodo estatico que permite crear un servicio especificando atencion presencial
     * @param tipo
     * @param codigo
     * @param nombre
     * @param descripcion
     * @param atencionPresencial
     * @return
     */
    public static Servicio crearServicio(
            TipoServicio tipo,
            String codigo,
            String nombre,
            String descripcion,
            boolean atencionPresencial) {
        return obtenerFactoria(tipo).crearServicio(codigo, nombre, descripcion, atencionPresencial);
    }

    /**
     * metodo fabrica abstracto que delega la instanciacion a cada creador concreto
     * @param codigo
     * @param nombre
     * @param descripcion
     * @param atencionPresencial
     * @return
     */
    public abstract Servicio crearServicio(String codigo, String nombre, String descripcion, boolean atencionPresencial);

    /**
     * metodo que delega la creacion del servicio con atencion presencial por defecto
     * @param codigo
     * @param nombre
     * @param descripcion
     * @return
     */
    public Servicio crearServicio(String codigo, String nombre, String descripcion) {
        return crearServicio(codigo, nombre, descripcion, false);
    }
}
