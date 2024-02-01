package com.example.sistemaBiblioteca.livros.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.sistemaBiblioteca.model.LivroModelo;
import com.example.sistemaBiblioteca.livros.repository.LivroRepository;
import com.example.sistemaBiblioteca.livros.service.LivroService;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

@RestController
@RequestMapping("/biblioteca")
public class LivroController {

    private final LivroRepository livroRepository;

    @Autowired
    private LivroService livroService;

    @Autowired
    public LivroController(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    @GetMapping
    public Iterable<LivroModelo> listaLivro(
            LivroModelo livroModelo) {
        return livroRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<?> cadastraLivro(LivroModelo livroModelo, @RequestPart("imagem") MultipartFile imagem) {
        try {
            if (livroModelo == null) {
                throw new IllegalArgumentException("ID não pode ser nulo");
            }

            livroRepository.save(livroModelo);
            livroModelo.setImagemDoLivro(livroModelo.getLivroId() + ".jpg");
            String diretorio = livroService.diretorioDaImagem(livroModelo);
            Files.copy(imagem.getInputStream(), Paths.get(diretorio), StandardCopyOption.REPLACE_EXISTING);
            livroRepository.save(livroModelo);

            return ResponseEntity.ok(livroModelo);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocorreu um erro ao processar o Cadastro." + e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> alteraLivro(LivroModelo livroModelo, MultipartFile imagem) {
        try {
            if (livroModelo == null) {
                throw new IllegalArgumentException("LivroModelo não pode ser nulo");
            }
            livroRepository.save(livroModelo);
            livroModelo.setImagemDoLivro(livroModelo.getLivroId() + ".jpg");
            String diretorio = livroService.diretorioDaImagem(livroModelo);
            Files.copy(imagem.getInputStream(), Paths.get(diretorio), StandardCopyOption.REPLACE_EXISTING);
            livroRepository.save(livroModelo);
            new ResponseEntity<>(HttpStatus.ACCEPTED);
            return ResponseEntity.ok(livroModelo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocorreu um erro ao processar o Alteracao.");
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