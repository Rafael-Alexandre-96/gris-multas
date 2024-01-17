package br.com.gris.multas.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.gris.multas.api.exception.EntityNotFoundException;
import br.com.gris.multas.domain.model.Multa;
import br.com.gris.multas.domain.repository.MultaRepository;

@Service
public class MultaService {
    @Autowired private MultaRepository repository;
    @Autowired private MotoristaService motoristaService;
    @Autowired private VeiculoService veiculoService;
    @Autowired private EnquadramentoService enquadramentoService;

    public List<Multa> findAll() {
        return repository.findAll();
    }

    public Multa findById(@NonNull String id) {
        return repository.findById(id).orElseThrow(() -> this.throwEntityNotFoundException(id));
    }

    @Transactional
    public Multa create(@NonNull Multa entity) {
        entity.getRegistroStatus().setCreateAtNow();
        entity.getRegistroStatus().setActive();
        return repository.save(this.validate(entity));
    }

    @Transactional
    public Multa update(@NonNull String id, @NonNull Multa entity) {
        Multa finded = repository.findById(id).orElseThrow(() -> this.throwEntityNotFoundException(id));
        entity.setId(finded.getId());
        entity.setRegistroStatus(finded.getRegistroStatus());
        entity.getRegistroStatus().setUpdateAtNow();
        return repository.save(this.validate(entity));
    }

    private Multa validate(@NonNull Multa entity) {
        entity.setEnquadramento(enquadramentoService.findById(entity.getEnquadramento().getId()));

        if (entity.getMotorista() != null)
            entity.setMotorista(motoristaService.findById(entity.getMotorista().getId()));

        if (entity.getVeiculo() != null)
            entity.setVeiculo(veiculoService.findById(entity.getVeiculo().getId()));

        if (entity.getSemiReboque() != null)
            entity.setSemiReboque(veiculoService.findById(entity.getSemiReboque().getId()));


        if (entity.getValorBoleto() == null)
            entity.setValorBoleto(entity.getEnquadramento().getValor());

        return entity;
    }

    @Transactional
    public void deleteById(@NonNull String id) {
        repository.findById(id).orElseThrow(() -> this.throwEntityNotFoundException(id));
        repository.deleteById(id);
    }

    private RuntimeException throwEntityNotFoundException(String id) {
        return new EntityNotFoundException(String.format("Entity (%s - ID %s) not founded.", "multa", id));
    }
}
