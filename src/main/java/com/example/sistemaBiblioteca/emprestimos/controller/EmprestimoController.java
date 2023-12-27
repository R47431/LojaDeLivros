package com.example.sistemaBiblioteca.emprestimos.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.sistemaBiblioteca.client.model.ClienteModelo;
import com.example.sistemaBiblioteca.client.repository.ClienteRepository;
import com.example.sistemaBiblioteca.dto.ClienteComEmprestimosDto;
import com.example.sistemaBiblioteca.dto.EmprestimoComLivroDto;
import com.example.sistemaBiblioteca.dto.LivroDto;
import com.example.sistemaBiblioteca.emprestimos.model.EmprestimoModelo;
import com.example.sistemaBiblioteca.emprestimos.repository.EmprestimosRepository;
import com.example.sistemaBiblioteca.livros.model.LivroModelo;
import com.example.sistemaBiblioteca.livros.repository.LivroRepository;

@RestController
@RequestMapping("/emprestimo")
public class EmprestimoController {

    private final EmprestimosRepository emprestimosRepository;

    private final LivroRepository livroRepository;

    private final ClienteRepository clienteRepository;


    @Autowired
    public EmprestimoController(EmprestimosRepository emprestimosRepository, LivroRepository livroRepository, ClienteRepository clienteRepository) {
        this.emprestimosRepository = emprestimosRepository;
        this.livroRepository = livroRepository;
        this.clienteRepository = clienteRepository;
    }

    @GetMapping("/{clienteId}")
    public ResponseEntity<ClienteComEmprestimosDto> getClienteComEmprestimos(@PathVariable Long clienteId) {
        Optional<ClienteModelo> clienteOptional = clienteRepository.findById(clienteId);

        ClienteModelo cliente = clienteOptional.get();
        List<EmprestimoComLivroDto> emprestimos = mapEmprestimos(cliente.getHistoricoDeEmprestimos());

        ClienteComEmprestimosDto clienteDto = new ClienteComEmprestimosDto();
        BeanUtils.copyProperties(cliente, clienteDto);
        clienteDto.setEmprestimos(emprestimos);

        return ResponseEntity.ok(clienteDto);
    }

    @PostMapping("/{clienteId}")
    public ResponseEntity<String> realizarEmprestimo(@PathVariable Long clienteId, LivroDto livroDto) {
        Optional<ClienteModelo> clienteOptional = clienteRepository.findById(clienteId);
        if (clienteOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        ClienteModelo cliente = clienteOptional.get();

        Optional<LivroModelo> livroOptional = livroRepository.findById(livroDto.getLivroId());
        if (livroOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        LivroModelo livro = livroOptional.get();

        EmprestimoModelo emprestimo = new EmprestimoModelo();
        emprestimo.setCliente(cliente);
        emprestimo.setLivro(livro);
        emprestimo.setDataEmprestimo(LocalDate.now());

        emprestimosRepository.save(emprestimo);

        return ResponseEntity.status(HttpStatus.CREATED).body("Empréstimo realizado com sucesso");

    }

    @PostMapping("/{clienteId}/devolver/{livroId}")
    public ResponseEntity<String> realizarDevolucao(@PathVariable Long clienteId, @PathVariable Long livroId) {
        Optional<ClienteModelo> clienteOptional = clienteRepository.findById(clienteId);
        if (clienteOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        ClienteModelo cliente = clienteOptional.get();

        Optional<LivroModelo> livroOptional = livroRepository.findById(livroId);
        if (livroOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        LivroModelo livro = livroOptional.get();

        Optional<EmprestimoModelo> emprestimoOptional = emprestimosRepository.findByClienteAndLivroAndDataDevolucaoIsNull(cliente, livro);
        if (emprestimoOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Este livro não foi emprestado a este cliente ou já foi devolvido.");
        }

        EmprestimoModelo emprestimo = emprestimoOptional.get();
        emprestimo.setDataDevolucao(LocalDate.now());

        emprestimosRepository.save(emprestimo);

        return ResponseEntity.ok("Devolução realizada com sucesso");
    }


    private List<EmprestimoComLivroDto> mapEmprestimos(List<EmprestimoModelo> emprestimos) {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.addMappings(new PropertyMap<EmprestimoModelo, EmprestimoComLivroDto>() {
            @Override
            protected void configure() {
                map().setDataEmprestimo(source.getDataEmprestimo());
                map().setDataDevolucao(source.getDataDevolucao());
                map().getLivroDto().setLivroId(source.getLivro().getLivroId());
                map().getLivroDto().setImagemDoLivro(source.getLivro().getImagemDoLivro());
                map().getLivroDto().setTitulo(source.getLivro().getTitulo());
                map().getLivroDto().setNomeDoAutor(source.getLivro().getNomeDoAutor());
                map().getLivroDto().setNacionalidade(source.getLivro().getNacionalidade());
                map().getLivroDto().setData(source.getLivro().getData());
                map().getLivroDto().setEditora(source.getLivro().getEditora());
                map().getLivroDto().setGenero(source.getLivro().getGenero());
                map().getLivroDto().setSinopse(source.getLivro().getSinopse());
            }
        });

        return emprestimos.stream()
                .map(emprestimo -> modelMapper.map(emprestimo, EmprestimoComLivroDto.class))
                .collect(Collectors.toList());
    }
}
