package br.com.phdigitalcode.erp_endereco.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.phdigitalcode.erp_endereco.domain.facet.FacetEndereco;
import br.com.phdigitalcode.erp_endereco.domain.model.vo.EnderecoVO;
import br.com.phdigitalcode.erp_endereco.domain.repository.OUTEnderecoRepository;

@RestController
@RequestMapping("endereco")
@CrossOrigin("*")
public class EnderecoController {
	@Autowired
	private FacetEndereco facet;
	
	@GetMapping
	public EnderecoVO consultaCep(String cep) throws InterruptedException {
		return facet.consultaEnderecoPorCep(cep);				 
	}
	private EnderecoVO consultaPorLogradouro(String logradouro) {
		// TODO Auto-generated method stubreturn 
		return facet.consultaEnderecoPorLogradouro(logradouro);
		
	}
}
