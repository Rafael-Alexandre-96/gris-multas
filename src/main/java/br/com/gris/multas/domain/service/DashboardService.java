package br.com.gris.multas.domain.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gris.multas.domain.model.Dashboard;
import br.com.gris.multas.domain.model.Multa;
import br.com.gris.multas.domain.model.enums.Infrator;
import br.com.gris.multas.domain.model.enums.Situacao;
import br.com.gris.multas.domain.repository.MotoristaRepository;
import br.com.gris.multas.domain.repository.MultaRepository;
import br.com.gris.multas.domain.repository.VeiculoRepository;

@Service
public class DashboardService {
  @Autowired private MultaRepository multaRepository;
  @Autowired private MotoristaRepository motoristaRepository;
  @Autowired private VeiculoRepository veiculoRepository;

  public Dashboard getDashboard() {
    Dashboard dashboard = new Dashboard();
    List<Multa> multas = multaRepository.findAll();

    List<Multa> agAssinatura = new ArrayList<>(multas);
    agAssinatura.removeIf(multa -> multa.getSituacao() != Situacao.AGUARDANDO_ASSINATURA);
    dashboard.setAguardandoAssinatura(agAssinatura.size());

    List<Multa> agBoleto = new ArrayList<>(multas);
    agBoleto.removeIf(multa -> (multa.getSituacao() != Situacao.AGUARDANDO_BOLETO || multa.getSituacao() != Situacao.AGUARDANDO_BOLETO_NI || multa.getSituacao() != Situacao.AGUARDANDO_NI));
    dashboard.setAguardandoBoletos(agBoleto.size());

    List<Multa> agEnvio = new ArrayList<>(multas);
    agEnvio.removeIf(multa -> multa.getSituacao() != Situacao.ENVIAR_PARA_FINANCEIRO);
    dashboard.setAguardandoEnvio(agEnvio.size());

    dashboard.setMotoristaInfrator(multaRepository.findByInfrator(Infrator.MOTORISTA).size());
    dashboard.setTotalMulta(multaRepository.findAll().size());

    dashboard.setMotoristasAtivos(motoristaRepository.findAllActive().size());
    dashboard.setVeiculosAtivos(veiculoRepository.findAllTracaoActive().size());
    dashboard.setReboquesAtivos(veiculoRepository.findAllReboqueActive().size());
    return dashboard;
  }
}
