package br.com.gris.multas.domain.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import br.com.gris.multas.domain.model.enums.Infrator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document("enquadramento")
public class Enquadramento {
  @Id private String id;
  private String numeroEnquadramento;
  private String descricao;
  private String baseLegal;
  private Infrator infrator;
  private Integer pontos;
  private Double valor;

  public void setDescricao(String descricao) {
    this.descricao = descricao.toUpperCase();
  }

  public void setBaseLegal(String baseLegal) {
    this.baseLegal = baseLegal.toUpperCase();
  }

  public void setPontos(Integer pontos) {
    if (pontos < 0)
      this.pontos = 0;
    else
      this.pontos = pontos;
  }

  public void setValor(Double valor) {
    if (valor < 0D)
      this.valor = 0D;
    else
      this.valor = valor;
  }
}
