package com.example.sistemaBiblioteca.service;

import jakarta.persistence.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(LivroService.class);

    @Autowired
    private ModelMapper mapper;

    private final LivroRepository livroRepository;

    @Autowired
    public LivroService(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;

    }

    String dirHome = System.getProperty("user.home");
    private String imgdir = dirHome + "/Documentos/2Biblioteca/frontend/src/assets/img";

    public String diretorioDaImagem(LivroModelo livroModelo) {
        String nomeImagem = livroModelo.getLivroId() + ".jpg";
        return imgdir + File.separator + nomeImagem;
    }

    public LivroModelo verLivro(long livroId) {
        Optional<LivroModelo> livro = livroRepository.findById(livroId);
        return livro.orElseThrow(() -> new IllegalArgumentException("Livro Not Found"));
    }

    /**
     * Cadastra um novo livro no sistema, incluindo a imagem do livro.
     * 
     * @param livroDto usado para cadastra
     * @param imagem   parametro usado para salva imagem
     * @return retona o livro com a alteraçao
     * @throws IOException lança exception da imagem
     */
    public LivroModelo cadastralivro(LivroDto livroDto, MultipartFile imagem) throws IOException {
        
        LivroModelo livroModelo = mapper.map(livroDto, LivroModelo.class);
        if (imagem == null || imagem.isEmpty()) {
            LOGGER.error("Imagem nula ou vazia");
            throw new IllegalArgumentException("sem imagem");
        }
        livroRepository.save(livroModelo);

        livroModelo.setImagemDoLivro(livroModelo.getLivroId() + ".jpg");

        String diretorio = diretorioDaImagem(livroModelo);

        Files.copy(imagem.getInputStream(), Paths.get(diretorio),
                StandardCopyOption.REPLACE_EXISTING);

        return livroRepository.save(livroModelo);
    }

    /**
     * Altera livro no sistema, incluindo a imagem do livro.
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
            livroModelo.setImagemDoLivro(livroModelo.getLivroId() + ".jpg");
            Files.copy(imagem.getInputStream(), Paths.get(diretorioDaImagem(livroModelo)),
                    StandardCopyOption.REPLACE_EXISTING);
        } else {
            livroModelo.setImagemDoLivro(livroExistente.getImagemDoLivro());

        }
        return livroRepository.save(livroModelo);
    }

    @Modifying
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