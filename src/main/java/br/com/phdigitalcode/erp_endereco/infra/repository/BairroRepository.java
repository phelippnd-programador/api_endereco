package br.com.phdigitalcode.erp_endereco.infra.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.phdigitalcode.erp_endereco.infra.entity.Bairro;
@Repository
public interface BairroRepository extends JpaRepository<Bairro, Integer> {


	List<Bairro> findByDescricao(String descricaoBairro);

}
