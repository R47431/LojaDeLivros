package com.example.sistemaBiblioteca.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sistemaBiblioteca.model.ClienteModelo;
import com.example.sistemaBiblioteca.repository.ClienteRepository;
import com.example.sistemaBiblioteca.dto.ClienteDTO;
import com.example.sistemaBiblioteca.global.GlobalService;

import javax.validation.Valid;

@RestController
@RequestMapping("/clientes")
@Validated
public class ClienteController {

    private final ClienteRepository clienteRepository;
    private final GlobalService globalService;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    public ClienteController(ClienteRepository clienteRepository, GlobalService globalService) {
        this.clienteRepository = clienteRepository;
        this.globalService = globalService;
    }

    /**
     * realiza GET usado ID cliente
     * 
     * @param clienteId
     * @return retorna os dados do cliente;
     */
    @GetMapping("/{clienteId}")
    public ResponseEntity<?> getClienteComEmprestimos(@PathVariable Long clienteId) {
        ClienteModelo cliente = globalService.encontrarEntidadePorId(clienteRepository, clienteId,
                "Cliente nao encontrado ou nao cadastrado");
        return ResponseEntity.ok(cliente);
    }

    /**
     * realiza POST usando ClienteDTO
     * 
     * @param clienteDTO
     * @return retonar o cliente casdastrado
     */
    @PostMapping
    public ResponseEntity<?> cadastraCliente(@Valid ClienteDTO clienteDTO) {
        ClienteModelo aaa = mapper.map(clienteDTO, ClienteModelo.class);
        ClienteModelo savedClienteModelo = clienteRepository.save(aaa);
        return ResponseEntity.ok(savedClienteModelo);
    }

    /**
     * realiza DELETE e valida o ID se é null
     * @param id
     * @return retonar OK("Cliente deletado")
     */
    @DeleteMapping
    public ResponseEntity<?> deletarCliente(@PathVariable Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }
        clienteRepository.deleteById(id);
        return ResponseEntity.ok().body("Cliente deletado");
    }

}
