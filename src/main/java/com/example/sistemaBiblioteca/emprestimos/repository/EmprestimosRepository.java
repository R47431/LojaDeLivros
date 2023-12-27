package com.example.sistemaBiblioteca.emprestimos.repository;

import com.example.sistemaBiblioteca.client.model.ClienteModelo;
import com.example.sistemaBiblioteca.livros.model.LivroModelo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.sistemaBiblioteca.emprestimos.model.EmprestimoModelo;

import java.util.Optional;


@Repository
public interface EmprestimosRepository extends CrudRepository<EmprestimoModelo,Long> {
    Optional<EmprestimoModelo> findByClienteAndLivroAndDataDevolucaoIsNull(ClienteModelo cliente, LivroModelo livro);
}
