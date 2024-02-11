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

  public Page<Enquadramento> findByFiltro(
    @NonNull String descricao,
    @NonNull Integer page,
    @NonNull Integer inPage,
    @NonNull String sort,
    @NonNull Boolean asc
  ) {
    PageRequest pageable = PageRequest.of(page, inPage, asc ? Sort.by(sort) : Sort.by(sort).descending());
    var entities = repository.findByDescricaoContains(descricao.toUpperCase(), pageable);
    return entities;
  }

  @Transactional
  public Enquadramento create(@NonNull Enquadramento entity) {
    return repository.save(this.validateEnquadramento(entity));
  }

  @Transactional
  public void createAll(@NonNull List<Enquadramento> entities) {
    repository.saveAll(entities);
  }

  @Transactional
  public Enquadramento update(@NonNull String id, Enquadramento entity) {
    Enquadramento finded = repository.findById(id).orElseThrow(() -> this.throwEntityNotFoundException(id));
    entity.setId(finded.getId());
    return repository.save(this.validateEnquadramento(entity));
  }

  private Enquadramento validateEnquadramento(@NonNull Enquadramento entity) {
    CustomConstraintViolationException ex = new CustomConstraintViolationException("Um ou mais campos estão inválidos.");

    if (entity.getNumeroEnquadramento() == null || entity.getNumeroEnquadramento().length() != 6)
			ex.addFieldError("NumeroEnquadramento", "Deve ter que 6 caracteres.");

    if (entity.getDescricao() == null || entity.getDescricao().length() < 10)
			ex.addFieldError("Descricao", "Deve ter mais que 10 caracteres.");

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
    return new EntityNotFoundException(String.format("Entity (%s - ID %s) not founded.", "enquadramento", id));
  }
}
