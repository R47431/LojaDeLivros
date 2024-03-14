package com.example.sistemaBiblioteca.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.sistemaBiblioteca.controller.EmprestimoController;

@Service
public class GlobalService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmprestimoController.class);

    public <T> T encontrarEntidadePorId(JpaRepository<T, Long> repositorio, Long id, String messagem) {
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }
        Optional<T> entidadeOptional = repositorio.findById(id);
        if (!entidadeOptional.isPresent()) {
            LOGGER.warn("{} não encontrado com ID: {}", messagem, id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, messagem + " não encontrado com ID: " + id);
        }
        return entidadeOptional.get();
    }

    public <T> void verificarIgualdade(T valorEntidade, T valorComparacao, String messagem, Long id) {
        if (!valorEntidade.equals(valorComparacao)) {
            LOGGER.warn("Dados inválidos {} ID {}", valorComparacao, id);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados de empréstimo inválidos para devolução.");
        }
    }

    public <T> void verificarNull(T valor, String mensagemErro) {
        if (valor != null) {
            LOGGER.warn("Tentativa de devolução de um empréstimo já devolvido.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, mensagemErro);
        }
    }
}
