package com.example.sistemaBiblioteca.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.example.sistemaBiblioteca.dto.EmprestimoComLivroDto;
import com.example.sistemaBiblioteca.model.EmprestimoModelo;

@Mapper
public interface EmprestimoMapper {
   EmprestimoMapper INSTANCE = Mappers.getMapper(EmprestimoMapper.class);

   List<EmprestimoComLivroDto> toDtoList(List<EmprestimoModelo> emprestimoModelos);
}
