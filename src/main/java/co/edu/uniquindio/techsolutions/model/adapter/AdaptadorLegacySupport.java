package co.edu.uniquindio.techsolutions.model.adapter;

import co.edu.uniquindio.techsolutions.model.InfoTecnico;

public class AdaptadorLegacySupport implements IProveedorTecnico {

    private LegacySupport legacySupport;

    /**
     * Constructor por defecto de la clase AdaptadorLegacySupport
     */
    public AdaptadorLegacySupport() {
        this.legacySupport = new LegacySupport();
    }

    /**
     * Constructor con inyeccion de dependencia de LegacySupport
     * @param legacySupport
     */
    public AdaptadorLegacySupport(LegacySupport legacySupport) {
        this.legacySupport = legacySupport;
    }

    /**
     * Metodo que adapta la consulta de tecnicos traduciendo el nombre de la especialidad al formato de LegacySupport
     * @param nombreEspecialidad
     * @return
     */
    @Override
    public InfoTecnico consultarDisponibilidad(String nombreEspecialidad) {
        if (nombreEspecialidad == null || nombreEspecialidad.isBlank()) {
            throw new IllegalArgumentException("El nombre de la especialidad es obligatorio.");
        }

        String especialidad = nombreEspecialidad.trim().toUpperCase();
        int codigo = switch (especialidad) {
            case "MANTENIMIENTO" -> 1;
            case "REDES" -> 2;
            case "RECUPERACION" -> 3;
            case "SEGURIDAD" -> 4;
            default -> 0;
        };

        String respuesta = legacySupport.verificarDisponibilidadTecnico(codigo);
        String[] partes = respuesta.split("\\|");

        String idTecnico = partes[0];
        boolean disponible = partes[1].equalsIgnoreCase("DISPONIBLE");
        double costoDesplazamiento = Double.parseDouble(partes[2]);

        return new InfoTecnico(idTecnico, especialidad, disponible, costoDesplazamiento);
    }
}
