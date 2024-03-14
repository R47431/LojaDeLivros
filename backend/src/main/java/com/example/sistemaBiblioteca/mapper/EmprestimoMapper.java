package com.example.sistemaBiblioteca.mapper;


import com.example.sistemaBiblioteca.dto.ObterClienteDTO;
import com.example.sistemaBiblioteca.dto.ObterLivroDTO;
import com.example.sistemaBiblioteca.dto.PedirEmprestimoDTO;
import com.example.sistemaBiblioteca.model.ClienteModelo;
import com.example.sistemaBiblioteca.model.EmprestimoModelo;
import com.example.sistemaBiblioteca.model.LivroModelo;

public interface EmprestimoMapper {


  EmprestimoModelo toEmprestimoModelo (PedirEmprestimoDTO pedirEmprestimoDTO);

  ObterClienteDTO toClienteDTO (ClienteModelo clienteModelo);
  ObterLivroDTO toLivroDto (LivroModelo livroModelo);
  
}
