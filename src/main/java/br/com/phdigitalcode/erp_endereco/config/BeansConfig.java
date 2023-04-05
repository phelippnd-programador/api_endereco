package br.com.phdigitalcode.erp_endereco.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import br.com.phdigitalcode.erp_endereco.domain.repository.OUTEnderecoRepository;
import br.com.phdigitalcode.erp_endereco.domain.service.EnderecoService;
import br.com.phdigitalcode.erp_endereco.domain.service.EnderecoServiceImpl;
import br.com.phdigitalcode.erp_endereco.infra.impl.endereco.EnderecoRepositoryBDImpl;
import br.com.phdigitalcode.erp_endereco.infra.impl.endereco.EnderecoRepositoryProxyImpl;

@Configuration
public class BeansConfig {
	@Bean(name = "enderecoRepositoryProxy")
	public OUTEnderecoRepository enderecoRepositoryProxy() {
		return new EnderecoRepositoryProxyImpl();
	}
	@Bean
	@Primary
	public OUTEnderecoRepository enderecoRepositoryBD() {
		return new EnderecoRepositoryBDImpl();
	}
	@Bean
	@Primary
	public EnderecoService enderecoServiceBD(OUTEnderecoRepository repository) {
		return new EnderecoServiceImpl(repository);
	}
	@Bean("enderecoServiceProxy")
	public EnderecoService enderecoServiceProxy(@Qualifier(value = "enderecoRepositoryProxy") OUTEnderecoRepository repository) {
		return new EnderecoServiceImpl(repository);
	}
}
