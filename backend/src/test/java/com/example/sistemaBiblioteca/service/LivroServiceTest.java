package com.example.sistemaBiblioteca.service;

import com.example.sistemaBiblioteca.dto.LivroDto;
import com.example.sistemaBiblioteca.model.ClienteModelo;
import com.example.sistemaBiblioteca.model.LivroModelo;
import com.example.sistemaBiblioteca.repository.ClienteRepository;
import com.example.sistemaBiblioteca.repository.LivroRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
@RunWith(SpringRunner.class)
@SpringBootTest
class LivroServiceTest {

   /*
    * @InjectMocks
    private LivroService livroService;
    @Mock
    private LivroRepository livroRepository;
    @Mock
    private ModelMapper mapper;

    private LivroModelo livroModelo;
    private LivroDto livroDto;
    */
    @InjectMocks
    private ClienteService clienteService;

    private ClienteModelo clienteModelo;

    @Mock
    private ClienteRepository clienteRepository;



    @BeforeEach
    void setUp() {
        clienteModelo = new ClienteModelo(
            1L,
            "111",
            LocalDate.of(2002, 02, 02),
            "123456789",
            "dadad@dada",
            null);
     /*
        livroModelo = new LivroModelo(1L, "Título", "Autor", "Editora", "Descrição", LocalDate.of(2002, 2, 2), "Categoria", "ISBN", "Imagem");
        livroModelo = new LivroModelo();
        livroModelo.setLivroId(1L); */
    }

 /*
  *    @Test
    void verLivro() {
        Mockito.when(livroRepository.findById(1L)).thenReturn(Optional.of(livroModelo));
        LivroModelo result = livroService.verLivro(1L);
        
        assertNotNull(result);
        assertEquals(LivroModelo.class, result.getClass());
    }

    @Test
    void diretorioDaImagem() {
        String result = livroService.diretorioDaImagem(livroModelo);
        assertEquals("/home/rafael/Documentos/2Biblioteca/frontend/src/assets/img/1.jpg", result);
    }
  */


   

    @Test
    void cadastralivro() {

        when(clienteRepository.save(any(ClienteModelo.class))).thenReturn(clienteModelo);

        // Calling the method to be tested
        ClienteModelo response = clienteService.cadastraCliente(clienteModelo);

        // Asserting the response
        assertNotNull(response);

        // Verifying if save method is called with the correct ClienteModelo object
        verify(clienteRepository, times(1)).save(eq(clienteModelo));
    }
}