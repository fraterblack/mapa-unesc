package br.com.unesc.algorithm;

import java.util.List;
import java.util.Map;
import java.util.Optional;
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
	
	public TreeMap<Integer, Path> findSmallestRoute(Integer originId, Integer destinationId) throws Exception {
		dij.findSmallestRoute(nodeIndexes.get(originId), nodeIndexes.get(destinationId));
		
		return parseRouteToDestination(dij.getRouteToDestination());
		
		//return paths;
	}
	
	private TreeMap<Integer, Path> parseRouteToDestination(TreeMap<Integer, Edge> route) {
		TreeMap<Integer, Path> pathsToDestination = new TreeMap<Integer, Path>();
		
		route.forEach((a, b) -> {
			Optional<Path> path = paths.stream()
				.filter(p -> 
					p.getOriginPlace().getId() == getKeyByValue(nodeIndexes, b.getNodeOrigin()) 
					&& p.getDestinationPlace().getId() == getKeyByValue(nodeIndexes, b.getNodeDestin())
				)
				.findFirst();
			
			if (path.isPresent()) {
				pathsToDestination.put(pathsToDestination.size(), path.get());
			}
		});
		
		return pathsToDestination;
	}
	
	private <K, V> K getKeyByValue(Map<K, V> map, V value) {
	    for (Map.Entry<K, V> entry : map.entrySet()) {
	            if (value.equals(entry.getValue())) {
	            return entry.getKey();
	        }
	    }
	    return null;
	}
}
