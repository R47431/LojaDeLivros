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

    /**
     * Busca um cliente por ID.
     *
     * @param id o ID do cliente a ser buscado
     * @return o cliente correspondente ao ID fornecido
     * @throws NotFoundException se o cliente não for encontrado
     */
    public ClienteModelo buscarClientePorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cliente Not Found"));
    }

    /**
     * Busca um livro por ID.
     *
     * @param id o ID do livro a ser buscado
     * @return o livro correspondente ao ID fornecido
     * @throws NotFoundException se o livro não for encontrado
     */
    public LivroModelo buscarLivroPorId(Long id) {
        return livroRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book Not Found"));
    }

    /**
     * Cria um novo empréstimo.
     *
     * @param cliente o cliente para o empréstimo
     * @param livro   o livro para o empréstimo
     * @return o empréstimo criado
     */
    public EmprestimoModelo criarEmprestimo(ClienteModelo cliente, LivroModelo livro) {
        return new EmprestimoModelo(cliente, livro, LocalDate.now());
    }

    /**
     * Busca um empréstimo por ID.
     *
     * @param emprestimoId o ID do empréstimo a ser buscado
     * @return o empréstimo correspondente ao ID fornecido
     * @throws NotFoundException se o empréstimo não for encontrado
     */
    public EmprestimoModelo buscarEmprestimoPorId(Long emprestimoId) {
        return emprestimosRepository.findById(emprestimoId)
                .orElseThrow(() -> new NotFoundException("Empréstimo Not Found"));
    }

    /**
     * Valida a devolução de um empréstimo.
     *
     * @param emprestimo o empréstimo a ser validado
     * @param cliente    o cliente associado ao empréstimo
     * @param livro      o livro associado ao empréstimo
     * @throws IllegalStateException se o empréstimo já foi devolvido ou se houver
     *                               uma inconsistência entre os detalhes do
     *                               empréstimo e os IDs fornecidos
     */
    public void validarDevolucao(EmprestimoModelo emprestimo, ClienteModelo cliente, LivroModelo livro) {
        if (emprestimo.getDataDevolucao() != null) {
            throw new IllegalStateException("Empréstimo already returned");
        }
        if (!emprestimo.getCliente().equals(cliente) || !emprestimo.getLivro().equals(livro)) {
            throw new IllegalStateException("Mismatch between loan details and provided IDs");
        }
    }

    /**
     * Verifica se há empréstimos pendentes para um livro.
     *
     * @param livro o livro para o qual os empréstimos pendentes devem ser
     *              verificados
     * @return 
     * @throws IllegalStateException se houver empréstimos pendentes para o livro
     */
    public ClienteModelo verificarEmprestimosPendentes(Object objectModelo) {
        if (objectModelo instanceof LivroModelo) {
            verificarEmprestimosPendentesParaLivro((LivroModelo) objectModelo);
        } else if (objectModelo instanceof ClienteModelo) {
            verificarEmprestimosPendentesParaCliente((ClienteModelo) objectModelo);
        } else {
            throw new IllegalArgumentException("Tipo de objeto inválido. Deve ser LivroModelo ou ClienteModelo.");
        }
        return null;
    }

    private void verificarEmprestimosPendentesParaLivro(LivroModelo livro) {
        List<EmprestimoModelo> emprestimos = emprestimosRepository.findByLivro(livro);
        for (EmprestimoModelo emprestimo : emprestimos) {
            if (emprestimo.getDataDevolucao() == null) {
                throw new IllegalStateException("Existem empréstimos pendentes para este livro");
            }
        }
    }

    private void verificarEmprestimosPendentesParaCliente(ClienteModelo cliente) {
        List<EmprestimoModelo> emprestimos = emprestimosRepository.findByCliente(cliente);
        for (EmprestimoModelo emprestimo : emprestimos) {
            if (emprestimo.getDataDevolucao() == null) {
                throw new IllegalStateException("Existem empréstimos pendentes para este livro");
            }
        }
    }
}
