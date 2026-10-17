package co.edu.uniquindio.techsolutions.model;

public class InfoTecnico {

    private String idTecnico;
    private String especialidad;
    private boolean disponible;
    private double costoDesplazamiento;

    /**
     * constructor de la clase InfoTecnico
     * @param idTecnico
     * @param especialidad
     * @param disponible
     * @param costoDesplazamiento
     */
    public InfoTecnico(String idTecnico, String especialidad, boolean disponible, double costoDesplazamiento) {
        this.idTecnico = idTecnico;
        this.especialidad = especialidad;
        this.disponible = disponible;
        this.costoDesplazamiento = costoDesplazamiento;
    }

    public String getIdTecnico() {
        return idTecnico;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public double getCostoDesplazamiento() {
        return costoDesplazamiento;
    }

    @Override
    public String toString() {
        return idTecnico + " - " + especialidad + " (" + (disponible ? "DISPONIBLE" : "NO DISPONIBLE") + ")";
    }
}
