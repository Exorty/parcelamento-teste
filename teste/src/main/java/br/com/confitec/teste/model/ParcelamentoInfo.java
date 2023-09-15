package br.com.confitec.teste.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ParcelamentoInfo {

    private int quantidadeParcelas;
    private BigDecimal valorPrimeiraParcela;


    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BigDecimal valorDemaisParcelas;

    private BigDecimal valorParcelamentoTotal;

}
