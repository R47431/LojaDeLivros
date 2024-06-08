package com.example.sistemaBiblioteca.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.sistemaBiblioteca.dto.LivroDto;
import com.example.sistemaBiblioteca.model.LivroModelo;
import com.example.sistemaBiblioteca.repository.LivroRepository;
import com.example.sistemaBiblioteca.service.LivroService;

import java.io.IOException;

import javax.validation.Valid;

@RestController
@RequestMapping("/livro")
@Validated
@CrossOrigin("*")
public class LivroController {

    private final LivroRepository livroRepository;
    private final LivroService livroService;

    public LivroController(LivroRepository livroRepository, LivroService livroService) {
        this.livroRepository = livroRepository;
        this.livroService = livroService;
    }

    @GetMapping("/lista")
    public Iterable<LivroModelo> listaLivro() {
        return livroRepository.findAll();
    }

    @GetMapping("/{livroId}")
    public LivroModelo verlivro(@PathVariable Long livroId) {
        return livroService.verLivro(livroId);
    }

    @PostMapping
    public ResponseEntity<?> cadastraLivro(
            @Valid
            @RequestPart("livro") LivroDto livroDto,
            @RequestPart("imagem") MultipartFile imagem) throws IOException {

        LivroModelo livroSalvo = livroService.cadastralivro(livroDto, imagem);
        return ResponseEntity.status(HttpStatus.CREATED).body(livroSalvo);

    }

    @PutMapping("/{livroId}")
    public ResponseEntity<?> alteraLivro(
            @PathVariable Long livroId,
            @RequestPart("livro") LivroDto livroDto,
            @RequestPart(value = "imagem", required = false)MultipartFile imagem) throws IOException {

        LivroModelo livroModelo = livroService.alteraLivro(livroId, imagem, livroDto);
        return ResponseEntity.ok(livroModelo);
    }

    @DeleteMapping(path = "/{livroId}")
    public ResponseEntity<?> deletarLivro(@PathVariable Long livroId) {
        livroService.deletaLivro(livroId);
        return ResponseEntity.ok().body("livro deletado");
    }



}
