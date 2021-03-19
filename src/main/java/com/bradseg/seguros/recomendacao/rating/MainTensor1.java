package com.bradseg.seguros.recomendacao.rating;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.bradseg.seguros.model.ArffDataModel;
import com.bradseg.seguros.recomendacao.vo.RecommendItemSortable;
import com.bradseg.seguros.recommender.EFMRecommender;
import com.bradseg.seguros.recommender.TensorRecommender;

import br.com.bradseg.sise.apolicevida.utils.file.File2Lines;
import br.com.bradseg.sise.apolicevida.utils.file.LineEnt;
import br.com.bradseg.sise.apolicevida.utils.serializacao.Serializacao;
import br.com.bradseg.sise.apolicevida.utils.texto.StringsUtils;
import net.librec.common.LibrecException;
import net.librec.conf.Configuration;
import net.librec.data.DataModel;
import net.librec.eval.Measure.MeasureValue;
import net.librec.eval.RecommenderEvaluator;
import net.librec.eval.ranking.AveragePrecisionEvaluator;
import net.librec.eval.rating.MAEEvaluator;
import net.librec.eval.rating.MPEEvaluator;
import net.librec.eval.rating.MSEEvaluator;
import net.librec.eval.rating.RMSEEvaluator;
import net.librec.filter.GenericRecommendedFilter;
import net.librec.math.structure.SparseTensor;
import net.librec.recommender.Recommender;
import net.librec.recommender.RecommenderContext;

import net.librec.recommender.cf.rating.SVDPlusPlusRecommender;

import net.librec.recommender.item.RecommendedItem;
import net.librec.similarity.BinaryCosineSimilarity;
import net.librec.similarity.CPCSimilarity;
import net.librec.similarity.CosineSimilarity;
import net.librec.similarity.DiceCoefficientSimilarity;
import net.librec.similarity.KRCCSimilarity;
import net.librec.similarity.PCCSimilarity;
import net.librec.similarity.RecommenderSimilarity;


public final class MainTensor1 {
//parece que cria avaliaçoes para todo mundo e da nota nao tem como prever, o resultado varia no tempo
	//mas posso filtrar por item
	private MainTensor1() {
		
	}
	   public static List<Object> recs ;
	public static void main(final String[] args) {
	//	recs= Serializacao.lerArquivoBinario("model.mdl");
	//	Recommender recommender =(Recommender) recs.get(0);
		TensorRecommender recommender = new EFMRecommender () ;
		double acumGlobal = 0;
		int countGlobal = 0;
		
		
		 Map<String,Double > hmapCSV= new HashMap<String,Double > ();
        // build data model
		 Configuration conf = new Configuration();
		 if(recs==null) {
		       
		        conf.set("dfs.data.dir", "D:\\Users\\renat\\Documentos\\projetoia\\desenvolvimentolibrec\\librec\\data");
		       // conf.set("data.input.path", "test/hfttest/musical_instruments.arff");
			    conf.set("data.input.path", "test/arfftest/testset/");
				conf.set("rec.recommender.similarity.key","user");
				conf.set("rec.recommender.class","efm");
				conf.set("rec.explain.flag","false");
				conf.set("rec.explain.userids","333");
				conf.set("rec.explain.numfeature","2");
				
				
			//	conf.set("rec.iterator.learnrate","0.000001");
				conf.set("rec.iterator.learnrate.maximum","0.000001");
				conf.set("rec.iterator.maximum","10000");
				
				conf.set("rec.regularization.lambdax","0.0001"); 	
				conf.set("rec.regularization.lambday","0.0001");
				conf.set("rec.regularization.lambdau","0.01");
				conf.set("rec.regularization.lambdah","0.01");
				conf.set("rec.regularization.lambdav","0.01");
				conf.set("data.model.format","arff");
				conf.set("data.convertor.format","arff");
				conf.set("data.splitter.trainset.ratio","0.95");
				
				conf.set("rec.recommender.isranking","false");
				conf.set("rec.recommender.ranking.topn","1000");
				
						
				 DataModel dataModel = new ArffDataModel(conf);
			
		        
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
		        PCCSimilarity similarity = new PCCSimilarity();
		        //RecommenderSimilarity similarity = new CosineSimilarity();
		      //  RecommenderSimilarity similarity = new BinaryCosineSimilarity();
		      //  RecommenderSimilarity similarity = new DiceCoefficientSimilarity();
		     //   RecommenderSimilarity similarity = new KRCCSimilarity();
		      //  RecommenderSimilarity similarity = new MSDSimilarity();
		        
		        similarity.buildSimilarityMatrix(dataModel);
		        context.setSimilarity(similarity);
		        
		        System.out.println("2");
		        // build recommender
		    
		        recommender . setContext ( context ) ;
		        System.out.println("3");
		        
		        
		        // run recommender algorithm
		        try {
					recommender.recommend(context);
				//	Map<MeasureValue,Double> map =recommender.evaluateMap();
					 recommender.saveModel("model2.mdl");
		        } catch (LibrecException e) {
				
					e.printStackTrace();
				}
		      
		  //      recs.add(recommender);
		        
		    //    Serializacao.gravarArquivoBinario(recs, "model.mdl");
		 }
        System.out.println("4");
        // evaluate the recommended result
       
        try {
			File2Lines fl= new  File2Lines("D:\\Users\\renat\\Documentos\\projetoia\\desenvolvimentolibrec\\librec\\data\\output_subprefeitura_cod_subgrupo\\ratings_0.txt");
			List<LineEnt> linhas = fl.getLines();
		 
			for(int i =  0 ;i< linhas.size();i++) {
				String linha = linhas.get(i).getConteudo();
				String[] registro = linha.split(" ");
				String perfilprod = registro[0]+"|"+registro[1];
				
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
           
				System.out.println(StringsUtils.buildStr("RMSE:" , String.valueOf(recommender.evaluate(evaluator1))));
			
           RecommenderEvaluator evaluator2 = new MAEEvaluator();
            System.out.println("MAE:" + recommender.evaluate(evaluator2));
            RecommenderEvaluator evaluator3 = new MPEEvaluator();
            System.out.println("MPE:" + recommender.evaluate(evaluator3));
            RecommenderEvaluator evaluator4 = new MSEEvaluator();
            System.out.println("MSE:" + recommender.evaluate(evaluator4));
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
    //   userIdList.add("72");
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
        HashMap<Integer,ArrayList<RecommendItemSortable> > hmap= new HashMap<Integer,ArrayList<RecommendItemSortable> >();
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
	        for (int i=0 ;i< sizekeymap&& i<10;i++) {
	        	
	        	final RecommendedItem recommendedItem=arrayhmap.get(i).getRecommendItem();
	        	final Double truerating =hmapCSV.get( recommendedItem.getUserId() +"|"+recommendedItem.getItemId());
	        	if(truerating !=null) { 
		        	final Double diferencaAbs=Math.abs(recommendedItem.getValue()-truerating);
		        	acumGlobal+=diferencaAbs;
		        	countGlobal ++;
		        	countLocal++;
		        	acumLocal+=diferencaAbs;
		            System.out.println(
		                     recommendedItem.getUserId() + "\t" +
		                    recommendedItem.getItemId() + "\t" +
		                   recommendedItem.getValue()+ "\t"+
		                   truerating+ "\t"+
		                   diferencaAbs
		            );
	        	}else {
	        		  System.out.println(
	 	                     recommendedItem.getUserId() + "\t" +
	 	                    recommendedItem.getItemId() + "\t" +
	 	                   recommendedItem.getValue()+ "\t"+
	 	                  "\t"
	 	            );
	        	}
	           
	        }
	        System.out.println("media dos erros locais:"+(acumLocal/countLocal));
	   }
	   System.out.println("media dos erros global:"+(acumGlobal/countGlobal));
	   
}

}
