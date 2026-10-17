package co.edu.uniquindio.techsolutions.controller;

import co.edu.uniquindio.techsolutions.model.Cliente;
import co.edu.uniquindio.techsolutions.model.NivelPrioridad;
import co.edu.uniquindio.techsolutions.model.SolicitudServicio;
import co.edu.uniquindio.techsolutions.model.TechSolutions;
import co.edu.uniquindio.techsolutions.model.builder.SolicitudBuilder;
import co.edu.uniquindio.techsolutions.model.composite.IComponenteServicio;

import java.time.LocalDate;
import java.util.List;

public class SolicitudesController {

    private final TechSolutions techSolutions = TechSolutions.getInstance();

    /**
     * metodo que retorna la lista de clientes registrados
     * @return
     */
    public List<Cliente> obtenerClientes() {
        return techSolutions.obtenerClientes();
    }

    /**
     * metodo que retorna la lista de elementos contratables disponibles
     * @return
     */
    public List<IComponenteServicio> obtenerElementosContratables() {
        return techSolutions.obtenerElementosContratables();
    }

    /**
     * metodo que retorna la lista de solicitudes registradas
     * @return
     */
    public List<SolicitudServicio> obtenerSolicitudes() {
        return techSolutions.obtenerSolicitudes();
    }

    /**
     * metodo que permite registrar una solicitud construyendola con el Builder y delegando en el modelo
     * @param codigo
     * @param fechaCreacion
     * @param cliente
     * @param elementosContratados
     * @param fechaProgramada
     * @param observacionesTecnicas
     * @param nivelPrioridad
     * @return
     */
    public SolicitudServicio registrarSolicitud(
            String codigo,
            LocalDate fechaCreacion,
            Cliente cliente,
            List<IComponenteServicio> elementosContratados,
            LocalDate fechaProgramada,
            String observacionesTecnicas,
            NivelPrioridad nivelPrioridad) {

        SolicitudServicio solicitud = new SolicitudBuilder()
                .conCodigo(codigo)
                .conFechaCreacion(fechaCreacion)
                .conCliente(cliente)
                .conElementosContratados(elementosContratados)
                .conFechaProgramada(fechaProgramada)
                .conObservacionesTecnicas(observacionesTecnicas)
                .conNivelPrioridad(nivelPrioridad)
                .build();

        return techSolutions.registrarSolicitud(solicitud);
    }
}
