package br.com.phdigitalcode.erp_endereco.infra.impl.endereco;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import br.com.phdigitalcode.erp_endereco.domain.model.dto.ConsultaLogradouroFiltroDto;
import br.com.phdigitalcode.erp_endereco.domain.model.vo.EnderecoVO;
import br.com.phdigitalcode.erp_endereco.domain.model.vo.LogradouroVO;
import br.com.phdigitalcode.erp_endereco.domain.repository.OUTEnderecoRepository;
import br.com.phdigitalcode.erp_endereco.infra.entity.Bairro;
import br.com.phdigitalcode.erp_endereco.infra.entity.Estado;
import br.com.phdigitalcode.erp_endereco.infra.entity.Logradouro;
import br.com.phdigitalcode.erp_endereco.infra.entity.Municipio;
import br.com.phdigitalcode.erp_endereco.infra.repository.BairroRepository;
import br.com.phdigitalcode.erp_endereco.infra.repository.EstadoRepository;
import br.com.phdigitalcode.erp_endereco.infra.repository.LogradouroRepository;
import br.com.phdigitalcode.erp_endereco.infra.repository.MunicipioRepository;

@Component
public class EnderecoRepositoryBDImpl implements OUTEnderecoRepository {
	@Autowired
	public LogradouroRepository logradouroRepository;
	@Autowired
	public BairroRepository bairroRepository;
	@Autowired
	public EstadoRepository estadoRepository;
	@Autowired
	public MunicipioRepository municipioRepository;

	@Override
	public Optional<EnderecoVO> consultaEnderecoPorCep(String cep) {
		Optional<Logradouro> logradouro = logradouroRepository.findByCep(cep).parallelStream().findFirst();
		if (logradouro.isEmpty()) {
			return Optional.empty();
		}
		return Optional.of(logradouro.get().converter());
	}

	@Override
	public Optional<Page<LogradouroVO>> consultaLogradouro(ConsultaLogradouroFiltroDto consultaLogradouroFiltro) {
		return Optional.empty();
	}
	public Logradouro carregarEndereco(EnderecoVO enderecoVO) {
		List<Logradouro> findByCep = logradouroRepository
			.findByCep(enderecoVO.getCep());
		return findByCep.parallelStream()
			.filter(l->l.getDescricao()
					.equalsIgnoreCase(enderecoVO.getLogradouro()))
			.filter(l-> l.getBairro().getDescricao().equalsIgnoreCase(enderecoVO.getBairro()))
			.filter(l-> l.getBairro().getMunicipio().getDescricao().equalsIgnoreCase(enderecoVO.getMunicipio()))
			.filter(l-> l.getBairro().getMunicipio().getEstado().getDescricao().equalsIgnoreCase(enderecoVO.getEstado()))
			.findFirst()
			.orElse(new Logradouro(null, carregaBairro(enderecoVO), enderecoVO.getLogradouro(),enderecoVO.getCep(), enderecoVO.getComplemento()));
	
	}
	private Bairro carregaBairro(EnderecoVO enderecoVO) {
		List<Bairro> findByDescricao = bairroRepository.findByDescricao(enderecoVO.getBairro());
		Optional<Bairro> bairro = findByDescricao.parallelStream()
					.filter(b->b.getMunicipio().getDescricao().equalsIgnoreCase(enderecoVO.getMunicipio()))
					.filter(b->b.getMunicipio().getEstado().getDescricao().equalsIgnoreCase(enderecoVO.getEstado()))
					.findFirst();
		if(bairro.isPresent())
		{
			return bairro.get();
		}
		return new Bairro(null,carregaMunicipio(enderecoVO),enderecoVO.getBairro());
	}

	private Municipio carregaMunicipio(EnderecoVO enderecoVO) {
		List<Municipio> municipioList = municipioRepository.findByDescricao(enderecoVO.getMunicipio());
		Optional<Municipio> municipio = municipioList.parallelStream()
					.filter(m-> m.getEstado().getDescricao().equalsIgnoreCase(enderecoVO.getEstado()))
					.findFirst();
		if(municipio.isPresent()) {
			return municipio.get();
		}
		return new Municipio(null, carregaEstado(enderecoVO), enderecoVO.getMunicipio());
	}

	private Estado carregaEstado(EnderecoVO enderecoVO) {
		return estadoRepository.findByDescricao(enderecoVO.getEstado())
			.orElse(new Estado(null, enderecoVO.getEstado()));
	}

	@Override
	public void salvarEndereco(EnderecoVO enderecoVO) {
		Logradouro carregarEndereco = carregarEndereco(enderecoVO);
		logradouroRepository.saveAndFlush(carregarEndereco);

	}


	

}
