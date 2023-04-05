package br.com.phdigitalcode.erp_endereco.domain.model.vo;

import java.io.Serializable;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class EnderecoVO implements Serializable {
	private static final long serialVersionUID = 1L;
	private final String cep;
	private final String logradouro;
	private final String numero;
	private final String complemento;
	private final String municipio;
	private final String bairro;
	private final String estado;

}
