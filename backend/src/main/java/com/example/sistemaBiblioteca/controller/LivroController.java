package com.example.sistemaBiblioteca.controller;

import jakarta.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.sistemaBiblioteca.dto.LivroDto;
import com.example.sistemaBiblioteca.model.LivroModelo;
import com.example.sistemaBiblioteca.repository.LivroRepository;
import com.example.sistemaBiblioteca.service.LivroService;

import javax.validation.Valid;

@RestController
@RequestMapping("/livro")
@Validated
public class LivroController {
    // TODO fazer melhora de codigo validadores no service
    //TODO add testes unitarios


    private final LivroRepository livroRepository;
    private final LivroService livroService;

    @Autowired
    public LivroController(LivroRepository livroRepository, LivroService livroService) {
        this.livroRepository = livroRepository;
        this.livroService = livroService;
    }

    @GetMapping("/lista")
    public Iterable<LivroModelo> listaLivro() {
        return livroRepository.findAll();
    }

    /**
     * Retorna os detalhes de um livro específico.
     *
     * @param livroId ID do livro a ser buscado.
     * @return DTO do livro.
     */
    @GetMapping("/{livroId}")
    public LivroModelo verlivro(@PathVariable Long livroId) {
        return livroService.verLivro(livroId);
    }

    @PostMapping
    public ResponseEntity<?> cadastraLivro(@Valid LivroDto livroDto, @RequestParam("imagem") MultipartFile imagem) {
        try {
            LivroModelo livroSalvo = livroService.cadastralivro(livroDto, imagem);
            return ResponseEntity.status(HttpStatus.CREATED).body(livroSalvo);
        } catch (NullPointerException e) {
            throw new NullPointerException("Entidade null {}");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (ConstraintViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getConstraintViolations());
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocorreu um erro ao processar o Cadastro." + e.getMessage());
        }
    }

    @PutMapping("/{livroId}")
    public ResponseEntity<?> alteraLivro(
            @PathVariable Long livroId,
            LivroDto livroDto,
            MultipartFile imagem) {
        try {
            LivroModelo livroModelo = livroService.alteraLivro(livroId, imagem, livroDto);
            return ResponseEntity.ok(livroModelo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocorreu um erro ao processar a alteração.");
        }
    }

    @DeleteMapping(path = "/{livroId}")
    public ResponseEntity<?> deletarLivro(@PathVariable Long livroId) {
        livroService.deletaLivro(livroId);
        return ResponseEntity.ok().body("livro deletado");
    }

}
