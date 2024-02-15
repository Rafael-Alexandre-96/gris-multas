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

import br.com.gris.multas.domain.model.Multa;
import br.com.gris.multas.domain.service.MultaService;


@RestController
@RequestMapping("/api/multa")
public class MultaController {
  @Autowired private MultaService service;
  
  @GetMapping()
  public List<Multa> findAll() {
    return service.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Multa> findById(@NonNull @PathVariable String id) {
    return new ResponseEntity<Multa>(service.findById(id), HttpStatus.OK);
  }

  @GetMapping("/filtro/all")
  public List<Multa> findByFieldContains(
    @NonNull @RequestParam (name = "field", defaultValue = "local") String field,
    @NonNull @RequestParam (name = "value", defaultValue = "") String value
  ) {
    return service.findByFieldContains(field, value);
  }
  
  @GetMapping("/filtro/pageable")
  public Page<Multa> findByFieldContains(
    @NonNull @RequestParam (name = "field", defaultValue = "local") String field,
    @NonNull @RequestParam (name = "value", defaultValue = "") String value,
    @NonNull @RequestParam (name = "page", defaultValue = "0") Integer page,
    @NonNull @RequestParam (name = "inPage", defaultValue = "10") Integer inPage,
    @NonNull @RequestParam (name = "sort", defaultValue = "local") String sort,
    @NonNull @RequestParam (name = "asc", defaultValue = "true") Boolean asc
  ) {
    return service.findByFieldContains(field, value, page, inPage, sort, asc);
  }

  @PostMapping()
  public ResponseEntity<Multa> create(@NonNull @RequestBody Multa input) {
    return new ResponseEntity<Multa>(service.create(input), HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Multa> update(@NonNull @PathVariable String id, @NonNull @RequestBody Multa input) {
    return new ResponseEntity<Multa>(service.update(id, input), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@NonNull @PathVariable String id) {
    service.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
