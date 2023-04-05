package br.com.phdigitalcode.erp_endereco.infra.impl.endereco;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

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
		Logradouro logradouro = new Logradouro();
		logradouro.setCep(enderecoVO.getCep());
		logradouro.setDescricao(enderecoVO.getLogradouro());
		logradouro.setComplemento(enderecoVO.getComplemento());
		Bairro bairro =carregaBairro(enderecoVO);
		logradouro.setBairro(bairro);
		return logradouro;
	}
	private Bairro carregaBairro(EnderecoVO enderecoVO) {
		List<Bairro> findByDescricao = bairroRepository.findByDescricao(enderecoVO.getBairro());
		Optional<Bairro> bairro = findByDescricao.parallelStream().filter(null).findFirst();
		if(bairro.isPresent())
		{
			return bairro.get();
		}
		return new Bairro(null,carregaMunicipio(enderecoVO),enderecoVO.getBairro());
	}

	private Municipio carregaMunicipio(EnderecoVO enderecoVO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void salvarEndereco(EnderecoVO enderecoVO) {
		if(enderecoVO== null) {
			throw new IllegalArgumentException("Endereço encontra-se nulo!");
		}
		Optional<Estado> estado = estadoRepository
					.findByDescricao(enderecoVO.getEstado()).parallelStream().findFirst();
		if (estado.isEmpty()) {
			gravarEstado(enderecoVO.getEstado());
		}
		Optional<Municipio> municipio = municipioRepository.findByDescricao(enderecoVO.getMunicipio())
					.parallelStream()
					.filter(m -> m.getEstado().getDescricao().equalsIgnoreCase(enderecoVO.getEstado()))
					.findFirst();
		if (municipio.isEmpty()) {
			gravarMunicipio(enderecoVO.getMunicipio(), enderecoVO.getEstado());
		}
		Optional<Bairro> bairro = bairroRepository.findByDescricao(enderecoVO.getBairro())
					.parallelStream()
					.filter(b -> b.getMunicipio().getDescricao().equalsIgnoreCase(enderecoVO.getMunicipio()))
					.filter(b -> b.getMunicipio().getEstado().getDescricao().equalsIgnoreCase(enderecoVO.getEstado()))
					.findFirst();
		if (bairro.isEmpty()) {
			gravarBairro(enderecoVO.getBairro(), enderecoVO.getMunicipio(), enderecoVO.getEstado());
		}
		Optional<Logradouro> logradouro = logradouroRepository.findByCep(enderecoVO.getCep().replace("-", ""))
					.parallelStream()
					.filter(b -> b.getBairro().getDescricao().equalsIgnoreCase(enderecoVO.getBairro()))
					.filter(b -> b.getBairro().getMunicipio().getDescricao()
								.equalsIgnoreCase(enderecoVO.getMunicipio()))
					.filter(b -> b.getBairro().getMunicipio().getEstado().getDescricao()
								.equalsIgnoreCase(enderecoVO.getEstado()))
					.findFirst();
		if (logradouro.isEmpty()) {
			gravarLogadouro(enderecoVO.getCep().replace("-", ""),enderecoVO.getComplemento(), enderecoVO.getLogradouro(), enderecoVO.getBairro(),
						enderecoVO.getMunicipio(), enderecoVO.getEstado());
		}

	}

	private void gravarLogadouro(String cep,String complemento, String logradouroDescricao, String bairroDescricao, String municipioDescricao, String estadoDescricao) {
		Logradouro logradouro = new Logradouro();
		Bairro bairro = bairroRepository.findByDescricao(bairroDescricao).parallelStream()
			.filter(b->b.getMunicipio().getDescricao().equalsIgnoreCase(municipioDescricao))
			.filter(b->b.getMunicipio().getEstado().getDescricao().equalsIgnoreCase(estadoDescricao))
			.findFirst().orElseThrow()
			;
		logradouro.setBairro(bairro);
		logradouro.setCep(cep);
		logradouro.setDescricao(logradouroDescricao);
		logradouro.setComplemento(complemento);
		logradouroRepository.saveAndFlush(logradouro);

	}

	private void gravarBairro(String bairroDescricao, String municipioDescricao, String estadoDescricao) {
		
		List<Municipio> findByDescricao = municipioRepository
					.findByDescricao(municipioDescricao);
		Municipio municipio = findByDescricao
					.parallelStream()
					.filter(m->m.getEstado().getDescricao().equalsIgnoreCase(estadoDescricao))
					.findFirst().orElseThrow();
		Bairro bairro = new Bairro();
		bairro.setDescricao(bairroDescricao);
		bairro.setMunicipio(municipio);
		bairroRepository.saveAndFlush(bairro);
	}

	private void gravarMunicipio(String municipioDescricao, String estadoDescricao) {
		if(!StringUtils.hasText(municipioDescricao)) {
			throw new IllegalArgumentException("Nome do municipio encontra-se nula");
		}
		Estado estado = estadoRepository.findByDescricao(estadoDescricao)
					.parallelStream()
					.findFirst().orElseThrow();
		Municipio municipio = new Municipio();
		municipio.setDescricao(municipioDescricao);
		municipio.setEstado(estado);
		municipioRepository.saveAndFlush(municipio);
	}

	private void gravarEstado(String estadoDescricao) {
		if(estadoDescricao==null) {
			throw new IllegalArgumentException("Nome do estado encontra-se nula");
		}
		Estado estado = new Estado();
		estado.setDescricao(estadoDescricao);
		estadoRepository.saveAndFlush(estado);

	}

}
