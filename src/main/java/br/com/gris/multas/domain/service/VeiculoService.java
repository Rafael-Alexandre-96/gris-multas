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
import br.com.gris.multas.domain.model.Veiculo;
import br.com.gris.multas.domain.model.enums.TipoRodado;
import br.com.gris.multas.domain.repository.VeiculoRepository;

@Service
public class VeiculoService {
  @Autowired private VeiculoRepository repository;

  public List<Veiculo> findAll() {
    return repository.findAll();
  }

  public Veiculo findById(@NonNull String id) {
    return repository.findById(id).orElseThrow(() -> this.throwEntityNotFoundException(id));
  }

  public Page<Veiculo> findByFiltro(
    @NonNull String placa,
    @NonNull Boolean showDeactive,
    @NonNull Integer page,
    @NonNull Integer inPage,
    @NonNull String sort,
    @NonNull Boolean asc
  ) {
    PageRequest pageable = PageRequest.of(page, inPage, asc ? Sort.by(sort) : Sort.by(sort).descending());
    var entities = showDeactive ? repository.findByPlacaContains(placa.toUpperCase(), pageable) : repository.findByPlacaContainsActive(placa.toUpperCase(), pageable);
    return entities;
  }

  public List<Veiculo> findAllTracao() {
    return repository.findByTipoRodado(TipoRodado.TRACAO);
  }

  public List<Veiculo> findAllReboque() {
    return repository.findByTipoRodado(TipoRodado.REBOQUE);
  }

  @Transactional
  public Veiculo create(@NonNull Veiculo entity) {
    entity.getRegistroStatus().setCreateAtNow();
    entity.getRegistroStatus().setActive();
    return repository.save(this.validadeVeiculo(entity));
  }

  @Transactional
  public Veiculo update(@NonNull String id, Veiculo entity) {
    Veiculo finded = repository.findById(id).orElseThrow(() -> this.throwEntityNotFoundException(id));
    entity.setId(id);
    entity.setRegistroStatus(finded.getRegistroStatus());
    entity.getRegistroStatus().setUpdateAtNow();
    return repository.save(this.validadeVeiculo(entity));
  }

  private Veiculo validadeVeiculo(@NonNull Veiculo entity) {
    CustomConstraintViolationException ex = new CustomConstraintViolationException("Um ou mais campos estão inválidos.");

    if (entity.getPlaca() == null || !entity.getPlaca().matches("[A-Z]{3}[0-9]{1}[A-Z]{1}[0-9]{2}|[A-Z]{3}[0-9]{4}"))
			ex.addFieldError("Placa", "Formato inválido: ABC0X00.");

    if (ex.getFieldErros().size() > 0)
			throw ex;

    return entity;
  }

  @Transactional
  public Veiculo setActive(@NonNull String id, @NonNull Boolean active) {
    Veiculo finded = repository.findById(id).orElseThrow(() -> this.throwEntityNotFoundException(id));
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
    return new EntityNotFoundException(String.format("Entity (%s - ID %s) not founded.", "veiculo", id));
  }
}
