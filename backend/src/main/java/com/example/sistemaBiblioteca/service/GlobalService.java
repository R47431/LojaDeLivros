package com.example.sistemaBiblioteca.service;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.thymeleaf.standard.expression.NotEqualsExpression;

import com.example.sistemaBiblioteca.exception.NullPointerException;
import com.example.sistemaBiblioteca.exception.NoEqualsException;


import com.example.sistemaBiblioteca.exception.NotFoundException;

@Service
public class GlobalService {

    public <T> T encontrarEntidadePorId(JpaRepository<T, Long> repositorio, Long id, String messagem) {
        if (id == null) {
            throw new NullPointerException("");
        }
        Optional<T> entidadeOptional = repositorio.findById(id);
        if (!entidadeOptional.isPresent()) {
            throw new NotFoundException("");
        }
        return entidadeOptional.get();
    }

    public <T> void verificarIgualdade(T valorEntidade, T valorComparacao) {
        if (!valorEntidade.equals(valorComparacao)) {
            throw new NoEqualsException("");
        }
    }

    public <T> void verificarNull(T valor, String mensagemErro) {
        if (valor == null) {
            throw new NullPointerException("");
        }
    }
}
