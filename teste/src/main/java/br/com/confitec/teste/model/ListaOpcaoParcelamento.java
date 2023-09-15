package br.com.confitec.teste.model;

import lombok.Data;

@Data
public class ListaOpcaoParcelamento {

    private int quantidadeMinimaParcelas;
    private int quantidadeMaximaParcelas;
    private double juros;

}
