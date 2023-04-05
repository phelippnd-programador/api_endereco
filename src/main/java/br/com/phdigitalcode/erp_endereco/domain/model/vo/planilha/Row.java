package br.com.phdigitalcode.erp_endereco.domain.model.vo.planilha;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Row implements Serializable{
	private static final long serialVersionUID = 1L;
	private List<Cell> cells;
	public void add(Cell cell) {
		if(cells == null) {
			cells = new ArrayList<Cell>();
		}
		cells.add(cell);
	}

}
