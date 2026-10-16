package co.edu.uniquindio.techsolutions.viewController;

import co.edu.uniquindio.techsolutions.controller.SolicitudesController;
import co.edu.uniquindio.techsolutions.model.Cliente;
import co.edu.uniquindio.techsolutions.model.NivelPrioridad;
import co.edu.uniquindio.techsolutions.model.SolicitudServicio;
import co.edu.uniquindio.techsolutions.model.composite.IComponenteServicio;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.util.ArrayList;

public class SolicitudesViewController {

    private final SolicitudesController solicitudesController = new SolicitudesController();

    @FXML
    private TextField txtCodigoSolicitud;
    @FXML
    private ComboBox<Cliente> cmbCliente;
    @FXML
    private DatePicker dpFechaCreacion;

    @FXML
    private ComboBox<IComponenteServicio> cmbElementoContratable;
    @FXML
    private TableView<IComponenteServicio> tblElementosSeleccionados;
    @FXML
    private TableColumn<IComponenteServicio, String> colSelCodigo;
    @FXML
    private TableColumn<IComponenteServicio, String> colSelNombre;
    @FXML
    private TableColumn<IComponenteServicio, String> colSelPresencial;
    @FXML
    private TableColumn<IComponenteServicio, String> colSelCosto;
    @FXML
    private Label lblSubtotalSeleccionado;

    @FXML
    private DatePicker dpFechaProgramada;
    @FXML
    private ComboBox<NivelPrioridad> cmbPrioridad;
    @FXML
    private TextArea txtObservaciones;

    @FXML
    private TableView<SolicitudServicio> tblSolicitudesRegistradas;
    @FXML
    private TableColumn<SolicitudServicio, String> colSolCodigo;
    @FXML
    private TableColumn<SolicitudServicio, String> colSolFechaCreacion;
    @FXML
    private TableColumn<SolicitudServicio, String> colSolCliente;
    @FXML
    private TableColumn<SolicitudServicio, String> colSolCantidadElementos;
    @FXML
    private TableColumn<SolicitudServicio, String> colSolPrioridad;
    @FXML
    private TableColumn<SolicitudServicio, String> colSolFechaProgramada;
    @FXML
    private TableColumn<SolicitudServicio, String> colSolPresencial;
    @FXML
    private TableColumn<SolicitudServicio, String> colSolEstado;
    @FXML
    private TableColumn<SolicitudServicio, String> colSolCostoTotal;

    private final ObservableList<IComponenteServicio> elementosSeleccionados = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        dpFechaCreacion.setValue(LocalDate.now());
        cmbPrioridad.setItems(FXCollections.observableArrayList(NivelPrioridad.values()));
        cmbPrioridad.getSelectionModel().select(NivelPrioridad.NORMAL);
        tblElementosSeleccionados.setItems(elementosSeleccionados);

        colSelCodigo.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getCodigo()));
        colSelNombre.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getNombre()));
        colSelPresencial.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().requiereAtencionPresencial() ? "Sí" : "No"));
        colSelCosto.setCellValueFactory(cell -> new SimpleStringProperty("$" + cell.getValue().calcularCosto()));

        colSolCodigo.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getCodigo()));
        colSolFechaCreacion.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getFechaCreacion())));
        colSolCliente.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getCliente().getNombreCompleto()));
        colSolCantidadElementos.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getElementosContratados().size())));
        colSolPrioridad.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getNivelPrioridad().name()));
        colSolFechaProgramada.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().getFechaProgramada() != null ? cell.getValue().getFechaProgramada().toString() : "Sin fecha"));
        colSolPresencial.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().requiereAtencionPresencial() ? "Sí" : "No"));
        colSolEstado.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().isConfirmada() ? "CONFIRMADA" : "PENDIENTE"));
        colSolCostoTotal.setCellValueFactory(cell -> new SimpleStringProperty("$" + cell.getValue().calcularCostoTotal()));

        refrescarDatos();
    }

    public void refrescarDatos() {
        cmbCliente.setItems(FXCollections.observableArrayList(solicitudesController.obtenerClientes()));
        if (!cmbCliente.getItems().isEmpty() && cmbCliente.getSelectionModel().isEmpty()) {
            cmbCliente.getSelectionModel().selectFirst();
        }

        cmbElementoContratable.setItems(FXCollections.observableArrayList(solicitudesController.obtenerElementosContratables()));
        if (!cmbElementoContratable.getItems().isEmpty() && cmbElementoContratable.getSelectionModel().isEmpty()) {
            cmbElementoContratable.getSelectionModel().selectFirst();
        }

        tblSolicitudesRegistradas.setItems(FXCollections.observableArrayList(solicitudesController.obtenerSolicitudes()));
        tblSolicitudesRegistradas.refresh();
        actualizarSubtotal();
    }

    @FXML
    public void onAgregarElementoSolicitud() {
        IComponenteServicio elem = cmbElementoContratable.getValue();
        if (elem != null) {
            elementosSeleccionados.add(elem);
            actualizarSubtotal();
        }
    }

    @FXML
    public void onQuitarElementoSolicitud() {
        IComponenteServicio elem = tblElementosSeleccionados.getSelectionModel().getSelectedItem();
        if (elem != null) {
            elementosSeleccionados.remove(elem);
            actualizarSubtotal();
        }
    }

    private void actualizarSubtotal() {
        double subtotal = 0;
        for (IComponenteServicio elem : elementosSeleccionados) {
            subtotal += elem.calcularCosto();
        }
        lblSubtotalSeleccionado.setText("$" + subtotal);
    }

    @FXML
    public void onRegistrarSolicitud() {
        try {
            SolicitudServicio creada = solicitudesController.registrarSolicitud(
                    txtCodigoSolicitud.getText(),
                    dpFechaCreacion.getValue(),
                    cmbCliente.getValue(),
                    new ArrayList<>(elementosSeleccionados),
                    dpFechaProgramada.getValue(),
                    txtObservaciones.getText(),
                    cmbPrioridad.getValue()
            );

            txtCodigoSolicitud.clear();
            dpFechaCreacion.setValue(LocalDate.now());
            dpFechaProgramada.setValue(null);
            txtObservaciones.clear();
            cmbPrioridad.getSelectionModel().select(NivelPrioridad.NORMAL);
            elementosSeleccionados.clear();
            refrescarDatos();

            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito",
                    "Solicitud registrada: " + creada.getCodigo() + " - Total: $" + creada.calcularCostoTotal());
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", e.getMessage());
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
