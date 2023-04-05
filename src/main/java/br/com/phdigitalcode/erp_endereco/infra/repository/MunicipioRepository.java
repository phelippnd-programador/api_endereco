package br.com.phdigitalcode.erp_endereco.infra.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.phdigitalcode.erp_endereco.infra.entity.Municipio;
@Repository
public interface MunicipioRepository extends JpaRepository<Municipio, Integer> {

	List<Municipio> findByDescricao(String descricao);

	Municipio findByDescricaoAndEstado(String estadoDescricao, Integer id);

}
