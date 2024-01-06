package com.example.sistemaBiblioteca.client.model;


import java.util.List;

import com.example.sistemaBiblioteca.dto.EmprestimoComLivroDto;
import com.example.sistemaBiblioteca.emprestimos.model.EmprestimoModelo;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private Integer numeroDeTelefone;
    private String email;

    @OneToMany(mappedBy = "cliente")
    //TODO se de bucha retire isso @JsonIgnore
    @JsonIgnore
    private List<EmprestimoModelo> historicoDeEmprestimos ;
}
