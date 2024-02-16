package br.com.gris.multas.domain.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import br.com.gris.multas.domain.model.Veiculo;

public interface VeiculoRepository extends MongoRepository<Veiculo, String> {
  @Query("{ ?0: /.*?1.*/ }")
  List<Veiculo> findByFieldContains(String field, String value);

  @Query("{ ?0: /.*?1.*/, 'registroStatus.active': true }")
  List<Veiculo> findByFieldContainsActive(String field, String value);

  @Query("{ ?0: /.*?1.*/ }")
  Page<Veiculo> findByFieldContains(String field, String value, Pageable pageable);

  @Query("{ ?0: /.*?1.*/, 'registroStatus.active': true }")
  Page<Veiculo> findByFieldContainsActive(String field, String value, Pageable pageable);

  @Query("{ 'tipoRodado': 'TRACAO', 'registroStatus.active': true }")
  List<Veiculo> findAllTracaoActive();

  @Query("{ 'tipoRodado': 'REBOQUE', 'registroStatus.active': true }")
  List<Veiculo> findAllReboqueActive();
}
