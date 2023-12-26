package com.example.sistemaBiblioteca.emprestimos.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.sistemaBiblioteca.emprestimos.model.EmprestimoModelo;


@Repository
public interface EmprestimosRepository extends CrudRepository<EmprestimoModelo,Long> {
}
