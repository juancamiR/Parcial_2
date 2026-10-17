package co.edu.uniquindio.techsolutions.model.composite;

public interface IComponenteServicio {

    String getCodigo();

    String getNombre();

    /**
     * metodo que calcula el costo del componente
     * @return
     */
    double calcularCosto();

    /**
     * metodo que indica si el componente requiere atencion presencial
     * @return
     */
    boolean requiereAtencionPresencial();
}
