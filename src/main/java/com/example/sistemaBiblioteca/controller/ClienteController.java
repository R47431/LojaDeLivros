package com.example.sistemaBiblioteca.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sistemaBiblioteca.repository.ClienteRepository;
import com.example.sistemaBiblioteca.model.ClienteModelo;
import com.example.sistemaBiblioteca.service.ClienteService;
import com.example.sistemaBiblioteca.dto.ClienteDTO;

import java.util.Optional;

import javax.validation.Valid;

@RestController
@RequestMapping("/clientes")
@CrossOrigin("*")
@Validated
public class ClienteController {

  private final ClienteService clienteService;

  private final ClienteRepository clienteRepository;

  public ClienteController(ClienteService clienteService, ClienteRepository clienteRepository) {
    this.clienteService = clienteService;
    this.clienteRepository = clienteRepository;
  }

  @GetMapping("/{clienteId}")
  public ResponseEntity<?> getClienteComEmprestimos(@PathVariable Long clienteId) {
    ClienteModelo cliente = clienteService.encontrarClientre(clienteId);
    return ResponseEntity.ok(cliente);
  }

  @GetMapping("/lista")
  public Iterable<ClienteModelo> getClienteComEmprestimos() {
    return clienteRepository.findAll();
  }

  @PostMapping
  public ResponseEntity<?> cadastraCliente(@Valid ClienteDTO clienteDTO) {
    ClienteModelo savedClienteModelo = clienteService.cadastraCliente(clienteDTO);
    return ResponseEntity.ok(savedClienteModelo);
  }

  @DeleteMapping("/{clienteId}")
  public ResponseEntity<?> deletarCliente(@PathVariable Long clienteId) {
    clienteService.deletaCliente(clienteId);
    return ResponseEntity.ok("Cliente Deletado Com sucesse");
  }

}
