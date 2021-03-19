package com.bradseg.seguros.recomendacao.service;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bradseg.seguros.recomendacao.dao.RecomendacaoDAOImpl;
import com.bradseg.seguros.recomendacao.facade.RecomendacaoFacadeImpl;
import com.bradseg.seguros.recomendacao.vo.DominioDTO;
import com.bradseg.seguros.recomendacao.vo.ItemRankVO;

import br.com.bradseg.sise.apolicevida.utils.serializacao.Serializacao;

@RestController
public class RecommenderService {
	
	public static RecomendacaoFacadeImpl recomendacaoFacade = new RecomendacaoFacadeImpl();
	 @RequestMapping(value="/dominio/estadocivil",method = RequestMethod.GET)
	 public List<DominioDTO> dominioestadocivil() {
		 return recomendacaoFacade.getDominioEstadoCivil();
	 }
	 @RequestMapping(value="/dominio/sexo",method = RequestMethod.GET)
	 public  List<DominioDTO>  dominiosexo() {
		 return recomendacaoFacade.getDominioSexo();
	 }
	 
	 @RequestMapping(value="/dominio/consultasdisponiveis",method = RequestMethod.GET)
	 public String disponibilidades() {
		 //fazer um serviço para saber configurações de perfis sao possiveis mostrar ao usuario
		 return "ola";
	 }
	 
	 @RequestMapping(value="/modelo/modelosDisponiveis",method = RequestMethod.GET)
	 public String modelosdisponiveis() {
		 //fazer um serviço para saber configurações de perfis sao possiveis mostrar ao usuario
		 return "ola";
	 }
	 
	 @RequestMapping(value="/traducao/pessoa_perfil",method = RequestMethod.GET)
	 public String traducaopessoaperfil() {
		 //fazer um serviço para saber configurações de perfis sao possiveis mostrar ao usuario
		 return "ola";
	 }
	 @RequestMapping(value="/traducao/planoproduto_id",method = RequestMethod.GET)
	 public String traducaoplanoproduto_id() {
		 //fazer um serviço para saber configurações de perfis sao possiveis mostrar ao usuario
		 return "ola";
	 }
	 @RequestMapping(value="/traducao/id_planoproduto",method = RequestMethod.GET)
	 public String traducaoid_planoproduto() {
		 //fazer um serviço para saber configurações de perfis sao possiveis mostrar ao usuario
		 return "ola";
	 }
	 @RequestMapping(value="/traducao/planoproduto_cober",method = RequestMethod.GET)
	 public String traducaoplanoproduto_cober() {
		 //fazer um serviço para saber configurações de perfis sao possiveis mostrar ao usuario
		 return "ola";
	 }
	 
	 @RequestMapping(value="/traducao/cober_planoproduto",method = RequestMethod.GET)
	 public String traducaocober_planoproduto() {
		 //fazer um serviço para saber configurações de perfis sao possiveis mostrar ao usuario
		 return "ola";
	 }
	 @RequestMapping(value="/ranking/fromuserdata",method = RequestMethod.GET)
	 public List<ItemRankVO> userdata(@RequestParam int id_modelo,@RequestParam int sexosegurado,@RequestParam int estadocivil, @RequestParam int  classe_peso,@RequestParam int faixa_idade) {
		 return  recomendacaoFacade.recuperaDadosFromModelo(id_modelo,sexosegurado,estadocivil,classe_peso,faixa_idade);
		 //fazer um serviço para saber configurações de perfis sao possiveis mostrar ao usuario
		
		 
	 }
	 
	 @RequestMapping(value="/ranking/fromuserdataArq",method = RequestMethod.GET)
	 public List<ItemRankVO> userdata() {
		 	return (List) Serializacao.lerArquivoBinario("RatingsList.obj");
	//	 return  recomendacaoFacade.recuperaDadosFromModelo(1,0,0,0,0);
		 //fazer um serviço para saber configurações de perfis sao possiveis mostrar ao usuario
		
		 
	 }
}
