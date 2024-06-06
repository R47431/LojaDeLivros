package com.example.sistemaBiblioteca.util;

import com.example.sistemaBiblioteca.global.exception.FileValidationException;
import com.example.sistemaBiblioteca.model.LivroModelo;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;


@Component
public class FileValidator {

    private static final List<String> ALLOWED_MIME_TYPES = Arrays.asList("image/jpeg", "image/png");
    private static final long MAX_FILE_SIZE_BYTES = 10 * 1024 * 1024; // 10MB em bytes

    public void validateFile(MultipartFile file) {
        if (!ALLOWED_MIME_TYPES.contains(file.getContentType())) {
            throw new FileValidationException("O tipo MIME do arquivo não é permitido.");
        }

        // Limita o tamanho do arquivo
        if (file.getSize() > MAX_FILE_SIZE_BYTES) {
            throw new FileValidationException("O tamanho do arquivo excede o limite permitido de 10MB.");
        }
    }

    public String getFileType(String mimeType) {
        return switch (mimeType) {
            case "image/jpeg" -> ".jpeg";
            case "image/png" -> ".png";
            default -> throw new IllegalArgumentException("Tipo de arquivo não suportado");
        };
    }

    public String getFile(String filePath) {
        if (filePath != null) {
            int lastDotIndex = filePath.lastIndexOf(".");
            if (lastDotIndex != -1) {
                return filePath.substring(lastDotIndex);
            }
        }
        return "";
    }
    private String getImageDirectory(String title, String fileType) {
        final String IMG_DIR = Paths.get(System.getProperty("user.dir")).getParent().resolve("img").toString();
        String sanitizedFileName = title.replaceAll("[^a-zA-Z0-9\\-_.]+", "_");
        return IMG_DIR + File.separator + sanitizedFileName + fileType;
    }

    public String diretorioDaImagem(LivroModelo livroModelo, String fileType) {
        return getImageDirectory(livroModelo.getTitulo(), fileType);
    }
}

