package br.com.phdigitalcode.erp_endereco.infra.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.phdigitalcode.erp_endereco.infra.entity.Estado;
import br.com.phdigitalcode.erp_endereco.infra.entity.Logradouro;
@Repository
public interface LogradouroRepository extends JpaRepository<Logradouro, Integer> {

	boolean existsByCep(String cep);

	List<Logradouro> findByCep(String cep);

	Collection<Estado> findByDescricao(String logradouro);

}
