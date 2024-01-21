package com.example.sistemaBiblioteca.client.repository;


import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.sistemaBiblioteca.model.ClienteModelo;


@Repository
public interface ClienteRepository extends CrudRepository<ClienteModelo,Long> {

   
}
