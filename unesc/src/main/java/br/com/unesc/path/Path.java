package br.com.unesc.path;

import java.util.Collection;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import br.com.unesc.place.Place;
import br.com.unesc.shared.Model;

@DatabaseTable(tableName = "paths")
public class Path extends Model {
	@DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
	private Place originPlace;
	
	@DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
	private Place destinationPlace;
	
	@DatabaseField
	private int distance = 0;
	
	@DatabaseField
	private boolean isAccessibleReducedMobility = false;
	
	@ForeignCollectionField(eager = false)
    private transient Collection<Excerpt> excerpts;
	
	public Path() {
	}

	public Path(Place originPlace, Place destinationPlace, boolean isAccessibleReducedMobility) {
		this.originPlace = originPlace;
		this.destinationPlace = destinationPlace;
		this.isAccessibleReducedMobility = isAccessibleReducedMobility;
	}

	public Place getOriginPlace() {
		return originPlace;
	}

	public void setOriginPlace(Place originPlace) {
		this.originPlace = originPlace;
	}

	public Place getDestinationPlace() {
		return destinationPlace;
	}

	public void setDestinationPlace(Place destinationPlace) {
		this.destinationPlace = destinationPlace;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public boolean isAccessibleReducedMobility() {
		return isAccessibleReducedMobility;
	}

	public void setIsAccessibleReducedMobility(boolean isAccessibleReducedMobility) {
		this.isAccessibleReducedMobility = isAccessibleReducedMobility;
	}

	public Collection<Excerpt> getExcerpts() {
		return excerpts;
	}

	public void setExcerpts(Collection<Excerpt> excerpts) {
		this.excerpts = excerpts;
	}
}