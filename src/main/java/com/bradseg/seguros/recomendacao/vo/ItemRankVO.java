package com.bradseg.seguros.recomendacao.vo;

import java.io.Serializable;

public class ItemRankVO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String getNm_ramo() {
		return nm_ramo;
	}

	public void setNm_ramo(String nm_ramo) {
		this.nm_ramo = nm_ramo;
	}
	Integer id_produto,  id_plano, deduzido;
	String descProduto, descPlano;
	String nm_ramo;
	
	public String getDescProduto() {
		return descProduto;
	}

	public void setDescProduto(String descProduto) {
		this.descProduto = descProduto;
	}

	public String getDescPlano() {
		return descPlano;
	}

	public void setDescPlano(String descPlano) {
		this.descPlano = descPlano;
	}
	Double rating;
	
	
	
	public ItemRankVO( Integer id_produto,Integer id_plano, String descProduto,String descPlano, String nm_ramo, Double rating,Integer deduzido) {
		setId_plano(id_plano);
		setId_produto(id_produto);
		setRating(rating);
		setDescPlano(descPlano);
		setDescProduto(descProduto);
		setNm_ramo(nm_ramo);
		setDeduzido(deduzido);
		
		
	}
	
	public Integer getId_plano() {
		return id_plano;
	}
	public void setId_plano(Integer id_plano) {
		this.id_plano = id_plano;
	}
	public Integer getId_produto() {
		return id_produto;
	}
	public void setId_produto(Integer id_produto) {
		this.id_produto = id_produto;
	}
	public Double getRating() {
		return rating;
	}
	public void setRating(Double ponto) {
		this.rating = ponto;
	}

	public Integer getDeduzido() {
		return deduzido;
	}

	public void setDeduzido(Integer deduzido) {
		this.deduzido = deduzido;
	}
	
}
