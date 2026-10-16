package co.edu.uniquindio.techsolutions.services;

import co.edu.uniquindio.techsolutions.model.composite.IComponenteServicio;
import co.edu.uniquindio.techsolutions.model.composite.PaqueteTecnologico;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PaqueteService {

    private final List<PaqueteTecnologico> paquetesTecnologicos = new ArrayList<>();

    public PaqueteTecnologico crearPaquete(String codigo, String nombre, String descripcion) {
        if (buscarPaquete(codigo) != null) {
            throw new IllegalArgumentException("Ya existe un paquete con el código: " + codigo);
        }
        PaqueteTecnologico paquete = new PaqueteTecnologico(codigo, nombre, descripcion);
        paquetesTecnologicos.add(paquete);
        return paquete;
    }

    public void agregarElementoAPaquete(String codigoPaquete, IComponenteServicio elemento) {
        PaqueteTecnologico paquete = buscarPaquete(codigoPaquete);
        if (paquete == null) {
            throw new IllegalArgumentException("No se encontró el paquete: " + codigoPaquete);
        }
        paquete.agregarElemento(elemento);
    }

    public void eliminarElementoDePaquete(String codigoPaquete, IComponenteServicio elemento) {
        PaqueteTecnologico paquete = buscarPaquete(codigoPaquete);
        if (paquete != null) {
            paquete.eliminarElemento(elemento);
        }
    }

    public PaqueteTecnologico buscarPaquete(String codigo) {
        if (codigo == null) return null;
        for (PaqueteTecnologico p : paquetesTecnologicos) {
            if (p.getCodigo().equalsIgnoreCase(codigo.trim())) {
                return p;
            }
        }
        return null;
    }

    public List<PaqueteTecnologico> obtenerPaquetes() {
        return Collections.unmodifiableList(paquetesTecnologicos);
    }

    public void limpiar() {
        paquetesTecnologicos.clear();
    }
}
