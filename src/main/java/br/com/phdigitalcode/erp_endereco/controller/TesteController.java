package br.com.phdigitalcode.erp_endereco.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.phdigitalcode.erp_endereco.infra.entity.Estado;
import br.com.phdigitalcode.erp_endereco.infra.entity.Municipio;
import br.com.phdigitalcode.erp_endereco.infra.repository.EstadoRepository;
import br.com.phdigitalcode.erp_endereco.infra.repository.MunicipioRepository;

@RestController
@RequestMapping("/teste")
public class TesteController {
	@Autowired
	MunicipioRepository repo;
	@GetMapping
	public String test(){
		 List<Municipio> findAll = repo.findAll();
		 return ""+findAll;
	}
}
