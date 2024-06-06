package com.example.sistemaBiblioteca.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.example.sistemaBiblioteca.global.exception.NotFoundException;
import com.example.sistemaBiblioteca.model.ClienteModelo;
import com.example.sistemaBiblioteca.model.EmprestimoModelo;
import com.example.sistemaBiblioteca.model.LivroModelo;
import com.example.sistemaBiblioteca.repository.ClienteRepository;
import com.example.sistemaBiblioteca.repository.EmprestimosRepository;
import com.example.sistemaBiblioteca.repository.LivroRepository;

@Service
public class EmprestimoService {

    private final EmprestimosRepository emprestimosRepository;
    private final LivroRepository livroRepository;
    private final ClienteRepository clienteRepository;

    public EmprestimoService(EmprestimosRepository emprestimosRepository, LivroRepository livroRepository,
            ClienteRepository clienteRepository) {
        this.livroRepository = livroRepository;
        this.clienteRepository = clienteRepository;
        this.emprestimosRepository = emprestimosRepository;

    }

    /**
     * Metodo para procura e valida ClienteId e LivroId
     * para salvar
     * 
     * @param clienteId
     * @param livroId
     * @return Retorna o emprestimo salvo
     */
    public EmprestimoModelo emprestimo(Long clienteId, Long livroId) {
        if (clienteId == null || livroId == null) {
            throw new IllegalArgumentException("ClienteId, LivroId must not be null");
        }
        ClienteModelo cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente Not Found"));
        LivroModelo livro = livroRepository.findById(livroId)
                .orElseThrow(() -> new IllegalArgumentException("Livro Not Found"));

        
        EmprestimoModelo emprestimoModelo = new EmprestimoModelo(cliente, livro, LocalDate.now());

        return emprestimosRepository.save(emprestimoModelo);
    }

    /**
     * Metodo para procura e valida ClienteId, LivroId e EmprestimoId
     * para altera
     * 
     * @param clienteId
     * @param livroId
     * @param emprestimoId
     * @return Retorna o emprestimo alterado
     */
    public EmprestimoModelo devolucao(Long clienteId, Long livroId, Long emprestimoId) {
        if (clienteId == null || livroId == null || emprestimoId == null) {
            throw new IllegalArgumentException("ClienteId, LivroId, and EmprestimoId must not be null");
        }
        ClienteModelo cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new NotFoundException("Cliente Not Found"));
        LivroModelo livro = livroRepository.findById(livroId)
                .orElseThrow(() -> new NotFoundException("Livro Not Found"));
        EmprestimoModelo emprestimo = emprestimosRepository.findById(emprestimoId)
                .orElseThrow(() -> new NotFoundException("Empréstimo Not Found"));

        if (emprestimo.getDataDevolucao() != null) {
            throw new IllegalStateException("Empréstimo already returned");
        }
        if (!emprestimo.getCliente().equals(cliente) || !emprestimo.getLivro().equals(livro)) {
            throw new IllegalStateException("Mismatch between loan details and provided IDs");
        }
        emprestimo.setDataDevolucao(LocalDate.now());
        return emprestimosRepository.save(emprestimo);

    }

  

    /**
     * Metodo para DELETA o emprestimo
     * 
     * @param emprestimoId
     */
    public void deletaEmprestimo(Long emprestimoId) {

        emprestimosRepository.deleteById(emprestimoId);
    }

}
