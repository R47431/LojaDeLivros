package com.example.sistemaBiblioteca.controller;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.sistemaBiblioteca.model.ClienteModelo;
import com.example.sistemaBiblioteca.repository.ClienteRepository;
import com.example.sistemaBiblioteca.repository.EmprestimosRepository;
import com.example.sistemaBiblioteca.service.EmprestimoService;
import com.example.sistemaBiblioteca.exception.EntityNotFoundException;
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

    private final EmprestimoService emprestimoService;

    @Autowired
    public EmprestimoController(EmprestimosRepository emprestimosRepository, LivroRepository livroRepository,
            ClienteRepository clienteRepository, EmprestimoService emprestimoService) {
        this.emprestimosRepository = emprestimosRepository;
        this.livroRepository = livroRepository;
        this.clienteRepository = clienteRepository;
        this.emprestimoService = emprestimoService;
    }

    @PostMapping(path = "/{clienteId}/{livroId}")
    public ResponseEntity<?> realizarEmprestimo(@PathVariable Long clienteId, @PathVariable Long livroId) {
        try {

            ClienteModelo clienteOptional = emprestimoService.encontrarEntidadePorId(clienteRepository, clienteId, "Cliente Not Found");
            LivroModelo livroOptional = emprestimoService.encontrarEntidadePorId(livroRepository, livroId, "Livro Not Found");
            /*         
            EmprestimoModelo emprestimo = new EmprestimoModelo();
            emprestimo.setCliente(clienteOptional);
            emprestimo.setLivro(livroOptional);
            emprestimo.setDataEmprestimo(LocalDate.now());
            emprestimosRepository.save(emprestimo);
            */

            EmprestimoModelo  emprestimo = emprestimosRepository.save(new EmprestimoModelo(clienteOptional,livroOptional,LocalDate.now()));

            LOGGER.info("Empréstimo realizado com sucesso. Cliente ID: {}, Livro ID: {}, Empretimo: {}", clienteId,
                    livroId, emprestimo.getEmprestimoId());
                    
            return ResponseEntity.status(HttpStatus.CREATED).body("Empréstimo realizado com sucesso");

        } catch (EntityNotFoundException e) {
            LOGGER.error("Entidade nao NotFound {}",e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        } catch (Exception e) {
            LOGGER.error("Erro ao realizar empréstimo. Cliente ID: {}, Livro ID: {}", clienteId, livroId,
                    e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/{clienteId}/devolver/{livroId}/{emprestimoId}")
    public ResponseEntity<?> realizarDevolucao(
            @PathVariable Long clienteId,
            @PathVariable Long livroId,
            @PathVariable Long emprestimoId
            ) {
        try {

            ClienteModelo cliente = emprestimoService.encontrarEntidadePorId(clienteRepository, clienteId, "Cliente Not Found");
            LivroModelo livro = emprestimoService.encontrarEntidadePorId(livroRepository, livroId, "Livro Not Found");
            EmprestimoModelo emprestimo = emprestimoService.encontrarEntidadePorId(emprestimosRepository, emprestimoId, "Empréstimo Not Found");
            
            emprestimoService.verificarNull(emprestimo.getDataDevolucao(), "Livro já foi devolvido anteriormente.");
            emprestimoService.verificarIgualdade(emprestimo.getCliente(), cliente, "Cliente", clienteId);
            emprestimoService.verificarIgualdade(emprestimo.getLivro(), livro, "Livro", livroId);

            emprestimo.setDataDevolucao(LocalDate.now());
            emprestimosRepository.save(emprestimo);

            LOGGER.info("Devolução realizada com sucesso. Empréstimo ID: {}", emprestimo.getEmprestimoId());
            return ResponseEntity.ok("Devolução realizada com sucesso. Empréstimo ID: " + emprestimo.getEmprestimoId());
        } catch (Exception e) {
            LOGGER.error("Erro ao realizar devolução. Cliente ID: {}, Livro ID: {}, Empréstimo ID: {}", clienteId,
                    livroId, emprestimoId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        }
    }

    /*
    private ClienteModelo getClienteOrThrow(Long clienteId) {
        return clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
    }

    private LivroModelo getLivroOrThrow(Long livroId) {
        return livroRepository.findById(livroId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado"));
    }

    private EmprestimoModelo getEmprestimoOrThrow(Long emprestimoId) {
        return emprestimosRepository.findById(emprestimoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empréstimo não encontrado"));
    }
    */


    //TODO isso ficava na devolucao
    /*
        
            if (emprestimo.getDataDevolucao() != null) {
                LOGGER.warn("Tentativa de devolução de um empréstimo já devolvido. Empréstimo ID: {}", emprestimoId);
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Livro já foi devolvido anteriormente.");
            }
     
            if (!emprestimo.getCliente().equals(cliente) || !emprestimo.getLivro().equals(livro)) {
                LOGGER.warn(
                        "Dados de empréstimo inválidos para devolução. Cliente ID: {}, Livro ID: {}, Empréstimo ID: {}",
                        clienteId, livroId, emprestimoId);
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Dados de empréstimo inválidos para devolução.");
            }

     */
}
