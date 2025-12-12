package com.rodriguesadmar.controlesistema.service;


import ch.qos.logback.core.net.server.Client;
import com.rodriguesadmar.controlesistema.model.Cliente;
import com.rodriguesadmar.controlesistema.repository.ClienteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> findById(Long id) {
        return clienteRepository.findById(id);
    }

    public Cliente save(Cliente cliente) {

        if (clienteRepository.existsByCpfOuCnpj(cliente.getCpfOuCnpj())) {
            throw new IllegalArgumentException("CPF/CNPJ já cadastrado no sistema.");
        }

        if (clienteRepository.existsByEmail(cliente.getEmail())) {
            throw new IllegalArgumentException("Email já cadastrado no sistema.");
        }

        return clienteRepository.save(cliente);

    }

    public void deleteById(Long id) {
         clienteRepository.deleteById(id);
    }

}
