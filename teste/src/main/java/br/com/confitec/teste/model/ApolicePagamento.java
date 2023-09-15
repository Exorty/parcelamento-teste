package br.com.confitec.teste.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ApolicePagamento {

    @JsonProperty("listCobertura")
    private List<ListaCobertura> listaCobertura;

    @JsonProperty("listOpcaoParcelamento")
    private List<ListaOpcaoParcelamento> listaOpcaoParcelamento;

}
