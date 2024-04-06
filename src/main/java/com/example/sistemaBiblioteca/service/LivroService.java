package com.example.sistemaBiblioteca.service;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import com.example.sistemaBiblioteca.dto.LivroDto;
import com.example.sistemaBiblioteca.global.ValidaCliente;
import com.example.sistemaBiblioteca.model.LivroModelo;

import com.example.sistemaBiblioteca.repository.LivroRepository;

import jakarta.transaction.Transactional;

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


   @Autowired
    private ValidaCliente validaCliente;

    private final LivroRepository livroRepository;


    @Autowired
    public LivroService(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;

    }

    @Value("${app.img.dir}")
    private String imgdir;

    protected String diretorioDaImagem(LivroModelo livroModelo) {
        String nomeImagem = livroModelo.getLivroId() + ".jpg";
        return imgdir + File.separator + nomeImagem;
    }

  /*@Transactional
    public LivroDto verLivro(long livroId) {
        LivroModelo livro = livroRepository.findById(livroId)
                .orElseThrow(() -> new IllegalArgumentException("Livro Not Found"));
        return livroModelo;
    }
   */  

    public LivroModelo cadastralivro(LivroDto livroDto, MultipartFile imagem) throws IOException {
           LivroModelo livroModelo = mapper.map(livroDto, LivroModelo.class);
         if (imagem == null || imagem.isEmpty()) {
            LOGGER.error("null");
            throw new IllegalArgumentException("sem imagem");
        }
        validaCliente.validaLivro(livroDto);

        livroRepository.save(livroModelo);

        livroModelo.setImagemDoLivro(livroModelo.getLivroId() + ".jpg");

        String diretorio = diretorioDaImagem(livroModelo);
        Files.copy(imagem.getInputStream(), Paths.get(diretorio),
                StandardCopyOption.REPLACE_EXISTING);


        return livroRepository.save(livroModelo);
    }

    @Modifying
    public LivroModelo alteraLivro(long livroId, MultipartFile imagem, LivroDto livroDto) throws IOException {
        LivroModelo livroExistente = livroRepository.findById(livroId)
                .orElseThrow(() -> new RuntimeException("Livro não encontrado"));

        LivroModelo livroModelo = mapper.map(livroDto, LivroModelo.class);

        if (livroModelo == null) {
            throw new IllegalArgumentException("LivroModelo não pode ser nulo");
        }
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

            livroRepository.deleteById(livroId);

        }

    }
} 