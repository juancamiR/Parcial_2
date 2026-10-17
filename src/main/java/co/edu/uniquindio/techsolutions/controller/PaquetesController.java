package co.edu.uniquindio.techsolutions.controller;

import co.edu.uniquindio.techsolutions.model.TechSolutions;
import co.edu.uniquindio.techsolutions.model.composite.IComponenteServicio;
import co.edu.uniquindio.techsolutions.model.composite.PaqueteTecnologico;

import java.util.List;

public class PaquetesController {

    private final TechSolutions techSolutions = TechSolutions.getInstance();

    /**
     * metodo que permite crear un paquete tecnologico delegando en el modelo
     * @param codigo
     * @param nombre
     * @param descripcion
     * @return
     */
    public PaqueteTecnologico crearPaquete(String codigo, String nombre, String descripcion) {
        return techSolutions.crearPaquete(codigo, nombre, descripcion);
    }

    /**
     * metodo que permite agregar un componente a un paquete
     * @param codigoPaquete
     * @param elemento
     */
    public void agregarElementoAPaquete(String codigoPaquete, IComponenteServicio elemento) {
        techSolutions.agregarElementoAPaquete(codigoPaquete, elemento);
    }

    /**
     * metodo que permite eliminar un componente de un paquete
     * @param codigoPaquete
     * @param elemento
     */
    public void eliminarElementoDePaquete(String codigoPaquete, IComponenteServicio elemento) {
        techSolutions.eliminarElementoDePaquete(codigoPaquete, elemento);
    }

    /**
     * metodo que retorna la lista de paquetes tecnologicos registrados
     * @return
     */
    public List<PaqueteTecnologico> obtenerPaquetes() {
        return techSolutions.obtenerPaquetes();
    }

    /**
     * metodo que retorna los elementos disponibles para componer paquetes
     * @return
     */
    public List<IComponenteServicio> obtenerElementosDisponibles() {
        return techSolutions.obtenerElementosContratables();
    }
}
