package com.example.sistemaBiblioteca.mapperImpl;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.example.sistemaBiblioteca.dto.ObterClienteDTO;
import com.example.sistemaBiblioteca.dto.ObterLivroDTO;
import com.example.sistemaBiblioteca.dto.PedirEmprestimoDTO;
import com.example.sistemaBiblioteca.mapper.EmprestimoMapper;
import com.example.sistemaBiblioteca.model.ClienteModelo;
import com.example.sistemaBiblioteca.model.EmprestimoModelo;
import com.example.sistemaBiblioteca.model.LivroModelo;

@Component
public class EmprestimoMapperImpl implements EmprestimoMapper {

    @Override
    public EmprestimoModelo toEmprestimoModelo(PedirEmprestimoDTO pedirEmprestimoDTO) {
        if (pedirEmprestimoDTO == null) {
            return null;
        }
        LocalDate dataEmprestimo = pedirEmprestimoDTO.dataEmprestimo();

        ObterLivroDTO livroDto = pedirEmprestimoDTO.obterLivroDTO();
        LivroModelo livroModelo = toLivroModelo(livroDto);

        ObterClienteDTO clienteDTO = pedirEmprestimoDTO.obterClienteDTO();
        ClienteModelo clienteModelo = toClienteModelo(clienteDTO);

        EmprestimoModelo emprestimoModelo = new EmprestimoModelo( clienteModelo, livroModelo,
                dataEmprestimo);
        return emprestimoModelo;
    }

    public ObterLivroDTO toLivroDto(LivroModelo livroModelo) {
        if (livroModelo == null) {
            return null;
        }
        Long livroId = livroModelo.getLivroId();

        ObterLivroDTO obterLivroDTO = new ObterLivroDTO(livroId);
        return obterLivroDTO;
    }

    protected LivroModelo toLivroModelo(ObterLivroDTO obterLivroDTO) {
        if (obterLivroDTO == null) {
            return null;
        }
        Long livroId = obterLivroDTO.livroId();

        LivroModelo livroModelo = new LivroModelo(livroId);
        return livroModelo;
    }

    protected ClienteModelo toClienteModelo(ObterClienteDTO obterClienteDTO) {
        if (obterClienteDTO == null) {
            return null;
        }
        Long clienteId = obterClienteDTO.clienteId();

        ClienteModelo clienteModelo = new ClienteModelo(clienteId);
        return clienteModelo;
    }

    public ObterClienteDTO toClienteDTO(ClienteModelo clienteModelo) {
        if (clienteModelo == null) {
            return null;
        }
        Long clienteId = clienteModelo.getClienteId();

        ObterClienteDTO obterClienteDTO = new ObterClienteDTO(clienteId);
        return obterClienteDTO;
    }

}
