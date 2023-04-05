package br.com.phdigitalcode.erp_endereco.domain.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;

import br.com.phdigitalcode.erp_endereco.domain.model.dto.ConsultaLogradouroFiltroDto;
import br.com.phdigitalcode.erp_endereco.domain.model.vo.EnderecoVO;
import br.com.phdigitalcode.erp_endereco.domain.model.vo.LogradouroVO;

public interface OUTEnderecoRepository {

	Optional<EnderecoVO> consultaEnderecoPorCep(String cep);

	Optional<Page<LogradouroVO>> consultaLogradouro(ConsultaLogradouroFiltroDto consultaLogradouroFiltro);

	void salvarEndereco(EnderecoVO enderecoVO);

}
