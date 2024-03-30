package com.example.sistemaBiblioteca.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.sistemaBiblioteca.dto.LivroDto;
import com.example.sistemaBiblioteca.exception.NotFoundException;
import com.example.sistemaBiblioteca.mapper.LivroMapper;
import com.example.sistemaBiblioteca.model.LivroModelo;
import com.example.sistemaBiblioteca.repository.LivroRepository;
import com.example.sistemaBiblioteca.service.GlobalService;
import com.example.sistemaBiblioteca.service.LivroService;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

@RestController
@RequestMapping("/livro")
public class LivroController {
    //TODO fazer melhora de codigo validadores no service
    private static final Logger LOGGER = LoggerFactory.getLogger(LivroService.class);

    private final LivroRepository livroRepository;
    private final LivroMapper livroMapper;
    private final GlobalService globalService;
    private final LivroService livroService;

    @Autowired
    public LivroController(LivroRepository livroRepository, GlobalService globalService, LivroMapper livroMapper,
            LivroService livroService) {
        this.livroRepository = livroRepository;
        this.livroMapper = livroMapper;
        this.globalService = globalService;
        this.livroService = livroService;
    }

    @GetMapping("/lista")
    public Iterable<LivroModelo> listaLivro() {
        return livroRepository.findAll();
    }

    @GetMapping("/{livroId}")
    public ResponseEntity<?> listaLivro(@PathVariable Long livroId) {
        try {
            LivroDto livroDto = livroService.verLivro(livroId);
            return ResponseEntity.ok(livroDto);
        } catch (IllegalArgumentException e) {
            LOGGER.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

        }

    }

    @PostMapping
    public ResponseEntity<?> cadastraLivro(LivroDto livroDto, @RequestParam("imagem") MultipartFile imagem) {
        try {
            LivroModelo livroModelo = livroMapper.toLivroModelo(livroDto);
            if (imagem == null || imagem.isEmpty()) {
                return ResponseEntity.badRequest().body("seleciona imagem");

            }
            livroRepository.save(livroModelo);
            livroModelo.setImagemDoLivro(livroModelo.getLivroId() + ".jpg");
            String diretorio = livroService.diretorioDaImagem(livroModelo);

            Files.copy(imagem.getInputStream(), Paths.get(diretorio), StandardCopyOption.REPLACE_EXISTING);
            livroRepository.save(livroModelo);

            return ResponseEntity.status(HttpStatus.CREATED).body(livroModelo);

        } catch (NullPointerException e) {
            throw new NullPointerException("Entidade null");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocorreu um erro ao processar o Cadastro." + e.getMessage());
        }
    }

    @PutMapping("/{livroId}")
    public ResponseEntity<?> alteraLivro(@PathVariable Long livroId, LivroDto livroDto,
            MultipartFile imagem) {
        try {

            LivroModelo livroExistente = livroRepository.findById(livroId)
                    .orElseThrow(() -> new RuntimeException("Livro não encontrado"));

            LivroModelo livroModelo = livroMapper.toLivroModelo(livroDto);

            if (livroModelo == null) {
                throw new IllegalArgumentException("LivroModelo não pode ser nulo");
            }

            if (imagem != null && !imagem.isEmpty()) {
                livroModelo.setImagemDoLivro(livroModelo.getLivroId() + ".jpg");
                String diretorio = livroService.diretorioDaImagem(livroModelo);
                Files.copy(imagem.getInputStream(), Paths.get(diretorio), StandardCopyOption.REPLACE_EXISTING);
            } else {
                livroModelo.setImagemDoLivro(livroExistente.getImagemDoLivro());
            }

            livroRepository.save(livroModelo);
            return ResponseEntity.ok(livroModelo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocorreu um erro ao processar a alteração.");
        }
    }

    @DeleteMapping(path = "/{livroId}")
    public ResponseEntity<?> deletarLivro(@PathVariable Long livroId) {
        if (livroId == null) {
            throw new IllegalArgumentException("livroId não pode ser nulo");
        }
        Optional<LivroModelo> obterId = livroRepository.findById(livroId);
        if (obterId.isPresent()) {
            String deletaImagem = livroService.diretorioDaImagem(obterId.get());
            File imagem = new File(deletaImagem);
            if (imagem.exists()) {
                imagem.delete();
            }
            livroRepository.deleteById(livroId);
            return ResponseEntity.ok().body("livro deletado");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /*
     * DESBLOQUEAR PARA LIMPA
     * 
     * @DeleteMapping("/all")
     * public void deletarAll() {
     * Iterable<LivroModelo> obterId = livroRepository.findAll();
     * for (LivroModelo livroModelo : obterId) {
     * String deletaImagem = livroService.diretorioDaImagem(livroModelo);
     * 
     * File imagem = new File(deletaImagem);
     * if (imagem.exists()) {
     * imagem.delete();
     * }
     * }
     * livroRepository.deleteAll();
     * 
     * }
     */

}