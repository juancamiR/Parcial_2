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

    public Servicio registrarServicio(TipoServicio tipo, String codigo, String nombre, String descripcion) {
        return registrarServicio(tipo, codigo, nombre, descripcion, false);
    }

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

    public Servicio buscarServicioCatalogo(String codigo) {
        if (codigo == null) return null;
        for (Servicio s : catalogoServicios) {
            if (s.getCodigo().equalsIgnoreCase(codigo.trim())) {
                return s;
            }
        }
        return null;
    }

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

    public List<Servicio> obtenerCatalogoServicios() {
        return Collections.unmodifiableList(catalogoServicios);
    }

    public List<IComponenteServicio> obtenerServiciosPersonalizados() {
        return Collections.unmodifiableList(serviciosPersonalizados);
    }

    public void limpiar() {
        catalogoServicios.clear();
        serviciosPersonalizados.clear();
    }
}
