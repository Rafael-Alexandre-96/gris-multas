package br.com.gris.multas.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.gris.multas.api.exception.EntityNotFoundException;
import br.com.gris.multas.domain.model.Enquadramento;
import br.com.gris.multas.domain.repository.EnquadramentoRepository;

@Service
public class EnquadramentoService {
    @Autowired private EnquadramentoRepository repository;

    public List<Enquadramento> findAll() {
        return repository.findAll();
    }

    public Enquadramento findById(@NonNull String id) {
        return repository.findById(id).orElseThrow(() -> this.throwEntityNotFoundException(id));
    }

    @Transactional
    public Enquadramento create(@NonNull Enquadramento entity) {
        return repository.save(entity);
    }

    @Transactional
    public void createAll(@NonNull List<Enquadramento> entities) {
        repository.saveAll(entities);
    }

    @Transactional
    public Enquadramento update(@NonNull String id, Enquadramento entity) {
        Enquadramento finded = repository.findById(id).orElseThrow(() -> this.throwEntityNotFoundException(id));
        entity.setId(finded.getId());
        return repository.save(entity);
    }

    @Transactional
    public void deleteById(@NonNull String id) {
        repository.findById(id).orElseThrow(() -> this.throwEntityNotFoundException(id));
        repository.deleteById(id);
    }

    private RuntimeException throwEntityNotFoundException(String id) {
        return new EntityNotFoundException(String.format("Entity (%s - ID %s) not founded.", "enquadramento", id));
    }
}
