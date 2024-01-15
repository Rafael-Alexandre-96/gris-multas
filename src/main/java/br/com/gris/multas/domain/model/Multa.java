package br.com.gris.multas.domain.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

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
    private String ait;
    /*private*/
    private Veiculo veiculo;
    private Veiculo semiReboque;
    private Motorista motorista;
    private Boolean assinado;
    private LocalDateTime envioPenalidade;
    private Boolean indicado;
    private LocalDateTime prazoIndicacao;
    private Boolean boletoRecebido;
    private LocalDateTime vencimentoBoleto;
    private Double valorBoleto;
    private Double descontoBoleto;
    private LocalDateTime envioBoleto;
    private Boolean niRecebido;
    private LocalDateTime vencimentoNi;
    private Double valorNi;
    private Double descontoNi;
    private LocalDateTime envioNi;
    private Integer multiplicadorNi;
    private String observacao;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY) private RegistroStatus registroStatus = new RegistroStatus();
}
