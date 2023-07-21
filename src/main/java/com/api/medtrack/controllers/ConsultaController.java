package com.api.medtrack.controllers;

import com.api.medtrack.dtos.ConsultaDto;
import com.api.medtrack.models.ConsultaModel;
import com.api.medtrack.models.ProntuarioModel;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.api.medtrack.services.ProntuarioService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/prontuario")
public class ConsultaController {

    final ProntuarioService prontuarioService;

    public ConsultaController(ProntuarioService prontuarioService) {
        this.prontuarioService = prontuarioService;
    }

    @PostMapping("/{prontuarioId}/consulta")
    public ResponseEntity<Object> criarConsulta(
            @PathVariable Long prontuarioId,
            @RequestBody @Valid ConsultaDto consultaDto
    ) {
        ProntuarioModel prontuario = prontuarioService.findById(prontuarioId);
        if (prontuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Prontuário não encontrado.");
        }

        ConsultaModel consulta = new ConsultaModel(consultaDto.getDescricao(), LocalDateTime.now(ZoneId.of("UTC")));
        consulta.setProntuario(prontuario);

        prontuario.getConsultas().add(consulta);

        prontuarioService.save(prontuario);

        return ResponseEntity.status(HttpStatus.CREATED).body(prontuarioService.save(prontuario));
    }

    @PutMapping("/{prontuarioId}/consulta/{consultaId}")
    public ResponseEntity<Object> atualizarConsulta(
            @PathVariable Long prontuarioId,
            @PathVariable Long consultaId,
            @RequestBody @Valid ConsultaDto consultaDto
    ) {
        ProntuarioModel prontuario = prontuarioService.findById(prontuarioId);
        if (prontuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Prontuário não encontrado.");
        }

        Optional<ConsultaModel> consultaOptional = prontuario.getConsultas().stream()
                .filter(consulta -> consulta.getId().equals(consultaId))
                .findFirst();

        if (consultaOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Consulta não encontrada.");
        }

        ConsultaModel consulta = consultaOptional.get();
        consulta.setDescricao(consultaDto.getDescricao());


        return ResponseEntity.status(HttpStatus.OK).body(prontuarioService.save(prontuario));
    }

    @DeleteMapping("/{prontuarioId}/consulta/{consultaId}")
    public ResponseEntity<Object> excluirConsulta(
            @PathVariable Long prontuarioId,
            @PathVariable Long consultaId
    ) {
        ProntuarioModel prontuario = prontuarioService.findById(prontuarioId);
        if (prontuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Prontuário não encontrado.");
        }

        boolean consultaRemovida = prontuario.getConsultas().removeIf(consulta -> consulta.getId().equals(consultaId));
        if (!consultaRemovida) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Consulta não encontrada.");
        }

        prontuarioService.save(prontuario);
        return ResponseEntity.status(HttpStatus.OK).body("Consulta removida com sucesso.");
    }

}
