package co.edu.uniquindio.techsolutions.model;

import co.edu.uniquindio.techsolutions.model.composite.IComponenteServicio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SolicitudServicio {

    private String codigo;
    private LocalDate fechaCreacion;
    private Cliente cliente;
    private List<IComponenteServicio> elementosContratados;

    private LocalDate fechaProgramada;
    private String observacionesTecnicas;
    private NivelPrioridad nivelPrioridad;

    private boolean confirmada;
    private InfoTecnico tecnicoAsignado;
    private double costoDesplazamientoAplicado;

    public SolicitudServicio(
            String codigo,
            LocalDate fechaCreacion,
            Cliente cliente,
            List<IComponenteServicio> elementosContratados,
            LocalDate fechaProgramada,
            String observacionesTecnicas,
            NivelPrioridad nivelPrioridad) {

        this.codigo = codigo.trim();
        this.fechaCreacion = fechaCreacion;
        this.cliente = cliente;
        this.elementosContratados = new ArrayList<>(elementosContratados);
        this.fechaProgramada = fechaProgramada;
        this.observacionesTecnicas = (observacionesTecnicas != null && !observacionesTecnicas.isBlank())
                ? observacionesTecnicas.trim()
                : "Sin observaciones";
        this.nivelPrioridad = nivelPrioridad != null ? nivelPrioridad : NivelPrioridad.NORMAL;
        this.confirmada = false;
        this.costoDesplazamientoAplicado = 0.0;
    }

    public boolean requiereAtencionPresencial() {
        for (IComponenteServicio elem : elementosContratados) {
            if (elem.requiereAtencionPresencial()) {
                return true;
            }
        }
        return false;
    }

    public double calcularSubtotalServicios() {
        double subtotal = 0;
        for (IComponenteServicio elem : elementosContratados) {
            subtotal += elem.calcularCosto();
        }
        return subtotal;
    }

    public double calcularCostoTotal() {
        return calcularSubtotalServicios() + costoDesplazamientoAplicado;
    }

    public void confirmar(InfoTecnico infoTecnico) {
        if (confirmada) {
            throw new IllegalStateException("La solicitud ya fue confirmada.");
        }
        if (requiereAtencionPresencial()) {
            if (infoTecnico == null || !infoTecnico.isDisponible()) {
                throw new IllegalStateException("No hay técnicos disponibles para confirmar la solicitud presencial.");
            }
            this.tecnicoAsignado = infoTecnico;
            this.costoDesplazamientoAplicado = infoTecnico.getCostoDesplazamiento();
        } else {
            this.tecnicoAsignado = infoTecnico;
            this.costoDesplazamientoAplicado = 0.0;
        }
        this.confirmada = true;
    }

    public String getCodigo() {
        return codigo;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public List<IComponenteServicio> getElementosContratados() {
        return Collections.unmodifiableList(elementosContratados);
    }

    public LocalDate getFechaProgramada() {
        return fechaProgramada;
    }

    public String getObservacionesTecnicas() {
        return observacionesTecnicas;
    }

    public NivelPrioridad getNivelPrioridad() {
        return nivelPrioridad;
    }

    public boolean isConfirmada() {
        return confirmada;
    }

    public InfoTecnico getTecnicoAsignado() {
        return tecnicoAsignado;
    }

    public double getCostoDesplazamientoAplicado() {
        return costoDesplazamientoAplicado;
    }
}
