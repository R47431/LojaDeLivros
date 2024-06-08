package com.example.sistemaBiblioteca.service;

import jakarta.persistence.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.sistemaBiblioteca.util.FileValidator;
import com.example.sistemaBiblioteca.util.LoanValidator;
import com.example.sistemaBiblioteca.dto.LivroDto;
import com.example.sistemaBiblioteca.model.LivroModelo;
import com.example.sistemaBiblioteca.repository.LivroRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
public class LivroService {

    private final ModelMapper mapper;
    private final LivroRepository livroRepository;
    private final FileValidator fileValidator;
    private final LoanValidator loanValidator;

    public LivroService(LivroRepository livroRepository, ModelMapper mapper, FileValidator fileValidator,
            LoanValidator loanValidator) {
        this.livroRepository = livroRepository;
        this.mapper = mapper;
        this.fileValidator = fileValidator;
        this.loanValidator = loanValidator;
    }

    /**
     * Obtém um livro cadastrado.
     * 
     * @param livroId o ID do livro a ser obtido
     * @return o livro correspondente ao ID fornecido
     * @throws EntityNotFoundException se o livro não for encontrado
     */
    public LivroModelo verLivro(long livroId) {
        return loanValidator.buscarLivroPorId(livroId);
    }

    /**
     * Cadastra um novo livro no sistema, incluindo a imagem do livro.
     *
     * @param livroDto o DTO do livro a ser cadastrado
     * @param imagem   a imagem do livro
     * @return o livro cadastrado
     * @throws IOException se ocorrer um erro de I/O durante a cópia da imagem
     */
    public LivroModelo cadastralivro(LivroDto livroDto, MultipartFile imagem) throws IOException {
        LivroModelo livroModelo = mapper.map(livroDto, LivroModelo.class);
        fileValidator.validarImagem(imagem);

        String fileType = fileValidator.getFileType(Objects.requireNonNull(imagem.getContentType()));
        String imageDirectory = fileValidator.getImageDirectory(livroModelo, fileType);

        Files.copy(imagem.getInputStream(), Paths.get(imageDirectory), StandardCopyOption.REPLACE_EXISTING);
        livroModelo.setImagemDoLivro(livroModelo.getTitulo() + fileType);

        return livroRepository.save(livroModelo);
    }

    /**
     * Altera um livro no sistema, incluindo a imagem do livro.
     *
     * @param livroId  o ID do livro a ser alterado
     * @param imagem   a nova imagem do livro
     * @param livroDto os novos dados do livro
     * @return o livro alterado
     * @throws IOException se ocorrer um erro de I/O durante a cópia da nova imagem
     */
    public LivroModelo alteraLivro(long livroId, MultipartFile imagem, LivroDto livroDto) throws IOException {
        LivroModelo livro = loanValidator.buscarLivroPorId(livroId);
        String fileType;

        if (imagem != null && !imagem.isEmpty()) {
            fileValidator.validarImagem(imagem);
            fileType = fileValidator.getFileType(Objects.requireNonNull(imagem.getContentType()));
            String imageDiretory = fileValidator.getImageDirectory(livro, fileType);
            fileValidator.copiarImagem(imagem, imageDiretory);
        }else {
            fileType = fileValidator.getFileTypeFromImagemDoLivro(livro);
        }
        LivroModelo livroModelo = mapper.map(livroDto, LivroModelo.class);
        livroModelo.setImagemDoLivro(livroModelo.getTitulo() + fileType);
        return livroRepository.save(livroModelo);
    }

    /**
     * Deleta um livro do sistema pelo ID.
     *
     * @param id o ID do livro a ser deletado
     * @throws IllegalStateException se houver empréstimos em aberto para o livro ou
     *                               se houver falha ao excluir a imagem do livro
     */
    public void deletaLivro(Long id) {
        LivroModelo livroOptional = loanValidator.buscarLivroPorId(id);
        loanValidator.verificarEmprestimosPendentes(livroOptional);
        fileValidator.deletarImagem(livroOptional);
        livroRepository.deleteById(id);
    }
}