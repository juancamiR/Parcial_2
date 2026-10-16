package co.edu.uniquindio.techsolutions.services;

import co.edu.uniquindio.techsolutions.model.Cliente;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ClienteService {

    private final List<Cliente> clientes = new ArrayList<>();

    public Cliente registrarCliente(String documento, String nombreCompleto, String correo) {
        if (buscarCliente(documento) != null) {
            throw new IllegalArgumentException("Ya existe un cliente con el documento: " + documento);
        }
        Cliente cliente = new Cliente(documento, nombreCompleto, correo);
        clientes.add(cliente);
        return cliente;
    }

    public Cliente buscarCliente(String documento) {
        if (documento == null) return null;
        for (Cliente c : clientes) {
            if (c.getDocumento().equalsIgnoreCase(documento.trim())) {
                return c;
            }
        }
        return null;
    }

    public List<Cliente> obtenerClientes() {
        return Collections.unmodifiableList(clientes);
    }

    public void limpiar() {
        clientes.clear();
    }
}
