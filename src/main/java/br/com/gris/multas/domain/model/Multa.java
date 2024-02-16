package br.com.gris.multas.domain.model;

import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.gris.multas.domain.model.enums.Infrator;
import br.com.gris.multas.domain.model.enums.Situacao;
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

  public Situacao getSituacao() {
    if (this.infrator == Infrator.MOTORISTA) {
      if (this.assinado) {
        if (this.indicado) { //Indicado
          if (this.boletoRecebido) { //Boleto Recebido
            if (this.envioBoleto != null) { //Boleto Enviado
              return Situacao.FINALIZADO;
            } else { //Boleto Não Enviado
              return Situacao.ENVIAR_PARA_FINANCEIRO;
            }
          } else { // Boleto Não Recebido
            return Situacao.AGUARDANDO_BOLETO;
          }
        } else { //Não Indicado
          if (this.boletoRecebido) { //Boleto Recebido
            if (this.envioBoleto != null) { //Boleto Enviado
              if (this.niRecebido) { //NI Recebido
                if (this.envioNi != null) { //Ni Enviado
                  return Situacao.FINALIZADO;
                } else { //NI não enviado
                  return Situacao.ENVIAR_PARA_FINANCEIRO;
                }
              } else { //NI não recebida
                return Situacao.AGUARDANDO_NI;
              }
            } else { //Boleto Não Enviado
              return Situacao.ENVIAR_PARA_FINANCEIRO;
            }
          } else { // Boleto Não Recebido
            return Situacao.AGUARDANDO_BOLETO_NI;
          }
        }
      } else {
        return Situacao.AGUARDANDO_ASSINATURA;
      }
    }

    return Situacao.OUTROS;
  }
}
