package br.com.gris.multas.domain.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
    private String infrator;
    private Integer pontos;
    private Double valor;
}
