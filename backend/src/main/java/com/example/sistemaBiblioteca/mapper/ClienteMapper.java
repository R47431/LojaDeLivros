package com.example.sistemaBiblioteca.mapper;


import com.example.sistemaBiblioteca.dto.ClienteComEmprestimosDto;
import com.example.sistemaBiblioteca.model.ClienteModelo;


public interface ClienteMapper {

    ClienteComEmprestimosDto toDto(ClienteModelo clienteModelo);
}
