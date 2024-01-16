package br.com.gris.multas.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
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

    public Veiculo findById(@NonNull String id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Entity not founded."));
    }

    public Veiculo findByPlaca(String placa) {
        return repository.findByPlaca(placa.toUpperCase()).orElseThrow(() -> new RuntimeException("Entity not founded."));
    }

    @Transactional
    public Veiculo create(Veiculo entity) {
        this.validate(entity);
        entity.getRegistroStatus().setCreateAtNow();
        entity.getRegistroStatus().setActive();
        return repository.save(entity);
    }

    @Transactional
    public Veiculo update(@NonNull String id, Veiculo entity) {
        Veiculo finded = repository.findById(id).orElseThrow(() -> new RuntimeException("Entity not founded."));
        finded.setFrota(entity.getFrota());
        finded.setPlaca(entity.getPlaca());
        this.validate(finded);
        finded.getRegistroStatus().setUpdateAtNow();
        return repository.save(finded);
    }

    @Transactional
    public Veiculo setActive(@NonNull String id, @NonNull Boolean active) {
        Veiculo finded = repository.findById(id).orElseThrow(() -> new RuntimeException("Entity not founded."));
        finded.getRegistroStatus().setUpdateAtNow();
        if (active)
            finded.getRegistroStatus().setActive();
        else
            finded.getRegistroStatus().setDeactive();
        return repository.save(finded);
    }

    private Veiculo validate(Veiculo entity) {
        entity.setFrota(entity.getFrota().toUpperCase());
        entity.setPlaca(entity.getPlaca().toUpperCase());
        return entity;
    }

    @Transactional
    public void deleteById(@NonNull String id) {
        repository.findById(id).orElseThrow(() -> new RuntimeException("Entity not founded."));
        repository.deleteById(id);
    }
}
