package com.example.sistemaBiblioteca.service;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import com.example.sistemaBiblioteca.model.ClienteModelo;
import com.example.sistemaBiblioteca.repository.ClienteRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ClienteServiceTeste {

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
    }

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
