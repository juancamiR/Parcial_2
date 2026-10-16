package co.edu.uniquindio.techsolutions.viewController;

import co.edu.uniquindio.techsolutions.controller.ConsultasTecnicosController;
import co.edu.uniquindio.techsolutions.model.InfoTecnico;
import co.edu.uniquindio.techsolutions.model.SolicitudServicio;
import co.edu.uniquindio.techsolutions.model.TipoEspecialidad;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ConsultasTecnicosViewController {

    private final ConsultasTecnicosController consultasTecnicosController = new ConsultasTecnicosController();

    @FXML
    private ComboBox<TipoEspecialidad> cmbEspecialidad;
    @FXML
    private Label lblIdTecnico;
    @FXML
    private Label lblEspecialidadConsultada;
    @FXML
    private Label lblEstadoDisponibilidad;
    @FXML
    private Label lblCostoDesplazamiento;

    @FXML
    private TableView<SolicitudServicio> tblSolicitudesConfirmacion;
    @FXML
    private TableColumn<SolicitudServicio, String> colConfCodigo;
    @FXML
    private TableColumn<SolicitudServicio, String> colConfCliente;
    @FXML
    private TableColumn<SolicitudServicio, String> colConfPresencial;
    @FXML
    private TableColumn<SolicitudServicio, String> colConfEstado;
    @FXML
    private TableColumn<SolicitudServicio, String> colConfTecnico;
    @FXML
    private TableColumn<SolicitudServicio, String> colConfSubtotal;
    @FXML
    private TableColumn<SolicitudServicio, String> colConfDesplazamiento;
    @FXML
    private TableColumn<SolicitudServicio, String> colConfCostoTotal;

    @FXML
    private Label lblDetalleSolicitud;
    @FXML
    private Label lblRequierePresencialSolicitud;
    @FXML
    private Label lblSubtotalSolicitud;
    @FXML
    private Label lblDesplazamientoSolicitud;
    @FXML
    private Label lblTotalDefinitivoSolicitud;

    @FXML
    public void initialize() {
        cmbEspecialidad.setItems(FXCollections.observableArrayList(TipoEspecialidad.values()));
        cmbEspecialidad.getSelectionModel().selectFirst();

        colConfCodigo.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getCodigo()));
        colConfCliente.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getCliente().getNombreCompleto()));
        colConfPresencial.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().requiereAtencionPresencial() ? "Sí" : "No"));
        colConfEstado.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().isConfirmada() ? "CONFIRMADA" : "PENDIENTE"));
        colConfTecnico.setCellValueFactory(cell -> {
            InfoTecnico tec = cell.getValue().getTecnicoAsignado();
            return new SimpleStringProperty(tec != null ? tec.getIdTecnico() : "Sin asignar");
        });
        colConfSubtotal.setCellValueFactory(cell -> new SimpleStringProperty("$" + cell.getValue().calcularSubtotalServicios()));
        colConfDesplazamiento.setCellValueFactory(cell -> new SimpleStringProperty("$" + cell.getValue().getCostoDesplazamientoAplicado()));
        colConfCostoTotal.setCellValueFactory(cell -> new SimpleStringProperty("$" + cell.getValue().calcularCostoTotal()));

        tblSolicitudesConfirmacion.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldV, newV) -> mostrarDetalleSolicitud());
        refrescarDatos();
    }

    public void refrescarDatos() {
        tblSolicitudesConfirmacion.setItems(FXCollections.observableArrayList(consultasTecnicosController.obtenerSolicitudes()));
        tblSolicitudesConfirmacion.refresh();
        mostrarDetalleSolicitud();
    }

    @FXML
    public void onConsultarDisponibilidad() {
        try {
            InfoTecnico info = consultasTecnicosController.consultarDisponibilidadTecnico(cmbEspecialidad.getValue());
            lblIdTecnico.setText(info.getIdTecnico());
            lblEspecialidadConsultada.setText(info.getEspecialidad());
            lblEstadoDisponibilidad.setText(info.isDisponible() ? "DISPONIBLE" : "NO DISPONIBLE");
            lblCostoDesplazamiento.setText("$" + info.getCostoDesplazamiento());
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    private void mostrarDetalleSolicitud() {
        SolicitudServicio s = tblSolicitudesConfirmacion.getSelectionModel().getSelectedItem();
        if (s == null) {
            lblDetalleSolicitud.setText("Seleccione una solicitud");
            lblRequierePresencialSolicitud.setText("-");
            lblSubtotalSolicitud.setText("$0");
            lblDesplazamientoSolicitud.setText("$0");
            lblTotalDefinitivoSolicitud.setText("$0");
            return;
        }
        lblDetalleSolicitud.setText(s.getCodigo() + " - " + s.getCliente().getNombreCompleto());
        lblRequierePresencialSolicitud.setText(s.requiereAtencionPresencial() ? "Sí" : "No");
        lblSubtotalSolicitud.setText("$" + s.calcularSubtotalServicios());
        lblDesplazamientoSolicitud.setText("$" + s.getCostoDesplazamientoAplicado());
        lblTotalDefinitivoSolicitud.setText("$" + s.calcularCostoTotal());
    }

    @FXML
    public void onConfirmarSolicitud() {
        try {
            SolicitudServicio s = tblSolicitudesConfirmacion.getSelectionModel().getSelectedItem();
            if (s == null) {
                throw new IllegalArgumentException("Seleccione una solicitud de la tabla.");
            }
            onConsultarDisponibilidad();
            SolicitudServicio confirmada = consultasTecnicosController.confirmarSolicitud(
                    s.getCodigo(),
                    cmbEspecialidad.getValue()
            );
            refrescarDatos();
            mostrarAlerta(Alert.AlertType.INFORMATION, "Solicitud Confirmada",
                    "Solicitud confirmada. Costo total definitivo: $" + confirmada.calcularCostoTotal());
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "No se pudo confirmar", e.getMessage());
        }
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
