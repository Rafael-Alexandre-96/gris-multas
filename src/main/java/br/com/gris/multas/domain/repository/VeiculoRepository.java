package br.com.gris.multas.domain.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.gris.multas.domain.model.Veiculo;

public interface VeiculoRepository extends MongoRepository<Veiculo, String> {

    public Optional<Veiculo> findByPlaca(String placa);
    
}
