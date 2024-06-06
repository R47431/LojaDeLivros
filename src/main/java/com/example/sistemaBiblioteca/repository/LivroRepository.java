package com.example.sistemaBiblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.sistemaBiblioteca.model.LivroModelo;

@Repository
public interface LivroRepository extends JpaRepository<LivroModelo,Long> {
}
