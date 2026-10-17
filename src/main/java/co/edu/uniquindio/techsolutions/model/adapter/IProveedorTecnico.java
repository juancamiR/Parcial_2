package co.edu.uniquindio.techsolutions.model.adapter;

import co.edu.uniquindio.techsolutions.model.InfoTecnico;

/**
 * Interfaz objetivo (Target en el patrón Adapter) que define el contrato requerido por
 * TechSolutions para consultar disponibilidad de técnicos especializados.
 */
public interface IProveedorTecnico {

    /**
     * metodo que consulta la disponibilidad de tecnicos y costo de desplazamiento
     * @param nombreEspecialidad
     * @return
     */
    InfoTecnico consultarDisponibilidad(String nombreEspecialidad);
}
