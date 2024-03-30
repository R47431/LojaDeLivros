package com.example.sistemaBiblioteca.mapper;

import java.util.List;


import com.example.sistemaBiblioteca.dto.EmprestimoComLivroDto;
import com.example.sistemaBiblioteca.model.EmprestimoModelo;


public interface EmprestimoMapper {


  List<EmprestimoComLivroDto> toDtoList(List<EmprestimoModelo> emprestimoModelos);

  EmprestimoComLivroDto toDto(EmprestimoModelo emprestimoModelo);

  
}
