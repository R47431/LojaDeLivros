package com.example.sistemaBiblioteca.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.sistemaBiblioteca.model.ClienteModelo;


@Repository
public interface ClienteRepository extends JpaRepository<ClienteModelo,Long> {
   
}
