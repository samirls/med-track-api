package com.api.medtrack.repositories;

import com.api.medtrack.models.ProntuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProntuarioRepository extends JpaRepository<ProntuarioModel, Long> {

    boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);
    List<ProntuarioModel> findByNomeIgnoreCaseLike(String nome);

}


//ProntuarioRepository acessa o banco de dados, o que escrever é importante, não é só inventar um nome;