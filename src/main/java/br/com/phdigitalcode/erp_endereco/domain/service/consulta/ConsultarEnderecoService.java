package br.com.phdigitalcode.erp_endereco.domain.service.consulta;

import br.com.phdigitalcode.erp_endereco.domain.exception.CepException;
import br.com.phdigitalcode.erp_endereco.domain.exception.QuantidadeCaracterException;
import br.com.phdigitalcode.erp_endereco.domain.model.vo.EnderecoVO;

public interface ConsultarEnderecoService {
	public static final String REGEX_CEP = "[\\d]{8}";
	default void validaConsultaCep(String cep){
		if (cep.length() != 8) {
			throw new QuantidadeCaracterException("Caracter diferente de 8");
		}
		if (!cep.matches(REGEX_CEP)) {
			throw new CepException("Cep Invalido");
		}
	}
	EnderecoVO consultaViaCep(String cep);
}
