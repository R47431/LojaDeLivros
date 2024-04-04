package com.example.sistemaBiblioteca.global;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.example.sistemaBiblioteca.exception.NotEqualsException;


@Service
public class GlobalService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalService.class);

    public <T> T encontrarEntidadePorId(JpaRepository<T, Long> repositorio, Long id, String messagem) {
        if (id == null) {
            throw new NullPointerException("");
        }
        Optional<T> entidadeOptional = repositorio.findById(id);
        if (!entidadeOptional.isPresent()) {
            LOGGER.warn("Entidade não encontrada: {}", messagem);
            

        }
        return entidadeOptional.get();
    }

    public <T> void verificarIgualdade(T valorEntidade, T valorComparacao) {
        if (!valorEntidade.equals(valorComparacao)) {
            throw new NotEqualsException("");
        }
    }

    public <T> void verificarNull(T valor) {
        if (valor == null || valor == "") {
            throw new NullPointerException("");
        }
    }
}
