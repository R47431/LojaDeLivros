package com.example.sistemaBiblioteca.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.sistemaBiblioteca.model.LivroModelo;

@Repository
public interface LivroRepository extends JpaRepository<LivroModelo, Long> {

    List<LivroModelo> findByGenero(String genero);

}
