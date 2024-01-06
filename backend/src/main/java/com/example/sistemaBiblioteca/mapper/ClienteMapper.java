package com.example.sistemaBiblioteca.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.example.sistemaBiblioteca.dto.ClienteComEmprestimosDto;
import com.example.sistemaBiblioteca.model.ClienteModelo;

@Mapper
public interface ClienteMapper {

    ClienteMapper INSTANCE = Mappers.getMapper(ClienteMapper.class);

    ClienteComEmprestimosDto toDto(ClienteModelo clienteModelo);

}
