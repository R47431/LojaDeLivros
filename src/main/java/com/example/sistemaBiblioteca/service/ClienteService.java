package com.example.sistemaBiblioteca.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.sistemaBiblioteca.repository.ClienteRepository;
import com.example.sistemaBiblioteca.repository.EmprestimosRepository;
import com.example.sistemaBiblioteca.util.LoanValidator;

import jakarta.transaction.Transactional;

import com.example.sistemaBiblioteca.dto.ClienteDTO;
import com.example.sistemaBiblioteca.global.exception.NotFoundException;
import com.example.sistemaBiblioteca.model.ClienteModelo;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ModelMapper mapper;
    private final EmprestimosRepository emprestimosRepository;
    private final LoanValidator loanValidator;

    public ClienteService(
            ClienteRepository clienteRepository,
            ModelMapper mapper,
            EmprestimosRepository emprestimosRepository,
            LoanValidator loanValidator) {
        this.clienteRepository = clienteRepository;
        this.mapper = mapper;
        this.emprestimosRepository = emprestimosRepository;
        this.loanValidator = loanValidator;
    }

    public ClienteModelo encontrarClientre(Long clienteId) {
        return loanValidator.buscarClientePorId(clienteId);

    }

    /**
     * Cadastra o Cliente
     * 
     * @param clienteDTO
     * @return cliente salvo
     */
    public ClienteModelo cadastraCliente(ClienteDTO clienteDTO) {
        ClienteModelo clienteModelo = mapper.map(clienteDTO, ClienteModelo.class);

        ClienteModelo savedClienteModelo = clienteRepository.save(clienteModelo);
        return savedClienteModelo;
    }

    /**
     * Deleta o cliente pelo ID
     * 
     * @param id do cliente
     */
    @Transactional
    public void deletaCliente(long id) {
        Optional<ClienteModelo> clienteOptional = clienteRepository.findById(id);
        if (!clienteOptional.isPresent()) {
            throw new NotFoundException("Cliente nao Encontrado");
        }
        ClienteModelo cliente = clienteOptional.get();
        loanValidator.verificarEmprestimosPendentes(cliente);

        // Exclui todos os empréstimos do cliente
        emprestimosRepository.deleteByCliente(cliente);
        // Exclui o cliente
        clienteRepository.deleteById(id);

    }
}
