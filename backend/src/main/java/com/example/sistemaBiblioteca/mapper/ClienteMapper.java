package com.example.sistemaBiblioteca.mapper;



import com.example.sistemaBiblioteca.dto.ClienteComEmprestimosDto;
import com.example.sistemaBiblioteca.dto.ClienteDTO;
import com.example.sistemaBiblioteca.model.ClienteModelo;


public interface ClienteMapper {

    ClienteComEmprestimosDto toClienteComEmprestimosDto(ClienteModelo clienteModelo);

    ClienteDTO toClienteDTO (ClienteModelo clienteModelo);
}
