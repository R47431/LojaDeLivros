package com.example.sistemaBiblioteca.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.sistemaBiblioteca.repository.ClienteRepository;
import com.example.sistemaBiblioteca.repository.EmprestimosRepository;
import com.example.sistemaBiblioteca.dto.ClienteDTO;
import com.example.sistemaBiblioteca.global.exception.NotFoundException;
import com.example.sistemaBiblioteca.model.ClienteModelo;
import com.example.sistemaBiblioteca.model.EmprestimoModelo;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ModelMapper mapper;
    private final EmprestimosRepository emprestimosRepository;

    public ClienteService(
            ClienteRepository clienteRepository,
            ModelMapper mapper,
            EmprestimosRepository emprestimosRepository) {
        this.clienteRepository = clienteRepository;
        this.mapper = mapper;
        this.emprestimosRepository = emprestimosRepository;
    }

    public Optional<ClienteModelo> encontrarClientre(Long clienteId) {
        Optional<ClienteModelo> clienteOptional = clienteRepository.findById(clienteId);
        if (!clienteOptional.isPresent()) {
            throw new NotFoundException("Cliente nao Encontrado");
        }
        return clienteOptional;

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
    public void deletaCliente(long id) {
        Optional<ClienteModelo> clienteOptional = clienteRepository.findById(id);
        if (!clienteOptional.isPresent()) {
            throw new NotFoundException("Cliente nao Encontrado");
        }
        List<EmprestimoModelo> emprestimos = emprestimosRepository.findByCliente(clienteOptional.get());

        for (EmprestimoModelo emprestimo : emprestimos) {
            if (emprestimo.getDataDevolucao() == null) {
                throw new IllegalStateException("Não é possível excluir o cliente com empréstimos em aberto");
            }
        }

        // Exclui todos os empréstimos do cliente
        emprestimosRepository.deleteAll(emprestimos);
        // Exclui o cliente
        clienteRepository.deleteById(id);

    }

}
