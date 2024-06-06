package com.example.sistemaBiblioteca.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.example.sistemaBiblioteca.model.ClienteModelo;
import com.example.sistemaBiblioteca.model.EmprestimoModelo;
import com.example.sistemaBiblioteca.model.LivroModelo;
import com.example.sistemaBiblioteca.repository.EmprestimosRepository;
import com.example.sistemaBiblioteca.util.LoanValidator;

@Service
public class EmprestimoService {
    private final EmprestimosRepository emprestimosRepository;
    private final LoanValidator loanValidator;

    public EmprestimoService(EmprestimosRepository emprestimosRepository, LoanValidator loanValidato) {
        this.emprestimosRepository = emprestimosRepository;
        this.loanValidator = loanValidato;
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
        ClienteModelo cliente = loanValidator.buscarClientePorId(clienteId);
        LivroModelo livro = loanValidator.buscarLivroPorId(livroId);

        EmprestimoModelo emprestimoModelo = loanValidator.criarEmprestimo(cliente, livro);

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
        ClienteModelo cliente = loanValidator.buscarClientePorId(clienteId);
        LivroModelo livro = loanValidator.buscarLivroPorId(livroId);
        EmprestimoModelo emprestimo = loanValidator.buscarEmprestimoPorId(emprestimoId);

        loanValidator.validarDevolucao(emprestimo, cliente, livro);
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
