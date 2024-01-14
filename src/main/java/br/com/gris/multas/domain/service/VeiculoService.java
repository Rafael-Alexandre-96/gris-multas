package br.com.gris.multas.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.gris.multas.domain.model.Veiculo;
import br.com.gris.multas.domain.repository.VeiculoRepository;

@Service
public class VeiculoService {
    @Autowired private VeiculoRepository repository;

    public List<Veiculo> findAll() {
        return repository.findAll();
    }

    public Veiculo findById(String id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Entity not founded."));
    }

    @Transactional
    public Veiculo create(Veiculo entity) {
        entity.getRegistroStatus().setCreateAtNow();
        return repository.save(entity);
    }

    @Transactional
    public Veiculo update(String id, Veiculo entity) {
        Veiculo finded = repository.findById(id).orElseThrow(() -> new RuntimeException("Entity not founded."));
        finded.setFrota(entity.getFrota().toUpperCase());
        finded.setPlaca(entity.getPlaca().toUpperCase());
        finded.getRegistroStatus().setUpdateAtNow();
        return repository.save(finded);
    }

    @Transactional
    public void deleteById(String id) {
        Veiculo finded = repository.findById(id).orElseThrow(() -> new RuntimeException("Entity not founded."));
        repository.delete(finded);
    }
}
