package com.example.sistemaBiblioteca.client.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.sistemaBiblioteca.client.model.ClienteModelo;


@Repository
public interface ClienteRepository extends CrudRepository<ClienteModelo,Long> {
}
