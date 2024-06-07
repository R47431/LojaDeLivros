package com.example.sistemaBiblioteca.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.sistemaBiblioteca.model.ClienteModelo;
import com.example.sistemaBiblioteca.model.EmprestimoModelo;
import com.example.sistemaBiblioteca.model.LivroModelo;

@Repository
public interface EmprestimosRepository extends JpaRepository<EmprestimoModelo, Long> {

    List<EmprestimoModelo> findByCliente(ClienteModelo cliente);
    List<EmprestimoModelo> findByLivro(LivroModelo livro);

    List<EmprestimoModelo> deleteByCliente(ClienteModelo cliente);
}
