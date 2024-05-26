package com.example.sistemaBiblioteca.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.sistemaBiblioteca.service.EmprestimoService;

@RestController
@RequestMapping("/emprestimo")
public class EmprestimoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmprestimoController.class);

    private final EmprestimoService emprestimoService;

    public EmprestimoController(EmprestimoService emprestimoService) {
        this.emprestimoService = emprestimoService;
    }

    @PostMapping("/{clienteId}/{livroId}")
    public ResponseEntity<?> pedirEmprestimo(@PathVariable Long clienteId, @PathVariable Long livroId) {
        emprestimoService.emprestimo(clienteId, livroId);
        LOGGER.info("Empréstimo realizado com sucesso. Cliente ID: {}, Livro ID: {}", clienteId, livroId);
        return ResponseEntity.status(HttpStatus.CREATED).body("Empréstimo realizado com sucesso");
    }

    @PutMapping("/{clienteId}/{livroId}/{emprestimoId}")
    public ResponseEntity<?> fazerDevolucao(
            @PathVariable Long clienteId,
            @PathVariable Long livroId,
            @PathVariable Long emprestimoId) {

        emprestimoService.devolucao(clienteId, livroId, emprestimoId);
        LOGGER.info("Devolução realizada com sucesso. Empréstimo ID: {}", emprestimoId);
        return ResponseEntity.ok("Devolução realizada com sucesso.");

    }

    @DeleteMapping("/{empretismoId}")
    public ResponseEntity<?> deleteEmprestimo(@PathVariable Long empretismoId) {
        emprestimoService.deletaEmprestimo(empretismoId);
        return ResponseEntity.ok().body("Emprestimo Deletado");
    }

}
