package com.example.sistemaBiblioteca.emprestimos.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @GetMapping("/{clienteId}")
    public ResponseEntity<ClienteComEmprestimosDto> getClienteComEmprestimos(@PathVariable Long clienteId) {
        try {
            Optional<ClienteModelo> clienteOptional = clienteRepository.findById(clienteId);

            if (clienteOptional.isPresent()) {
                ClienteModelo clienteComEmprestimosDto = clienteOptional.get();
                List<EmprestimoComLivroDto> emprestimosDoCliente = emprestimoService
                        .convertToDtoList(clienteComEmprestimosDto.getEmprestimos());
                ClienteComEmprestimosDto client = emprestimoService.toDto(clienteComEmprestimosDto);
                client.setEmprestimos(emprestimosDoCliente);
                LOGGER.info("Cliente encontrado");
                return ResponseEntity.ok(client);
            } else {
                LOGGER.warn("Nenhum cliente encontrado com ID: {}", clienteId);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            LOGGER.error("Erro ao obter empréstimos do cliente com ID: {}", clienteId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<String> realizarEmprestimo(Long clienteId, LivroDto livroDto) {
        try {
            Optional<ClienteModelo> clienteOptional = clienteRepository.findById(clienteId);
            if (!clienteOptional.isPresent()) {
                LOGGER.warn("Cliente não encontrado com ID: {}", clienteId);
                return ResponseEntity.notFound().build();
            }

            ClienteModelo cliente = clienteOptional.get();
            Optional<LivroModelo> livroOptional = livroRepository.findById(livroDto.getLivroId());
            if (!livroOptional.isPresent()) {
                LOGGER.warn("Livro não encontrado com ID: {}", livroDto.getLivroId());
                return ResponseEntity.notFound().build();
            }

            LivroModelo livro = livroOptional.get();
            EmprestimoModelo emprestimo = new EmprestimoModelo();
            emprestimo.setCliente(cliente);
            emprestimo.setLivro(livro);
            emprestimo.setDataEmprestimo(LocalDate.now());
            emprestimosRepository.save(emprestimo);
            LOGGER.info("Empréstimo realizado com sucesso. Cliente ID: {}, Livro ID: {}", clienteId,
                    livroDto.getLivroId());
            return ResponseEntity.status(HttpStatus.CREATED).body("Empréstimo realizado com sucesso");
        } catch (Exception e) {
            LOGGER.error("Erro ao realizar empréstimo. Cliente ID: {}, Livro ID: {}", clienteId, livroDto.getLivroId(),
                    e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/{clienteId}/devolver/{livroId}/{emprestimoId}")
    public ResponseEntity<String> realizarDevolucao(
            @PathVariable Long clienteId,
            @PathVariable Long livroId,
            @PathVariable Long emprestimoId) {
        try {
            ClienteModelo cliente = getClienteOrThrow(clienteId);
            LivroModelo livro = getLivroOrThrow(livroId);
            EmprestimoModelo emprestimo = getEmprestimoOrThrow(emprestimoId);

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
