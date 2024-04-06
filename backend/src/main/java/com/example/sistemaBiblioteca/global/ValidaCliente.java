package com.example.sistemaBiblioteca.global;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Collection;

import org.springframework.util.ReflectionUtils;

import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sistemaBiblioteca.dto.ClienteDTO;
import com.example.sistemaBiblioteca.dto.LivroDto;
import com.example.sistemaBiblioteca.model.ClienteModelo;
import com.example.sistemaBiblioteca.model.LivroModelo;

@Service
public class ValidaCliente {

    private ClienteModelo clienteModelo;
    private LivroModelo livroModelo;
    private LivroDto livroDto;

    private final LocalValidatorFactoryBean validator;

    @Autowired
    public ValidaCliente(LocalValidatorFactoryBean validator) {
        this.validator = validator;
    }

    // TODO Fazer uso do dto
    // TODO muda os DTO para class

    public void validaCliente(ClienteDTO clienteDTO) {
        for (Field field : ClienteDTO.class.getDeclaredFields()) {
            ReflectionUtils.makeAccessible(field);
            Object value = ReflectionUtils.getField(field, clienteDTO);
            if (field.getName().equals("clienteId")) {
                continue;
            }
            if (value == null || (value instanceof String && ((String) value).isEmpty() ||
                    (value instanceof LocalDate) ||
                    (value instanceof Collection && ((Collection<?>) value).isEmpty()))) {
                throw new IllegalArgumentException("O campo " + field.getName() + " é obrigatório.");
            }
            if (!validator.validateValue(ClienteDTO.class, field.getName(), value).isEmpty()) {
                throw new IllegalArgumentException("Falha na validação do campo " + field.getName());
            }
        }
    }

    public void validaLivro(LivroDto livroDto) {
        for (Field field : LivroDto.class.getDeclaredFields()) {
            ReflectionUtils.makeAccessible(field);
            Object value = ReflectionUtils.getField(field, livroDto);
            if (field.getName().equals("livroId") || field.getName().equals("imagemDoLivro")) {
                continue;
            }
            if (value == null
                    || (value instanceof String && ((String) value).isEmpty())) {
                throw new IllegalArgumentException("O campo " + field.getName() +
                        " é obrigatório.");
            }
            if (!validator.validateValue(LivroDto.class, field.getName(),
                    value).isEmpty()) {
                throw new IllegalArgumentException("Falha na validação do campo " +
                        field.getName());
            }
        }
    }

}