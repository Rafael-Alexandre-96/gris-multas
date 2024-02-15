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
import br.com.gris.multas.domain.model.Motorista;
import br.com.gris.multas.domain.repository.MotoristaRepository;

@Service
public class MotoristaService {
  @Autowired private MotoristaRepository repository;

  public List<Motorista> findAll() {
    return repository.findAll();
  }

  public Motorista findById(@NonNull String id) {
    return repository.findById(id).orElseThrow(() -> this.throwEntityNotFoundException(id));
  }

  public List<Motorista> findByFieldContains(
    @NonNull String field,
    @NonNull String value,
    @NonNull Boolean showDeactive
  ) {
    var entities = showDeactive ? repository.findByFieldContains(field, value.toUpperCase()) : repository.findByFieldContainsActive(field, value.toUpperCase());
    return entities;
  }

  public Page<Motorista> findByFieldContains(
    @NonNull String field,
    @NonNull String value,
    @NonNull Boolean showDeactive,
    @NonNull Integer page,
    @NonNull Integer inPage,
    @NonNull String sort,
    @NonNull Boolean asc
  ) {
    PageRequest pageable = PageRequest.of(page, inPage, asc ? Sort.by(sort) : Sort.by(sort).descending());
    var entities = showDeactive ? repository.findByFieldContains(field, value.toUpperCase(), pageable) : repository.findByFieldContainsActive(field, value.toUpperCase(), pageable);
    return entities;
  }

  @Transactional
  public Motorista create(@NonNull Motorista entity) {
    entity.getRegistroStatus().setCreateAtNow();
    entity.getRegistroStatus().setActive();
    return repository.save(this.validadeMotorista(entity));
  }

  @Transactional
  public Motorista update(@NonNull String id, Motorista entity) {
    Motorista finded = repository.findById(id).orElseThrow(() -> this.throwEntityNotFoundException(id));
    entity.setId(id);
    entity.setRegistroStatus(finded.getRegistroStatus());
    entity.getRegistroStatus().setUpdateAtNow();
    return repository.save(this.validadeMotorista(entity));
  }

  @NonNull private Motorista validadeMotorista(@NonNull Motorista entity) {
    CustomConstraintViolationException ex = new CustomConstraintViolationException("Um ou mais campos estão inválidos.");

    if (entity.getNome() == null || entity.getNome().length() < 10)
			ex.addFieldError("Nome", "Deve ter mais que 10 caracteres.");

    if (entity.getCpf() == null || !entity.getCpf().matches("[0-9]{11}"))
			ex.addFieldError("CPF", "Deve conter 11 numeros.");

    if (ex.getFieldErros().size() > 0)
			throw ex;

    return entity;
  }

  @Transactional
  public Motorista setActive(@NonNull String id, @NonNull Boolean active) {
    Motorista finded = repository.findById(id).orElseThrow(() -> this.throwEntityNotFoundException(id));
    finded.getRegistroStatus().setUpdateAtNow();
    if (active)
      finded.getRegistroStatus().setActive();
    else
      finded.getRegistroStatus().setDeactive();
    return repository.save(finded);
  }

  @Transactional
  public void deleteById(@NonNull String id) {
    repository.findById(id).orElseThrow(() -> this.throwEntityNotFoundException(id));
    repository.deleteById(id);
  }

  private RuntimeException throwEntityNotFoundException(String id) {
    return new EntityNotFoundException(String.format("Entity (%s - ID %s) not founded.", "motorista", id));
  }
}
