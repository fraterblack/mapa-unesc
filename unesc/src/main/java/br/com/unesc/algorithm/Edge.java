package br.com.unesc.algorithm;

public class Edge {
	private Integer nodeOrigin;
	private Integer nodeDestin;
	private Integer distance;
	
	public Edge(Integer nodeOrigin, Integer nodeDestin, Integer distance) {
		this.nodeOrigin = nodeOrigin;
		this.nodeDestin = nodeDestin;
		this.distance = distance;
	}

	public Integer getNodeOrigin() {
		return nodeOrigin;
	}

	public Integer getNodeDestin() {
		return nodeDestin;
	}

	public Integer getAccumulatedDistance() {
		return distance;
	}
}
