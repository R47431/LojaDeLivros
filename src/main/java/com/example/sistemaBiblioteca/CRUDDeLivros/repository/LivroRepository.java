package com.example.sistemaBiblioteca.CRUDDeLivros.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.sistemaBiblioteca.CRUDDeLivros.model.LivroModelo;

@Repository
public interface LivroRepository extends CrudRepository<LivroModelo,Long> {
}
