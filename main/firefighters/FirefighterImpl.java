package main.firefighters;

import main.api.CityNode;
import main.api.Firefighter;

public class FirefighterImpl implements Firefighter {

	private int totalDistanceTraveld;

	// in case we need a way to go back. Not clear from the instruction
	private CityNode currentLocation;

	/**
	 * When a firefighter is hired, he/she will be at the station, so we will
	 * required a location initialization
	 * 
	 * @param fireStationLocation
	 */
	public FirefighterImpl(CityNode fireStationLocation) {
		this.totalDistanceTraveld = 0;
		this.currentLocation = fireStationLocation;
	}

	/**
	 * This method set the destination for the firefighter to move to
	 * 
	 * @param newDestination
	 */
	public void setDestination(CityNode newDestination) {
		// calculate distance when add instead of after to pre-compute the distance
		// We don't want to calculate total distance everytime, so it's better to do it
		// here.
		totalDistanceTraveld += Math.abs(currentLocation.getX() - newDestination.getX())
								+ Math.abs(currentLocation.getY() - newDestination.getY());

		// let the firefighter move to the location
		this.currentLocation = newDestination;
	}

	@Override
	public CityNode getLocation() {
		return this.currentLocation;
	}

	@Override
	public int distanceTraveled() {
		return totalDistanceTraveld;
	}
}
