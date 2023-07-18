package com.api.medtrack.controllers;

import com.api.medtrack.dtos.ProntuarioDto;
import com.api.medtrack.models.ProntuarioModel;
import com.api.medtrack.services.ProntuarioService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/prontuario")
public class ProntuarioController {

    final ProntuarioService prontuarioService;

    public ProntuarioController(ProntuarioService prontuarioService) {
        this.prontuarioService = prontuarioService;
    }

    @PostMapping
    public ResponseEntity<Object> saveProntuario(@RequestBody @Valid ProntuarioDto prontuarioDto) {

        if (prontuarioService.existsByCpf(prontuarioDto.getCpf())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("O CPF informado já existe no Banco de Dados. Ao ivés de criar novo usuário, atualize o já existente");
        }
        if (prontuarioDto.getCpf().length() != 11) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("O CPF deve conter 11 números");
        } // Isso não esta sendo usado pois o DTO contém uma validação de 11 caracteres(como adicionar resposta no DTO?)
        if (prontuarioService.existsByEmail(prontuarioDto.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("O E-mail informado já existe no Banco de Dados. Ao ivés de criar novo usuário, atualize o já existente");
        }
        ProntuarioModel prontuarioModel = new ProntuarioModel();
        BeanUtils.copyProperties(prontuarioDto, prontuarioModel);
        prontuarioModel.setDataDeRegistro(LocalDateTime.now(ZoneId.of("UTC")));
        return ResponseEntity.status(HttpStatus.CREATED).body(prontuarioService.save(prontuarioModel));
    }

    @GetMapping
    public ResponseEntity<Page<ProntuarioModel>> getAllProntuarios
            (@PageableDefault
                     (page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(prontuarioService.findAll(pageable));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProntuarioModel getProntuarioById(@PathVariable Long id, Principal principal) {
        //verificar role do usuario antes de retornar os dados
        //principal é a secretaria, ver se é ela mesmo e passar dados
        return prontuarioService.findById(id);
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<Object> getProntuarioByNome(@PathVariable(value = "nome") String nome) {
        List<ProntuarioModel> prontuarios = prontuarioService.findByNome(nome);
        if (prontuarios.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum prontuário encontrado.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(prontuarios);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProntuario(@PathVariable(value = "id") Long id) {
        ProntuarioModel prontuarioModelOptional = prontuarioService.findById(id);
        prontuarioService.delete(prontuarioModelOptional);
        return ResponseEntity.status(HttpStatus.OK).body("Prontuario deletado com sucesso.");

    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProntuario(@PathVariable(value = "id") Long id,
                                                   @RequestBody @Valid ProntuarioDto prontuarioDto) {
        ProntuarioModel prontuarioModel = prontuarioService.findById(id);
        prontuarioModel.setNome(prontuarioDto.getNome());
        prontuarioModel.setEmail(prontuarioDto.getEmail());
        prontuarioModel.setSexo(prontuarioDto.getSexo());
        prontuarioModel.setTelefone(prontuarioDto.getTelefone());
        prontuarioModel.setDataDeNascimento(prontuarioDto.getDataDeNascimento());
        prontuarioModel.setCpf(prontuarioDto.getCpf());
        prontuarioModel.setIdentidade(prontuarioDto.getIdentidade());
        prontuarioModel.setEndereço(prontuarioDto.getEndereço());
        prontuarioModel.setCidade(prontuarioDto.getCidade());
        prontuarioModel.setEstado(prontuarioDto.getEstado());
        prontuarioModel.setTipoSanguineo(prontuarioDto.getTipoSanguineo());
        prontuarioModel.setAlergias(prontuarioDto.getAlergias());
        prontuarioModel.setConsulta(prontuarioDto.getConsulta());

        return ResponseEntity.status(HttpStatus.OK).body(prontuarioService.save(prontuarioModel));
    }


}


//recebemos do cliente um DTO e passamos para o banco de dados um MODEL, por isso deve-se converter o DTO
//em model nesta linha: <BeanUtils.copyProperties(prontuarioDto, prontuarioService)>