package com.example.sistemaBiblioteca.model;

import java.util.List;

import javax.validation.constraints.Email;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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

    @Email
    private String email;

    // TODO se de bucha retire isso @JsonIgnore,@ToString.Exclude
    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "cliente")
    private List<EmprestimoModelo> emprestimos;
}
