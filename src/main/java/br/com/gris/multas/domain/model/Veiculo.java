package br.com.gris.multas.domain.model;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Veiculo {

    @Id
    private String id;
    private String placa;
    private String frota;
}
