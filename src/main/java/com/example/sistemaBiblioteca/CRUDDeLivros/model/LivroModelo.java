package com.example.sistemaBiblioteca.CRUDDeLivros.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class LivroModelo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long livroId;
    
    private String imagemDoLivro;
    private String nomeDoLivro;
    private String autoDoLivro;
}