package com.bradseg.seguros.recomendacao.rating;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.bradseg.seguros.model.DaoDataModel;
import com.bradseg.seguros.recomendacao.dao.RecomendacaoDAOImpl;
import com.bradseg.seguros.recomendacao.vo.RatingVO;
import com.bradseg.seguros.recomendacao.vo.RecommendItemSortable;

import br.com.bradseg.sise.apolicevida.utils.file.File2Lines;
import br.com.bradseg.sise.apolicevida.utils.file.LineEnt;
import br.com.bradseg.sise.apolicevida.utils.serializacao.Serializacao;
import br.com.bradseg.sise.apolicevida.utils.texto.StringsUtils;
import net.librec.common.LibrecException;
import net.librec.conf.Configuration;
import net.librec.data.model.TextDataModel;
import net.librec.eval.RecommenderEvaluator;
import net.librec.eval.ranking.AveragePrecisionEvaluator;
import net.librec.eval.rating.MAEEvaluator;
import net.librec.eval.rating.MPEEvaluator;
import net.librec.eval.rating.MSEEvaluator;
import net.librec.eval.rating.RMSEEvaluator;
import net.librec.filter.GenericRecommendedFilter;
import net.librec.recommender.FactorizationMachineRecommender;
import net.librec.recommender.MatrixFactorizationRecommender;
import net.librec.recommender.Recommender;
import net.librec.recommender.RecommenderContext;
import net.librec.recommender.baseline.RandomGuessRecommender;
import net.librec.recommender.cf.rating.BiasedMFRecommender;
import net.librec.recommender.cf.rating.MFALSRecommender;
import net.librec.recommender.cf.rating.SVDPlusPlusRecommender;
import net.librec.recommender.ext.AssociationRuleRecommender;
import net.librec.recommender.ext.PersonalityDiagnosisRecommender;
import net.librec.recommender.item.RecommendedItem;
import net.librec.similarity.BinaryCosineSimilarity;
import net.librec.similarity.CPCSimilarity;
import net.librec.similarity.CosineSimilarity;
import net.librec.similarity.DiceCoefficientSimilarity;
import net.librec.similarity.ExJaccardSimilarity;
import net.librec.similarity.JaccardSimilarity;
import net.librec.similarity.KRCCSimilarity;
import net.librec.similarity.MSDSimilarity;
import net.librec.similarity.MSESimilarity;
import net.librec.similarity.PCCSimilarity;
import net.librec.similarity.RecommenderSimilarity;


public final class MainBiasedmf {
//parece que cria avaliaçoes para todo mundo e da nota nao tem como prever, o resultado varia no tempo
	//mas posso filtrar por item

	
	RecomendacaoDAOImpl recomendacaoDAOImpl = new RecomendacaoDAOImpl();
	private double rmse;
	private double mae;
	private double mpe;
	private double mse;
	private HashMap<Integer,ArrayList<RecommendItemSortable> > hmap;
	private  ArrayList<RecommendItemSortable> itensMatched=new  ArrayList<RecommendItemSortable> ();
	private double maeErrosGlobal;
	StringBuffer sb  = new StringBuffer();
	private MainBiasedmf() throws NumberFormatException, SQLException 
	{
		for(int i = 0 ; i < 1;i++) {
		run();
		}
	//	System.out.println(sb.toString());
	} 	
	Long idmodelo = 0L;
	private void run() throws NumberFormatException, SQLException {
		idmodelo = this.recomendacaoDAOImpl.iniciaModeloRank();
		//this.recomendacaoDAOImpl.closeModeloRank(18L);
		//System.exit(1);
//		recs= Serializacao.lerArquivoBinario("model.mdl");
		//	Recommender recommender =(Recommender) recs.get(0);
			ArrayList<String> alocais = new ArrayList<String> ();
			ArrayList<String> errsuperam = new ArrayList<String> ();
			BiasedMFRecommender recommender = new BiasedMFRecommender () ;
		//	StringBuffer sb = new StringBuffer();
			double acumGlobal = 0;
			double acumquad = 0;
			int countGlobal = 0;
			//RecomendacaoDAO rd = new RecomendacaoDAO();
			//List<RatingVO> ratings= 	rd.recuperaRatingsUIR();
			
			 Map<String,Double > hmapCSV= new HashMap<String,Double > ();
	        // build data model
			 Configuration conf = new Configuration();
			 if(recs==null) {
			       
			      conf.set("dfs.data.dir", "D:\\Users\\renat\\Documentos\\projetoia\\desenvolvimentolibrec\\librec\\data");
			 //      conf.set("data.input.path", "output_subprefeitura_cod_subgrupo");//0.9966698653035351
			      //conf.set("data.input.path", "output_subprefeitura"); //2.15 2.27  2.14 2.23 2.223(nao ranking) // 
			    //  conf.set("data.input.path", "output_idh_distrito"); //1.50 //
			   //   conf.set("data.input.path", "output_basico"); //1.17 //1.04//
			     // conf.set("data.input.path", "output_basico_buy_simp"); 
			      conf.set("data.input.path", "output_basico_simp"); 
			     // conf.set("data.input.path", "output_basico_cober_atu"); 
			    //  conf.set("data.input.path", "output_basico_cober_atu_simp"); 
			      //  conf.set("data.input.path", "output_idh_distrito_cod_subgrupo"); //--1.1080663992476771
			       // conf.set("data.input.path", "output_nivel_idh_distrito_cod_subgrupo"); //--1.1133218945426286  0.5439646088630846
			      // conf.set("data.input.path", "v_output_nm_distritos_cod_subgrupo"); //--1.1095837612184118 --sem avg  1.1675865435912356//0.6583312938820717
			   //    conf.set("data.input.path", "v_output_nm_zona_cod_subgrupo"); //--1.0438038839664816 --4000
			      
			        conf.set("rec.recommender.similarity.key","user");
					conf.set("rec.recommender.class","biasedmf");
					conf.set("rec.iterator.learnrate","0.0001");// 0.0001 para year e 0.01 para buy
					conf.set("rec.iterator.learnrate.maximum","0.01");//0.01 :2.135546785756788 e  2.1180089829172384,2.1372254903644485,2.1024045966689258
					conf.set("rec.iterator.maximum","6000"); //1.02 , 1.15 com 6000 vai para 0.96 e 1.08 com 24000 vai para  0.97 e 1.001 128000 vai para 0.91 e 1.06
					conf.set("rec.user.regularization","1"); //0.4 1.9511897034611143
					conf.set("rec.item.regularization","0.2"); //0.4 1.929 0.6 1.936
					conf.set("rec.impItem.regularization","0.2"); //0.4 1.931 
					conf.set("rec.factor.number","20");
					conf.set("rec.learnrate.bolddriver","false"); ///true = 2.16 false = 1.94,1,933,1.91
					conf.set("rec.learnrate.decay","1.0");
					conf.set("rec.recommender.isranking","true");
					conf.set("rec.recommender.ranking.topn","2000");
			       //0.6194525145143205 4000
				//	0.5842856505964263 3000
					//0.5920097692964972 3000
					//0.563297701551835 3000
				
			    	
					   TextDataModel dataModel = new TextDataModel(conf);
			    	// TextDataModel dataModel = new TextDataModel(conf);
			        try {
						dataModel.buildDataModel();
					} catch (LibrecException e) {
				
						e.printStackTrace();
					}
			       
			        // build recommender context
			        RecommenderContext context = new RecommenderContext(conf, dataModel);
			        System.out.println("1");
			        // build similarity
			        conf.set("rec.recommender.similarity.key" ,"item");
			    
			    //    RecommenderSimilarity similarity = new CosineSimilarity(); //1.795069023942323
				    //   RecommenderSimilarity similarity = new BinaryCosineSimilarity();//1.7954179970293958
				     			      // RecommenderSimilarity similarity = new KRCCSimilarity();//1.7770325306433754, 1.7858563788730015
				      
			    //    RecommenderSimilarity similarity = new CPCSimilarity();1.7795086066307817,1.7863912372748816
			        
			        

			        
			        
			      //  RecommenderSimilarity similarity = new JaccardSimilarity();//1.7762358145196153,1.7739808409199456
			      
			    
			   		    // RecommenderSimilarity similarity = new ExJaccardSimilarity();//1.7839280964674442 1.7658479164016554,1.7633402113215593/1.0966333690959436
			   //  RecommenderSimilarity similarity = new DiceCoefficientSimilarity();//1.7802874110723028,1.7694740906246629,1.780893403962574/1.1041798196649628
			     // RecommenderSimilarity similarity = new MSDSimilarity();//1.793886073468866,1.7677637243440942,1.7683243496091847/1.1011899848290663

			        
					    RecommenderSimilarity similarity = new MSESimilarity();//1.7544751915794532,1.7739308146215522,1.744616529106069/1.0921148653459147   ,1.9423172126772013/1.1256550292821266
				    //   PCCSimilarity similarity = new PCCSimilarity();//1.8027210975630705,1.7489508307470643 ,1.7625355281326889/1.1014390117005726

			        
			        similarity.buildSimilarityMatrix(dataModel);
			        context.setSimilarity(similarity);
			        
			        System.out.println("2");
			        // build recommender
			    
			        recommender . setContext ( context ) ;
			        System.out.println("3");
			      
			        
			        // run recommender algorithm
			        try {
						recommender.recommend(context);
					} catch (LibrecException e) {
					
						e.printStackTrace();
					}
			       recommender.saveRec("DadosModelo");
			    
			        	//Serializacao.gravarArquivoBinario(   recommender.getDataModel(), "recommender");
			       //recommender.saveModel("model2.mdl");
			  //      recs.add(recommender);
			        
			    //    Serializacao.gravarArquivoBinario(recs, "model.mdl");
			 }
	        System.out.println("4");
	        // evaluate the recommended result
	       
	        try {
				File2Lines fl= new  File2Lines("D:\\Users\\renat\\Documentos\\projetoia\\desenvolvimentolibrec\\librec\\data\\output_basico_simp\\ratings_0.txt");
				List<LineEnt> linhas = fl.getLines();
			 
				for(int i =  0 ;i< linhas.size();i++) {
			//	for(int i =  0 ;i< ratings.size();i++) {	
				String linha = linhas.get(i).getConteudo();
				String[] registro = linha.split(" ");
				//	String perfilprod=ratings.get(i).getId_perfil()+"|"+ratings.get(i).getId_produto();
					String perfilprod = registro[0]+"|"+registro[1];
					//Double rating=ratings.get(i).getPonto();
					Double rating = Double.parseDouble(registro[2]);
					hmapCSV.put(perfilprod,rating);
				}
	        } catch (FileNotFoundException e1) {
				// TODO Bloco catch gerado automaticamente
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Bloco catch gerado automaticamente
				e1.printStackTrace();
			}
	        try {
	        if(!conf.getBoolean("rec.recommender.isranking")) { 
	       		RecommenderEvaluator evaluator1 = new RMSEEvaluator();
	       		rmse=recommender.evaluate(evaluator1);
				System.out.println(StringsUtils.buildStr("RMSE:" , String.valueOf(rmse)));
			
				
	           RecommenderEvaluator evaluator2 = new MAEEvaluator();
	            mae=recommender.evaluate(evaluator2);
	            System.out.println("MAE:" + mae);
	           
	            RecommenderEvaluator evaluator3 = new MPEEvaluator();
	            mpe=recommender.evaluate(evaluator3);
	            System.out.println("MPE:" + mpe);
	            RecommenderEvaluator evaluator4 = new MSEEvaluator();
	            mse=recommender.evaluate(evaluator4);
	            System.out.println("MSE:" +mse);
	       	}
	        } catch (LibrecException e) {
			
				e.printStackTrace();
			}
	        RecommenderEvaluator evaluator5 = new AveragePrecisionEvaluator();
	        try {
				System.out.println("AveragePrecisionEvaluator:" + recommender.evaluate(evaluator5));
			} catch (LibrecException e) {
				
				e.printStackTrace();
			}
	        System.out.println("5");
	       
	        // set id list of filter
	        List<String> userIdList = new ArrayList<String>(0);
	        List<String> itemIdList = new ArrayList<String>(0);
	       //userIdList.add("8");
	    //   userIdList.add("63");
	    //   userIdList.add("73");
	    //   userIdList.add("78");
	    //   userIdList.add("79");
	    //  itemIdList.add("70");
	        System.out.println("6");
	        
	        // filter the recommended result
	        List<RecommendedItem> recommendedItemList = recommender.getRecommendedList();
	        GenericRecommendedFilter filter = new GenericRecommendedFilter();
	        
	        filter.setUserIdList(userIdList);
	        
	        filter.setItemIdList(itemIdList);
	        recommendedItemList = filter.filter(recommendedItemList);
	        System.out.println(recommendedItemList.size());
	         hmap= new HashMap<Integer,ArrayList<RecommendItemSortable> >();
	       // recommendedItemList.
	        for (RecommendedItem recommendedItem : recommendedItemList) {
	        	final int userId=Integer.parseInt(recommendedItem.getUserId());
	        	 ArrayList<RecommendItemSortable> recommendsm=	hmap.getOrDefault(userId,new   ArrayList<RecommendItemSortable>(3));
	        	
	        	 recommendsm.add(new RecommendItemSortable(recommendedItem));
	        	 hmap.put(userId,recommendsm);
	        	}
	        	
	        	
	        final Set<Integer> keysethmap=hmap.keySet();
		   for(Integer key:keysethmap) {
			   ArrayList<RecommendItemSortable> arrayhmap=hmap.get(key);
			   Collections.sort(arrayhmap);
		  
	        
		   
	  //      HashMap<Integer,ArrayList<RecommendItemSortable> > hmap= new HashMap<Integer,ArrayList<RecommendItemSortable> >();
	        
	        // print filter result
	        System.out.println("user\titem\tvalue\ttruerating\tdiferenca");
	        	final int sizekeymap = arrayhmap.size();
	        	double acumLocal = 0;
	    		int countLocal = 0;
	    		RecommendedItem recommendedItem= null;
		        for (int i=0 ;i< sizekeymap;i++) {
		        	RecommendItemSortable ris= arrayhmap.get(i);
		        	recommendedItem=ris.getRecommendItem();
		        	final Double truerating =hmapCSV.get( recommendedItem.getUserId() +"|"+recommendedItem.getItemId());
		        	if(truerating !=null) { 
		        		ris.setMatch(true);
		        		ris.setTruerating(truerating);
		        		itensMatched.add(ris);
			        	final Double diferencaAbs=Math.abs(recommendedItem.getValue()-truerating);
			        	acumGlobal+=diferencaAbs;
			        	acumquad+=Math.pow((recommendedItem.getValue()-truerating),2);
			        	countGlobal ++;
			        	countLocal++;
			        	acumLocal+=diferencaAbs;
			        	sb.append((recommendedItem.getValue()-truerating)+"\n");
			        	recomendacaoDAOImpl.insertItemRank(this.idmodelo, Integer.parseInt(recommendedItem.getUserId()),Integer.parseInt( recommendedItem.getItemId()), truerating, (Double)recommendedItem.getValue());
			        	
			            System.out.println(
			                     recommendedItem.getUserId() + "\t" +
			                    recommendedItem.getItemId() + "\t" +
			                   recommendedItem.getValue()+ "\t"+
			                   truerating+ "\t"+
			                   diferencaAbs
			            );
		        	}else {
		        		recomendacaoDAOImpl.insertItemRank(this.idmodelo, Integer.parseInt(recommendedItem.getUserId()),Integer.parseInt( recommendedItem.getItemId()), null, (Double)recommendedItem.getValue());
				        
		        		   System.out.println(
		 	                     recommendedItem.getUserId() + "\t" +
		 	                    recommendedItem.getItemId() + "\t" +
		 	                   recommendedItem.getValue()+ "\t"+
		 	                  "\t"
		 	            );
		        	}
		           
		        }
		        
		        alocais.add(recommendedItem.getUserId()+";"+(acumLocal/countLocal)+";"+countLocal);
		        System.out.println("media dos erros locais:"+(acumLocal/countLocal));
		        if((acumLocal/countLocal)<1.47 && countLocal > 3){
		        	///sb.append(recommendedItem.getUserId()+",");
		        	errsuperam.add(recommendedItem.getUserId()+";"+(acumLocal/countLocal)+";"+countLocal);
		        	//errsuperam.add(recommendedItem.getUserId());
		        }
		   }
		   maeErrosGlobal=(acumGlobal/countGlobal);
		   System.out.println("media dos erros global:"+maeErrosGlobal);
		  // System.out.println("erro quadratico medio:"+(acumquad/countGlobal));
		   
		   for(String slocal:alocais) {
			//   System.out.println(slocal);
		   }
		   
		   for(String ssup:errsuperam) {
			//  System.out.println(ssup);
		   }
		 //  System.out.println(sb.toString());
		    /*try {
	        	 System.out.println("prediction: "+ recommender.predict(91, 119));
			} catch (LibrecException e) {
				// TODO Bloco catch gerado automaticamente
				e.printStackTrace();
			}*/
		   this.recomendacaoDAOImpl.closeModeloRank(idmodelo);
	}
	   public static List<Object> recs ;
	public static void main(final String[] args) throws NumberFormatException, SQLException {
	new MainBiasedmf();
}

}
