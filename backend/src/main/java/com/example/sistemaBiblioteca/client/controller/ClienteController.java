package com.example.sistemaBiblioteca.client.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sistemaBiblioteca.model.ClienteModelo;
import com.example.sistemaBiblioteca.client.repository.ClienteRepository;
import com.example.sistemaBiblioteca.client.service.ClienteService;
import com.example.sistemaBiblioteca.dto.ClienteComEmprestimosDto;
import com.example.sistemaBiblioteca.mapper.ClienteMapper;


@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteRepository clienteRepository;
    private final ClienteService clienteService;
    private final ClienteMapper clienteMapper;

    @Autowired
    public ClienteController(ClienteService clienteService, ClienteMapper clienteMapper, ClienteRepository clienteRepository) {
        this.clienteService = clienteService;
        this.clienteMapper = clienteMapper;
        this.clienteRepository = clienteRepository;
    }

    @PostMapping
    public ClienteModelo postEmprestimo(ClienteModelo clienteModelo) {

        return clienteRepository.save(clienteModelo);
    }

    @GetMapping("/{clienteId}")
    public ResponseEntity<ClienteComEmprestimosDto> getClienteComEmprestimos(@PathVariable Long clienteId) {
        ClienteModelo cliente = clienteService.findById(clienteId);

        if (cliente != null) {
            ClienteComEmprestimosDto clienteDto = clienteMapper.toDto(cliente);

            return ResponseEntity.ok(clienteDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
