package com.bradseg.seguros.recomendacao.facade;

import java.util.List;

import com.bradseg.seguros.recomendacao.dao.RecomendacaoDAOImpl;
import com.bradseg.seguros.recomendacao.vo.DominioDTO;
import com.bradseg.seguros.recomendacao.vo.ItemRankVO;

public class RecomendacaoFacadeImpl {

	public static RecomendacaoDAOImpl recomendacaoDAO = new RecomendacaoDAOImpl();
	public List<DominioDTO> getDominioEstadoCivil() {
		
		return recomendacaoDAO.recuperaEstadoCivil();
	}
	public List<DominioDTO> getDominioSexo() {
		return recomendacaoDAO.recuperaSexo();
	}
	public List<ItemRankVO>  recuperaDadosFromModelo(int id_modelo,int sexosegurado,int estadocivil, int  classe_peso,int faixa_idade) {
		return recomendacaoDAO.recuperaDadosFromModelo(id_modelo,sexosegurado,estadocivil,classe_peso,faixa_idade);
	}
		

	
}
