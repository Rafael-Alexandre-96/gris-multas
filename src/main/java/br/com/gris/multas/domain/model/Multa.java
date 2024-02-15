package br.com.gris.multas.domain.model;

import java.time.LocalDateTime;
import java.util.Date;

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
  private Infrator infrator = Infrator.MOTORISTA;
  private Veiculo veiculo;
  private Veiculo semiReboque;
  private Motorista motorista;
  private Boolean assinado = false;
  private Boolean indicado = false;
  private Date prazoIndicacao;
  private Boolean boletoRecebido = false;
  private Date vencimentoBoleto;
  private Double valorBoleto = 0D;
  private Double descontoBoleto = 0D;
  private Date envioBoleto;
  private Boolean niRecebido = false;
  private Date vencimentoNi;
  private Double valorNi = 0D;
  private Double descontoNi = 0D;
  private Date envioNi;
  private Integer multiplicadorNi = 1;
  private String observacao;
  @JsonProperty(access = JsonProperty.Access.READ_ONLY) private RegistroStatus registroStatus = new RegistroStatus();

  public void setLocal(String local) {
    if (local != null)
      this.local = local.toUpperCase();
  }

  public void setNumeroAit(String numeroAit) {
    if (numeroAit != null)
      this.numeroAit = numeroAit.toUpperCase();
  }

  public Double getValorValeBoleto() {
    return this.valorBoleto - this.descontoBoleto;
  }

  public Double getValorValeNi() {
    return this.valorNi - this.descontoNi;
  }

  public String getSituacao() {
    if (this.infrator == Infrator.MOTORISTA) {
      if (this.assinado) {

        int ass = (this.assinado) ? 3 : 0;
        int ind = (this.indicado) ? 5 : 0;
        int bolRec = (this.boletoRecebido) ? 7 : 0;
        int envBol = (this.envioBoleto != null) ? 9 : 0;
        int niRec = (this.niRecebido) ? 11 : 0;
        int envNi = (this.envioNi != null) ? 13 : 0;
        int soma = ass + ind + bolRec + envBol + niRec + envNi;

        switch (soma) {
          case 8: return "AGUARDANDO BOLETO";
          case 15: return "ENVIAR PARA FINANCEIRO";
          case 24: return "FINALIZADO";
          case 3: return "AGUARDANDO BOLETO/NI";
          case 10: case 34: return "ENVIAR PARA FINANCEIRO (BOLETO)";
          case 19: return "AGUARDANDO NI";
          case 21: return "ENVIAR PARA FINANCEIRO (BOLETO/NI)";
          case 30: return "ENVIAR PARA FINANCEIRO (NI)";
          case 43: return "FINALIZADO";
          default: return "ERROR:" + soma;
        }

      } else {
        return "AGUARDANDO ASSINATURA";
      }
    } else {
      return "OUTROS";
    }
  }
}
