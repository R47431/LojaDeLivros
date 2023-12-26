package com.example.sistemaBiblioteca.livros.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.sistemaBiblioteca.livros.model.LivroModelo;

@Repository
public interface LivroRepository extends CrudRepository<LivroModelo,Long> {
}
