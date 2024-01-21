package com.example.sistemaBiblioteca.mapperImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.sistemaBiblioteca.dto.EmprestimoComLivroDto;
import com.example.sistemaBiblioteca.dto.LivroDto;
import com.example.sistemaBiblioteca.mapper.EmprestimoMapper;
import com.example.sistemaBiblioteca.model.EmprestimoModelo;
import com.example.sistemaBiblioteca.model.LivroModelo;

@Component
public class EmprestimoMapperImpl implements EmprestimoMapper {
    @Override
    public List<EmprestimoComLivroDto> toDtoList(List<EmprestimoModelo> emprestimoModelos) {
        if (emprestimoModelos == null) {
            return null;
        }

        List<EmprestimoComLivroDto> list = new ArrayList<EmprestimoComLivroDto>(emprestimoModelos.size());
        for (EmprestimoModelo emprestimoModelo : emprestimoModelos) {
            list.add(toDto(emprestimoModelo));
        }

        return list;
    }

    @Override
    public EmprestimoComLivroDto toDto(EmprestimoModelo emprestimoModelo) {
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
}
