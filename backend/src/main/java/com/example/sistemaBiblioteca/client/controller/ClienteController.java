package com.example.sistemaBiblioteca.client.controller;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sistemaBiblioteca.model.ClienteModelo;
import com.example.sistemaBiblioteca.client.repository.ClienteRepository;


@RestController
@RequestMapping("/clientes")
public class ClienteController {
    @Autowired
    private ClienteRepository clienteRepository;


    @PostMapping
    public ClienteModelo postEmprestimo(ClienteModelo clienteModelo) {
        return clienteRepository.save(clienteModelo);
    }


    @GetMapping
    public ResponseEntity<Iterable<ClienteModelo>> obterTodosClientes() {
        Iterable<ClienteModelo> clientes = clienteRepository.findAll();
        return ResponseEntity.ok(clientes);
    }


}

