package br.com.phdigitalcode.erp_endereco.domain.service;

import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;

import br.com.phdigitalcode.erp_endereco.domain.model.dto.ConsultaBairroFiltroDto;
import br.com.phdigitalcode.erp_endereco.domain.model.dto.ConsultaEstadoFiltroDto;
import br.com.phdigitalcode.erp_endereco.domain.model.dto.ConsultaLogradouroFiltroDto;
import br.com.phdigitalcode.erp_endereco.domain.model.dto.ConsultaMunicipioFiltroDto;
import br.com.phdigitalcode.erp_endereco.domain.model.dto.ConsultaPaisFiltroDto;
import br.com.phdigitalcode.erp_endereco.domain.model.vo.BairroVO;
import br.com.phdigitalcode.erp_endereco.domain.model.vo.EnderecoVO;
import br.com.phdigitalcode.erp_endereco.domain.model.vo.EstadoVO;
import br.com.phdigitalcode.erp_endereco.domain.model.vo.LogradouroVO;
import br.com.phdigitalcode.erp_endereco.domain.model.vo.MunicipioVO;
import br.com.phdigitalcode.erp_endereco.domain.model.vo.PaisVO;
import br.com.phdigitalcode.erp_endereco.domain.service.consulta.ConsultarEnderecoService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EnderecoServiceProxyImpl implements EnderecoService {
	private final ConsultarEnderecoService consultarEnderecoService;

	@Override
	public EnderecoVO consultaViaCep(String cep) {
		return consultarEnderecoService.consultaViaCep(cep);
					
	}

	@Override
	public Page<LogradouroVO> listarLogradouro(ConsultaLogradouroFiltroDto consultaLogradouroFiltro) {
		if (consultaLogradouroFiltro == null) {
			throw new NullPointerException("Parametro passado não pode ser nulo!");
		}
		if (!StringUtils.hasText(consultaLogradouroFiltro.getBairro())
					&& !StringUtils.hasText(consultaLogradouroFiltro.getMunicipio())
					&& !StringUtils.hasText(consultaLogradouroFiltro.getEstado())
					&& consultaLogradouroFiltro.getPagina()==null
					&& consultaLogradouroFiltro.getQuantidade()==null
					) {
			throw new IllegalArgumentException("Todos os campos se encontra nulos ou vazios!");
		}
		if(consultaLogradouroFiltro.getQuantidade()==null) {
			throw new NullPointerException("Quantidade não deve ser nula");	
		}
		if(consultaLogradouroFiltro.getPagina()==null) {
			throw new NullPointerException("Pagina não deve ser nula");	
		}
		if(consultaLogradouroFiltro.getQuantidade().compareTo(0)<=0) {
			throw new IllegalArgumentException("Quantidade deve se superior a zero");	
		}
		if(consultaLogradouroFiltro.getPagina().compareTo(0)<0) {
			throw new IllegalArgumentException("Pagina deve maior ou igual a zero");	
		}
		return null;
//		return repository.consultaLogradouro(consultaLogradouroFiltro)
//			.orElseThrow(()->new NoContentException("Nenhum conteudo encontrado!"));
	}

	@Override
	public Page<BairroVO> listarBairro(ConsultaBairroFiltroDto consultaBairroFiltro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<MunicipioVO> listarMunicipio(ConsultaMunicipioFiltroDto consultaMunicipioFiltro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<EstadoVO> listarEstado(ConsultaEstadoFiltroDto consultaEstadoFiltro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<PaisVO> listarPais(ConsultaPaisFiltroDto consultaEstadoFiltro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void gravarEndereco(EnderecoVO enderecoVO) {
		// TODO Auto-generated method stub
		
	}

}
