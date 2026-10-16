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

    public static TechSolutions getInstance() {
        if (instance == null) {
            instance = new TechSolutions();
        }
        return instance;
    }

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

    // Clientes

    public Cliente registrarCliente(String documento, String nombreCompleto, String correo) {
        return clienteService.registrarCliente(documento, nombreCompleto, correo);
    }

    public Cliente buscarCliente(String documento) {
        return clienteService.buscarCliente(documento);
    }

    public List<Cliente> obtenerClientes() {
        return clienteService.obtenerClientes();
    }

    //Servicios

    public Servicio registrarServicio(TipoServicio tipo, String codigo, String nombre, String descripcion) {
        return servicioService.registrarServicio(tipo, codigo, nombre, descripcion, false);
    }

    public Servicio registrarServicio(
            TipoServicio tipo,
            String codigo,
            String nombre,
            String descripcion,
            boolean atencionPresencial) {
        return servicioService.registrarServicio(tipo, codigo, nombre, descripcion, atencionPresencial);
    }

    public Servicio buscarServicioCatalogo(String codigo) {
        return servicioService.buscarServicioCatalogo(codigo);
    }

    public IComponenteServicio aplicarComplementos(
            IComponenteServicio servicioBase,
            boolean prioritaria,
            boolean soporte30Dias,
            boolean domicilio) {
        return servicioService.aplicarComplementos(servicioBase, prioritaria, soporte30Dias, domicilio);
    }

    public IComponenteServicio guardarServicioPersonalizado(
            IComponenteServicio servicioBase,
            boolean prioritaria,
            boolean soporte30Dias,
            boolean domicilio) {
        return servicioService.guardarServicioPersonalizado(servicioBase, prioritaria, soporte30Dias, domicilio);
    }

    public List<Servicio> obtenerCatalogoServicios() {
        return servicioService.obtenerCatalogoServicios();
    }

    public List<IComponenteServicio> obtenerServiciosPersonalizados() {
        return servicioService.obtenerServiciosPersonalizados();
    }

    // Paquetes

    public PaqueteTecnologico crearPaquete(String codigo, String nombre, String descripcion) {
        if (servicioService.buscarServicioCatalogo(codigo) != null) {
            throw new IllegalArgumentException("Ya existe un servicio con el código: " + codigo);
        }
        return paqueteService.crearPaquete(codigo, nombre, descripcion);
    }

    public void agregarElementoAPaquete(String codigoPaquete, IComponenteServicio elemento) {
        paqueteService.agregarElementoAPaquete(codigoPaquete, elemento);
    }

    public void eliminarElementoDePaquete(String codigoPaquete, IComponenteServicio elemento) {
        paqueteService.eliminarElementoDePaquete(codigoPaquete, elemento);
    }

    public PaqueteTecnologico buscarPaquete(String codigo) {
        return paqueteService.buscarPaquete(codigo);
    }

    public List<PaqueteTecnologico> obtenerPaquetes() {
        return paqueteService.obtenerPaquetes();
    }

    // Solicitudes y Técnicos

    public List<IComponenteServicio> obtenerElementosContratables() {
        List<IComponenteServicio> todos = new ArrayList<>();
        todos.addAll(servicioService.obtenerCatalogoServicios());
        todos.addAll(servicioService.obtenerServiciosPersonalizados());
        todos.addAll(paqueteService.obtenerPaquetes());
        return Collections.unmodifiableList(todos);
    }

    public SolicitudServicio registrarSolicitud(SolicitudServicio solicitud) {
        return solicitudService.registrarSolicitud(solicitud);
    }

    public SolicitudServicio buscarSolicitud(String codigo) {
        return solicitudService.buscarSolicitud(codigo);
    }

    public List<SolicitudServicio> obtenerSolicitudes() {
        return solicitudService.obtenerSolicitudes();
    }

    public InfoTecnico consultarDisponibilidadTecnico(TipoEspecialidad especialidad) {
        if (especialidad == null) {
            throw new IllegalArgumentException("Debe seleccionar una especialidad.");
        }
        return proveedorTecnico.consultarDisponibilidad(especialidad.name());
    }

    public SolicitudServicio confirmarSolicitud(String codigoSolicitud, TipoEspecialidad especialidad) {
        return solicitudService.confirmarSolicitud(codigoSolicitud, especialidad, proveedorTecnico);
    }
}
