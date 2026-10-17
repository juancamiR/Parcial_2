package co.edu.uniquindio.techsolutions.model;

public class Cliente {

    private String documento;
    private String nombreCompleto;
    private String correo;

    /**
     * constructor de la clase cliente
     * @param documento
     * @param nombreCompleto
     * @param correo
     */
    public Cliente(String documento, String nombreCompleto, String correo) {
        if (documento == null || documento.isBlank()) {
            throw new IllegalArgumentException("El documento es obligatorio.");
        }
        if (nombreCompleto == null || nombreCompleto.isBlank()) {
            throw new IllegalArgumentException("El nombre completo es obligatorio.");
        }
        if (correo == null || correo.isBlank()) {
            throw new IllegalArgumentException("El correo es obligatorio.");
        }
        this.documento = documento.trim();
        this.nombreCompleto = nombreCompleto.trim();
        this.correo = correo.trim();
    }

    public String getDocumento() {
        return documento;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public String getCorreo() {
        return correo;
    }

    @Override
    public String toString() {
        return nombreCompleto + " (" + documento + ")";
    }
}
