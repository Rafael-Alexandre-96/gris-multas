package br.com.gris.multas.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.gris.multas.domain.model.Multa;
import br.com.gris.multas.domain.repository.MotoristaRepository;
import br.com.gris.multas.domain.repository.MultaRepository;
import br.com.gris.multas.domain.repository.VeiculoRepository;

@Service
public class MultaService {
    @Autowired private MultaRepository repository;
    @Autowired private MotoristaRepository motoristaRepository;
    @Autowired private VeiculoRepository veiculoRepository;
    @Autowired private EnquadramentoService enquadramentoService;

    public List<Multa> findAll() {
        return repository.findAll();
    }

    public Multa findById(@NonNull String id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Entity not founded."));
    }

    @Transactional
    public Multa create(@NonNull Multa entity) {
        entity.getRegistroStatus().setCreateAtNow();
        entity.getRegistroStatus().setActive();
        return repository.save(this.validate(entity));
    }

    @Transactional
    public Multa update(@NonNull String id, @NonNull Multa entity) {
        Multa finded = repository.findById(id).orElseThrow(() -> new RuntimeException("Entity not founded."));
        entity.setId(finded.getId());
        entity.setRegistroStatus(finded.getRegistroStatus());
        entity.getRegistroStatus().setUpdateAtNow();
        return repository.save(this.validate(entity));
    }

    private Multa validate(@NonNull Multa entity) {
        entity.setEnquadramento(enquadramentoService.findById(entity.getEnquadramento().getId()));

        if (entity.getMotorista() != null)
            entity.setMotorista(motoristaRepository.findById(entity.getMotorista().getId()).get());

        if (entity.getVeiculo() != null)
            entity.setVeiculo(veiculoRepository.findById(entity.getVeiculo().getId()).get());

        if (entity.getSemiReboque() != null)
            entity.setSemiReboque(veiculoRepository.findById(entity.getSemiReboque().getId()).get());


        if (entity.getValorBoleto() == null)
            entity.setValorBoleto(entity.getEnquadramento().getValor());

        return entity;
    }

    @Transactional
    public void deleteById(@NonNull String id) {
        repository.findById(id).orElseThrow(() -> new RuntimeException("Entity not founded."));
        repository.deleteById(id);
    }
}
