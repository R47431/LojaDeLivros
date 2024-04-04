package com.example.sistemaBiblioteca.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sistemaBiblioteca.model.ClienteModelo;
import com.example.sistemaBiblioteca.repository.ClienteRepository;
import com.example.sistemaBiblioteca.dto.ClienteComEmprestimosDto;
import com.example.sistemaBiblioteca.dto.ClienteDTO;
import com.example.sistemaBiblioteca.global.GlobalService;
import com.example.sistemaBiblioteca.global.ValidaCliente;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteRepository clienteRepository;
    private final GlobalService globalService;
    @Autowired
    private ValidaCliente validaCliente;

    @Autowired
    public ClienteController(GlobalService globalService,
            ClienteRepository clienteRepository) {
        this.globalService = globalService;
        this.clienteRepository = clienteRepository;
    }
/*   @GetMapping("/{clienteId}")
    public ResponseEntity<?> getClienteComEmprestimos(@PathVariable Long clienteId) {
        ClienteModelo cliente = globalService.encontrarEntidadePorId(clienteRepository, clienteId,
                "Cliente nao encontrado ou nao cadastrado");

        if (cliente != null) {
            ClienteComEmprestimosDto clienteDto = clienteMapper.toClienteComEmprestimosDto(cliente);

            return ResponseEntity.ok(clienteDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }*/
  

    @PostMapping
    public ResponseEntity<?> cadastraCliente(@Valid ClienteModelo clienteModelo, ClienteDTO clienteDTO) {
        validaCliente.validaCliente(clienteDTO);
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

    /*
     * if (clienteModelo.getNome() == null ||
     * clienteModelo.getNome().trim().isEmpty()) {
     * return ResponseEntity.badRequest().body("Nome é obrigatório");
     * }
     * if (clienteModelo.getDataDeNascimento() == null) {
     * return ResponseEntity.badRequest().body("Data de nascimento é obrigatória");
     * }
     * if (clienteModelo.getNumeroDeTelefone() == null) {
     * return ResponseEntity.badRequest().body("Número de telefone é obrigatório");
     * }
     * if (clienteModelo.getEmail() == null ||
     * clienteModelo.getEmail().trim().isEmpty()) {
     * return ResponseEntity.badRequest().body("Email é obrigatório");
     * }
     */
}
