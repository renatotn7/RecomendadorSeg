package com.bradseg.seguros.recomendacao.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bradseg.seguros.recomendacao.dao.RecomendacaoDAOImpl;
import com.bradseg.seguros.recomendacao.facade.RecomendacaoFacadeImpl;
import com.bradseg.seguros.recomendacao.vo.DominioDTO;
import com.bradseg.seguros.recomendacao.vo.ItemRankVO;


import br.com.bradseg.sise.apolicevida.utils.serializacao.Serializacao;

@RestController

public class RecommenderService {
	
	

	public static RecomendacaoFacadeImpl recomendacaoFacade = new RecomendacaoFacadeImpl();
	 @RequestMapping(value="/dominio/estadocivil",method = RequestMethod.GET)
	 public List<DominioDTO> dominioestadocivil(@RequestParam(defaultValue = "-1") Integer id) {
		 List<DominioDTO> dominios= ( List ) Serializacao.lerArquivoBinario ( "GeneroList.obj" );
		 // SexoList.obj
		 if(id ==-1) {
			 return dominios;	
			 }else {
				 for(DominioDTO dominio : dominios) {
					 if(dominio.getIdDominio()==id) {
						 List<DominioDTO> listec = new ArrayList<DominioDTO>();
						 listec.add(dominio);
						 return listec;
					 }
				 }
			 }
			 
			 return null;
		 }
	 @RequestMapping(value="/dominio/genero",method = RequestMethod.GET)
	 public  List<DominioDTO>  dominioGenero(@RequestParam(defaultValue = "-1") Integer id) {
		 List<DominioDTO> dominios= ( List ) Serializacao.lerArquivoBinario ( "SexoList.obj" );
		 //List<DominioDTO> dominios= recomendacaoFacade.getDominioSexo()
		 if(id ==-1) {
		 return dominios;
		 }else {
			 for(DominioDTO dominio :dominios) {
				 if(dominio.getIdDominio()==id) {
					 List<DominioDTO> listsexo = new ArrayList<DominioDTO>();
					 listsexo.add(dominio);
					 return listsexo;
				 }
			 }
		 }
		 
		 return null;
	 }
	 
	 @RequestMapping(value="/dominio/consultasdisponiveis",method = RequestMethod.GET)
	 public String disponibilidades() {
		 //fazer um servi�o para saber configura��es de perfis sao possiveis mostrar ao usuario
		 return "ola";
	 }
	 
	 @RequestMapping(value="/modelo/modelosDisponiveis",method = RequestMethod.GET)
	 public String modelosdisponiveis() {
		 //fazer um servi�o para saber configura��es de perfis sao possiveis mostrar ao usuario
		 return "ola";
	 }
	 
	 @RequestMapping(value="/traducao/pessoa_perfil",method = RequestMethod.GET)
	 public String traducaopessoaperfil() {
		 //fazer um servi�o para saber configura��es de perfis sao possiveis mostrar ao usuario
		 return "ola";
	 }
	 @RequestMapping(value="/traducao/planoproduto_id",method = RequestMethod.GET)
	 public String traducaoplanoproduto_id() {
		 //fazer um servi�o para saber configura��es de perfis sao possiveis mostrar ao usuario
		 return "ola";
	 }
	 @RequestMapping(value="/traducao/id_planoproduto",method = RequestMethod.GET)
	 public String traducaoid_planoproduto() {
		 //fazer um servi�o para saber configura��es de perfis sao possiveis mostrar ao usuario
		 return "ola";
	 }
	 @RequestMapping(value="/traducao/planoproduto_cober",method = RequestMethod.GET)
	 public String traducaoplanoproduto_cober() {
		 //fazer um servi�o para saber configura��es de perfis sao possiveis mostrar ao usuario
		 return "ola";
	 }
	 
	 @RequestMapping(value="/traducao/cober_planoproduto",method = RequestMethod.GET)
	 public String traducaocober_planoproduto() {
		 //fazer um servi�o para saber configura��es de perfis sao possiveis mostrar ao usuario
		 return "ola";
	 }
	 @RequestMapping(value="/ranking/fromuserdata",method = RequestMethod.GET)
	 public List<ItemRankVO> userdata(@RequestParam int id_modelo,@RequestParam int sexosegurado,@RequestParam int estadocivil, @RequestParam int  classe_peso,@RequestParam int faixa_idade) {
		 return  recomendacaoFacade.recuperaDadosFromModelo(id_modelo,sexosegurado,estadocivil,classe_peso,faixa_idade);
		 //fazer um servi�o para saber configura��es de perfis sao possiveis mostrar ao usuario
		
		 
	 }
	 
	 @RequestMapping(value="/ranking/fromuserdataArq",method = RequestMethod.GET)
	 public List<ItemRankVO> userdata() {
		 	return (List) Serializacao.lerArquivoBinario("RatingsList.obj");
	//	 return  recomendacaoFacade.recuperaDadosFromModelo(1,0,0,0,0);
		 //fazer um servi�o para saber configura��es de perfis sao possiveis mostrar ao usuario
		
		 
	 }
	 @RequestMapping(value="/ranking/fromuserdataArq2",method = RequestMethod.GET)
	 public List<ItemRankVO> userdata(@RequestParam Integer id_modelo,@RequestParam Integer genero,@RequestParam Integer estadocivil, @RequestParam Double  peso,@RequestParam Integer idade) throws Exception {
		 if(genero==null || id_modelo==null || estadocivil==null|| peso==null|| estadocivil==null) {
			 throw new Exception();
		 }
		 
		//Serializacao.gravarArquivoBinario( recomendacaoFacade.recuperaDadosFromModelo(id_modelo,genero,estadocivil,2,4), "RatingsList.obj");
		 
		return (List) Serializacao.lerArquivoBinario("RatingsList.obj");
		 //tem que ser calculado faixa peso e faiza idade
	//return  recomendacaoFacade.recuperaDadosFromModelo(id_modelo,genero,estadocivil,2,4);
		 //fazer um servi�o para saber configura��es de perfis sao possiveis mostrar ao usuario
		
		 
	 }
	 @RequestMapping(value = "/ranking/fromuserdataArq", method = RequestMethod.GET, produces = { MimeTypeUtils.TEXT_PLAIN_VALUE})
		public ResponseEntity<ItemRankVO> jsn() {
			try {
				ResponseEntity<ItemRankVO> responseEntity = new ResponseEntity<ItemRankVO>(HttpStatus.OK);
				return responseEntity;
			} catch (Exception e) {
				return new ResponseEntity<ItemRankVO>(HttpStatus.BAD_REQUEST);
			}
		}
	
}
