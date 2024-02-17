package br.com.gris.multas.domain.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import br.com.gris.multas.domain.model.Multa;
import br.com.gris.multas.domain.model.ResumoMotorista;
import br.com.gris.multas.domain.model.enums.Infrator;

public interface MultaRepository extends MongoRepository<Multa, String> {
  @Query("{ ?0: /.*?1.*/ }")
  List<Multa> findByFieldContains(String field, String value);

  @Query("{ ?0: /.*?1.*/ }")
  Page<Multa> findByFieldContains(String field, String value, Pageable pageable);

  List<Multa> findByInfrator(Infrator infrator);

  @Query("{ assinado: false, infrator: MOTORISTA, ?0: /.*?1.*/ }")
  Page<Multa> findAguardandoAssinatura(String field, String value, Pageable pageable);

  @Aggregation(pipeline = {"{ $match: { assinado: false, infrator: MOTORISTA } }", "{ $group: { _id: $motorista, total: { $sum: 1 } } }", "{ $sort: { '_id.nome': 1 } }"})
  List<ResumoMotorista> motoristasAguardandoAssinatura();

}