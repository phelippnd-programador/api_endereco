package br.com.phdigitalcode.erp_endereco.domain.model.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
@Getter
@RequiredArgsConstructor
public class ConsultaLogradouroFiltroDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Integer pagina;
	private final Integer quantidade;
	private final String estado;
	private final String municipio;
	private final String bairro;
}
