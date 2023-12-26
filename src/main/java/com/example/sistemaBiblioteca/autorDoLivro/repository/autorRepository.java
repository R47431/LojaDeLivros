package com.example.sistemaBiblioteca.autorDoLivro.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.sistemaBiblioteca.autorDoLivro.model.AutoresModelo;


@Repository
public interface autorRepository extends CrudRepository<AutoresModelo,Long> {
}
