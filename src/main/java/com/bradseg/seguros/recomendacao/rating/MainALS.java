/**
 *
 * @author renat
 *
 * @version 1.0
 */
package com.bradseg.seguros.recomendacao.rating;

import java.util.ArrayList;
import java.util.List;

import net.librec.conf.Configuration;
import net.librec.data.model.TextDataModel;
import net.librec.eval.RecommenderEvaluator;
import net.librec.eval.rating.RMSEEvaluator;
import net.librec.filter.GenericRecommendedFilter;
import net.librec.recommender.Recommender;
import net.librec.recommender.RecommenderContext;
import net.librec.recommender.cf.ItemKNNRecommender;
import net.librec.recommender.cf.rating.MFALSRecommender;
import net.librec.recommender.cf.rating.SVDPlusPlusRecommender;
import net.librec.recommender.item.RecommendedItem;
import net.librec.similarity.PCCSimilarity;
import net.librec.similarity.RecommenderSimilarity;

public class MainALS {
//parece que cria avaliaçoes para todo mundo e da nota nao tem como prever, o resultado varia no tempo
	//mas posso filtrar por item
	private MainALS() {
		
	}
	public static void main(final  String[] args) throws Exception {

        // build data model
        Configuration conf = new Configuration();
        conf.set(Messages.getString("dfsDataDir"), "D:\\Users\\renat\\Documentos\\projetoia\\desenvolvimentolibrec\\librec\\data"); //$NON-NLS-1$ //$NON-NLS-2$
        conf.set(Messages.getString("dataInputPath"), "bradseg"); 
        
		conf.set(Messages.getString("recomendersSimilarityKey"),"user"); 
		conf.set("rec.recommender.class","mfals"); //$NON-NLS-1$ //$NON-NLS-2$
		conf.set("rec.iterator.learnrate","0.01"); //$NON-NLS-1$ //$NON-NLS-2$
		conf.set("rec.iterator.learnrate.maximum","0.01"); //$NON-NLS-1$ //$NON-NLS-2$
		conf.set("rec.iterator.maximum","100"); //$NON-NLS-1$ //$NON-NLS-2$
		conf.set("rec.user.regularization","0.01"); //$NON-NLS-1$ //$NON-NLS-2$
		conf.set("rec.item.regularization","0.01"); //$NON-NLS-1$ //$NON-NLS-2$
		conf.set("rec.impItem.regularization","0.001"); //$NON-NLS-1$ //$NON-NLS-2$
		conf.set("rec.factor.number","10"); //$NON-NLS-1$ //$NON-NLS-2$
		conf.set("rec.learnrate.bolddriver","false"); //$NON-NLS-1$ //$NON-NLS-2$
		conf.set("rec.learnrate.decay","1.0"); //$NON-NLS-1$ //$NON-NLS-2$
      
       
        TextDataModel dataModel = new TextDataModel(conf);
        dataModel.buildDataModel();

        // build recommender context
        RecommenderContext context = new RecommenderContext(conf, dataModel);

        // build similarity
        conf.set("rec.recommender.similarity.key" ,"item"); //$NON-NLS-1$ //$NON-NLS-2$
        RecommenderSimilarity similarity = new PCCSimilarity();
        similarity.buildSimilarityMatrix(dataModel);
        context.setSimilarity(similarity);

        // build recommender
        Recommender recommender = new MFALSRecommender () ;
        recommender . setContext ( context ) ;

        // run recommender algorithm
        recommender.recommend(context);

        // evaluate the recommended result
        RecommenderEvaluator evaluator = new RMSEEvaluator();
        System.out.println("RMSE:" + recommender.evaluate(evaluator)); //$NON-NLS-1$

        // set id list of filter
        List<String> userIdList = new ArrayList<String>();
        List<String> itemIdList = new ArrayList<String>();
      //  userIdList.add("63");
   //    itemIdList.add("70");

        // filter the recommended result
        
        List<RecommendedItem> recommendedItemList = recommender.getRecommendedList();
        GenericRecommendedFilter filter = new GenericRecommendedFilter();
        filter.setUserIdList(userIdList);
        filter.setItemIdList(itemIdList);
        recommendedItemList = filter.filter(recommendedItemList);

        // print filter result
        for (RecommendedItem recommendedItem : recommendedItemList) {
            System.out.println(
                    "user:" + recommendedItem.getUserId() + " " + //$NON-NLS-1$ //$NON-NLS-2$
                    "item:" + recommendedItem.getItemId() + " " + //$NON-NLS-1$ //$NON-NLS-2$
                    "value:" + recommendedItem.getValue() //$NON-NLS-1$
            );
        }
}

}
