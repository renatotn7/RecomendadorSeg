package com.bradseg.seguros.recomendacao.vo;

import java.util.Comparator;

import net.librec.recommender.item.RecommendedItem;

public class RecommendItemSortable implements Comparable<RecommendItemSortable>{
	private boolean match=false;
	public boolean isMatch() {
		return match;
	}

	public void setMatch(boolean match) {
		this.match = match;
	}

	public Double getTruerating() {
		return truerating;
	}

	public void setTruerating(Double truerating) {
		this.truerating = truerating;
	}




	private RecommendedItem recommendItem;
	private Double truerating;
	public RecommendItemSortable(final RecommendedItem recommendItem) {
		this.recommendItem=recommendItem;
	}

	public RecommendedItem getRecommendItem() {
		return recommendItem;
	}




	public int compareTo(final RecommendItemSortable o) {
		int resultadoComparacao=0;
		double othervalue=o.getRecommendItem().getValue();
		double thisvalue=this.getRecommendItem().getValue();
		
		if(othervalue>thisvalue) {
			resultadoComparacao= 1;
		}
		if(othervalue<thisvalue) {
			resultadoComparacao= -1;
		}
		
		return resultadoComparacao;
	}

}
