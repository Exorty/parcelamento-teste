package br.com.confitec.teste.controller;

import br.com.confitec.teste.model.ApolicePagamento;
import br.com.confitec.teste.model.DadosParcelamento;
import br.com.confitec.teste.model.ListaCobertura;
import br.com.confitec.teste.service.ParcelamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ParcelamentoController {

    @Autowired
    private ParcelamentoService parcelamentoService;

    @PostMapping(value = "/confitec/teste/parcelamento", consumes = "application/json")
    public ResponseEntity<DadosParcelamento> parcelamento(@RequestBody ApolicePagamento request) {
        DadosParcelamento response = parcelamentoService.parcelamento(request);
        System.out.println("Teste");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
