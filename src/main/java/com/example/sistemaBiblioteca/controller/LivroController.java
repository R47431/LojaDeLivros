package com.example.sistemaBiblioteca.controller;

import com.example.sistemaBiblioteca.model.LivroModelo;
import com.example.sistemaBiblioteca.repository.LivroRepository;
import com.example.sistemaBiblioteca.service.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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
    public ResponseEntity<?> cadastraLivro(LivroModelo livroModelo, MultipartFile imagem) {
        try {
            livroRepository.save(livroModelo);
            livroModelo.setImagemDoLivro(livroModelo.getLivroId() + ".jpg");
            String diretorio = livroService.salvaImagem(livroModelo);
            Files.copy(imagem.getInputStream(), Paths.get(diretorio), StandardCopyOption.REPLACE_EXISTING);
            livroRepository.save(livroModelo);
            new ResponseEntity<>(HttpStatus.CREATED);
            return ResponseEntity.ok(livroModelo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocorreu um erro ao processar o Cadastro.");
        }
    }

    @PutMapping
    public ResponseEntity<?> alteraLivro(LivroModelo livroModelo, MultipartFile imagem) {
        try {
            livroRepository.save(livroModelo);
            livroModelo.setImagemDoLivro(livroModelo.getLivroId() + ".jpg");
            String diretorio = livroService.salvaImagem(livroModelo);
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
        Optional<LivroModelo> obterId = livroRepository.findById(livroId);
        if (obterId.isPresent()) {
            String deletaImagem = livroService.salvaImagem(obterId.get());

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
}