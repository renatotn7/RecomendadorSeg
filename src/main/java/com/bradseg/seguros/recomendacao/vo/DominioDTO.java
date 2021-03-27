package com.bradseg.seguros.recomendacao.vo;

import java.io.Serializable;

public class DominioDTO implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
private String grupoDominio;
private int idDominio ;
private String valor;
public DominioDTO(String grupoDominio, int idDominio,String valor) {
	this.grupoDominio = grupoDominio;
	this.idDominio = idDominio;
	this.valor = valor;
}
public String getGrupoDominio() {
	return grupoDominio;
}
public void setGrupoDominio(String grupoDominio) {
	this.grupoDominio = grupoDominio;
}
public int getIdDominio() {
	return idDominio;
}
public void setIdDominio(int idDominio) {
	this.idDominio = idDominio;
}
public String getValor() {
	return valor;
}
public void setValor(String valor) {
	this.valor = valor;
}
}
