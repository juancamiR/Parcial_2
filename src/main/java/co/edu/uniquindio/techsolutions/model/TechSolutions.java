package co.edu.uniquindio.techsolutions.model;

import co.edu.uniquindio.techsolutions.model.adapter.AdaptadorLegacySupport;
import co.edu.uniquindio.techsolutions.model.adapter.IProveedorTecnico;
import co.edu.uniquindio.techsolutions.model.composite.IComponenteServicio;
import co.edu.uniquindio.techsolutions.model.composite.PaqueteTecnologico;
import co.edu.uniquindio.techsolutions.model.composite.Servicio;
import co.edu.uniquindio.techsolutions.services.ClienteService;
import co.edu.uniquindio.techsolutions.services.PaqueteService;
import co.edu.uniquindio.techsolutions.services.ServicioService;
import co.edu.uniquindio.techsolutions.services.SolicitudService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TechSolutions {

    private static TechSolutions instance;

    private final ClienteService clienteService;
    private final ServicioService servicioService;
    private final PaqueteService paqueteService;
    private final SolicitudService solicitudService;
    private final IProveedorTecnico proveedorTecnico;

    private TechSolutions() {
        this.clienteService = new ClienteService();
        this.servicioService = new ServicioService();
        this.paqueteService = new PaqueteService();
        this.solicitudService = new SolicitudService();
        this.proveedorTecnico = new AdaptadorLegacySupport();
    }

    /**
     * metodo estatico que retorna la instancia unica de TechSolutions aplicando el patron Singleton
     * @return
     */
    public static TechSolutions getInstance() {
        if (instance == null) {
            instance = new TechSolutions();
        }
        return instance;
    }

    /**
     * metodo para reiniciar los datos de todos los servicios del sistema
     */
    public void limpiarDatos() {
        clienteService.limpiar();
        servicioService.limpiar();
        paqueteService.limpiar();
        solicitudService.limpiar();
    }

    public ClienteService getClienteService() {
        return clienteService;
    }

    public ServicioService getServicioService() {
        return servicioService;
    }

    public PaqueteService getPaqueteService() {
        return paqueteService;
    }

    public SolicitudService getSolicitudService() {
        return solicitudService;
    }

    // --- Clientes ---

    /**
     * metodo que permite registrar un cliente delegando en ClienteService
     * @param documento
     * @param nombreCompleto
     * @param correo
     * @return
     */
    public Cliente registrarCliente(String documento, String nombreCompleto, String correo) {
        return clienteService.registrarCliente(documento, nombreCompleto, correo);
    }

    /**
     * metodo que permite buscar un cliente por documento delegando en ClienteService
     * @param documento
     * @return
     */
    public Cliente buscarCliente(String documento) {
        return clienteService.buscarCliente(documento);
    }

    /**
     * metodo que retorna la lista de clientes registrados
     * @return
     */
    public List<Cliente> obtenerClientes() {
        return clienteService.obtenerClientes();
    }

    // --- Servicios y Personalización ---

    /**
     * metodo que permite registrar un servicio en catalogo con atencion presencial por defecto
     * @param tipo
     * @param codigo
     * @param nombre
     * @param descripcion
     * @return
     */
    public Servicio registrarServicio(TipoServicio tipo, String codigo, String nombre, String descripcion) {
        return servicioService.registrarServicio(tipo, codigo, nombre, descripcion, false);
    }

    /**
     * metodo que permite registrar un servicio en catalogo especificando atencion presencial
     * @param tipo
     * @param codigo
     * @param nombre
     * @param descripcion
     * @param atencionPresencial
     * @return
     */
    public Servicio registrarServicio(
            TipoServicio tipo,
            String codigo,
            String nombre,
            String descripcion,
            boolean atencionPresencial) {
        return servicioService.registrarServicio(tipo, codigo, nombre, descripcion, atencionPresencial);
    }

    /**
     * metodo que permite buscar un servicio en el catalogo por codigo
     * @param codigo
     * @return
     */
    public Servicio buscarServicioCatalogo(String codigo) {
        return servicioService.buscarServicioCatalogo(codigo);
    }

    /**
     * metodo que aplica decoradores de complementos sobre un servicio
     * @param servicioBase
     * @param prioritaria
     * @param soporte30Dias
     * @param domicilio
     * @return
     */
    public IComponenteServicio aplicarComplementos(
            IComponenteServicio servicioBase,
            boolean prioritaria,
            boolean soporte30Dias,
            boolean domicilio) {
        return servicioService.aplicarComplementos(servicioBase, prioritaria, soporte30Dias, domicilio);
    }

    /**
     * metodo que guarda un servicio personalizado con sus decoradores
     * @param servicioBase
     * @param prioritaria
     * @param soporte30Dias
     * @param domicilio
     * @return
     */
    public IComponenteServicio guardarServicioPersonalizado(
            IComponenteServicio servicioBase,
            boolean prioritaria,
            boolean soporte30Dias,
            boolean domicilio) {
        return servicioService.guardarServicioPersonalizado(servicioBase, prioritaria, soporte30Dias, domicilio);
    }

    /**
     * metodo que retorna la lista de servicios del catalogo
     * @return
     */
    public List<Servicio> obtenerCatalogoServicios() {
        return servicioService.obtenerCatalogoServicios();
    }

    /**
     * metodo que retorna la lista de servicios personalizados
     * @return
     */
    public List<IComponenteServicio> obtenerServiciosPersonalizados() {
        return servicioService.obtenerServiciosPersonalizados();
    }

    // --- Paquetes ---

    /**
     * metodo que permite crear un paquete tecnologico validando que no exista el codigo en servicios
     * @param codigo
     * @param nombre
     * @param descripcion
     * @return
     */
    public PaqueteTecnologico crearPaquete(String codigo, String nombre, String descripcion) {
        if (servicioService.buscarServicioCatalogo(codigo) != null) {
            throw new IllegalArgumentException("Ya existe un servicio con el código: " + codigo);
        }
        return paqueteService.crearPaquete(codigo, nombre, descripcion);
    }

    /**
     * metodo que permite agregar un elemento a un paquete existente
     * @param codigoPaquete
     * @param elemento
     */
    public void agregarElementoAPaquete(String codigoPaquete, IComponenteServicio elemento) {
        paqueteService.agregarElementoAPaquete(codigoPaquete, elemento);
    }

    /**
     * metodo que permite eliminar un elemento de un paquete
     * @param codigoPaquete
     * @param elemento
     */
    public void eliminarElementoDePaquete(String codigoPaquete, IComponenteServicio elemento) {
        paqueteService.eliminarElementoDePaquete(codigoPaquete, elemento);
    }

    /**
     * metodo que permite buscar un paquete por su codigo
     * @param codigo
     * @return
     */
    public PaqueteTecnologico buscarPaquete(String codigo) {
        return paqueteService.buscarPaquete(codigo);
    }

    /**
     * metodo que retorna la lista de paquetes registrados
     * @return
     */
    public List<PaqueteTecnologico> obtenerPaquetes() {
        return paqueteService.obtenerPaquetes();
    }

    // --- Solicitudes y Técnicos ---

    /**
     * metodo que retorna todos los elementos contratables (servicios base, personalizados y paquetes)
     * @return
     */
    public List<IComponenteServicio> obtenerElementosContratables() {
        List<IComponenteServicio> todos = new ArrayList<>();
        todos.addAll(servicioService.obtenerCatalogoServicios());
        todos.addAll(servicioService.obtenerServiciosPersonalizados());
        todos.addAll(paqueteService.obtenerPaquetes());
        return Collections.unmodifiableList(todos);
    }

    /**
     * metodo que permite registrar una nueva solicitud de servicio
     * @param solicitud
     * @return
     */
    public SolicitudServicio registrarSolicitud(SolicitudServicio solicitud) {
        return solicitudService.registrarSolicitud(solicitud);
    }

    /**
     * metodo que permite buscar una solicitud por su codigo
     * @param codigo
     * @return
     */
    public SolicitudServicio buscarSolicitud(String codigo) {
        return solicitudService.buscarSolicitud(codigo);
    }

    /**
     * metodo que retorna la lista de solicitudes registradas
     * @return
     */
    public List<SolicitudServicio> obtenerSolicitudes() {
        return solicitudService.obtenerSolicitudes();
    }

    /**
     * metodo que permite consultar la disponibilidad de un tecnico para una especialidad
     * @param especialidad
     * @return
     */
    public InfoTecnico consultarDisponibilidadTecnico(TipoEspecialidad especialidad) {
        if (especialidad == null) {
            throw new IllegalArgumentException("Debe seleccionar una especialidad.");
        }
        return proveedorTecnico.consultarDisponibilidad(especialidad.name());
    }

    /**
     * metodo que permite confirmar una solicitud validando disponibilidad tecnica
     * @param codigoSolicitud
     * @param especialidad
     * @return
     */
    public SolicitudServicio confirmarSolicitud(String codigoSolicitud, TipoEspecialidad especialidad) {
        return solicitudService.confirmarSolicitud(codigoSolicitud, especialidad, proveedorTecnico);
    }
}
