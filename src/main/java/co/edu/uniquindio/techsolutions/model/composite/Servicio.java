package co.edu.uniquindio.techsolutions.model.composite;

import co.edu.uniquindio.techsolutions.model.TipoServicio;

public abstract class Servicio implements IComponenteServicio {

    protected String codigo;
    protected String nombre;
    protected String descripcion;
    protected double costoBase;
    protected boolean atencionPresencial;

    /**
     * constructor de la clase servicio
     * @param codigo
     * @param nombre
     * @param descripcion
     * @param costoBase
     */
    public Servicio(String codigo, String nombre, String descripcion, double costoBase) {
        this(codigo, nombre, descripcion, costoBase, false);
    }

    /**
     * constructor de la clase servicio con atencion presencial
     * @param codigo
     * @param nombre
     * @param descripcion
     * @param costoBase
     * @param atencionPresencial
     */
    public Servicio(String codigo, String nombre, String descripcion, double costoBase, boolean atencionPresencial) {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("El código del servicio es obligatorio.");
        }
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del servicio es obligatorio.");
        }
        if (descripcion == null || descripcion.isBlank()) {
            throw new IllegalArgumentException("La descripción del servicio es obligatoria.");
        }
        this.codigo = codigo.trim();
        this.nombre = nombre.trim();
        this.descripcion = descripcion.trim();
        this.costoBase = costoBase;
        this.atencionPresencial = atencionPresencial;
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

    public double getCostoBase() {
        return costoBase;
    }

    public void setAtencionPresencial(boolean atencionPresencial) {
        this.atencionPresencial = atencionPresencial;
    }

    /**
     * metodo que retorna el costo base del servicio
     * @return
     */
    @Override
    public double calcularCosto() {
        return costoBase;
    }

    /**
     * metodo que indica si el servicio requiere atencion presencial
     * @return
     */
    @Override
    public boolean requiereAtencionPresencial() {
        return atencionPresencial;
    }

    public abstract TipoServicio getTipoServicio();

    @Override
    public String toString() {
        return codigo + " - " + nombre + " ($" + calcularCosto() + ")";
    }
}
