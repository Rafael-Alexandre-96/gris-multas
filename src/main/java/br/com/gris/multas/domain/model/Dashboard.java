package br.com.gris.multas.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Dashboard {
  private Integer aguardandoAssinatura;
  private Integer aguardandoBoletos;
  private Integer aguardandoEnvio;
  private Integer totalMulta;
  private Integer motoristaInfrator;
  private Integer motoristasAtivos;
  private Integer veiculosAtivos;
  private Integer reboquesAtivos;

  @SuppressWarnings("unused")
  private Integer getOutroInfrator() {
    return totalMulta - motoristaInfrator;
  }
}
