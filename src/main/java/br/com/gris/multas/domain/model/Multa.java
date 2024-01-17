package br.com.gris.multas.domain.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.gris.multas.domain.model.enums.Infrator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document("multa")
public class Multa {
    @Id private String id;
    private LocalDateTime dataInfracao;
    private String local;
    private Enquadramento enquadramento;
    private String numeroAit;
    private Infrator infrator;
    private Veiculo veiculo;
    private Veiculo semiReboque;
    private Motorista motorista;
    private Boolean assinado = false;
    private LocalDateTime envioPenalidade;
    private Boolean indicado = false;
    private LocalDateTime prazoIndicacao;
    private Boolean boletoRecebido = false;
    private LocalDateTime vencimentoBoleto;
    private Double valorBoleto;
    private Double descontoBoleto;
    private LocalDateTime envioBoleto;
    private Boolean niRecebido = false;
    private LocalDateTime vencimentoNi;
    private Double valorNi;
    private Double descontoNi;
    private LocalDateTime envioNi;
    private Integer multiplicadorNi = 1;
    private String observacao;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY) private RegistroStatus registroStatus = new RegistroStatus();

    public void setLocal(String local) {
        this.local = local.toUpperCase();
    }

    public void setNumeroAit(String numeroAit) {
        this.numeroAit = numeroAit.toUpperCase();
    }

    /***
    public void setValorBoleto(Double valorBoleto) {
        this.valorBoleto = valorBoleto;
        this.descontoBoleto = valorBoleto * 0.2;
        this.valorNi = this.valorBoleto * this.multiplicadorNi;
        this.descontoNi = this.valorNi * 0.2;
    }

    public void setValorNi(Double valorNi) {
        this.valorNi = valorNi;
        this.descontoNi = this.valorNi * 0.2;
    }
    ***/

    public Double getValorValeBoleto() {
        return this.valorBoleto - this.descontoBoleto;
    }

    public Double getValorValeNi() {
        return this.valorNi - this.descontoNi;
    }
}
