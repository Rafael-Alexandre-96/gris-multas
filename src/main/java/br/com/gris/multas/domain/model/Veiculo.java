package br.com.gris.multas.domain.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.gris.multas.domain.model.enums.TipoRodado;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document("veiculo")
public class Veiculo {

  @Id private String id;
  private String placa;
  private String frota;
  private TipoRodado tipoRodado = TipoRodado.TRACAO;
  @JsonProperty(access = JsonProperty.Access.READ_ONLY) private RegistroStatus registroStatus = new RegistroStatus();

  public void setPlaca(String placa) {
    if (placa != null)
      this.placa = placa.toUpperCase();
  }

  public void setFrota(String frota) {
    if (frota != null)
      this.frota = frota.toUpperCase();
  }
}
