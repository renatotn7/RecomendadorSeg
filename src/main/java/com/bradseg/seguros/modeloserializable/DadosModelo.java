package com.bradseg.seguros.modeloserializable;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.BiMap;

import net.librec.conf.Configuration;
import net.librec.math.structure.DenseMatrix;
import net.librec.math.structure.DenseVector;
import net.librec.math.structure.SparseMatrix;
import net.librec.recommender.RecommenderContext;
import net.librec.recommender.item.RecommendedList;

public class DadosModelo implements Serializable {
	 public DenseVector getUserBiases() {
		return userBiases;
	}

	public void setUserBiases(DenseVector userBiases) {
		this.userBiases = userBiases;
	}

	public DenseVector getItemBiases() {
		return itemBiases;
	}

	public void setItemBiases(DenseVector itemBiases) {
		this.itemBiases = itemBiases;
	}

	public Double getRegBias() {
		return regBias;
	}

	public void setRegBias(Double regBias) {
		this.regBias = regBias;
	}

	public DenseMatrix getUserFactors() {
		return userFactors;
	}

	public void setUserFactors(DenseMatrix userFactors) {
		this.userFactors = userFactors;
	}

	public DenseMatrix getItemFactors() {
		return itemFactors;
	}

	public void setItemFactors(DenseMatrix itemFactors) {
		this.itemFactors = itemFactors;
	}

	public Integer getNumFactors() {
		return numFactors;
	}

	public void setNumFactors(Integer numFactors) {
		this.numFactors = numFactors;
	}

	public Integer getNumIterations() {
		return numIterations;
	}

	public void setNumIterations(Integer numIterations) {
		this.numIterations = numIterations;
	}

	public Float getInitMean() {
		return initMean;
	}

	public void setInitMean(Float initMean) {
		this.initMean = initMean;
	}

	public Float getInitStd() {
		return initStd;
	}

	public void setInitStd(Float initStd) {
		this.initStd = initStd;
	}

	public Float getRegUser() {
		return regUser;
	}

	public void setRegUser(Float regUser) {
		this.regUser = regUser;
	}

	public Float getRegItem() {
		return regItem;
	}

	public void setRegItem(Float regItem) {
		this.regItem = regItem;
	}

	public Boolean isRanking() {
		return isRanking;
	}

	public void setRanking(Boolean isRanking) {
		this.isRanking = isRanking;
	}

	public Integer getTopN() {
		return topN;
	}

	public void setTopN(Integer topN) {
		this.topN = topN;
	}

	public Configuration getConf() {
		return conf;
	}

	public void setConf(Configuration conf) {
		this.conf = conf;
	}

	public RecommenderContext getContext() {
		return context;
	}

	public void setContext(RecommenderContext context) {
		this.context = context;
	}

	public SparseMatrix getTrainMatrix() {
		return trainMatrix;
	}

	public void setTrainMatrix(SparseMatrix trainMatrix) {
		this.trainMatrix = trainMatrix;
	}

	public SparseMatrix getTestMatrix() {
		return testMatrix;
	}

	public void setTestMatrix(SparseMatrix testMatrix) {
		this.testMatrix = testMatrix;
	}

	public SparseMatrix getValidMatrix() {
		return validMatrix;
	}

	public void setValidMatrix(SparseMatrix validMatrix) {
		this.validMatrix = validMatrix;
	}

	public RecommendedList getRecommendedList() {
		return recommendedList;
	}

	public void setRecommendedList(RecommendedList recommendedList) {
		this.recommendedList = recommendedList;
	}

	public Integer getNumUsers() {
		return numUsers;
	}

	public void setNumUsers(Integer numUsers) {
		this.numUsers = numUsers;
	}

	public Integer getNumItems() {
		return numItems;
	}

	public void setNumItems(Integer numItems) {
		this.numItems = numItems;
	}

	public Integer getNumRates() {
		return numRates;
	}

	public void setNumRates(Integer numRates) {
		this.numRates = numRates;
	}

	public Double getMaxRate() {
		return maxRate;
	}

	public void setMaxRate(Double maxRate) {
		this.maxRate = maxRate;
	}

	public Double getMinRate() {
		return minRate;
	}

	public void setMinRate(Double minRate) {
		this.minRate = minRate;
	}

	public static List<Double> getRatingScale() {
		return ratingScale;
	}

	public static void setRatingScale(List<Double> ratingScale) {
		DadosModelo.ratingScale = ratingScale;
	}

	public BiMap<String, Integer> getUserMappingData() {
		return userMappingData;
	}

	public void setUserMappingData(BiMap<String, Integer> userMappingData) {
		this.userMappingData = userMappingData;
	}

	public BiMap<String, Integer> getItemMappingData() {
		return itemMappingData;
	}

	public void setItemMappingData(BiMap<String, Integer> itemMappingData) {
		this.itemMappingData = itemMappingData;
	}

	public Double getGlobalMean() {
		return globalMean;
	}

	public void setGlobalMean(Double globalMean) {
		this.globalMean = globalMean;
	}

	public Boolean isEarlyStop() {
		return earlyStop;
	}

	public void setEarlyStop(Boolean earlyStop) {
		this.earlyStop = earlyStop;
	}

	public static Boolean isVerbose() {
		return verbose;
	}

	public static void setVerbose(Boolean verbose) {
		DadosModelo.verbose = verbose;
	}

	public Double getLoss() {
		return loss;
	}

	public void setLoss(Double loss) {
		this.loss = loss;
	}

	public Double getLastLoss() {
		return lastLoss;
	}

	public void setLastLoss(Double lastLoss) {
		this.lastLoss = lastLoss;
	}

	public Boolean isBoldDriver() {
		return isBoldDriver;
	}

	public void setBoldDriver(Boolean isBoldDriver) {
		this.isBoldDriver = isBoldDriver;
	}

	public Float getDecay() {
		return decay;
	}

	public void setDecay(Float decay) {
		this.decay = decay;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

		protected DenseVector userBiases;
	    protected DenseVector itemBiases;
	    protected Double regBias;
	    protected DenseMatrix userFactors;
	    protected DenseMatrix itemFactors;
	    protected Integer numFactors;

	    protected Integer numIterations;

	    protected Float initMean;

	    protected Float initStd;

	    protected Float regUser;

	    protected Float regItem;
	    
	    /**
	     * is ranking or rating
	     */
	    protected Boolean isRanking;

	    /**
	     * topN
	     */
	    protected Integer topN;

	    /**
	     * conf
	     */
	    protected Configuration conf;

	    /**
	     * RecommenderContext
	     */
	    protected RecommenderContext context;

	    /**
	     * trainMatrix
	     */
	    protected SparseMatrix trainMatrix;

	    /**
	     * testMatrix
	     */
	    protected SparseMatrix testMatrix;

	    /**
	     * validMatrix
	     */
	    protected SparseMatrix validMatrix;

	    /**
	     * Recommended Item List
	     */
	    protected RecommendedList recommendedList;

	    /**
	     * the number of users
	     */
	    protected Integer numUsers;

	    /**
	     * the number of items
	     */
	    protected Integer numItems;

	    /**
	     * the number of rates
	     */
	    protected Integer numRates;

	    /**
	     * Maximum rate of rating scale
	     */
	    protected Double maxRate;

	    /**
	     * Minimum rate of rating scale
	     */
	    protected Double minRate;

	    /**
	     * a list of rating scales
	     */
	    protected static List<Double> ratingScale;

	    /**
	     * user Mapping Data
	     */
	    public BiMap<String, Integer> userMappingData;

	    /**
	     * item Mapping Data
	     */
	    public BiMap<String, Integer> itemMappingData;

	    /**
	     * global mean of ratings
	     */
	    protected Double globalMean;

	    /**
	     * early-stop criteria
	     */
	    protected Boolean earlyStop;

	    /**
	     * verbose
	     */
	    protected static Boolean verbose = true;

	    /**
	     * objective loss
	     */
	    protected Double loss, lastLoss = 0.0d;

	    /**
	     * whether to adjust learning rate automatically
	     */
	    protected Boolean isBoldDriver;

	    /**
	     * decay of learning rate
	     */
	    protected Float decay;

}
