package co.edu.uniquindio.techsolutions.controller;

import co.edu.uniquindio.techsolutions.model.TechSolutions;
import co.edu.uniquindio.techsolutions.model.composite.IComponenteServicio;
import co.edu.uniquindio.techsolutions.model.composite.PaqueteTecnologico;

import java.util.List;

public class PaquetesController {

    private final TechSolutions techSolutions = TechSolutions.getInstance();

    public PaqueteTecnologico crearPaquete(String codigo, String nombre, String descripcion) {
        return techSolutions.crearPaquete(codigo, nombre, descripcion);
    }

    public void agregarElementoAPaquete(String codigoPaquete, IComponenteServicio elemento) {
        techSolutions.agregarElementoAPaquete(codigoPaquete, elemento);
    }

    public void eliminarElementoDePaquete(String codigoPaquete, IComponenteServicio elemento) {
        techSolutions.eliminarElementoDePaquete(codigoPaquete, elemento);
    }

    public List<PaqueteTecnologico> obtenerPaquetes() {
        return techSolutions.obtenerPaquetes();
    }

    public List<IComponenteServicio> obtenerElementosDisponibles() {
        return techSolutions.obtenerElementosContratables();
    }
}
