package br.com.phdigitalcode.erp_endereco.infra.impl.endereco;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import br.com.phdigitalcode.erp_endereco.domain.model.dto.ConsultaLogradouroFiltroDto;
import br.com.phdigitalcode.erp_endereco.domain.model.payload.EnderecoViaCepPayload;
import br.com.phdigitalcode.erp_endereco.domain.model.vo.EnderecoVO;
import br.com.phdigitalcode.erp_endereco.domain.model.vo.LogradouroVO;
import br.com.phdigitalcode.erp_endereco.domain.repository.OUTEnderecoRepository;
import br.com.phdigitalcode.erp_endereco.infra.proxy.ViaCepProxy;

@Component
public class EnderecoRepositoryProxyImpl implements OUTEnderecoRepository {
	@Autowired
	ViaCepProxy repository;

	@Override
	public Optional<EnderecoVO> consultaEnderecoPorCep(String cep) {
		try {
			EnderecoViaCepPayload consultaCep = repository.consultaCep(cep);
			if (consultaCep == null) {
				return Optional.empty();
			}
			return Optional.of(consultaCep.converter());
		} catch (Exception e) {
			return Optional.empty();
		}
	}

	@Override
	public Optional<Page<LogradouroVO>> consultaLogradouro(ConsultaLogradouroFiltroDto consultaLogradouroFiltro) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public void salvarEndereco(EnderecoVO enderecoVO) {
		// TODO Auto-generated method stub
		
	}

}
