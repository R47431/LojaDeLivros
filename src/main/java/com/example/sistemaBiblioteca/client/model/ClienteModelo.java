package com.example.sistemaBiblioteca.client.model;


import java.util.List;

import com.example.sistemaBiblioteca.emprestimos.model.EmprestimoModelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "clientes")
public class ClienteModelo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clienteId;
    private String nome;
    private String numeroDeTelefone;
    private String email;

    @OneToMany(mappedBy = "cliente")
    private List<EmprestimoModelo> historicoDeEmprestimos ;
}
