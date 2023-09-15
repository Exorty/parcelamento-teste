package br.com.confitec.teste.dto;

import br.com.confitec.teste.model.ApolicePagamento;
import br.com.confitec.teste.model.ListaCobertura;
import br.com.confitec.teste.model.ListaOpcaoParcelamento;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ApolicePagamentoDTO {

    private List<ListaCobertura> listaCobertura;

    private List<ListaOpcaoParcelamento> listaOpcaoParcelamento;

    public ApolicePagamentoDTO(ApolicePagamento entity) {

        this.listaCobertura = new ArrayList<>();
        this.listaOpcaoParcelamento = new ArrayList<>();

        entity.getListaCobertura().forEach(cobertura ->
                listaCobertura.add(cobertura)

        );
        entity.getListaOpcaoParcelamento().forEach(opcaoPagamento ->
                listaOpcaoParcelamento.add(opcaoPagamento)
        );

    }


}
