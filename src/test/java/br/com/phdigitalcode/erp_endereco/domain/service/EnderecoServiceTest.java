package br.com.phdigitalcode.erp_endereco.domain.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import br.com.phdigitalcode.erp_endereco.domain.exception.CepException;
import br.com.phdigitalcode.erp_endereco.domain.exception.NoContentException;
import br.com.phdigitalcode.erp_endereco.domain.exception.NotFoundException;
import br.com.phdigitalcode.erp_endereco.domain.exception.QuantidadeCaracterException;
import br.com.phdigitalcode.erp_endereco.domain.model.dto.ConsultaLogradouroFiltroDto;
import br.com.phdigitalcode.erp_endereco.domain.model.vo.EnderecoVO;
import br.com.phdigitalcode.erp_endereco.domain.model.vo.LogradouroVO;
import br.com.phdigitalcode.erp_endereco.domain.repository.OUTEnderecoRepository;

class EnderecoServiceTest {
	@InjectMocks
	EnderecoServiceImpl service;
	@Mock
	OUTEnderecoRepository repository;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
	}

	/**
	 * Metodo retorno Endereco por cep
	 */
	@Test
	@DisplayName("Deve retornar erro quando a quantidade de caracter do cep for diferente de 8")
	void deveRetornarErroAoTerNumeroDiferenteDe8CaracterNoCep() {
		String cep = "0100390";
		assertThrows(QuantidadeCaracterException.class, () -> service.consultaViaCep(cep));
	}

	@Test
	@DisplayName("Deve retornar erro quando o cep contiver letras")
	void deveRetornarErroQuandoOCepContiverLetras() {
		String cep = "0100390A";
		assertThrows(CepException.class, () -> service.consultaViaCep(cep));
	}

	@Test
	@DisplayName("Deve retornar erro quando o cep não for encontrado")
	void deveRetornarErroQuandoOCepNaoForEncontrado() {
		String cep = "01003902";
		EnderecoVO enderecoResultado = new EnderecoVO("01003902", "Rua José Bonifácio, 209", "209", "complemento",
					"São Paulo", "Rua José Bonifácio, 209", "SP");
		Mockito.when(repository.consultaEnderecoPorCep(Mockito.anyString()))
					.thenReturn(Optional.empty());
		assertThrows(NotFoundException.class, () -> service.consultaViaCep(cep));
	}

	@Test
	@DisplayName("Nao deve retornar erro quando o cep for encontrado")
	void naoDeveRetornarErroQuandoOCepNaoForEncontrado() {
		String cep = "01003902";
		EnderecoVO enderecoResultado = new EnderecoVO("01003902", "Rua José Bonifácio, 209", "209", "complemento",
					"São Paulo", "Rua José Bonifácio, 209", "SP");
		Mockito.when(repository.consultaEnderecoPorCep(Mockito.anyString()))
					.thenReturn(Optional.of(enderecoResultado));
		assertDoesNotThrow(() -> service.consultaViaCep(cep));
	}

	/**
	 * Listar Logradouro
	 */
	@Test
	@DisplayName("Deve retornar erro quando o filtro for nulo")
	void deveRetornarErroQuandoOFiltroForNulo() {
		ConsultaLogradouroFiltroDto filtro = null;
		assertThrows(NullPointerException.class, () -> service.listarLogradouro(filtro));
	}

	@Test
	@DisplayName("Deve retornar erro quando todas as propriedades forem vazias")
	void deveRetornarErroQuandoTodasAsPropriedadesForemVaziasOuNulas() {
		Integer pagina = null;
		Integer quantidade = null;
		ConsultaLogradouroFiltroDto filtro = new ConsultaLogradouroFiltroDto(pagina, quantidade, "", null, "");
		assertThrows(IllegalArgumentException.class, () -> service.listarLogradouro(filtro));
	}

	@Test
	@DisplayName("Deve retornar erro quando quantidade for iqual a zero")
	void deveRetornarErroQuandoQuantidadeForIqualAZero() {
		Integer pagina = 0;
		Integer quantidade = 0;
		ConsultaLogradouroFiltroDto filtro = new ConsultaLogradouroFiltroDto(pagina, quantidade, "RJ", "Rio de Janeiro",
					"Recreio");
		assertThrows(IllegalArgumentException.class, () -> service.listarLogradouro(filtro));
	}

	@Test
	@DisplayName("Deve retornar erro quando quantidade for menor que zero")
	void deveRetornarErroQuandoQuantidadeForMenorQueZero() {
		Integer pagina = 0;
		Integer quantidade = -1;
		ConsultaLogradouroFiltroDto filtro = new ConsultaLogradouroFiltroDto(pagina, quantidade, "RJ", "Rio de Janeiro",
					"Recreio");
		;
		assertThrows(IllegalArgumentException.class, () -> service.listarLogradouro(filtro));
	}

	@Test
	@DisplayName("Deve retornar erro quando quantidade for nula")
	void deveRetornarErroQuandoQuantidadeForNula() {
		Integer pagina = 0;
		Integer quantidade = null;
		ConsultaLogradouroFiltroDto filtro = new ConsultaLogradouroFiltroDto(pagina, quantidade, "RJ", "Rio de Janeiro",
					"Recreio");
		assertThrows(NullPointerException.class, () -> service.listarLogradouro(filtro));
	}

	@Test
	@DisplayName("Deve retornar erro quando pagina for menor que zero")
	void deveRetornarErroQuandoPaginaForMenorQueZero() {
		Integer pagina = -1;
		Integer quantidade = 1;
		ConsultaLogradouroFiltroDto filtro = new ConsultaLogradouroFiltroDto(pagina, quantidade, "RJ", "Rio de Janeiro",
					"Recreio");
		assertThrows(IllegalArgumentException.class, () -> service.listarLogradouro(filtro));
	}

	@Test
	@DisplayName("Deve retornar erro quando pagina for nula")
	void deveRetornarErroQuandoPaginaForNula() {
		Integer pagina = null;
		Integer quantidade = 1;
		ConsultaLogradouroFiltroDto filtro = new ConsultaLogradouroFiltroDto(pagina, quantidade, "RJ", "Rio de Janeiro",
					"Recreio");
		assertThrows(NullPointerException.class, () -> service.listarLogradouro(filtro));
	}

	@Test
	@DisplayName("Deve retornar erro quando não contiver conteudo")
	void deveRetornarErroQuandoNaoContiverConteudo() {
		Integer pagina = 1;
		Integer quantidade = 1;
		Mockito
					.when(repository.consultaLogradouro(Mockito.any(ConsultaLogradouroFiltroDto.class)))
					.thenReturn(Optional.empty());
		ConsultaLogradouroFiltroDto filtro = new ConsultaLogradouroFiltroDto(pagina, quantidade, "RJ", "Rio de Janeiro",
					"Recreio");
		assertThrows(NoContentException.class, () -> service.listarLogradouro(filtro));
	}

	@Test
	@DisplayName("Nao deve retornar erro quando retornar conteudo")
	void naoDeveRetornarErroQuandoNaoContiverConteudo() {
		List<LogradouroVO> list = new ArrayList<LogradouroVO>();

		Integer pagina = 1;
		Integer quantidade = 1;
		Page<LogradouroVO> resultadoLogradouroPage = new PageImpl<LogradouroVO>(list,
					PageRequest.of(pagina, quantidade), list.size());

		Mockito
					.when(repository.consultaLogradouro(Mockito.any(ConsultaLogradouroFiltroDto.class)))
					.thenReturn(Optional.of(resultadoLogradouroPage));

		ConsultaLogradouroFiltroDto filtro = new ConsultaLogradouroFiltroDto(pagina, quantidade, "RJ", "Rio de Janeiro",
					"Recreio");
		assertDoesNotThrow(() -> service.listarLogradouro(filtro));
	}
}
