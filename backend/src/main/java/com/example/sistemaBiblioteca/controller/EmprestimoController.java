package com.example.sistemaBiblioteca.controller;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.sistemaBiblioteca.exception.NullPointerException;

import com.example.sistemaBiblioteca.model.ClienteModelo;
import com.example.sistemaBiblioteca.repository.ClienteRepository;
import com.example.sistemaBiblioteca.repository.EmprestimosRepository;
import com.example.sistemaBiblioteca.service.EmprestimoService;
import com.example.sistemaBiblioteca.service.GlobalService;
import com.example.sistemaBiblioteca.dto.ObterClienteDTO;
import com.example.sistemaBiblioteca.dto.ObterLivroDTO;
import com.example.sistemaBiblioteca.dto.PedirEmprestimoDTO;
import com.example.sistemaBiblioteca.exception.NoEqualsException;
import com.example.sistemaBiblioteca.exception.NotFoundException;
import com.example.sistemaBiblioteca.mapper.EmprestimoMapper;
import com.example.sistemaBiblioteca.model.EmprestimoModelo;
import com.example.sistemaBiblioteca.model.LivroModelo;

import com.example.sistemaBiblioteca.repository.LivroRepository;

@RestController
@RequestMapping("/emprestimo")
public class EmprestimoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmprestimoController.class);

    private final EmprestimosRepository emprestimosRepository;
    private final LivroRepository livroRepository;
    private final ClienteRepository clienteRepository;
    private final EmprestimoMapper emprestimoMapper;

    private final GlobalService globalService;

    @Autowired
    public EmprestimoController(EmprestimosRepository emprestimosRepository, LivroRepository livroRepository,
            ClienteRepository clienteRepository, EmprestimoMapper emprestimoMapper, GlobalService globalService) {
        this.emprestimosRepository = emprestimosRepository;
        this.livroRepository = livroRepository;
        this.clienteRepository = clienteRepository;
        this.emprestimoMapper = emprestimoMapper;
        this.globalService = globalService;

    }

    @PostMapping("/{clienteId}/{livroId}")
    public ResponseEntity<?> realizarEmprestimo(@PathVariable Long clienteId, @PathVariable Long livroId) {
        try {

            ClienteModelo clienteOptional = globalService.encontrarEntidadePorId(clienteRepository, clienteId, "Cliente Not Found");
            LivroModelo livroOptional = globalService.encontrarEntidadePorId(livroRepository, livroId, "Livro Not Found ");

        
            EmprestimoModelo emprestimoModelo = new EmprestimoModelo(clienteOptional, livroOptional, LocalDate.now());

            EmprestimoModelo emprestimo = emprestimosRepository.save(emprestimoModelo);
            LOGGER.info("Empréstimo realizado com sucesso. Cliente ID: {}, Livro ID: {}, Empretimo: {}", clienteId,
                    livroId, emprestimo.getEmprestimoId());
            return ResponseEntity.status(HttpStatus.CREATED).body("Empréstimo realizado com sucesso");

        } catch (NotFoundException e) {
            throw new NotFoundException("Entidade nao Encontrada");
        }catch (NullPointerException e){
            throw new NullPointerException("Entidade null");
        }
    }

    @PostMapping("/{clienteId}/devolver/{livroId}/{emprestimoId}")
    public ResponseEntity<?> realizarDevolucao(
            @PathVariable Long clienteId,
            @PathVariable Long livroId,
            @PathVariable Long emprestimoId) {
        try {

            ClienteModelo cliente = globalService.encontrarEntidadePorId(clienteRepository, clienteId,
                    "Cliente Not Found");
            LivroModelo livro = globalService.encontrarEntidadePorId(livroRepository, livroId, "Livro Not Found");
            EmprestimoModelo emprestimo = globalService.encontrarEntidadePorId(emprestimosRepository, emprestimoId,
                    "Empréstimo Not Found");

            globalService.verificarNull(emprestimo.getDataDevolucao(), "Livro já foi devolvido anteriormente.");
            globalService.verificarIgualdade(emprestimo.getCliente(), cliente);
            globalService.verificarIgualdade(emprestimo.getLivro(), livro);

            emprestimo.setDataDevolucao(LocalDate.now());
            emprestimosRepository.save(emprestimo);

            LOGGER.info("Devolução realizada com sucesso. Empréstimo ID: {}", emprestimo.getEmprestimoId());
            return ResponseEntity.ok("Devolução realizada com sucesso. Empréstimo ID: " + emprestimo.getEmprestimoId());
        } catch (NoEqualsException e) {
            throw new NoEqualsException("Dados de empréstimo inválidos para devolução.");
        }catch (NullPointerException e){
            throw new NullPointerException("Entidade null");
        }catch (NotFoundException e){
            throw new NotFoundException("Entidade não encontrada");
        }
    }
}
