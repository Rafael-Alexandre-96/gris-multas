package br.com.gris.multas.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.gris.multas.domain.model.Veiculo;

public interface VeiculoRepository extends MongoRepository<Veiculo, String> {

    Page<Veiculo> findByPlacaContains(String placa, Pageable pageable);
    
}
