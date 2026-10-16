package co.edu.uniquindio.techsolutions.model.composite;

public interface IComponenteServicio {

    String getCodigo();

    String getNombre();

    double calcularCosto();

    boolean requiereAtencionPresencial();
}
