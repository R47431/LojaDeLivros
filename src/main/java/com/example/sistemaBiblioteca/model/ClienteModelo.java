package com.example.sistemaBiblioteca.model;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
@Entity
@Table(name = "clientes")
public class ClienteModelo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clienteId;

    @NotNull(message = "NOME NAO PODE SER NULL")
    @NotEmpty(message = "NOME NAO PODE SER VAZIO")
    private String nome;

    @Past(message = "DATA DE NASCIMENTO DEVE SER NO PASSADO")
    @NotNull(message = "NASCIMENTO NAO PODE SER NULL")
    @NotEmpty(message = "NASCIMENTO NAO PODE SER VAZIO")
    private LocalDate dataDeNascimento;

    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "NUMERO DE TELEFONE INVALIDO")
    @NotNull(message = "TELEFONE NAO PODE SER NULL")
    @NotEmpty(message = "TELEFONE NAO PODE SER VAZIO")
    private String numeroDeTelefone;

    @Email(message = "EMAIL INVALIDO")
    @NotNull(message = "EMAIL NAO PODE SER NULL")
    @NotEmpty(message = "EMAIL NAO PODE SER VAZIO")
    private String email;

    @JsonManagedReference
    @OneToMany(mappedBy = "cliente")
    private List<EmprestimoModelo> emprestimos;

    

    public ClienteModelo(Long clienteId,
            @NotNull(message = "NOME NAO PODE SER NULL") @NotEmpty(message = "NOME NAO PODE SER VAZIO") String nome,
            @Past(message = "DATA DE NASCIMENTO DEVE SER NO PASSADO") @NotNull(message = "NASCIMENTO NAO PODE SER NULL") @NotEmpty(message = "NASCIMENTO NAO PODE SER VAZIO") LocalDate dataDeNascimento,
            @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "NUMERO DE TELEFONE INVALIDO") @NotNull(message = "TELEFONE NAO PODE SER NULL") @NotEmpty(message = "TELEFONE NAO PODE SER VAZIO") String numeroDeTelefone,
            @Email(message = "EMAIL INVALIDO") @NotNull(message = "EMAIL NAO PODE SER NULL") @NotEmpty(message = "EMAIL NAO PODE SER VAZIO") String email,
            List<EmprestimoModelo> emprestimos) {
        this.clienteId = clienteId;
        this.nome = nome;
        this.dataDeNascimento = dataDeNascimento;
        this.numeroDeTelefone = numeroDeTelefone;
        this.email = email;
        this.emprestimos = emprestimos;
    }


    public ClienteModelo(Long clienteId, String nome, LocalDate dataDeNascimento, String numeroDeTelefone, String email) {
        this.clienteId = clienteId;
        this.nome = nome;
        this.dataDeNascimento = dataDeNascimento;
        this.numeroDeTelefone = numeroDeTelefone;
        this.email = email;
    }


    public ClienteModelo(Long clienteId) {
        this.clienteId = clienteId;
    }


    public ClienteModelo() {
    }


    
    
}
