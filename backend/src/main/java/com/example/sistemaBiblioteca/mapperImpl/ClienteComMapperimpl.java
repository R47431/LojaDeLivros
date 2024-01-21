package com.example.sistemaBiblioteca.mapperImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.sistemaBiblioteca.dto.ClienteComEmprestimosDto;
import com.example.sistemaBiblioteca.dto.EmprestimoComLivroDto;
import com.example.sistemaBiblioteca.dto.LivroDto;
import com.example.sistemaBiblioteca.mapper.ClienteMapper;
import com.example.sistemaBiblioteca.model.ClienteModelo;
import com.example.sistemaBiblioteca.model.EmprestimoModelo;
import com.example.sistemaBiblioteca.model.LivroModelo;

@Component
public class ClienteComMapperimpl implements ClienteMapper {
    @Override
    public ClienteComEmprestimosDto toDto(ClienteModelo clienteModelo) {
        if (clienteModelo == null) {
            return null;
        }

        Long clienteId = null;
        String nome = null;
        String email = null;
        List<EmprestimoComLivroDto> emprestimos = null;

        clienteId = clienteModelo.getClienteId();
        nome = clienteModelo.getNome();
        email = clienteModelo.getEmail();
        emprestimos = emprestimoModeloListToEmprestimoComLivroDtoList(clienteModelo.getEmprestimos());

        ClienteComEmprestimosDto clienteComEmprestimosDto = new ClienteComEmprestimosDto(clienteId, nome, email,
                emprestimos);

        return clienteComEmprestimosDto;
    }

    protected LivroDto livroModeloToLivroDto(LivroModelo livroModelo) {
        if (livroModelo == null) {
            return null;
        }

        Long livroId = null;
        String imagemDoLivro = null;
        String titulo = null;
        String nomeDoAutor = null;
        String nacionalidade = null;
        LocalDate data = null;
        String editora = null;
        String genero = null;
        String sinopse = null;

        livroId = livroModelo.getLivroId();
        imagemDoLivro = livroModelo.getImagemDoLivro();
        titulo = livroModelo.getTitulo();
        nomeDoAutor = livroModelo.getNomeDoAutor();
        nacionalidade = livroModelo.getNacionalidade();
        data = livroModelo.getData();
        editora = livroModelo.getEditora();
        genero = livroModelo.getGenero();
        sinopse = livroModelo.getSinopse();

        LivroDto livroDto = new LivroDto(livroId, imagemDoLivro, titulo, nomeDoAutor, nacionalidade, data, editora,
                genero, sinopse);

        return livroDto;
    }

    protected EmprestimoComLivroDto emprestimoModeloToEmprestimoComLivroDto(EmprestimoModelo emprestimoModelo) {
        if (emprestimoModelo == null) {
            return null;
        }

        Long emprestimoId = null;
        LocalDate dataEmprestimo = null;
        LocalDate dataDevolucao = null;
        LivroDto livro = null;

        emprestimoId = emprestimoModelo.getEmprestimoId();
        dataEmprestimo = emprestimoModelo.getDataEmprestimo();
        dataDevolucao = emprestimoModelo.getDataDevolucao();
        livro = livroModeloToLivroDto(emprestimoModelo.getLivro());

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
