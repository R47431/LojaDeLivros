package com.example.sistemaBiblioteca.controller;

import java.util.Optional;

import org.modelmapper.ModelMapper;
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
import com.example.sistemaBiblioteca.global.ValidaCliente;
import com.example.sistemaBiblioteca.model.LivroModelo;
import com.example.sistemaBiblioteca.repository.LivroRepository;
import com.example.sistemaBiblioteca.service.LivroService;

@RestController
@RequestMapping("/livro")
public class LivroController {
    // TODO fazer melhora de codigo validadores no service
    //TODO add testes unitarios
    
    private static final Logger LOGGER = LoggerFactory.getLogger(EmprestimoController.class);

    private final LivroRepository livroRepository;
//    private final LivroService livroService;

    @Autowired
    private ValidaCliente cliente;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private LivroService livroService;

    @Autowired
    public LivroController(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    @GetMapping("/lista")
    public Iterable<LivroModelo> listaLivro() {
        return livroRepository.findAll();
    }

    @GetMapping("/{livroId}")
    public LivroDto listaLivro(@PathVariable Long livroId) {
            Optional<LivroModelo> livroModelo = livroRepository.findById(livroId);
            return mapper.map(livroModelo, LivroDto.class);

       /* try {
            LivroDto livroDto = livroService.verLivro(livroId);
            return ResponseEntity.ok(livroDto);
        } catch (IllegalArgumentException e) {
            LOGGER.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

        } */

    }

    @PostMapping
    public ResponseEntity<?> cadastraLivro(LivroDto livroDto, @RequestParam("imagem") MultipartFile imagem) {
        try {

            LivroModelo livroSalvo = livroService.cadastralivro(livroDto, imagem);

            return ResponseEntity.status(HttpStatus.CREATED).body(livroSalvo);

        } catch (NullPointerException e) {
            throw new NullPointerException("Entidade null {}");

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocorreu um erro ao processar o Cadastro." + e.getMessage());
        }
    }

  /*  @PutMapping("/{livroId}")
    public ResponseEntity<?> alteraLivro(@PathVariable Long livroId, LivroDto livroDto,
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
    } */
}
