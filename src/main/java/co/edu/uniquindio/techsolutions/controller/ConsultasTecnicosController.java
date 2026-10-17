package co.edu.uniquindio.techsolutions.controller;

import co.edu.uniquindio.techsolutions.model.InfoTecnico;
import co.edu.uniquindio.techsolutions.model.SolicitudServicio;
import co.edu.uniquindio.techsolutions.model.TechSolutions;
import co.edu.uniquindio.techsolutions.model.TipoEspecialidad;

import java.util.List;

public class ConsultasTecnicosController {

    private final TechSolutions techSolutions = TechSolutions.getInstance();

    /**
     * metodo que consulta la disponibilidad de un tecnico para una especialidad
     * @param especialidad
     * @return
     */
    public InfoTecnico consultarDisponibilidadTecnico(TipoEspecialidad especialidad) {
        return techSolutions.consultarDisponibilidadTecnico(especialidad);
    }

    /**
     * metodo que retorna la lista de solicitudes registradas
     * @return
     */
    public List<SolicitudServicio> obtenerSolicitudes() {
        return techSolutions.obtenerSolicitudes();
    }

    /**
     * metodo que confirma una solicitud validando la asignacion del tecnico
     * @param codigoSolicitud
     * @param especialidad
     * @return
     */
    public SolicitudServicio confirmarSolicitud(String codigoSolicitud, TipoEspecialidad especialidad) {
        return techSolutions.confirmarSolicitud(codigoSolicitud, especialidad);
    }
}
