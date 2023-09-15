package br.com.confitec.teste.service;

import br.com.confitec.teste.dto.ApolicePagamentoDTO;
import br.com.confitec.teste.model.*;
import br.com.confitec.teste.validator.ParcelamentoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.OptionalInt;

@Service
public class ParcelamentoService {

    @Autowired
    private ParcelamentoValidator validator;


    public DadosParcelamento parcelamento(ApolicePagamento request) {

        validator.execute(request);

        ApolicePagamentoDTO apolicePagamentoDTO = new ApolicePagamentoDTO(request);

        BigDecimal valorTotal = BigDecimal.ZERO;

        for (ListaCobertura cobertura : apolicePagamentoDTO.getListaCobertura()) {
            valorTotal = valorTotal.add(cobertura.getValor());
        }

        OptionalInt maxQuantidadeMaximaParcelas = apolicePagamentoDTO.getListaOpcaoParcelamento().stream()
                .mapToInt(ListaOpcaoParcelamento::getQuantidadeMaximaParcelas)
                .max();

        DadosParcelamento response = new DadosParcelamento();


        if (response.getParcelamentoInfo() == null) {
            response.setParcelamentoInfo(new ArrayList<>());
        }

        if (maxQuantidadeMaximaParcelas.isPresent()) {
            int tamanhoMaximo = maxQuantidadeMaximaParcelas.getAsInt();


            for (int i = 1; i <= tamanhoMaximo; i++) {
                ParcelamentoInfo parcelamentoInfo = new ParcelamentoInfo();

                BigDecimal valorParcelamentoTotal = calcularValorTotal(apolicePagamentoDTO, valorTotal, i);


                parcelamentoInfo.setQuantidadeParcelas(i);

                parcelamentoInfo.setValorParcelamentoTotal(valorParcelamentoTotal);

                BigDecimal restoDivisao = valorTotal.remainder(valorParcelamentoTotal);
                parcelamentoInfo.setValorPrimeiraParcela(valorParcelamentoTotal.add(restoDivisao));

                BigDecimal primeiraParcela = calcularRateio(valorParcelamentoTotal, i);

                parcelamentoInfo.setValorPrimeiraParcela(primeiraParcela);

                BigDecimal demaisParcelas = calcularDemaisParcelas(valorParcelamentoTotal, i);

                parcelamentoInfo.setValorDemaisParcelas(demaisParcelas);

                response.getParcelamentoInfo().add(parcelamentoInfo);

            }
        }


        return response;
    }

    private BigDecimal calcularValorTotal(ApolicePagamentoDTO apolicePagamento, BigDecimal valorTotal, int numParcela) {

        double juros = 0;

        for (int i = 0; i < apolicePagamento.getListaOpcaoParcelamento().size(); i++) {
            if (numParcela >= apolicePagamento.getListaOpcaoParcelamento().get(i).getQuantidadeMinimaParcelas()
                    && numParcela <= apolicePagamento.getListaOpcaoParcelamento().get(i).getQuantidadeMaximaParcelas()) {
                juros = apolicePagamento.getListaOpcaoParcelamento().get(i).getJuros();
                break;
            }

        }

        BigDecimal resultado = valorTotal.multiply(BigDecimal.ONE.add(BigDecimal.valueOf(juros)).pow(numParcela));

        resultado = resultado.setScale(2, RoundingMode.HALF_UP);

        return resultado;

    }

    private BigDecimal calcularRateio(BigDecimal valorTotal, int numParcelas) {
        BigDecimal valorParcela = valorTotal.divide(BigDecimal.valueOf(numParcelas), 2, RoundingMode.HALF_UP);
        BigDecimal restoDivisao = valorTotal.subtract(valorParcela.multiply(BigDecimal.valueOf(numParcelas)));
        return valorParcela.add(restoDivisao);
    }

    private BigDecimal calcularDemaisParcelas(BigDecimal valorTotal, int numParcelas) {
        BigDecimal valorParcela = valorTotal.divide(BigDecimal.valueOf(numParcelas), 2, RoundingMode.HALF_UP);
        return valorParcela;
    }


}
