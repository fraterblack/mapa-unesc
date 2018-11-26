package br.com.unesc.algorithm;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Dijkstra extends Graph {
	//Results
	private int distanceToDestination;
	private TreeMap<Integer, Edge> pathToDestination = new TreeMap<Integer, Edge>();;
	private TreeMap<Integer, String> processingLog;
	
	public Dijkstra(int vertexQuantity) throws Exception {
		super(vertexQuantity);
	}
	
	public void findSmallestRoute(int origin, int destination) throws Exception {
		try {
			//Reset last process status
			resetProcessingState();
			
			//Validate origin and destination
			if (origin < 0) {
				throw new Exception("Origem deve ser maior ou igual a zero");
			}
			
			if (destination >= getNodesQuantity()) {
				throw new Exception("Destino deve ser menor ou igual a " + (getNodesQuantity() - 1));
			}
			
			TreeMap<Integer, Integer> unsolvedNodes = new TreeMap<Integer, Integer>();
			TreeMap<Integer, Integer> solvedNodes = new TreeMap<Integer, Integer>();
			TreeMap<Integer, Edge> processedPaths = new TreeMap<Integer, Edge>();
			
			for (int i = 0; i < getNodesQuantity(); i++) {
				//Create vector of remaining nodes
				unsolvedNodes.put(unsolvedNodes.size(), new Integer(i));
			}

			//Move origin node to resolveds
			solvedNodes.put(origin, 0);
			unsolvedNodes.remove(new Integer(origin));

			//While exists unresolved nodes
			while (!unsolvedNodes.isEmpty()) {
				int smallestResolvedNode = -1;
				int smallestAdjacentNode = -1;
				int smallestAdjacentValue = 0;
				
				for (Map.Entry<Integer, Integer> entry : solvedNodes.entrySet()) {
					Integer currentNode = entry.getKey();
					Integer acummulatedDistance = entry.getValue();
					
					//Logging
					addLog("Itera sobre não resolvido [" + currentNode + "]");
					
					//From resolved node (matrix row), iterate over adjacents nodes (matrix columns)
					for (int i = 0; i < getNodesQuantity(); i++) {
						//Only considers node with values different from null and that is attached to an unresolved node
						if (getMatrix()[currentNode][i] != null && getMatrix()[currentNode][i].getDistance() > 0 && solvedNodes.get(i) == null) {
							//Sum weight of adjacent node with accumulated value of the resolved node
							int adjacentDistance = getMatrix()[currentNode][i].getDistance() + acummulatedDistance;
							
							//Logging
							String logMessage = "  Nó Adjacente [" + i + "] - Distância: " + adjacentDistance + "(" + acummulatedDistance + ")";
							
							//First iteration
							if (smallestAdjacentNode == -1 || adjacentDistance <= smallestAdjacentValue) {
								smallestResolvedNode = currentNode;
								smallestAdjacentNode = i;
								smallestAdjacentValue = adjacentDistance;
								
								//Logging
								logMessage += " ***"; //Identifica o n� adjascente como o de menor caminho at� o momento
							}
							
							//Logging
							addLog(logMessage);
						}
					}
				}
				
				//Logging
				addLog("[Menor caminho: " + smallestResolvedNode + "->" + smallestAdjacentNode + " = " + smallestAdjacentValue + "]");
				
				//Add the path to processed list
				processedPaths.put(processedPaths.size(), new Edge(smallestResolvedNode, smallestAdjacentNode, smallestAdjacentValue));
				
				//Move smallest adjacent node from unresolved nodes to the resolves
				solvedNodes.put(smallestAdjacentNode, smallestAdjacentValue);
				unsolvedNodes.remove(new Integer(smallestAdjacentNode));
				
				addLog(".........................................................");
				
				//Smallest path found
				if (smallestAdjacentNode == destination) {
					//Logging
					addLog("----- Menor caminho encontrado: " + smallestAdjacentValue + " -----");
					
					distanceToDestination = smallestAdjacentValue;
					
					break;
				}
			}
			
			//Generate the path to the destination from processed paths
			generatePathToDestination(processedPaths);
		} catch (Exception error) {
			if (error.getMessage().equals("-1")) {
				throw new Exception("Não existe um caminho viável entre os pontos");
			}
			
			throw new Exception(error.getMessage());
		}
	}
	
	public TreeMap<Integer, Edge> getRouteToDestination() {
		return pathToDestination;
	}
	
	public Integer getDistanceToDestination() {
		return distanceToDestination;
	}
	
	public void printLog() {
		processingLog.forEach((index, log) -> System.out.println(log));
	}
	
	private void resetProcessingState() {
		processingLog = new TreeMap<Integer, String>();
		
		distanceToDestination = 0;
		pathToDestination.clear();
	}
	
	private void generatePathToDestination(TreeMap<Integer, Edge> processedPaths) {
		int lastOriginToFound = -1;
		for (Entry<Integer, Edge> entry : processedPaths.entrySet().stream()
				.sorted((p1, p2) -> p2.getKey().compareTo(p1.getKey()))
				.collect(Collectors.toList())
		) {
			if (lastOriginToFound == -1) {
				lastOriginToFound = entry.getValue().getNodeOrigin();
				
				pathToDestination.put(entry.getKey(), entry.getValue());
			} else {
				if (entry.getValue().getNodeDestin() == lastOriginToFound) {
					lastOriginToFound = entry.getValue().getNodeOrigin();
					
					pathToDestination.put(entry.getKey(), entry.getValue());
				}
			}
		}
	}
	
	private void addLog(String message) {
		processingLog.put(processingLog.size(), message);
	}
}
