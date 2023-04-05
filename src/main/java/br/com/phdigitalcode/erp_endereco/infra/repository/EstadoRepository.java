package br.com.phdigitalcode.erp_endereco.infra.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.phdigitalcode.erp_endereco.infra.entity.Estado;
@Repository
public interface EstadoRepository extends JpaRepository<Estado, Integer> {

	List<Estado> findByDescricao(String estado);

}
