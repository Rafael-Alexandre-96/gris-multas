package br.com.gris.multas.domain.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import br.com.gris.multas.domain.model.Enquadramento;

public interface EnquadramentoRepository extends MongoRepository<Enquadramento, String> {
  @Query("{ ?0: /.*?1.*/ }")
  List<Enquadramento> findByFieldContains(String field, String value);

  @Query("{ ?0: /.*?1.*/ }")
  Page<Enquadramento> findByFieldContains(String field, String value, Pageable pageable);
}