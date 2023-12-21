package com.example.sistemaBiblioteca.repository;

import com.example.sistemaBiblioteca.model.LivroModelo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivroRepository extends CrudRepository<LivroModelo,Long> {
}
