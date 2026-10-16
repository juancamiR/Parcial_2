package co.edu.uniquindio.techsolutions.model.builder;

import co.edu.uniquindio.techsolutions.model.Cliente;
import co.edu.uniquindio.techsolutions.model.NivelPrioridad;
import co.edu.uniquindio.techsolutions.model.SolicitudServicio;
import co.edu.uniquindio.techsolutions.model.composite.IComponenteServicio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SolicitudBuilder {

    private String codigo;
    private LocalDate fechaCreacion = LocalDate.now();
    private Cliente cliente;
    private List<IComponenteServicio> elementosContratados = new ArrayList<>();

    private LocalDate fechaProgramada;
    private String observacionesTecnicas;
    private NivelPrioridad nivelPrioridad;

    public SolicitudBuilder conCodigo(String codigo) {
        this.codigo = codigo;
        return this;
    }

    public SolicitudBuilder conFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
        return this;
    }

    public SolicitudBuilder conCliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public SolicitudBuilder conElementosContratados(List<IComponenteServicio> elementos) {
        this.elementosContratados.clear();
        if (elementos != null) {
            this.elementosContratados.addAll(elementos);
        }
        return this;
    }

    public SolicitudBuilder agregarElemento(IComponenteServicio elemento) {
        if (elemento == null) {
            throw new IllegalArgumentException("El elemento no puede ser nulo.");
        }
        this.elementosContratados.add(elemento);
        return this;
    }

    public SolicitudBuilder conFechaProgramada(LocalDate fechaProgramada) {
        this.fechaProgramada = fechaProgramada;
        return this;
    }

    public SolicitudBuilder conObservacionesTecnicas(String observacionesTecnicas) {
        this.observacionesTecnicas = observacionesTecnicas;
        return this;
    }

    public SolicitudBuilder conNivelPrioridad(NivelPrioridad nivelPrioridad) {
        this.nivelPrioridad = nivelPrioridad;
        return this;
    }

    public SolicitudServicio build() {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("El código de la solicitud es obligatorio.");
        }
        if (fechaCreacion == null) {
            throw new IllegalArgumentException("La fecha de creación es obligatoria.");
        }
        if (cliente == null) {
            throw new IllegalArgumentException("El cliente es obligatorio.");
        }
        if (elementosContratados == null || elementosContratados.isEmpty()) {
            throw new IllegalArgumentException("Debe incluir al menos un servicio o paquete contratado.");
        }

        return new SolicitudServicio(
                codigo,
                fechaCreacion,
                cliente,
                elementosContratados,
                fechaProgramada,
                observacionesTecnicas,
                nivelPrioridad
        );
    }
}
