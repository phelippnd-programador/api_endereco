package br.com.phdigitalcode.erp_endereco.domain.service.gravar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.phdigitalcode.erp_endereco.domain.model.vo.EnderecoVO;
import br.com.phdigitalcode.erp_endereco.domain.repository.OUTEnderecoRepository;
@Component
public class GravarEnderecoDB implements GravarEndereco {
	@Autowired
	OUTEnderecoRepository repository;
	@Override
	public void gravarEndereco(EnderecoVO vo) {
		repository.salvarEndereco(vo);
	}
	
	
	
}
