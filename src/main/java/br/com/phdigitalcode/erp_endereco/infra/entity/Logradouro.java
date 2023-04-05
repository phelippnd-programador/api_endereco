package br.com.phdigitalcode.erp_endereco.infra.entity;

import java.io.Serializable;

import br.com.phdigitalcode.erp_endereco.domain.model.vo.EnderecoVO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Logradouro implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@ManyToOne
	private Bairro bairro;
	private String descricao;
	private String cep;
	private String complemento;
	
	public EnderecoVO converter() {
		Municipio municipio = bairro!=null? bairro.getMunicipio():null;
		Estado estado =municipio!=null? municipio.getEstado():null;
		String bairroDescricao =bairro!=null ?bairro.getDescricao():"";
		String municipioDescricao = municipio!=null ?municipio.getDescricao():"";
		String estadoDescricao =estado!=null ?estado.getDescricao():"";
		return new EnderecoVO(cep, descricao, "", complemento, municipioDescricao, bairroDescricao, estadoDescricao);
	}
}

