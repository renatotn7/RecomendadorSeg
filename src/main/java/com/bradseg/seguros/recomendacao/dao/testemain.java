package com.bradseg.seguros.recomendacao.dao;

import java.util.List;

import com.bradseg.seguros.recomendacao.vo.RatingVO;

public class testemain {

	public static void main(String[] args) {
		// TODO Stub de método gerado automaticamente
		RecomendacaoDAOImpl rd = new RecomendacaoDAOImpl();
	List<RatingVO> ratings= 	rd.recuperaRatingsUIR();
	for(RatingVO r: ratings) {
		System.out.println(r.getId_perfil());
	}
		
	}

}
