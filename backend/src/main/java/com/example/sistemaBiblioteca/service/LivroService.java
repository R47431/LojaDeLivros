package com.example.sistemaBiblioteca.service;

import org.springframework.stereotype.Service;

import com.example.sistemaBiblioteca.model.LivroModelo;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class LivroService {

    //TODO criar tratamento de execao para se ocorra imagem null pedir para fazer novaemnte

    Path currentPath = Paths.get(System.getProperty("user.dir"));
    Path desiredPath = currentPath.getParent();
    String diretorio = desiredPath.toString() + "/imagem";

    public String diretorioDaImagem(LivroModelo livroModelo) {
        String nomeImagem = livroModelo.getLivroId() + ".jpg";
        return diretorio + File.separator + nomeImagem;
    }

}