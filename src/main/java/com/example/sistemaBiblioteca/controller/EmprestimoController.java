package com.example.sistemaBiblioteca.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.sistemaBiblioteca.service.EmprestimoService;

import com.example.sistemaBiblioteca.exception.NotEqualsException;
import com.example.sistemaBiblioteca.model.EmprestimoModelo;

@RestController
@RequestMapping("/emprestimo")
public class EmprestimoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmprestimoController.class);

    private final EmprestimoService emprestimoService;

    @Autowired
    public EmprestimoController(EmprestimoService emprestimoService) {
        this.emprestimoService = emprestimoService;
    }

    @PostMapping("/{clienteId}/{livroId}")
    public ResponseEntity<?> realizarEmprestimo(@PathVariable Long clienteId, @PathVariable Long livroId, Long emprestimoId) {
        try {
           emprestimoService.realizarEmprestimo(clienteId, livroId);
            LOGGER.info("Empréstimo realizado com sucesso. Cliente ID: {}, Livro ID: {}, Empretimo: {}", clienteId, livroId, emprestimoId);
            return ResponseEntity.status(HttpStatus.CREATED).body("Empréstimo realizado com sucesso");
        } catch (IllegalArgumentException e) {
            LOGGER.error("Erro ao realizar empréstimo: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao realizar empréstimo: " + e.getMessage());
        }
    }

    @PostMapping("/{clienteId}/devolver/{livroId}/{emprestimoId}")
    public ResponseEntity<?> realizarDevolucao(
            @PathVariable Long clienteId,
            @PathVariable Long livroId,
            @PathVariable Long emprestimoId) {
        try {

            EmprestimoModelo emprestimo = emprestimoService.realizarDevolucao(clienteId, livroId, emprestimoId);

            LOGGER.info("Devolução realizada com sucesso. Empréstimo ID: {}", emprestimoId);
            return ResponseEntity
                    .ok("Devolução realizada com sucesso.");
        } catch (NotEqualsException e) {
            throw new NotEqualsException("Dados de empréstimo inválidos para devolução.");
        } catch (NullPointerException e) {
            throw new NullPointerException("Entidade null");
        }
    }
}
