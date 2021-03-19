/**
 * Copyright (C) 2016 LibRec
 * <p>
 * This file is part of LibRec.
 * LibRec is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * LibRec is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with LibRec. If not, see <http://www.gnu.org/licenses/>.
 */
package net.librec.recommender.cf.rating;

import java.io.Serializable;

import com.bradseg.seguros.modeloserializable.DadosModelo;

import br.com.bradseg.sise.apolicevida.utils.serializacao.Serializacao;
import net.librec.annotation.ModelData;
import net.librec.common.LibrecException;
import net.librec.math.structure.DenseMatrix;
import net.librec.math.structure.DenseVector;
import net.librec.math.structure.MatrixEntry;
import net.librec.recommender.MatrixFactorizationRecommender;

/**
 * Biased Matrix Factorization Recommender
 *
 * @author GuoGuibing and Keqiang Wang
 */
@ModelData({"isRating", "biasedMF", "userFactors", "itemFactors", "userBiases", "itemBiases"})
public class BiasedMFRecommender  extends MatrixFactorizationRecommender implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -7788382781176326776L;

	/**
     * bias regularization
     */
    protected double regBias;

    /**
     * user biases
     */
    protected DenseVector userBiases;

    /**
     * user biases
     */
    protected DenseVector itemBiases;

    /*
     * (non-Javadoc)
	 *
	 * @see net.librec.recommender.AbstractRecommender#setup()
	 */
    @Override
    protected void setup() throws LibrecException {
        super.setup();
        regBias = conf.getDouble("rec.bias.regularization", 0.01);

        //initialize the userBiased and itemBiased
        userBiases = new DenseVector(numUsers);
        itemBiases = new DenseVector(numItems);

        userBiases.init(initMean, initStd);
        itemBiases.init(initMean, initStd);
    }

    @Override
    protected void trainModel() throws LibrecException {
        for (int iter = 1; iter <= numIterations; iter++) {
            loss = 0.0d;

            for (MatrixEntry matrixEntry : trainMatrix) {

                int userIdx = matrixEntry.row(); // user userIdx
                int itemIdx = matrixEntry.column(); // item itemIdx
                double realRating = matrixEntry.get(); // real rating on item itemIdx rated by user userIdx

                double predictRating = predict(userIdx, itemIdx);
                double error = realRating - predictRating;
                loss += error * error;

                // update user and item bias
                double userBiasValue = userBiases.get(userIdx);
                userBiases.add(userIdx, learnRate * (error - regBias * userBiasValue));
                loss += regBias * userBiasValue * userBiasValue;

                double itemBiasValue = itemBiases.get(itemIdx);
                itemBiases.add(itemIdx, learnRate * (error - regBias * itemBiasValue));
                loss += regBias * itemBiasValue * itemBiasValue;

                //update user and item factors
                for (int factorIdx = 0; factorIdx < numFactors; factorIdx++) {
                    double userFactorValue = userFactors.get(userIdx, factorIdx);
                    double itemFactorValue = itemFactors.get(itemIdx, factorIdx);

                    userFactors.add(userIdx, factorIdx, learnRate * (error * itemFactorValue - regUser * userFactorValue));
                    itemFactors.add(itemIdx, factorIdx, learnRate * (error * userFactorValue - regItem * itemFactorValue));
                    loss += regUser * userFactorValue * userFactorValue + regItem * itemFactorValue * itemFactorValue;
                }
            }

            loss *= 0.5d;
            if (isConverged(iter) && earlyStop) {
                break;
            }
            updateLRate(iter);
        }
    }


    public void saveRec(String pathfile) {
    	DadosModelo dm = new DadosModelo();
    	dm.setBoldDriver(isBoldDriver);
    	//	dm.setConf(conf);
    	//dm.setContext(context);
    	dm.setDecay(decay);
    	dm.setEarlyStop(earlyStop);
    	dm.setGlobalMean(globalMean);
    	dm.setInitMean(initMean);
    	dm.setInitStd(initStd);
    	dm.setItemBiases(itemBiases);
    	dm.setItemFactors(itemFactors);
    	dm.setItemMappingData(itemMappingData);//
    	dm.setLastLoss(lastLoss);
    	dm.setLoss(loss);
    	dm.setMaxRate(maxRate);
    	dm.setNumFactors(numFactors);
    	dm.setNumItems(numItems);
    	dm.setNumIterations(numIterations);
    	dm.setNumRates(numRates);
    	dm.setNumUsers(numUsers);
    	dm.setRanking(isRanking);//ok
    	
    	dm.setRecommendedList(recommendedList);
    	dm.setRegBias(regBias);
    	dm.setRegItem(regItem);
    	dm.setRegUser(regUser);
    	dm.setTestMatrix(testMatrix);
    	dm.setTopN(topN);
    	dm.setTrainMatrix(trainMatrix);
    	dm.setUserBiases(userBiases);
    	dm.setUserFactors(userFactors);
    	dm.setUserMappingData(userMappingData);
    	dm.setValidMatrix(validMatrix);
    	dm.setRatingScale(ratingScale);
    	dm.setVerbose(verbose);
    	
    	
    	Serializacao.gravarArquivoBinario( dm,pathfile);
    	
    }
    /**
     * predict a specific rating for user userIdx on item itemIdx.
     *
     * @param userIdx user index
     * @param itemIdx item index
     * @return predictive rating for user userIdx on item itemIdx
     * @throws LibrecException if error occurs
     */
    @Override
    public double predict(int userIdx, int itemIdx) throws LibrecException {
    	double returnmult=DenseMatrix.rowMult(userFactors, userIdx, itemFactors, itemIdx);
        return returnmult+ userBiases.get(userIdx) + itemBiases.get(itemIdx) + globalMean;
    }
}
