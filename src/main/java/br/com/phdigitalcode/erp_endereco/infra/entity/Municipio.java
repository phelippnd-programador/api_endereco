package br.com.phdigitalcode.erp_endereco.infra.entity;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Municipio implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@ManyToOne (cascade = CascadeType.PERSIST)
	@JoinColumn(name = "estado_id" ,referencedColumnName = "id")
	private Estado estado;
//	@OneToMany(mappedBy = "municipio",fetch = FetchType.LAZY)
//	private List<Bairro> bairro;
	private String descricao;

}
