package co.edu.uniquindio.techsolutions.controller;

import co.edu.uniquindio.techsolutions.model.TechSolutions;
import co.edu.uniquindio.techsolutions.model.TipoServicio;
import co.edu.uniquindio.techsolutions.model.composite.IComponenteServicio;
import co.edu.uniquindio.techsolutions.model.composite.Servicio;

import java.util.List;

public class ServiciosController {

    private final TechSolutions techSolutions = TechSolutions.getInstance();

    /**
     * metodo que retorna la lista de servicios del catalogo
     * @return
     */
    public List<Servicio> obtenerCatalogoServicios() {
        return techSolutions.obtenerCatalogoServicios();
    }

    /**
     * metodo que retorna la lista de servicios personalizados
     * @return
     */
    public List<IComponenteServicio> obtenerServiciosPersonalizados() {
        return techSolutions.obtenerServiciosPersonalizados();
    }

    /**
     * metodo que permite registrar un servicio en catalogo
     * @param tipo
     * @param codigo
     * @param nombre
     * @param descripcion
     * @return
     */
    public Servicio registrarServicio(TipoServicio tipo, String codigo, String nombre, String descripcion) {
        return techSolutions.registrarServicio(tipo, codigo, nombre, descripcion, false);
    }

    /**
     * metodo que permite registrar un servicio en catalogo con indicador presencial
     * @param tipo
     * @param codigo
     * @param nombre
     * @param descripcion
     * @param atencionPresencial
     * @return
     */
    public Servicio registrarServicio(
            TipoServicio tipo,
            String codigo,
            String nombre,
            String descripcion,
            boolean atencionPresencial) {
        return techSolutions.registrarServicio(tipo, codigo, nombre, descripcion, atencionPresencial);
    }

    /**
     * metodo que permite previsualizar la personalizacion de un servicio aplicando decoradores
     * @param servicioBase
     * @param prioritaria
     * @param soporte30Dias
     * @param domicilio
     * @return
     */
    public IComponenteServicio previsualizarPersonalizacion(
            IComponenteServicio servicioBase,
            boolean prioritaria,
            boolean soporte30Dias,
            boolean domicilio) {
        return techSolutions.aplicarComplementos(servicioBase, prioritaria, soporte30Dias, domicilio);
    }

    /**
     * metodo que permite guardar un servicio personalizado en el modelo
     * @param servicioBase
     * @param prioritaria
     * @param soporte30Dias
     * @param domicilio
     * @return
     */
    public IComponenteServicio guardarServicioPersonalizado(
            IComponenteServicio servicioBase,
            boolean prioritaria,
            boolean soporte30Dias,
            boolean domicilio) {
        return techSolutions.guardarServicioPersonalizado(servicioBase, prioritaria, soporte30Dias, domicilio);
    }
}
