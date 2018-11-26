package br.com.unesc.algorithm;

import java.util.Map.Entry;
import java.util.TreeMap;

import br.com.unesc.path.Path;

public class Graph {
	private final int NODES_QUANTITY;
	private Path[][] matrix;
	
	public Graph(int nodesQuantity) throws Exception {
		validateNodeQuantity(nodesQuantity);
		
		NODES_QUANTITY = nodesQuantity;
		
		createMatrix();
	}
	
	public Path[][] getMatrix() {
		return matrix;
	}
	
	public int getNodesQuantity() {
		return NODES_QUANTITY;
	}
	
	public void insertEdge(Integer row, Integer col, Path path) throws Exception {
		validatePositions(row, col);
		validateWeight(path.getDistance());
		
		matrix[row][col] = path;
	}
	
	private boolean validatePositions(Integer row, Integer col) throws Exception {
		if (row < 0 || col < 0) {
			throw new Exception("Um dos vertices são inválidos.");
		}
		
		if (row > NODES_QUANTITY || col > NODES_QUANTITY) {
			throw new Exception("Um dos vertices é maior que o tamanho de vértices.");
		}
		
		return true;
	}
	
	private boolean validateWeight(Integer distance) throws Exception {
		 if (distance < 0) {
			throw new Exception("Peso não pode ser negativo.");
		}
		
		return true;
	}
	
	private boolean validateNodeQuantity(int nodesQuantity) throws Exception {
		if (nodesQuantity < 0) {
			throw new Exception("A quantidade de vértices n�o pode ser menor que zero.");
		}
		
		return true;
	}
	
	private void createMatrix() {
		matrix = new Path[NODES_QUANTITY][];
		
		for (int i = 0; i < NODES_QUANTITY; i++) {
			matrix[i] = new Path[NODES_QUANTITY];
	
			for (int j = 0; j < matrix[i].length; j++) {
				matrix[i][j] = null;
			}
		}
	}

	protected Integer sumDistanceOfPaths(TreeMap<Integer, Edge> mapOfPaths) {
		Integer sumOfDistance = 0;
		if(mapOfPaths == null) {
			return -1;
		}
		for (Entry<Integer, Edge> entry : mapOfPaths.entrySet()) {
			sumOfDistance += entry.getValue().getAccumulatedDistance();
		}
		return sumOfDistance;
	}
}
