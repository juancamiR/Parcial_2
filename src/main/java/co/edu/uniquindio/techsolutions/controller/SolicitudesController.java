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

    public List<Cliente> obtenerClientes() {
        return techSolutions.obtenerClientes();
    }

    public List<IComponenteServicio> obtenerElementosContratables() {
        return techSolutions.obtenerElementosContratables();
    }

    public List<SolicitudServicio> obtenerSolicitudes() {
        return techSolutions.obtenerSolicitudes();
    }

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
