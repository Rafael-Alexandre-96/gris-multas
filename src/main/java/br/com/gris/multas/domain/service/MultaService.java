package br.com.gris.multas.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.gris.multas.api.exception.CustomConstraintViolationException;
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

  public List<Multa> findByFieldContains(
    @NonNull String field,
    @NonNull String value
  ) {
    var entities = repository.findByFieldContains(field, value.toUpperCase());
    return entities;
  }

  public Page<Multa> findByFieldContains(
    @NonNull String field,
    @NonNull String value,
    @NonNull Integer page,
    @NonNull Integer inPage,
    @NonNull String sort,
    @NonNull Boolean asc
  ) {
    PageRequest pageable = PageRequest.of(page, inPage, asc ? Sort.by(sort) : Sort.by(sort).descending());
    var entities = repository.findByFieldContains(field, value.toUpperCase(), pageable);
    return entities;
  }

  @Transactional
  public Multa create(@NonNull Multa entity) {
    entity.getRegistroStatus().setCreateAtNow();
    entity.getRegistroStatus().setActive();
    return repository.save(this.validateMulta(entity));
  }

  @Transactional
  public Multa update(@NonNull String id, @NonNull Multa entity) {
    Multa finded = repository.findById(id).orElseThrow(() -> this.throwEntityNotFoundException(id));
    entity.setId(finded.getId());
    entity.setRegistroStatus(finded.getRegistroStatus());
    entity.getRegistroStatus().setUpdateAtNow();
    return repository.save(this.validateMulta(entity));
  }

  @SuppressWarnings("null")
  @NonNull private Multa validateMulta(@NonNull Multa entity) {
    CustomConstraintViolationException ex = new CustomConstraintViolationException("Um ou mais campos estão inválidos.");

    if (entity.getEnquadramento() == null || entity.getEnquadramento().getId() == null) {
      ex.addFieldError("Enquadramento", "Enquadramento não pode ser nulo.");
    } else {
      entity.setEnquadramento(enquadramentoService.findById(entity.getEnquadramento().getId()));
    }
    
    if (entity.getMotorista() != null && entity.getMotorista().getId() != null)
      entity.setMotorista(motoristaService.findById(entity.getMotorista().getId()));

    if (entity.getVeiculo() != null && entity.getVeiculo().getId() != null)
      entity.setVeiculo(veiculoService.findById(entity.getVeiculo().getId()));

    if (entity.getSemiReboque() != null && entity.getSemiReboque().getId() != null)
      entity.setSemiReboque(veiculoService.findById(entity.getSemiReboque().getId()));

    if (ex.getFieldErros().size() > 0)
			throw ex;

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
