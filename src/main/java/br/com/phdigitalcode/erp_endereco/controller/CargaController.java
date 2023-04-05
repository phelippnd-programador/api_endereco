package br.com.phdigitalcode.erp_endereco.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.phdigitalcode.erp_endereco.domain.service.FacetCarga;
@RestController
@RequestMapping("carga")
public class CargaController {
	@Autowired
	FacetCarga carga;
	@GetMapping
	public String carrega() throws Exception {
		 carga.cargaLogradouro("CepsCompletoLimpo.xlsx");
		 return "OK";
	}
}
