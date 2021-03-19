package com.bradseg.seguros.recomendacao.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




import com.bradseg.seguros.recomendacao.vo.DominioDTO;
import com.bradseg.seguros.recomendacao.vo.ItemRankVO;
import com.bradseg.seguros.recomendacao.vo.RatingVO;

import br.com.bradseg.sise.apolicevida.utils.serializacao.Serializacao;
import br.com.bradseg.sise.apolicevida.utils.texto.StringsUtils;



public class RecomendacaoDAOImpl {
	
	private  Connection connection;
	private PreparedStatement   stmt;
	public Long iniciaModeloRank() throws SQLException {
		initConnection();
		Long retorno=-1L;
		  try(CallableStatement cstmt = connection.prepareCall("{call dbo.prc_resgistra_mdl_geral(?)}");) {  
		       
		        cstmt.registerOutParameter(1, java.sql.Types.BIGINT );  
		        cstmt.execute();  
		        System.out.println("*****MANAGER ID: " + cstmt.getInt(1));
		        retorno =  cstmt.getLong(1);
		    }
		return retorno;  
	}
	public void closeModeloRank(Long idmodelo) throws SQLException {
		initConnection();
		
		String ssql = "{call dbo.[upInserePrdSimpFinalizaMdlItem](@idModelo)}";
		ssql	=StringsUtils.replaceFor(ssql, idmodelo, "@idModelo");
		stmt = connection.prepareStatement(ssql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		stmt.execute();
		connection.commit();
		  
	}
	public void insertItemRank(Long idmodelo,Integer user, Integer item, Double rating, Double inferido ) throws SQLException {

		
			initConnection();
		   
			
			String result = "";

			String ssql = "{call dbo.upInserePrdSimpMdlItem (@idModelo,@user,@item,@rating,@inferido)}";

		ssql	=StringsUtils.replaceFor(ssql, idmodelo, "@idModelo");
		ssql	=StringsUtils.replaceFor(ssql, user, "@user");
		ssql	=StringsUtils.replaceFor(ssql, item, "@item");
		ssql	=StringsUtils.replaceFor(ssql, rating, "@rating");
		ssql	=StringsUtils.replaceFor(ssql, inferido, "@inferido");
			//ResultSet rs = statement.executeQuery(sqlconsultas);
			stmt = connection.prepareStatement(ssql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			stmt.execute();
			connection.commit();
		
	}
	public List<DominioDTO> recuperaEstadoCivil(){
		 List<DominioDTO> dominios = new ArrayList<DominioDTO>(2);
		 initConnection();
		 StringBuilder sql =  new StringBuilder();
		 
			try {
				sql.append("select *   from  TBG_DOMINIO_VALOR_RESOURCE  WHERE CD_DOMINIO='ESTADO_CIVIL' ");
				//sql.append(" grp.cGrpDomnoUnfcaServc,");
				stmt = connection.prepareStatement(sql.toString());
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {//grupoDominio
					dominios.add(new DominioDTO(rs.getString("CD_DOMINIO"),rs.getInt("CD_VALOR"),rs.getString("NM_TAG")));
				}
			} catch (SQLException e) {
				// TODO Bloco catch gerado automaticamente
				e.printStackTrace();
			}finally {
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Bloco catch gerado automaticamente
					e.printStackTrace();
				}
			}
		 return dominios;
	}
	public List<DominioDTO> recuperaSexo(){
		 List<DominioDTO> dominios = new ArrayList<DominioDTO>(2);
		 initConnection();
		 StringBuilder sql =  new StringBuilder();
		 
			try {
				sql.append("select *   from  TBG_DOMINIO_VALOR_RESOURCE  WHERE CD_DOMINIO='SEXO' ");
				//sql.append(" grp.cGrpDomnoUnfcaServc,");
				stmt = connection.prepareStatement(sql.toString());
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {//grupoDominio
					dominios.add(new DominioDTO(rs.getString("CD_DOMINIO"),rs.getInt("CD_VALOR"),rs.getString("NM_TAG")));
				}
			} catch (SQLException e) {
				// TODO Bloco catch gerado automaticamente
				e.printStackTrace();
			}finally {
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Bloco catch gerado automaticamente
					e.printStackTrace();
				}
			}
		 return dominios;
	}
	public List<RatingVO>  recuperaRatingsUIR() {
		 List<RatingVO> ratings = new  ArrayList<RatingVO> (200);
		 initConnection();
		 StringBuilder sql =  new StringBuilder();
		 
			try {
				sql.append("select id_perfil, id_produto, avg(convert(float,pontos)) pontos  from  v_output_subprefeitura_cod_subgrupo  group by id_perfil, id_produto");
				//sql.append(" grp.cGrpDomnoUnfcaServc,");
				stmt = connection.prepareStatement(sql.toString());
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					ratings.add(new RatingVO(rs.getInt("id_perfil"),rs.getInt("id_produto"),rs.getDouble("pontos"),null));
				}
			} catch (SQLException e) {
				// TODO Bloco catch gerado automaticamente
				e.printStackTrace();
			}finally {
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Bloco catch gerado automaticamente
					e.printStackTrace();
				}
			}
			return ratings;
	}
	
	
	public List<RatingVO>  recuperaRatingsUIRT() {
		 List<RatingVO> ratings = new  ArrayList<RatingVO> (200);
		 initConnection();
		 StringBuilder sql =  new StringBuilder();
			try {
				sql.append("select id_perfil, id_produto, convert(float,pontos) pontos, DATEDIFF(second, '1970-01-01 00:00:00.000', dataEmissao)/100 milis  from  v_output_subprefeitura_cod_subgrupo  ");
				//sql.append(" grp.cGrpDomnoUnfcaServc,");
				stmt = connection.prepareStatement(sql.toString());
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					
					ratings.add(new RatingVO(rs.getInt("id_perfil"),rs.getInt("id_produto"),rs.getDouble("pontos"),rs.getLong("milis")));
				}
			} catch (SQLException e) {
				// TODO Bloco catch gerado automaticamente
				e.printStackTrace();
			}finally {
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Bloco catch gerado automaticamente
					e.printStackTrace();
				}
			}
			return ratings;
	}
	

	
	public List<ItemRankVO>  recuperaDadosFromModelo(int id_modelo,int sexosegurado,int estadocivil, int  classe_peso,int faixa_idade) {
		 List<ItemRankVO> ratings = new  ArrayList<ItemRankVO> (2000);
		 initConnection();
		 StringBuilder sql =  new StringBuilder();
			try {
				sql.append("  select  a.cd_produto,a.cd_plano,b.NM_PRODUTO,c.nm_plano, e.nm_RM_SUSEP,  round (isnull(rating,ratinginf) ,3) rating from mdl_modelo_item a,PRD_PRODUTO b, prd_plano c,tbg_ramo d,tbg_ramo_susep e where id_modelo = 1 and a.cd_produto = b.cd_produto and c.cd_produto = b.cd_produto and c.cd_plano = a.cd_plano and b.CD_PRODUTO=c.CD_PRODUTO and b.CD_RM = d.CD_RM and d.CD_RM_SUSEP = e.CD_RM_SUSEP\r\n" + 
						" and id_modelo = 1 and classe_peso = 6 and faixa_idade = 4 and sexosegurado = 1 and estadocivil=1\r\n" + 
						"  order by round (isnull(rating,ratinginf) ,3)  desc");
				//sql.append(" grp.cGrpDomnoUnfcaServc,");
				stmt = connection.prepareStatement(sql.toString());
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					
					ratings.add(new ItemRankVO(rs.getInt("cd_produto"),rs.getInt("cd_plano"),rs.getString("nm_produto"),rs.getString("nm_plano"),rs.getString("nm_rm_susep"),rs.getDouble("rating")));
				}
			} catch (SQLException e) {
				// TODO Bloco catch gerado automaticamente
				e.printStackTrace();
			}finally {
				/*try {
				//	connection.close();
				} catch (SQLException e) {
					// TODO Bloco catch gerado automaticamente
					e.printStackTrace();
				}*/
			}
		
			return ratings;
	}
	public void initConnection()  {
		
		
									            
			connection = DataSourceUtils.getConnection();
		    try {
				connection.setAutoCommit(false);
			} catch (SQLException e) {
				// TODO Bloco catch gerado automaticamente
				e.printStackTrace();
			}
		
		
	}
	 
}
