package br.com.phdigitalcode.erp_endereco.domain.service.consulta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import br.com.phdigitalcode.erp_endereco.domain.exception.NotFoundException;
import br.com.phdigitalcode.erp_endereco.domain.model.vo.EnderecoVO;
import br.com.phdigitalcode.erp_endereco.domain.repository.OUTEnderecoRepository;
@Primary
@Component
public class ConsultaEnderecoDB implements ConsultarEnderecoService {
	@Autowired
	private OUTEnderecoRepository repository;
	@Override
	public EnderecoVO consultaViaCep(String cep) {
		validaConsultaCep(cep);
		return repository
					.consultaEnderecoPorCep(cep)
					.orElseThrow(() -> new NotFoundException("Endereco não encontrado!"));
	}
}
