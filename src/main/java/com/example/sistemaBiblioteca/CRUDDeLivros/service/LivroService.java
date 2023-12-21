package com.example.sistemaBiblioteca.CRUDDeLivros.service;

import org.springframework.stereotype.Service;

import com.example.sistemaBiblioteca.CRUDDeLivros.model.LivroModelo;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class LivroService {


    Path currentPath = Paths.get(System.getProperty("user.dir"));
    Path desiredPath = currentPath.getParent();
    String diretorio = desiredPath.toString() + "/backend/src/main/java/com/example/sistemaBiblioteca/imagem";

    public String salvaImagem(LivroModelo livroModelo) {
        String nomeImagem = livroModelo.getLivroId() + ".jpg";
        return diretorio + File.separator + nomeImagem;
    }

}