package com.example.sistemaBiblioteca.client.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sistemaBiblioteca.client.repository.ClienteRepository;
import com.example.sistemaBiblioteca.model.ClienteModelo;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public ClienteModelo findById(Long clienteId) {
        Optional<ClienteModelo> clienteOptional = clienteRepository.findById(clienteId);
        return clienteOptional.orElse(null);
    }
}
