package br.com.senai.duvidas;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoDuvida(
    @NotNull
    Long id,
    
    @NotBlank
    String resposta
) {}