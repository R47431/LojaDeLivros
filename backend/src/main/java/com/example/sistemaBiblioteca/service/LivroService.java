package com.example.sistemaBiblioteca.service;

import com.example.sistemaBiblioteca.util.FileValidator;
import jakarta.persistence.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.sistemaBiblioteca.dto.LivroDto;
import com.example.sistemaBiblioteca.model.EmprestimoModelo;
import com.example.sistemaBiblioteca.model.LivroModelo;
import com.example.sistemaBiblioteca.repository.EmprestimosRepository;
import com.example.sistemaBiblioteca.repository.LivroRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class LivroService {
    // TODO teste uuid para nome da imagem
    private static final Logger LOGGER = LoggerFactory.getLogger(LivroService.class);

    private final ModelMapper mapper;
    private final LivroRepository livroRepository;
    private final EmprestimosRepository emprestimosRepository;
    private final FileValidator fileValidator;


    public LivroService(LivroRepository livroRepository, ModelMapper mapper,
                        EmprestimosRepository emprestimosRepository, FileValidator fileValidator) {
        this.livroRepository = livroRepository;
        this.mapper = mapper;
        this.emprestimosRepository = emprestimosRepository;
        this.fileValidator = fileValidator;
    }

    /**
     * Obter o livro Cadastrados.
     * 
     * @param livroId
     * @return livro ou erro
     */
    public LivroModelo verLivro(long livroId) {
        Optional<LivroModelo> livro = livroRepository.findById(livroId);
        return livro.orElseThrow(() -> new IllegalArgumentException("Livro Not Found"));
    }

    /**
     * Metodo que cadastra um novo livro no sistema, incluindo a imagem do livro.
     *
     * @param livroDto usado para cadastra
     * @param imagem   parametro usado para salva imagem
     * @return retona o livro cadastrado
     * @throws IOException lança exception da imagem
     */
    public LivroModelo cadastralivro(LivroDto livroDto, MultipartFile imagem) throws IOException {
        LivroModelo livroModelo = mapper.map(livroDto, LivroModelo.class);
        if (imagem == null || imagem.isEmpty()) {
            LOGGER.error("Imagem nula ou vazia");
            throw new IllegalArgumentException("sem imagem");
        }

        fileValidator.validateFile(imagem);

        String fileType = fileValidator.getFileType(Objects.requireNonNull(imagem.getContentType())); // obtém a extensão do arquivo
        String imageDirectory = fileValidator.diretorioDaImagem(livroModelo, fileType); // obtém o diretório da imagem

        Files.copy(imagem.getInputStream(), Paths.get(imageDirectory), StandardCopyOption.REPLACE_EXISTING);
        livroModelo.setImagemDoLivro(livroModelo.getTitulo() + fileType); // Define o caminho da imagem no modelo de livro


        return livroRepository.save(livroModelo);
    }

    /**
     * Metodo que altera livro no sistema, incluindo a imagem do livro.
     *
     * @param livroDto usado para altera
     * @param imagem   parametro usado para altera imagem
     * @param livroId
     * @return
     * @throws IOException
     */
    public LivroModelo alteraLivro(long livroId, MultipartFile imagem, LivroDto livroDto) throws IOException {
        LivroModelo livroModelo = mapper.map(livroDto, LivroModelo.class);

        LivroModelo livroExistente = livroRepository.findById(livroId)
                .orElseThrow(() -> new EntityNotFoundException("Livro não encontrado"));

        if (imagem != null && !imagem.isEmpty()) {

            fileValidator.validateFile(imagem);
            String fileType = fileValidator.getFileType(Objects.requireNonNull(imagem.getContentType()));
            String imageDiretory = fileValidator.diretorioDaImagem(livroModelo, fileType);

            livroModelo.setImagemDoLivro(livroModelo.getTitulo() + fileType);
            Files.copy(imagem.getInputStream(), Paths.get(imageDiretory), StandardCopyOption.REPLACE_EXISTING);
        } else {
            livroModelo.setImagemDoLivro(livroExistente.getImagemDoLivro());

        }
        return livroRepository.save(livroModelo);
    }

    /**
     * Metodo que deleta Livro pelo Id
     *
     * @param id id do livro
     */
    public void deletaLivro(Long id) {
        Optional<LivroModelo> livroOptional = livroRepository.findById(id);

        if (livroOptional.isEmpty()) {
            throw new IllegalArgumentException("Livro não encontrado com o ID fornecido: " + id);
        }

        List<EmprestimoModelo> emprestimos = emprestimosRepository.findByLivro(livroOptional.get());
        for (EmprestimoModelo emprestimo : emprestimos) {
            if (emprestimo.getDataDevolucao() == null) {
                throw new IllegalStateException("Não é possível excluir o Cliente com empréstimos em aberto ");
            }
        }

        String fileType = fileValidator.getFile(livroOptional.get().getImagemDoLivro());
        String caminhoImagem = fileValidator.diretorioDaImagem(livroOptional.get(), fileType);
        File imagem = new File(caminhoImagem);
        if (imagem.exists() && !imagem.delete()) {
            throw new IllegalStateException("Falha ao excluir a imagem do livro: " + caminhoImagem);
        }

        emprestimosRepository.deleteAll();
        livroRepository.deleteById(id);
    }
}