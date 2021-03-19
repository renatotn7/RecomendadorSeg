package com.bradseg.seguros.recomendacao.vo;

public class RatingVO {
	
	Integer id_perfil,  id_produto;
	Double rating;
	
	Long milis;
	public Long getMilis() {
		return milis;
	}
	public void setMilis(Long milis) {
		this.milis = milis;
	}
	public RatingVO(Integer id_perfil, Integer id_produto,Double ponto,Long milis) {
		setId_perfil(id_perfil);
		setId_produto(id_produto);
		setRating(ponto);
		setMilis(milis);
		
	}
	public Integer getId_perfil() {
		return id_perfil;
	}
	public void setId_perfil(Integer id_perfil) {
		this.id_perfil = id_perfil;
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
