package br.com.phdigitalcode.erp_endereco;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
@EnableFeignClients
@SpringBootApplication
public class ErpEnderecoApplication {
	public static void main(String[] args) {
		SpringApplication.run(ErpEnderecoApplication.class, args);
	}
	

}
