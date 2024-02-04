package br.com.gris.multas.domain.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.gris.multas.domain.model.Multa;

public interface MultaRepository extends MongoRepository<Multa, String> {

}
