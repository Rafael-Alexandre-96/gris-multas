package br.com.gris.multas.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gris.multas.domain.model.Motorista;
import br.com.gris.multas.domain.service.MotoristaService;


@RestController
@RequestMapping("/api/motorista")
public class MotoristaController {
    @Autowired private MotoristaService service;
    
    @GetMapping()
    public List<Motorista> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Motorista> findById(@NonNull @PathVariable String id) {
        return new ResponseEntity<Motorista>(service.findById(id), HttpStatus.OK);
    }
    
    @PostMapping()
    public ResponseEntity<Motorista> create(@RequestBody Motorista input) {
        return new ResponseEntity<Motorista>(service.create(input), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Motorista> update(@NonNull @PathVariable String id, @RequestBody Motorista input) {
        return new ResponseEntity<Motorista>(service.update(id, input), HttpStatus.OK);
    }

    @PatchMapping("/{id}/active")
    public ResponseEntity<Motorista> active(@NonNull @PathVariable String id) {
        return new ResponseEntity<Motorista>(service.setActive(id, true), HttpStatus.OK);
    }

    @PatchMapping("/{id}/deactive")
    public ResponseEntity<Motorista> deactive(@NonNull @PathVariable String id) {
        return new ResponseEntity<Motorista>(service.setActive(id, false), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@NonNull @PathVariable String id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
