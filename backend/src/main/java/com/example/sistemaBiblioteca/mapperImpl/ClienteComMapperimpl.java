package com.example.sistemaBiblioteca.mapperImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.sistemaBiblioteca.dto.ClienteComEmprestimosDto;
import com.example.sistemaBiblioteca.dto.ClienteDTO;
import com.example.sistemaBiblioteca.dto.EmprestimoComLivroDto;
import com.example.sistemaBiblioteca.dto.LivroDto;
import com.example.sistemaBiblioteca.mapper.ClientMapper;
import com.example.sistemaBiblioteca.model.ClienteModelo;
import com.example.sistemaBiblioteca.model.EmprestimoModelo;
import com.example.sistemaBiblioteca.model.LivroModelo;

@Component
public class ClienteComMapperimpl implements ClientMapper {
    @Override
    public ClienteComEmprestimosDto toClienteComEmprestimosDto(ClienteModelo clienteModelo) {
        if (clienteModelo == null) {
            return null;
        }

        Long clienteId = clienteModelo.getClienteId();
        String nome = clienteModelo.getNome();
        String email = clienteModelo.getEmail();
        List<EmprestimoComLivroDto> emprestimos = emprestimoModeloListToEmprestimoComLivroDtoList(
                clienteModelo.getEmprestimos());

        ClienteComEmprestimosDto clienteComEmprestimosDto = new ClienteComEmprestimosDto(clienteId, nome, email,
                emprestimos);
        return clienteComEmprestimosDto;
    }

    @Override
    public ClienteDTO toClienteDTO(ClienteModelo clienteModelo) {
        if (clienteModelo == null) {
            return null;
        }
        Long clienteId = clienteModelo.getClienteId();
        String nome = clienteModelo.getNome();
        LocalDate dataDeNascimento = clienteModelo.getDataDeNascimento();
        Integer numeroDeTelefone = clienteModelo.getNumeroDeTelefone();
        String email = clienteModelo.getEmail();

        ClienteDTO clienteDTO = new ClienteDTO(clienteId, nome, dataDeNascimento, numeroDeTelefone, email);
        return clienteDTO;
    }

    @Override
    public ClienteModelo toClienteModelo(ClienteDTO clienteDTO) {
        if (clienteDTO == null) {
            return null;
        }
        Long clienteId = clienteDTO.clienteId();
        String nome = clienteDTO.nome();
        LocalDate dataDeNascimento = clienteDTO.dataDeNascimento();
        Integer numeroDeTelefone = clienteDTO.numeroDeTelefone();
        String email = clienteDTO.email();

        ClienteModelo clienteModelo = new ClienteModelo(clienteId, nome, dataDeNascimento, numeroDeTelefone, email);
        return clienteModelo;

    }

    protected LivroDto ToLivroDto(LivroModelo livroModelo) {
        if (livroModelo == null) {
            return null;
        }

        Long livroId = livroModelo.getLivroId();
        String imagemDoLivro = livroModelo.getImagemDoLivro();
        String titulo = livroModelo.getTitulo();
        String nomeDoAutor = livroModelo.getNomeDoAutor();
        String nacionalidade = livroModelo.getNacionalidade();
        LocalDate data = livroModelo.getData();
        String editora = livroModelo.getEditora();
        String genero = livroModelo.getGenero();
        String sinopse = livroModelo.getSinopse();

        LivroDto livroDto = new LivroDto(livroId, imagemDoLivro, titulo, nomeDoAutor, nacionalidade, data, editora,
                genero, sinopse);

        return livroDto;
    }

    protected EmprestimoComLivroDto emprestimoModeloToEmprestimoComLivroDto(EmprestimoModelo emprestimoModelo) {
        if (emprestimoModelo == null) {
            return null;
        }

        Long emprestimoId = emprestimoModelo.getEmprestimoId();
        LocalDate dataEmprestimo = emprestimoModelo.getDataEmprestimo();
        LocalDate dataDevolucao = emprestimoModelo.getDataDevolucao();
        LivroDto livro = ToLivroDto(emprestimoModelo.getLivro());

        EmprestimoComLivroDto emprestimoComLivroDto = new EmprestimoComLivroDto(emprestimoId, dataEmprestimo,
                dataDevolucao, livro);

        return emprestimoComLivroDto;
    }

    protected List<EmprestimoComLivroDto> emprestimoModeloListToEmprestimoComLivroDtoList(List<EmprestimoModelo> list) {
        if (list == null) {
            return null;
        }

        List<EmprestimoComLivroDto> list1 = new ArrayList<EmprestimoComLivroDto>(list.size());
        for (EmprestimoModelo emprestimoModelo : list) {
            list1.add(emprestimoModeloToEmprestimoComLivroDto(emprestimoModelo));
        }

        return list1;
    }

}
