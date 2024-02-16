package br.com.gris.multas.domain.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import br.com.gris.multas.domain.model.Motorista;

public interface MotoristaRepository extends MongoRepository<Motorista, String> {
  @Query("{ ?0: /.*?1.*/ }")
  List<Motorista> findByFieldContains(String field, String value);

  @Query("{ ?0: /.*?1.*/, 'registroStatus.active': true }")
  List<Motorista> findByFieldContainsActive(String field, String value);

  @Query("{ ?0: /.*?1.*/ }")
  Page<Motorista> findByFieldContains(String field, String value, Pageable pageable);

  @Query("{ ?0: /.*?1.*/, 'registroStatus.active': true }")
  Page<Motorista> findByFieldContainsActive(String field, String value, Pageable pageable);

  @Query("{ 'registroStatus.active': true }")
  List<Motorista> findAllActive();
}
