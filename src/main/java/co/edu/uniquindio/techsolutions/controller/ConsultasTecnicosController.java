package co.edu.uniquindio.techsolutions.controller;

import co.edu.uniquindio.techsolutions.model.InfoTecnico;
import co.edu.uniquindio.techsolutions.model.SolicitudServicio;
import co.edu.uniquindio.techsolutions.model.TechSolutions;
import co.edu.uniquindio.techsolutions.model.TipoEspecialidad;

import java.util.List;

public class ConsultasTecnicosController {

    private final TechSolutions techSolutions = TechSolutions.getInstance();

    public InfoTecnico consultarDisponibilidadTecnico(TipoEspecialidad especialidad) {
        return techSolutions.consultarDisponibilidadTecnico(especialidad);
    }

    public List<SolicitudServicio> obtenerSolicitudes() {
        return techSolutions.obtenerSolicitudes();
    }

    public SolicitudServicio confirmarSolicitud(String codigoSolicitud, TipoEspecialidad especialidad) {
        return techSolutions.confirmarSolicitud(codigoSolicitud, especialidad);
    }
}
