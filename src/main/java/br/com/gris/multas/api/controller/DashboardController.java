package br.com.gris.multas.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gris.multas.domain.model.Dashboard;
import br.com.gris.multas.domain.service.DashboardService;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
  @Autowired private DashboardService service;

  @GetMapping()
  public ResponseEntity<Dashboard> getDashboard() {
    return new ResponseEntity<Dashboard>(service.getDashboard(), HttpStatus.OK);
  }
}
