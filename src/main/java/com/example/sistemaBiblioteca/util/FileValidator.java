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

    /**
     * Valida se o tipo do arquivo é aceito e o tamanho máximo é de 10MB.
     * 
     * @param file o arquivo a ser validado
     */
    private void validateFile(MultipartFile file) {
        if (!ALLOWED_MIME_TYPES.contains(file.getContentType())) {
            throw new FileValidationException("O tipo MIME do arquivo não é permitido.");
        }

        if (file.getSize() > MAX_FILE_SIZE_BYTES) {
            throw new FileValidationException("O tamanho do arquivo excede o limite permitido de 10MB.");
        }
    }

    /**
     * Obtém a extensão do arquivo com base no tipo MIME.
     * 
     * @param mimeType o tipo MIME do arquivo
     * @return a extensão do arquivo
     */
    public String getFileType(String mimeType) {
        return switch (mimeType) {
            case "image/jpeg" -> ".jpeg";
            case "image/png" -> ".png";
            default -> throw new IllegalArgumentException("Tipo de arquivo não suportado");
        };
    }

    /**
     * Obtém a extensão de um arquivo a partir do seu caminho.
     * 
     * @param filePath o caminho do arquivo
     * @return a extensão do arquivo
     */
    public String getFile(String filePath) {
        if (filePath != null) {
            int lastDotIndex = filePath.lastIndexOf(".");
            if (lastDotIndex != -1) {
                return filePath.substring(lastDotIndex);
            }
        }
        return "";
    }

    /**
     * Retorna o diretório da imagem com base no título do livro e no tipo de
     * arquivo.
     * 
     * @param livroModelo o modelo de livro contendo o título
     * @param fileType    o tipo do arquivo da imagem
     * @return o diretório da imagem
     */
    public String getImageDirectory(LivroModelo livroModelo, String fileType) {
        final String IMG_DIR = Paths.get(System.getProperty("user.dir")).getParent().resolve("img").toString();
        String sanitizedFileName = livroModelo.getTitulo().replaceAll("[^a-zA-Z0-9\\-_.]+", "_");
        return IMG_DIR + File.separator + sanitizedFileName + fileType;
    }

    /**
     * Deleta a imagem associada a um livro do sistema.
     *
     * @param livro o livro do qual a imagem deve ser deletada
     * @throws IllegalStateException se houver uma falha ao excluir a imagem do
     *                               livro
     */
    public void deletarImagem(LivroModelo livro) {
        String fileType = getFile(livro.getImagemDoLivro());
        String imagePath = getImageDirectory(livro, fileType);
        File imageFile = new File(imagePath);
        if (imageFile.exists() && !imageFile.delete()) {
            throw new IllegalStateException("Falha ao excluir a imagem do livro: " + imagePath);
        }
    }

    /**
     * Valida se uma imagem foi fornecida e se ela atende aos critérios de
     * validação.
     *
     * @param imagem a imagem a ser validada
     * @throws IllegalArgumentException se a imagem não for fornecida ou estiver
     *                                  vazia
     * @throws FileValidationException  se a imagem não atender aos critérios de
     *                                  validação
     */
    public void validarImagem(MultipartFile imagem) {
        if (imagem == null || imagem.isEmpty()) {
            throw new IllegalArgumentException("Imagem não fornecida");
        }
        validateFile(imagem);
    }
}
