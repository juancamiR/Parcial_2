package co.edu.uniquindio.techsolutions.controller;

import co.edu.uniquindio.techsolutions.model.TechSolutions;
import co.edu.uniquindio.techsolutions.model.TipoServicio;
import co.edu.uniquindio.techsolutions.model.composite.IComponenteServicio;
import co.edu.uniquindio.techsolutions.model.composite.Servicio;

import java.util.List;

public class ServiciosController {

    private final TechSolutions techSolutions = TechSolutions.getInstance();

    public List<Servicio> obtenerCatalogoServicios() {
        return techSolutions.obtenerCatalogoServicios();
    }

    public List<IComponenteServicio> obtenerServiciosPersonalizados() {
        return techSolutions.obtenerServiciosPersonalizados();
    }

    public Servicio registrarServicio(TipoServicio tipo, String codigo, String nombre, String descripcion) {
        return techSolutions.registrarServicio(tipo, codigo, nombre, descripcion, false);
    }

    public Servicio registrarServicio(
            TipoServicio tipo,
            String codigo,
            String nombre,
            String descripcion,
            boolean atencionPresencial) {
        return techSolutions.registrarServicio(tipo, codigo, nombre, descripcion, atencionPresencial);
    }

    public IComponenteServicio previsualizarPersonalizacion(
            IComponenteServicio servicioBase,
            boolean prioritaria,
            boolean soporte30Dias,
            boolean domicilio) {
        return techSolutions.aplicarComplementos(servicioBase, prioritaria, soporte30Dias, domicilio);
    }

    public IComponenteServicio guardarServicioPersonalizado(
            IComponenteServicio servicioBase,
            boolean prioritaria,
            boolean soporte30Dias,
            boolean domicilio) {
        return techSolutions.guardarServicioPersonalizado(servicioBase, prioritaria, soporte30Dias, domicilio);
    }
}
