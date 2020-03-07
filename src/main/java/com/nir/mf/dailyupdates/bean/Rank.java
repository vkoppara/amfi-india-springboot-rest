package com.nir.mf.dailyupdates.bean;

public class Rank {
	private int rank;
	private double percentage;
	public Rank(int rank, double percentage) {
		// TODO Auto-generated constructor stub
		this.rank = rank;
		this.percentage= percentage;
	}
	public Rank() {
		
	}
	public double getPercentage() {
		return percentage;
	}
	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}

}
