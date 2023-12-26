package com.example.sistemaBiblioteca.emprestimos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sistemaBiblioteca.emprestimos.model.EmprestimoModelo;
import com.example.sistemaBiblioteca.emprestimos.repository.EmprestimosRepository;

@RestController
@RequestMapping("/listaEmprestimo")
public class EmprestimoController {
    @Autowired
    private EmprestimosRepository emprestimosRepository;

    @GetMapping
    public Iterable<EmprestimoModelo> lista (){
        return emprestimosRepository.findAll();
    }
}
