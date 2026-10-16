package co.edu.uniquindio.techsolutions.model.composite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PaqueteTecnologico implements IComponenteServicio {

    private String codigo;
    private String nombre;
    private String descripcion;
    private List<IComponenteServicio> elementos;

    public PaqueteTecnologico(String codigo, String nombre, String descripcion) {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("El código del paquete es obligatorio.");
        }
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del paquete es obligatorio.");
        }
        if (descripcion == null || descripcion.isBlank()) {
            throw new IllegalArgumentException("La descripción del paquete es obligatoria.");
        }
        this.codigo = codigo.trim();
        this.nombre = nombre.trim();
        this.descripcion = descripcion.trim();
        this.elementos = new ArrayList<>();
    }

    public void agregarElemento(IComponenteServicio elem) {
        if (elem == null) {
            throw new IllegalArgumentException("El elemento no puede ser nulo.");
        }

        if (elem instanceof PaqueteTecnologico otroPaquete) {
            if (this.codigo.equalsIgnoreCase(otroPaquete.getCodigo()) || otroPaquete.contienePaquete(this)) {
                throw new IllegalArgumentException("Un paquete no puede incluirse directa o indirectamente dentro de sí mismo.");
            }
        }

        this.elementos.add(elem);
    }

    public boolean contienePaquete(PaqueteTecnologico destino) {
        for (IComponenteServicio hijo : elementos) {
            if (hijo instanceof PaqueteTecnologico subPaquete) {
                if (subPaquete.getCodigo().equalsIgnoreCase(destino.getCodigo()) || subPaquete.contienePaquete(destino)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void eliminarElemento(IComponenteServicio elem) {
        this.elementos.remove(elem);
    }

    public List<IComponenteServicio> getElementos() {
        return Collections.unmodifiableList(elementos);
    }

    @Override
    public String getCodigo() {
        return codigo;
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public double calcularCosto() {
        double total = 0;
        for (IComponenteServicio elem : elementos) {
            total += elem.calcularCosto();
        }
        return total;
    }

    @Override
    public boolean requiereAtencionPresencial() {
        for (IComponenteServicio elem : elementos) {
            if (elem.requiereAtencionPresencial()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "[Paquete] " + codigo + " - " + nombre + " ($" + calcularCosto() + ")";
    }
}
