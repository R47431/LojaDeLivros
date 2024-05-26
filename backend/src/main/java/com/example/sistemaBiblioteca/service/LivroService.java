package com.example.sistemaBiblioteca.service;

import jakarta.persistence.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.sistemaBiblioteca.dto.LivroDto;
import com.example.sistemaBiblioteca.model.LivroModelo;
import com.example.sistemaBiblioteca.repository.LivroRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

@Service
public class LivroService {
    // TODO teste uuid para nome da imagem
    private static final Logger LOGGER = LoggerFactory.getLogger(LivroService.class);

    private final ModelMapper mapper;

    private final LivroRepository livroRepository;

    public LivroService(LivroRepository livroRepository, ModelMapper mapper) {
        this.livroRepository = livroRepository;
        this.mapper = mapper;

    }

    private final String IMG_DIR = Paths.get(System.getProperty("user.dir")).getParent().resolve("img").toString();

    private String sanitizeFileName(String input) {
        return input.replaceAll("[^a-zA-Z0-9\\-_.]+", "_");
    }

    public String diretorioDaImagem(LivroModelo livroModelo) {
        return IMG_DIR + File.separator + sanitizeFileName(livroModelo.getTitulo()) + ".jpg";
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
        livroModelo.setImagemDoLivro(livroModelo.getTitulo() + ".jpg");

        Files.copy(imagem.getInputStream(), Paths.get(diretorioDaImagem(livroModelo)),
                StandardCopyOption.REPLACE_EXISTING);

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
            livroModelo.setImagemDoLivro(livroModelo.getTitulo() + ".jpg");
            Files.copy(imagem.getInputStream(), Paths.get(diretorioDaImagem(livroModelo)),
                    StandardCopyOption.REPLACE_EXISTING);
        } else {
            livroModelo.setImagemDoLivro(livroExistente.getImagemDoLivro());

        }
        return livroRepository.save(livroModelo);
    }

    /**
     * Metodo que deleta Livro pelo Id
     *
     * @param livroId
     */
    public void deletaLivro(Long livroId) {
        if (livroId == null) {
            throw new IllegalArgumentException("livroId não pode ser nulo");
        }
        Optional<LivroModelo> obterId = livroRepository.findById(livroId);
        if (obterId.isPresent()) {
            String deletaImagem = diretorioDaImagem(obterId.get());
            File imagem = new File(deletaImagem);
            imagem.delete();

            livroRepository.deleteById(livroId);

        }
    }

}