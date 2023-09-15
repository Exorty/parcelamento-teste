package br.com.confitec.teste.validator;

import br.com.confitec.teste.exception.BusinessException;
import br.com.confitec.teste.model.ApolicePagamento;
import br.com.confitec.teste.model.ListaOpcaoParcelamento;
import org.springframework.stereotype.Component;

@Component
public class ParcelamentoValidator {

    public void execute(ApolicePagamento request) {

        if (request == null) {
            throw new BusinessException("A solicitação de apólice de pagamento está nula.");
        }

        if (request.getListaOpcaoParcelamento() == null) {
            throw new BusinessException("A lista de opções de parcelamento está nula.");
        }

        for (ListaOpcaoParcelamento opcao : request.getListaOpcaoParcelamento()) {
            if (opcao.getQuantidadeMinimaParcelas() < 0 || opcao.getQuantidadeMaximaParcelas() < 0) {
                throw new BusinessException("Uma ou mais opções de parcelamento contêm valores negativos.");
            }

            if (opcao.getJuros() < 0) {
                throw new BusinessException("Uma ou mais opções de parcelamento contêm taxas de juros negativas.");
            }
        }
    }
}
