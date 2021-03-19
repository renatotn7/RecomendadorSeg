package com.bradseg.seguros.recomendacao.ranking;

import java.util.ArrayList;
import java.util.List;

import net.librec.conf.Configuration;
import net.librec.data.model.TextDataModel;
import net.librec.eval.RecommenderEvaluator;
import net.librec.eval.ranking.AveragePrecisionEvaluator;
import net.librec.eval.ranking.PrecisionEvaluator;
import net.librec.eval.ranking.RecallEvaluator;
import net.librec.eval.ranking.ReciprocalRankEvaluator;
import net.librec.eval.rating.MAEEvaluator;
import net.librec.eval.rating.MPEEvaluator;
import net.librec.eval.rating.MSEEvaluator;
import net.librec.eval.rating.RMSEEvaluator;
import net.librec.filter.GenericRecommendedFilter;
import net.librec.recommender.Recommender;
import net.librec.recommender.RecommenderContext;
import net.librec.recommender.cf.ItemKNNRecommender;
import net.librec.recommender.cf.ranking.AoBPRRecommender;
import net.librec.recommender.cf.rating.SVDPlusPlusRecommender;
import net.librec.recommender.item.RecommendedItem;
import net.librec.similarity.PCCSimilarity;
import net.librec.similarity.RecommenderSimilarity;

public class MainOBPRRecommender {
//parece que cria avaliaçoes para todo mundo e da nota nao tem como prever, o resultado varia no tempo
	//mas posso filtrar por item
	
	public static void main(final String[] args) throws Exception {

        // build data model
        Configuration conf = new Configuration();
        conf.set("dfs.data.dir", "D:\\Users\\renat\\Documentos\\projetoia\\desenvolvimentolibrec\\librec\\data");
        conf.set("data.input.path", "bradseg");
        
		conf.set("rec.recommender.similarity.key","user");
		conf.set("rec.recommender.class","aobpr");
		conf.set("rec.item.distribution.parameter","500");
		conf.set("rec.iterator.learnrate","0.01");
		conf.set("rec.iterator.learnrate.maximum","0.01");
		conf.set("rec.iterator.maximum","13");
		conf.set("rec.user.regularization","0.01");
		conf.set("rec.item.regularization","0.01");
		conf.set("rec.impItem.regularization","0.01");
		conf.set("rec.factor.number","10");
		conf.set("rec.learnrate.bolddriver","false");
		conf.set("rec.recommender.isranking","false");
		conf.set("rec.recommender.ranking.topn","10");
       
        TextDataModel dataModel = new TextDataModel(conf);
        
        dataModel.buildDataModel();
//3.22
        // build recommender context
        RecommenderContext context = new RecommenderContext(conf, dataModel);
        System.out.println("1");
        // build similarity
        conf.set("rec.recommender.similarity.key" ,"item");
        RecommenderSimilarity similarity = new PCCSimilarity();
        similarity.buildSimilarityMatrix(dataModel);
        context.setSimilarity(similarity);
        
        System.out.println("2");
        // build recommender
        Recommender recommender = new AoBPRRecommender () ;
        recommender . setContext ( context ) ;
        System.out.println("3");
        // run recommender algorithm
        recommender.recommend(context);
        System.out.println("4");
        // evaluate the recommended result
      RecommenderEvaluator evaluator = new PrecisionEvaluator();
        System.out.println("precEvaluator:" + recommender.evaluate(evaluator));
         evaluator = new ReciprocalRankEvaluator();
        System.out.println("ReciprocalRankEvaluator:" + recommender.evaluate(evaluator));
        evaluator = new RecallEvaluator();
       System.out.println("RecallEvaluator:" + recommender.evaluate(evaluator));
        /*  RecommenderEvaluator evaluator2 = new MAEEvaluator();
        System.out.println("MAE:" + recommender.evaluate(evaluator2));
        RecommenderEvaluator evaluator3 = new MPEEvaluator();
        System.out.println("MPE:" + recommender.evaluate(evaluator3));
        RecommenderEvaluator evaluator4 = new MSEEvaluator();
        System.out.println("MSE:" + recommender.evaluate(evaluator4));
       */ 
       if(!conf.getBoolean("rec.recommender.isranking")) { 
      		RecommenderEvaluator evaluator1 = new RMSEEvaluator();
           System.out.println("RMSE:" + recommender.evaluate(evaluator1));
          RecommenderEvaluator evaluator2 = new MAEEvaluator();
           System.out.println("MAE:" + recommender.evaluate(evaluator2));
           RecommenderEvaluator evaluator3 = new MPEEvaluator();
           System.out.println("MPE:" + recommender.evaluate(evaluator3));
           RecommenderEvaluator evaluator4 = new MSEEvaluator();
           System.out.println("MSE:" + recommender.evaluate(evaluator4));
      	}
        RecommenderEvaluator evaluator5 = new AveragePrecisionEvaluator();
        System.out.println("AveragePrecisionEvaluator:" + recommender.evaluate(evaluator5));
        System.out.println("5");
       
        // set id list of filter
        List<String> userIdList = new ArrayList<String>();
        List<String> itemIdList = new ArrayList<String>(10);
       userIdList.add("63");
     // itemIdList.add("70");
        System.out.println("6");
        // filter the recommended result
        List<RecommendedItem> recommendedItemList = recommender.getRecommendedList();
        GenericRecommendedFilter filter = new GenericRecommendedFilter();
        filter.setUserIdList(userIdList);
        filter.setItemIdList(itemIdList);
        recommendedItemList = filter.filter(recommendedItemList);
        System.out.println(recommendedItemList.size());

        // print filter result
        for (RecommendedItem recommendedItem : recommendedItemList) {
            System.out.println(
                    "user:" + recommendedItem.getUserId() + " " +
                    "item:" + recommendedItem.getItemId() + " " +
                    "value:" + recommendedItem.getValue()
            );
        }
}

}
