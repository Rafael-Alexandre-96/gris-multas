package br.com.gris.multas.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.gris.multas.domain.model.Enquadramento;
import br.com.gris.multas.domain.service.EnquadramentoService;



@RestController
@RequestMapping("/api/enquadramento")
public class EnquadramentoController {
  @Autowired private EnquadramentoService service;
  
  @GetMapping()
  public List<Enquadramento> findAll() {
    return service.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Enquadramento> findById(@NonNull @PathVariable String id) {
    return new ResponseEntity<Enquadramento>(service.findById(id), HttpStatus.OK);
  }

  @GetMapping("/filtro/all")
  public List<Enquadramento> findByFieldContains(
    @NonNull @RequestParam (name = "field", defaultValue = "descricao") String field,
    @NonNull @RequestParam (name = "value", defaultValue = "") String value
  ) {
    return service.findByFieldContains(field, value);
  }

  @GetMapping("/filtro/pageable")
  public Page<Enquadramento> findByFieldContains(
    @NonNull @RequestParam (name = "field", defaultValue = "descricao") String field,
    @NonNull @RequestParam (name = "value", defaultValue = "") String value,
    @NonNull @RequestParam (name = "page", defaultValue = "0") Integer page,
    @NonNull @RequestParam (name = "inPage", defaultValue = "10") Integer inPage,
    @NonNull @RequestParam (name = "sort", defaultValue = "descricao") String sort,
    @NonNull @RequestParam (name = "asc", defaultValue = "true") Boolean asc
  ) {
    return service.findByFieldContains(field, value, page, inPage, sort, asc);
  }
  
  @PostMapping()
  public ResponseEntity<Enquadramento> create(@NonNull @RequestBody Enquadramento input) {
    return new ResponseEntity<Enquadramento>(service.create(input), HttpStatus.CREATED);
  }

  @PostMapping("/all")
  public ResponseEntity<Void> createAll(@NonNull @RequestBody List<Enquadramento> inputs) {
    service.createAll(inputs);
    return new ResponseEntity<Void>(HttpStatus.CREATED);
  }
  
  @PutMapping("/{id}")
  public ResponseEntity<Enquadramento> update(@NonNull @PathVariable String id, @RequestBody Enquadramento input) {
    return new ResponseEntity<Enquadramento>(service.update(id, input), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@NonNull @PathVariable String id) {
    service.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
