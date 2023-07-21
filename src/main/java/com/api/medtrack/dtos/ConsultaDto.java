package com.api.medtrack.dtos;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class ConsultaDto {

    @NotBlank(message = "A descrição da consulta é obrigatória.")
    private String descricao;

    private LocalDateTime dataConsulta;

    public ConsultaDto(String descricao, LocalDateTime dataConsulta) {
        this.descricao = descricao;
        this.dataConsulta = dataConsulta;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDateTime getDataConsulta() {
        return dataConsulta;
    }

    public void setDataConsulta(LocalDateTime dataConsulta) {
        this.dataConsulta = dataConsulta;
    }
}
