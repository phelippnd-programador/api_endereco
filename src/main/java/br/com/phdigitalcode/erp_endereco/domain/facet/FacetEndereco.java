package br.com.phdigitalcode.erp_endereco.domain.facet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.com.phdigitalcode.erp_endereco.domain.exception.NotFoundException;
import br.com.phdigitalcode.erp_endereco.domain.model.vo.EnderecoVO;
import br.com.phdigitalcode.erp_endereco.domain.service.EnderecoService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FacetEndereco {
	@Autowired
	private EnderecoService enderecoServiceDB;
	@Autowired
	@Qualifier( "enderecoServiceProxy")
	private EnderecoService enderecoServiceProxy;

	public synchronized EnderecoVO consultaEnderecoPorCep(String cep) throws InterruptedException {
		
		try {
			return enderecoServiceDB.consultaViaCep(cep);
		} catch (NotFoundException e) {
			EnderecoVO endereco = enderecoServiceProxy.consultaViaCep(cep);
			Thread thread = new Thread(()->{
				enderecoServiceDB.gravarEndereco(endereco);				
			});
			thread.start();
			
			return endereco;
		}
	}

	public EnderecoVO consultaEnderecoPorLogradouro(String logradouro) {
		// TODO Auto-generated method stub
		return null;
	}

}
