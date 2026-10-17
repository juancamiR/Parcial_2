package co.edu.uniquindio.techsolutions.services;

import co.edu.uniquindio.techsolutions.model.InfoTecnico;
import co.edu.uniquindio.techsolutions.model.SolicitudServicio;
import co.edu.uniquindio.techsolutions.model.TipoEspecialidad;
import co.edu.uniquindio.techsolutions.model.adapter.IProveedorTecnico;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SolicitudService {

    private final List<SolicitudServicio> solicitudes = new ArrayList<>();

    /**
     * metodo que permite registrar una nueva solicitud validando que no este duplicada
     * @param solicitud
     * @return
     */
    public SolicitudServicio registrarSolicitud(SolicitudServicio solicitud) {
        if (solicitud == null) {
            throw new IllegalArgumentException("La solicitud no puede ser nula.");
        }
        if (buscarSolicitud(solicitud.getCodigo()) != null) {
            throw new IllegalArgumentException("Ya existe una solicitud con el código: " + solicitud.getCodigo());
        }
        solicitudes.add(solicitud);
        return solicitud;
    }

    /**
     * metodo que permite buscar una solicitud por su codigo unico
     * @param codigo
     * @return
     */
    public SolicitudServicio buscarSolicitud(String codigo) {
        if (codigo == null) return null;
        for (SolicitudServicio s : solicitudes) {
            if (s.getCodigo().equalsIgnoreCase(codigo.trim())) {
                return s;
            }
        }
        return null;
    }

    /**
     * metodo que retorna la lista inmutable de solicitudes registradas
     * @return
     */
    public List<SolicitudServicio> obtenerSolicitudes() {
        return Collections.unmodifiableList(solicitudes);
    }

    /**
     * metodo que confirma una solicitud validando la disponibilidad del tecnico si requiere atencion presencial
     * @param codigoSolicitud
     * @param especialidad
     * @param proveedorTecnico
     * @return
     */
    public SolicitudServicio confirmarSolicitud(String codigoSolicitud, TipoEspecialidad especialidad, IProveedorTecnico proveedorTecnico) {
        SolicitudServicio solicitud = buscarSolicitud(codigoSolicitud);
        if (solicitud == null) {
            throw new IllegalArgumentException("No se encontró la solicitud: " + codigoSolicitud);
        }
        if (solicitud.isConfirmada()) {
            throw new IllegalStateException("La solicitud ya fue confirmada previamente.");
        }

        if (solicitud.requiereAtencionPresencial()) {
            if (especialidad == null) {
                throw new IllegalArgumentException("Debe seleccionar una especialidad.");
            }
            InfoTecnico info = proveedorTecnico.consultarDisponibilidad(especialidad.name());
            if (!info.isDisponible()) {
                throw new IllegalStateException(
                        "No se puede confirmar: requiere atención presencial y no hay técnicos disponibles en " + especialidad);
            }
            solicitud.confirmar(info);
        } else {
            solicitud.confirmar(null);
        }
        return solicitud;
    }

    /**
     * metodo para limpiar la lista de solicitudes
     */
    public void limpiar() {
        solicitudes.clear();
    }
}
