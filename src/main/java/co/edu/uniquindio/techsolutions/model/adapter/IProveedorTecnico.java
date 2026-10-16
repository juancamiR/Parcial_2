package co.edu.uniquindio.techsolutions.model.adapter;

import co.edu.uniquindio.techsolutions.model.InfoTecnico;


public interface IProveedorTecnico {

    InfoTecnico consultarDisponibilidad(String nombreEspecialidad);
}
