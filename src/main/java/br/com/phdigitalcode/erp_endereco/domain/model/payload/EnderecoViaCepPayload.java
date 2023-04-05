package br.com.phdigitalcode.erp_endereco.domain.model.payload;

import java.io.Serializable;

import br.com.phdigitalcode.erp_endereco.domain.model.vo.EnderecoVO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
@Getter
@RequiredArgsConstructor
public class EnderecoViaCepPayload implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	private final String cep;
	private final String logradouro;
	private final String complemento;
	private final String bairro;
	private final String localidade;
	private final String uf;
	private final String ibge;
	private final String gia;
	private final String ddd;
	private final String siafi;
	
	public EnderecoVO converter() {
		return new EnderecoVO(cep, logradouro, "", complemento,localidade, bairro, uf);
	}

}
