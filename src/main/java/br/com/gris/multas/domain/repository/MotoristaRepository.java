package br.com.gris.multas.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import br.com.gris.multas.domain.model.Motorista;

public interface MotoristaRepository extends MongoRepository<Motorista, String> {

    Page<Motorista> findByNomeContains(String nome, Pageable pageable);

    @Query("{ 'nome': /.*?0.*/, 'registroStatus.active': true }")
    Page<Motorista> findByNomeContainsActive(String nome, Pageable pageable);
    
}
