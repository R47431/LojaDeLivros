package com.example.sistemaBiblioteca.util;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.sistemaBiblioteca.global.exception.NotFoundException;
import com.example.sistemaBiblioteca.model.ClienteModelo;
import com.example.sistemaBiblioteca.model.EmprestimoModelo;
import com.example.sistemaBiblioteca.model.LivroModelo;
import com.example.sistemaBiblioteca.repository.ClienteRepository;
import com.example.sistemaBiblioteca.repository.EmprestimosRepository;
import com.example.sistemaBiblioteca.repository.LivroRepository;

@Component
public class LoanValidator {
    private final EmprestimosRepository emprestimosRepository;
    private final LivroRepository livroRepository;
    private final ClienteRepository clienteRepository;

    public LoanValidator(
            EmprestimosRepository emprestimosRepository,
            LivroRepository livroRepository,
            ClienteRepository clienteRepository) {
        this.livroRepository = livroRepository;
        this.clienteRepository = clienteRepository;
        this.emprestimosRepository = emprestimosRepository;
    }

    public ClienteModelo buscarClientePorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cliente Not Found"));
    }

    public LivroModelo buscarLivroPorId(Long id) {
        return livroRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book Not Found"));
    }

    public EmprestimoModelo criarEmprestimo(ClienteModelo cliente, LivroModelo livro) {
        return new EmprestimoModelo(cliente, livro, LocalDate.now());
    }

    public EmprestimoModelo buscarEmprestimoPorId(Long emprestimoId) {
        return emprestimosRepository.findById(emprestimoId)
                .orElseThrow(() -> new NotFoundException("Empréstimo Not Found"));
    }

    public void validarDevolucao(EmprestimoModelo emprestimo, ClienteModelo cliente, LivroModelo livro) {
        if (emprestimo.getDataDevolucao() != null) {
            throw new IllegalStateException("Empréstimo already returned");
        }
        if (!emprestimo.getCliente().equals(cliente) || !emprestimo.getLivro().equals(livro)) {
            throw new IllegalStateException("Mismatch between loan details and provided IDs");
        }
    }

    public void verificarEmprestimosPendentes(LivroModelo livro) {
        List<EmprestimoModelo> emprestimos = emprestimosRepository.findByLivro(livro);
        for (EmprestimoModelo emprestimo : emprestimos) {
            if (emprestimo.getDataDevolucao() == null) {
                throw new IllegalStateException("Existem empréstimos pendentes para este livro");
            }
        }
    }
}
