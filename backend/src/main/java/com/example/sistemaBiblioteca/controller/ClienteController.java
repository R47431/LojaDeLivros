package com.example.sistemaBiblioteca.controller;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sistemaBiblioteca.model.ClienteModelo;
import com.example.sistemaBiblioteca.repository.ClienteRepository;
import com.example.sistemaBiblioteca.service.GlobalService;
import com.example.sistemaBiblioteca.dto.ClienteComEmprestimosDto;
import com.example.sistemaBiblioteca.dto.ClienteDTO;
import com.example.sistemaBiblioteca.mapper.ClienteMapper;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteRepository clienteRepository;
    private final GlobalService globalService;
    private final ClienteMapper clienteMapper;
  


    @Autowired
    public ClienteController(GlobalService globalService, ClienteMapper clienteMapper,
            ClienteRepository clienteRepository) {
        this.globalService = globalService;
        this.clienteMapper = clienteMapper;
        this.clienteRepository = clienteRepository;
    }

    @GetMapping("/{clienteId}")
    public ResponseEntity<ClienteComEmprestimosDto> getClienteComEmprestimos(@PathVariable Long clienteId) {
        ClienteModelo cliente = globalService.encontrarEntidadePorId(clienteRepository, clienteId,
                "Cliente nao encontrado ou nao cadastrado");

        if (cliente != null) {
            ClienteComEmprestimosDto clienteDto = clienteMapper.toClienteComEmprestimosDto(cliente);

            return ResponseEntity.ok(clienteDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<ClienteModelo> cadastraCliente( ClienteDTO clienteDTO) {

        ClienteModelo clienteModelo = clienteMapper.toClienteModelo(clienteDTO);
        if (clienteModelo == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        ClienteModelo savedClienteModelo = clienteRepository.save(clienteModelo);
        return ResponseEntity.ok(savedClienteModelo);
    }

    @DeleteMapping
    public ResponseEntity<?> deletarCliente(@PathVariable Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }
        clienteRepository.deleteById(id);
        return ResponseEntity.ok().body("Cliente deletado");
    }

}
