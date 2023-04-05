package br.com.phdigitalcode.erp_endereco.domain.model.vo.planilha;

import java.io.Serializable;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor

public class Cell implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Integer indice;
	private final String value;
}
