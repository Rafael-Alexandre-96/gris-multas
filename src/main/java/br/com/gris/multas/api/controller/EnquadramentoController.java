package br.com.gris.multas.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
