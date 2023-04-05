package br.com.phdigitalcode.erp_endereco.domain.service;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import br.com.phdigitalcode.erp_endereco.domain.handler.SheetHandler;
import br.com.phdigitalcode.erp_endereco.infra.entity.Bairro;
import br.com.phdigitalcode.erp_endereco.infra.entity.Logradouro;
import br.com.phdigitalcode.erp_endereco.infra.entity.Municipio;
import br.com.phdigitalcode.erp_endereco.infra.repository.BairroRepository;
import br.com.phdigitalcode.erp_endereco.infra.repository.LogradouroRepository;
import br.com.phdigitalcode.erp_endereco.infra.repository.MunicipioRepository;

@Service
public class FacetCarga {
	@Autowired
	ResourceLoader resource;
	@Autowired
	private BairroRepository bairroRepository;
	@Autowired
	private MunicipioRepository municipioRepository;
	@Autowired
	private LogradouroRepository logradouroRepository;

	public void cargaBairro() throws EncryptedDocumentException, IOException {
		Set<Bairro> bairrosCadastrar = new HashSet();
		try (Workbook planilha = WorkbookFactory
					.create(new File("src/main/resources/static/bairros_estados_municipios_part3.xlsx"))) {

			Sheet pagina = planilha.getSheetAt(0);
			int ultimaLinha = pagina.getLastRowNum();
			for (int i = 0; i < ultimaLinha; i++) {
				Row row = pagina.getRow(i);
				if (row.getCell(1) == null || row.getCell(1).getCellType().compareTo(CellType.BLANK) == 0) {
					continue;
				}
				if (row.getCell(2) == null || row.getCell(2).getCellType().compareTo(CellType.BLANK) == 0) {
					continue;
				}
				if (row.getCell(3) == null || row.getCell(3).getCellType().compareTo(CellType.BLANK) == 0) {
					continue;
				}
				String descricaoMunicipio = row.getCell(1).getStringCellValue();
				Integer descricaoEstado = (int) row.getCell(2).getNumericCellValue();
				String descricaoBairro = row.getCell(3).getStringCellValue();
				List<Municipio> municipios = municipioRepository.findByDescricao(descricaoMunicipio);
				Optional<Municipio> municipioOptional = municipios.parallelStream()
							.filter(m -> m.getEstado().getId().equals(descricaoEstado)).findFirst();
				municipioOptional.ifPresent(m -> {
					Bairro bairro = new Bairro(null, m, descricaoBairro);
					bairrosCadastrar.add(bairro);
				});
			}
		}

		bairroRepository.saveAllAndFlush(bairrosCadastrar);
	}

	public void cargaLogradouro(String nomeFIle) throws Exception {
		SheetHandler sheetHandler = new SheetHandler();
		sheetHandler.processSheets("src/main/resources/static/" + nomeFIle);
		System.out.println("Iniciando carga");
		List<br.com.phdigitalcode.erp_endereco.domain.model.vo.planilha.Row> rows = sheetHandler.getRows();
//		System.out.println("Convertendo Objeto");
		rows.parallelStream().forEach(row -> {

			String cep = row.getCells().get(0).getValue();
//			System.out.println("Cep : " + cep);
			if (!logradouroRepository.existsByCep(cep)) {
				String descricaoMunicipio = row.getCells().get(1).getValue();
				String descricaoEstado = row.getCells().get(2).getValue();
				String descricaoBairro = row.getCells().get(3).getValue();
				String descricaoLogradouro = row.getCells().get(4).getValue();
				String descricaoNomeEdificio = row.getCells().get(5).getValue();
				List<Bairro> bairros = bairroRepository.findByDescricao(descricaoBairro);
				registraBairro(descricaoMunicipio, descricaoEstado, descricaoBairro, bairros);
				Optional<Bairro> bairroOptional = bairros.parallelStream()
							.filter(b -> b.getMunicipio().getDescricao().equals(descricaoMunicipio))
							.filter(b -> b.getMunicipio().getEstado().getDescricao().equals(descricaoEstado))
							.findFirst();
				bairroOptional.ifPresent(b -> {
					Logradouro logradouro = new Logradouro(null, b, descricaoLogradouro.trim(), cep,
								descricaoNomeEdificio.trim());
					try {
						logradouroRepository.saveAndFlush(logradouro);
						System.out.println("Gravado >>> CEP : " + cep);
					} catch (Exception e) {
						System.err.println("Error : CEP : " + cep + ">>> "+e.getMessage());
					}
//					logradouroCadastrar.add(logradouro);
				});
//				if (logradouroCadastrar.size()>10000) {
//					try {
//						logradouroListCadastrar.add(logradouroCadastrar);
////						logradouroRepository.saveAllAndFlush(logradouroCadastrar);
//					} catch (Exception e) {
//						e.printStackTrace();
//					} finally {
//						logradouroCadastrar.clear();
//					}
//				}
			}
		});
		
		System.out.println("Terminando carga");
//		int size = logradouroCadastrar.size();
//		System.out.println(size);
//		logradouroRepository.saveAllAndFlush(logradouroCadastrar);
	}

	private void registraBairro(String descricaoMunicipio, String descricaoEstado, String descricaoBairro,
				List<Bairro> bairros) {
		try {
			if (bairros.isEmpty()) {
				municipioRepository.findByDescricao(descricaoMunicipio)
							.parallelStream()
							.filter(m -> m.getEstado().getDescricao().equals(descricaoEstado))
							.findFirst()
							.ifPresent(muni -> {
								bairroRepository.saveAndFlush(new Bairro(null, muni, descricaoBairro));
							});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getNomeFile() {
		ClassPathResource classPathResource = new ClassPathResource(
					"classpath:/resources/static/bairros_estados_municipios.xlsx");

		return classPathResource.getFilename();
	}
}
