package com.example.sistemaBiblioteca.emprestimos.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.sistemaBiblioteca.dto.EmprestimoComLivroDto;
import com.example.sistemaBiblioteca.emprestimos.model.EmprestimoModelo;
import com.example.sistemaBiblioteca.mapper.EmprestimoMapper;

@Service
public class EmprestimoService {
   
   public List<EmprestimoComLivroDto> convertToDtoList(List<EmprestimoModelo> emprestimoModelos) {
      return EmprestimoMapper.INSTANCE.toDtoList(emprestimoModelos);
   }

}
