package br.com.confitec.teste.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class DadosParcelamento {

    @JsonProperty("dados")
    private List<ParcelamentoInfo> parcelamentoInfo;
}
