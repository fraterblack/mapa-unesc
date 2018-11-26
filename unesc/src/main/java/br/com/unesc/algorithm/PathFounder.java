package br.com.unesc.algorithm;

import java.util.List;
import java.util.TreeMap;

import br.com.unesc.path.Path;

public class PathFounder {
	private List<Path> paths;
	private TreeMap<Integer, Integer> nodeIndexes = new TreeMap<Integer, Integer>();
	private Dijkstra dij;
	
	public PathFounder(List<Path> paths) throws Exception {
		//Set paths attribute
		this.paths = paths;
		
		//Add node indexes
		paths.forEach(path -> {
			try {
				if (nodeIndexes.get(path.getOriginPlace().getId()) == null) {
					nodeIndexes.put(path.getOriginPlace().getId(), nodeIndexes.size());
				}
				
				if (nodeIndexes.get(path.getDestinationPlace().getId()) == null) {
					nodeIndexes.put(path.getDestinationPlace().getId(), nodeIndexes.size());
				}
	        } catch(Exception e) {
	        	throw new RuntimeException(e);
	        }
		});
		
		//Instantiate Dijsktra
		dij = new Dijkstra(nodeIndexes.size());
		
		//Add paths to Dijsktra
		paths.forEach(path -> {
			System.out.println(nodeIndexes.get(path.getOriginPlace().getId()));
			System.out.println(nodeIndexes.get(path.getDestinationPlace().getId()));
			try {
				dij.insertEdge(
					nodeIndexes.get(path.getOriginPlace().getId()), 
					nodeIndexes.get(path.getDestinationPlace().getId()), 
					path
				);
	        } catch(Exception e) {
	        	throw new RuntimeException(e);
	        }
		});
	}
	
	public List<Path> findSmallestRoute(Integer originId, Integer destinationId) throws Exception {
		dij.findSmallestRoute(nodeIndexes.get(originId), nodeIndexes.get(destinationId));
		
		System.out.println(dij.getDistanceToDestination());
		
		dij.getRouteToDestination().forEach((a, b) -> {
			System.out.println(a);
			System.out.println(b.getNodeOrigin());
			System.out.println(b.getNodeDestin());
			System.out.println(b.getAccumulatedDistance());
			System.out.println("...............................");
		});
		
		return paths;
	}
}
