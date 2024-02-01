package com.example.sistemaBiblioteca.emprestimos.repository;

import com.example.sistemaBiblioteca.model.ClienteModelo;
import com.example.sistemaBiblioteca.model.LivroModelo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.sistemaBiblioteca.model.EmprestimoModelo;

import java.util.Optional;


@Repository
public interface EmprestimosRepository extends JpaRepository<EmprestimoModelo,Long> {
    Optional<EmprestimoModelo> findByClienteAndLivroAndDataDevolucaoIsNull(ClienteModelo cliente, LivroModelo livro);



}
