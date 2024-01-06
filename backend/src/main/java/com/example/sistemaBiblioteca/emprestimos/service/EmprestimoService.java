package com.example.sistemaBiblioteca.emprestimos.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.sistemaBiblioteca.model.ClienteModelo;
import com.example.sistemaBiblioteca.model.EmprestimoModelo;
import com.example.sistemaBiblioteca.dto.ClienteComEmprestimosDto;
import com.example.sistemaBiblioteca.dto.EmprestimoComLivroDto;
import com.example.sistemaBiblioteca.mapper.ClienteMapper;
import com.example.sistemaBiblioteca.mapper.EmprestimoMapper;

@Service
public class EmprestimoService {

   public List<EmprestimoComLivroDto> convertToDtoList(List<EmprestimoModelo> emprestimoModelos) {
      return EmprestimoMapper.INSTANCE.toDtoList(emprestimoModelos);
   }

   public ClienteComEmprestimosDto toDto(ClienteModelo clienteModelos) {
      return ClienteMapper.INSTANCE.toDto(clienteModelos);
   }

}