package br.com.gris.multas.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import br.com.gris.multas.domain.model.Veiculo;

public interface VeiculoRepository extends MongoRepository<Veiculo, String> {

    Page<Veiculo> findByPlacaContains(String placa, Pageable pageable);

    @Query("{ 'placa': /.*?0.*/, 'registroStatus.active': true }")
    Page<Veiculo> findByPlacaContainsActive(String placa, Pageable pageable);
    
}
