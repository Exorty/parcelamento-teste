package br.com.confitec.teste.parcelamento;

import br.com.confitec.teste.controller.ParcelamentoController;
import br.com.confitec.teste.model.*;
import br.com.confitec.teste.service.ParcelamentoService;
import br.com.confitec.teste.validator.ParcelamentoValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ParcelamentoServiceTest {

    @InjectMocks
    ParcelamentoService service;

    @Mock
    ParcelamentoController controller;

    ApolicePagamento apolicePagamento;

    @Mock
    ParcelamentoValidator validator;

    @BeforeEach
    public void setUp() {
        apolicePagamento = new ApolicePagamento();

        List<ListaCobertura> listaCobertura = new ArrayList<>();
        ListaCobertura cobertura1 = new ListaCobertura();
        ListaCobertura cobertura2 = new ListaCobertura();
        cobertura1.setCobertura(1);
        cobertura1.setValor(BigDecimal.valueOf(120));

        listaCobertura.add(cobertura1);

        cobertura2.setCobertura(2);
        cobertura2.setValor(BigDecimal.valueOf(130));

        listaCobertura.add(cobertura2);

        apolicePagamento.setListaCobertura(listaCobertura);
        List<ListaOpcaoParcelamento> listaOpcaoParcelamentos = new ArrayList<>();
        ListaOpcaoParcelamento opcaoParcelamento1 = new ListaOpcaoParcelamento();
        ListaOpcaoParcelamento opcaoParcelamento2 = new ListaOpcaoParcelamento();

        opcaoParcelamento1.setJuros(0);
        opcaoParcelamento1.setQuantidadeMinimaParcelas(1);
        opcaoParcelamento1.setQuantidadeMaximaParcelas(6);

        listaOpcaoParcelamentos.add(opcaoParcelamento1);

        opcaoParcelamento2.setJuros(0.01);
        opcaoParcelamento2.setQuantidadeMinimaParcelas(7);
        opcaoParcelamento2.setQuantidadeMaximaParcelas(9);

        listaOpcaoParcelamentos.add(opcaoParcelamento2);


        apolicePagamento.setListaOpcaoParcelamento(listaOpcaoParcelamentos);


    }


    @Test
    @DisplayName("Teste de quantidade maxima de parcelas")
    public void quantidadeMaximaDeParcelasEsperada() {
        DadosParcelamento dadosParcelamento = service.parcelamento(apolicePagamento);
        int quantidadeParcelasEsperada = apolicePagamento.getListaOpcaoParcelamento()
                .get(apolicePagamento.getListaOpcaoParcelamento().size() - 1)
                .getQuantidadeMaximaParcelas();
        int quantidadeParcelasCalculada = dadosParcelamento.getParcelamentoInfo()
                .get(dadosParcelamento.getParcelamentoInfo().size() - 1)
                .getQuantidadeParcelas();

        assertEquals(quantidadeParcelasEsperada, quantidadeParcelasCalculada);
    }

    @Test
    @DisplayName("Teste de valor do parcelamento total sem juros")
    public void valorParcelamentoTotalSemJuros() {

        DadosParcelamento dadosParcelamento = service.parcelamento(apolicePagamento);

        BigDecimal valorTotal = BigDecimal.ZERO;
        for (ListaCobertura cobertura : apolicePagamento.getListaCobertura()) {
            valorTotal = valorTotal.add(cobertura.getValor());
        }


        int comparacao = valorTotal.compareTo(dadosParcelamento.getParcelamentoInfo().get(0).getValorParcelamentoTotal());
        assertEquals(0, comparacao);

    }

    @Test
    @DisplayName("Teste de valor do parcelamento total com juros")
    public void valorParcelamentoTotalComJuros() {

        DadosParcelamento dadosParcelamento = service.parcelamento(apolicePagamento);

        BigDecimal valorTotal = BigDecimal.ZERO;
        for (ListaCobertura cobertura : apolicePagamento.getListaCobertura()) {
            valorTotal = valorTotal.add(cobertura.getValor());
        }

        BigDecimal resultado = valorTotal.multiply(BigDecimal.ONE.add(BigDecimal.valueOf(apolicePagamento.getListaOpcaoParcelamento().get(1).getJuros())).pow(9));

        resultado = resultado.setScale(2, RoundingMode.HALF_UP);

        int comparacao = resultado.compareTo(dadosParcelamento.getParcelamentoInfo().get(8).getValorParcelamentoTotal());
        assertEquals(0, comparacao);

    }


}
