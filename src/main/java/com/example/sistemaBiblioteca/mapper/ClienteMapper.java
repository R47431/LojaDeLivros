package com.example.sistemaBiblioteca.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import com.example.sistemaBiblioteca.client.model.ClienteModelo;
import com.example.sistemaBiblioteca.dto.ClienteComEmprestimosDto;

@Mapper
public interface ClienteMapper {

    ClienteMapper INSTANCE = Mappers.getMapper(ClienteMapper.class);

    @Mapping(source = "historicoDeEmprestimos", target = "emprestimos")
    ClienteComEmprestimosDto toDto(ClienteModelo clienteModelo);

}
