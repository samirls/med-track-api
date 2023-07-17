package com.api.medtrack.services;

import com.api.medtrack.models.ProntuarioModel;
import com.api.medtrack.repositories.ProntuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ProntuarioService {

    final ProntuarioRepository prontuarioRepository;

    public ProntuarioService(ProntuarioRepository prontuarioRepository) {
        this.prontuarioRepository = prontuarioRepository;
    }

    @Transactional
    public ProntuarioModel save(ProntuarioModel prontuarioModel) {
        return prontuarioRepository.save(prontuarioModel);
    }

    public boolean existsByCpf(String cpf) {
        return prontuarioRepository.existsByCpf(cpf);
    }

    public boolean existsByEmail(String email) {
        return prontuarioRepository.existsByEmail(email);
    }

    public Page<ProntuarioModel> findAll(Pageable pageable) {
        return prontuarioRepository.findAll(pageable);

    }

    public Optional<ProntuarioModel> findById(Long id) {
        return prontuarioRepository.findById(id);
    }

    public List<ProntuarioModel> findByNome(String nome) {
        return prontuarioRepository.findByNomeIgnoreCaseLike("%" + nome + "%");
    }

    @Transactional
    public void delete(ProntuarioModel prontuarioModel) {
        prontuarioRepository.delete(prontuarioModel);
    }
}

//Os dados do formulário que o usuario escreve passam através do DTO, vem para o controller, o controller chama
//o service e o service chama o Repository(que tem acesso ao banco de dados);