package co.edu.uniquindio.techsolutions.services;

import co.edu.uniquindio.techsolutions.model.Cliente;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ClienteService {

    private final List<Cliente> clientes = new ArrayList<>();

    /**
     * metodo que permite registrar un nuevo cliente validando que no exista previamente
     * @param documento
     * @param nombreCompleto
     * @param correo
     * @return
     */
    public Cliente registrarCliente(String documento, String nombreCompleto, String correo) {
        if (buscarCliente(documento) != null) {
            throw new IllegalArgumentException("Ya existe un cliente con el documento: " + documento);
        }
        Cliente cliente = new Cliente(documento, nombreCompleto, correo);
        clientes.add(cliente);
        return cliente;
    }

    /**
     * metodo que permite buscar un cliente en base a su documento
     * @param documento
     * @return
     */
    public Cliente buscarCliente(String documento) {
        if (documento == null) return null;
        for (Cliente c : clientes) {
            if (c.getDocumento().equalsIgnoreCase(documento.trim())) {
                return c;
            }
        }
        return null;
    }

    /**
     * metodo que retorna la lista inmutable de clientes registrados
     * @return
     */
    public List<Cliente> obtenerClientes() {
        return Collections.unmodifiableList(clientes);
    }

    /**
     * metodo para limpiar la lista de clientes
     */
    public void limpiar() {
        clientes.clear();
    }
}
