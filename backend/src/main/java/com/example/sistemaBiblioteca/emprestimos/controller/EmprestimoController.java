package com.example.sistemaBiblioteca.emprestimos.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.example.sistemaBiblioteca.model.ClienteModelo;
import com.example.sistemaBiblioteca.client.repository.ClienteRepository;
import com.example.sistemaBiblioteca.dto.ClienteComEmprestimosDto;
import com.example.sistemaBiblioteca.dto.EmprestimoComLivroDto;
import com.example.sistemaBiblioteca.dto.LivroDto;
import com.example.sistemaBiblioteca.model.EmprestimoModelo;
import com.example.sistemaBiblioteca.emprestimos.repository.EmprestimosRepository;
import com.example.sistemaBiblioteca.emprestimos.service.EmprestimoService;
import com.example.sistemaBiblioteca.model.LivroModelo;
import com.example.sistemaBiblioteca.livros.repository.LivroRepository;

@RestController
@RequestMapping("/emprestimo")
public class EmprestimoController {

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

    @GetMapping("/{clienteId}")
    public ResponseEntity<ClienteComEmprestimosDto> getClienteComEmprestimos(@PathVariable Long clienteId) {
        return clienteRepository.findById(clienteId)
                .map(clienteComEmprestimosDto -> {
                    List<EmprestimoComLivroDto> emprestimosDoCliente = emprestimoService
                            .convertToDtoList(clienteComEmprestimosDto.getEmprestimos());
                    ClienteComEmprestimosDto client = emprestimoService.toDto(clienteComEmprestimosDto);
                    client.setEmprestimos(emprestimosDoCliente);
                    return ResponseEntity.ok(client);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<String> realizarEmprestimo(Long clienteId, LivroDto livroDto) {
        return clienteRepository.findById(clienteId)
                .map(cliente -> livroRepository.findById(livroDto.getLivroId())
                        .map(livro -> {
                            EmprestimoModelo emprestimo = new EmprestimoModelo();
                            emprestimo.setCliente(cliente);
                            emprestimo.setLivro(livro);
                            emprestimo.setDataEmprestimo(LocalDate.now());
                            emprestimosRepository.save(emprestimo);
                            return ResponseEntity.status(HttpStatus.CREATED).body("Empréstimo realizado com sucesso");
                        })
                        .orElse(ResponseEntity.notFound().build()))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{clienteId}/devolver/{livroId}/{emprestimoId}")
    public ResponseEntity<String> realizarDevolucao(
            @PathVariable Long clienteId,
            @PathVariable Long livroId,
            @PathVariable Long emprestimoId) {
        ClienteModelo cliente = getClienteOrThrow(clienteId);
        LivroModelo livro = getLivroOrThrow(livroId);
        EmprestimoModelo emprestimo = getEmprestimoOrThrow(emprestimoId);

        if (emprestimo.getDataDevolucao() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Livro já foi devolvido anteriormente.");
        }

        if (!emprestimo.getCliente().equals(cliente) || !emprestimo.getLivro().equals(livro)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados de empréstimo inválidos para devolução.");
        }

        emprestimo.setDataDevolucao(LocalDate.now());
        emprestimosRepository.save(emprestimo);

        return ResponseEntity.ok("Devolução realizada com sucesso. Empréstimo ID: " + emprestimo.getEmprestimoId());
    }

    // TODO muda para um service
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
}
