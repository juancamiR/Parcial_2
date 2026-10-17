package co.edu.uniquindio.techsolutions.services;

import co.edu.uniquindio.techsolutions.model.TipoServicio;
import co.edu.uniquindio.techsolutions.model.composite.IComponenteServicio;
import co.edu.uniquindio.techsolutions.model.composite.Servicio;
import co.edu.uniquindio.techsolutions.model.decorator.AtencionDomicilio;
import co.edu.uniquindio.techsolutions.model.decorator.AtencionPrioritaria;
import co.edu.uniquindio.techsolutions.model.decorator.Soporte30Dias;
import co.edu.uniquindio.techsolutions.model.factory.ServicioFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ServicioService {

    private final List<Servicio> catalogoServicios = new ArrayList<>();
    private final List<IComponenteServicio> serviciosPersonalizados = new ArrayList<>();

    /**
     * Metodo que permite registrar un servicio en el catálogo con atencion presencial desactivada por defecto
     * @param tipo
     * @param codigo
     * @param nombre
     * @param descripcion
     * @return
     */
    public Servicio registrarServicio(TipoServicio tipo, String codigo, String nombre, String descripcion) {
        return registrarServicio(tipo, codigo, nombre, descripcion, false);
    }

    /**
     * metodo que permite registrar un servicio en el catalogo especificando atencion presencial
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

        if (buscarServicioCatalogo(codigo) != null) {
            throw new IllegalArgumentException("Ya existe un servicio con el código: " + codigo);
        }
        Servicio nuevo = ServicioFactory.crearServicio(tipo, codigo, nombre, descripcion, atencionPresencial);
        catalogoServicios.add(nuevo);
        return nuevo;
    }

    /**
     * metodo que permite buscar un servicio del catalogo por su codigo
     * @param codigo
     * @return
     */
    public Servicio buscarServicioCatalogo(String codigo) {
        if (codigo == null) return null;
        for (Servicio s : catalogoServicios) {
            if (s.getCodigo().equalsIgnoreCase(codigo.trim())) {
                return s;
            }
        }
        return null;
    }

    /**
     * Metodo que permite aplicar los decoradores de complementos sobre un servicio base
     * @param servicioBase
     * @param prioritaria
     * @param soporte30Dias
     * @param domicilio
     * @return
     */
    public IComponenteServicio aplicarComplementos(
            IComponenteServicio servicioBase,
            boolean prioritaria,
            boolean soporte30Dias,
            boolean domicilio) {

        if (servicioBase == null) {
            throw new IllegalArgumentException("Debe seleccionar un servicio base.");
        }

        IComponenteServicio resultado = servicioBase;
        if (prioritaria) {
            resultado = new AtencionPrioritaria(resultado);
        }
        if (soporte30Dias) {
            resultado = new Soporte30Dias(resultado);
        }
        if (domicilio) {
            resultado = new AtencionDomicilio(resultado);
        }
        return resultado;
    }

    /**
     * Metodo qué válida, decora y almacena un nuevo servicio personalizado
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

        if (!prioritaria && !soporte30Dias && !domicilio) {
            throw new IllegalArgumentException("Seleccione al menos un complemento para personalizar el servicio.");
        }
        IComponenteServicio decorado = aplicarComplementos(servicioBase, prioritaria, soporte30Dias, domicilio);
        serviciosPersonalizados.add(decorado);
        return decorado;
    }

    /**
     * metodo que retorna la lista inmutable de servicios del catalogo
     * @return
     */
    public List<Servicio> obtenerCatalogoServicios() {
        return Collections.unmodifiableList(catalogoServicios);
    }

    /**
     * metodo que retorna la lista inmutable de servicios personalizados
     * @return
     */
    public List<IComponenteServicio> obtenerServiciosPersonalizados() {
        return Collections.unmodifiableList(serviciosPersonalizados);
    }

    /**
     * metodo para limpiar los catalogos y servicios registrados
     */
    public void limpiar() {
        catalogoServicios.clear();
        serviciosPersonalizados.clear();
    }
}
