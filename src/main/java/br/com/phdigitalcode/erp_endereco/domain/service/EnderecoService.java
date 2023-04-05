package br.com.phdigitalcode.erp_endereco.domain.service;

import org.springframework.data.domain.Page;

import br.com.phdigitalcode.erp_endereco.domain.exception.CepException;
import br.com.phdigitalcode.erp_endereco.domain.exception.QuantidadeCaracterException;
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

public interface EnderecoService {
	EnderecoVO consultaViaCep(String cep);
//	Page<LogradouroVO> listarLogradouro(ConsultaLogradouroFiltroDto consultaEstadoFiltro);
	Page<BairroVO> listarBairro(ConsultaBairroFiltroDto consultaBairroFiltro);
	Page<MunicipioVO> listarMunicipio(ConsultaMunicipioFiltroDto consultaMunicipioFiltro);
	Page<EstadoVO> listarEstado(ConsultaEstadoFiltroDto consultaEstadoFiltro);
	Page<PaisVO> listarPais(ConsultaPaisFiltroDto consultaEstadoFiltro);
	void gravarEndereco(EnderecoVO enderecoVO);
	Page<LogradouroVO> listarLogradouro(ConsultaLogradouroFiltroDto consultaLogradouroFiltro);
	
	
}
