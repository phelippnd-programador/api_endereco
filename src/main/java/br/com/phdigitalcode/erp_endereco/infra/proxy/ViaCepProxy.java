package br.com.phdigitalcode.erp_endereco.infra.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.phdigitalcode.erp_endereco.domain.model.payload.EnderecoViaCepPayload;

@FeignClient(url = "https://viacep.com.br/ws/" ,name = "viacep")
public interface ViaCepProxy {
	@GetMapping("/{cep}/json/")
	public EnderecoViaCepPayload consultaCep(@PathVariable String cep);
}
