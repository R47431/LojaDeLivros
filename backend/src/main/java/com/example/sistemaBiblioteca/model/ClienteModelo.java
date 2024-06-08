package com.example.sistemaBiblioteca.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@Entity
@Table(name = "clientes")
@NoArgsConstructor
@AllArgsConstructor
public class ClienteModelo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clienteId;
    private String nome;
    @Temporal(TemporalType.DATE)
    private LocalDate dataDeNascimento;
    private String numeroDeTelefone;
    private String email;
    
    @OneToMany(mappedBy = "cliente")
    private List<EmprestimoModelo> emprestimos;
}
