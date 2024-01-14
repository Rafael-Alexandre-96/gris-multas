package br.com.gris.multas.domain.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.gris.multas.domain.model.Motorista;

public interface MotoristaRepository extends MongoRepository<Motorista, String> {
    
}
