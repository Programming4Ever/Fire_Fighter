package main.firefighters;

import java.util.ArrayList;
import java.util.List;

import main.api.Building;
import main.api.City;
import main.api.CityNode;
import main.api.FireDispatch;
import main.api.Firefighter;
import main.api.exceptions.NoFireFoundException;
import main.api.exceptions.OutOfCityBoundsException;

public class FireDispatchImpl implements FireDispatch {

	private List<Firefighter> fighterFighterList;
	private City currentCity;
		
	public FireDispatchImpl(City city) {
		fighterFighterList = new ArrayList<>();
		this.currentCity = city;
	}

	@Override
	public void setFirefighters(int numFirefighters) {

		if(numFirefighters < 1) {
			throw new IllegalArgumentException("Number of firefighter is less than 1, not sure what to do here.");
		}
		
		//create fighter fighters
		for(int i = 0; i < numFirefighters; i++) {
			//set the firefighters to be at the station when first hires/creates
			this.fighterFighterList.add(new FirefighterImpl(this.currentCity.getFireStation().getLocation()));
		}
		
	}

	@Override
	public List<Firefighter> getFirefighters() {
		return fighterFighterList;
	}

	@Override
	public void dispatchFirefighers(CityNode... burningBuildings) {
		
		//Nothing to do here since the there is no fire.
		if(burningBuildings == null || burningBuildings.length == 0) {
			return;
		}
		
		//loop through all fire buldings and get the distance
		for(CityNode currentNode: burningBuildings) {
						
			FirefighterImpl targetFirefighter = (FirefighterImpl) chooseFirefighter(currentNode);
			targetFirefighter.setDestination(currentNode);
			
			//the building is no longer on fire
			this.extinquishFire(currentNode);
		}
	}
	
	/*
	 * This method put out the fire
	 */
	private void extinquishFire(CityNode location) {
		
		Building building = this.currentCity.getBuilding(location);
		
		//not sure what we want to do since this is the fireproof building
		if(building.isFireproof()) {
			return;
		}
		
		//check to see if there's fire
		if(building.isBurning()) {
			try {
				this.currentCity.getBuilding(location).extinguishFire();
			} catch (OutOfCityBoundsException | NoFireFoundException e) {
				throw new IllegalStateException("The exception has been caught.  How should we handle this?");
			}
		}
		
	}
	
	/*
	 * Select the best firefighter based on the givent total distance
	 */
	private Firefighter chooseFirefighter(CityNode destination) {
		
		if(destination.getX() > currentCity.getXDimension() || destination.getY() > currentCity.getYDimension()) {
			throw new OutOfCityBoundsException();
		}
		
		//set the first one
		Firefighter leastTraveler = fighterFighterList.get(0);
		//loop through the list to figure out which one hasn't gone first
		
		for(Firefighter currentFirefighter: fighterFighterList) {
			
			if(currentFirefighter.distanceTraveled() == 0) {
				return currentFirefighter;
			}
			
			//check to see if the current one is less than the next one because we are looking to optimize the total distance traveled
			//also want to skip the one equal to itself
			if(leastTraveler != currentFirefighter && leastTraveler.distanceTraveled() > currentFirefighter.distanceTraveled()) {
				//no longer the least
				leastTraveler = currentFirefighter;
			}
			
		}
		
		return leastTraveler;
	}
}
