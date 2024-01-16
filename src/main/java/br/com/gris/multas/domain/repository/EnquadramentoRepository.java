package br.com.gris.multas.domain.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.gris.multas.domain.model.Enquadramento;

public interface EnquadramentoRepository extends MongoRepository<Enquadramento, String> {
    
}
