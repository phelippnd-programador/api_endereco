package br.com.phdigitalcode.erp_endereco.domain.service.consulta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import br.com.phdigitalcode.erp_endereco.domain.exception.NotFoundException;
import br.com.phdigitalcode.erp_endereco.domain.model.vo.EnderecoVO;
import br.com.phdigitalcode.erp_endereco.domain.repository.OUTEnderecoRepository;
@Component("consultaEnderecoViaCep")
public class ConsultaEnderecoViaCep implements ConsultarEnderecoService {
	@Autowired
	@Qualifier("enderecoRepositoryProxy")
	private OUTEnderecoRepository repository;
	@Override
	public EnderecoVO consultaViaCep(String cep) {
		validaConsultaCep(cep);
		return repository
					.consultaEnderecoPorCep(cep)
					.orElseThrow(() -> new NotFoundException("Endereco não encontrado!"));
	}
}
