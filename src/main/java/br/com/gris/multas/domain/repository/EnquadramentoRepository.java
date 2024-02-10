package br.com.gris.multas.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.gris.multas.domain.model.Enquadramento;

public interface EnquadramentoRepository extends MongoRepository<Enquadramento, String> {

  Page<Enquadramento> findByDescricaoContains(String descricao, Pageable pageable);
    
}
