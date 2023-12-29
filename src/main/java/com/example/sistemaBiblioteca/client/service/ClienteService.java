package com.example.sistemaBiblioteca.client.service;

import org.springframework.stereotype.Service;

import com.example.sistemaBiblioteca.client.model.ClienteModelo;
import com.example.sistemaBiblioteca.dto.ClienteComEmprestimosDto;
import com.example.sistemaBiblioteca.mapper.ClienteMapper;

@Service
public class ClienteService {
    
    public ClienteComEmprestimosDto toDto(ClienteModelo clienteModelos) {
      return ClienteMapper.INSTANCE.toDto(clienteModelos);
   }
}
