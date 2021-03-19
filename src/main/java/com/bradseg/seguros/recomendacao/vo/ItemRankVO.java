package com.bradseg.seguros.recomendacao.vo;

public class ItemRankVO {
	
	Integer id_produto,  id_plano;
	String descProduto, descPlano;
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
	
	
	
	public ItemRankVO( Integer id_produto,Integer id_plano, Double rating) {
		setId_plano(id_plano);
		setId_produto(id_produto);
		setRating(rating);
		
		
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
	
}
