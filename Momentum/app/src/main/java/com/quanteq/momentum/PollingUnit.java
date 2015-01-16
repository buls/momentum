package com.quanteq.momentum;

import android.location.Location;

import java.util.Comparator;

public class PollingUnit implements Comparable<PollingUnit>{
	
	private String locationId;
	private String stateId;
	private String lgaId;
	private String raId;
	private String pu;
	private String buildingTypeId;
    private Location location;
    private Float distance = 0.0f;
	private String numAffiliation;
	private String desc;
	
	
	public PollingUnit() {
		super();
	}


	public PollingUnit(String locationId, String stateId, String lgaId,
			String raId, String pu, String buildingTypeId, String numAffiliation, String desc) {
		super();
		this.locationId = locationId;
		this.stateId = stateId;
		this.lgaId = lgaId;
		this.raId = raId;
		this.pu = pu;
		this.buildingTypeId = buildingTypeId;
		this.numAffiliation = numAffiliation;
		this.desc = desc;
	}


	public String getLocationId() {
		return locationId;
	}


	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}


	public String getStateId() {
		return stateId;
	}


	public void setStateId(String stateId) {
		this.stateId = stateId;
	}


	public String getLgaId() {
		return lgaId;
	}


	public void setLgaId(String lgaId) {
		this.lgaId = lgaId;
	}


	public String getRaId() {
		return raId;
	}


	public void setRaId(String raId) {
		this.raId = raId;
	}


	public String getPu() {
		return pu;
	}


	public void setPu(String pu) {
		this.pu = pu;
	}


	public String getBuildingTypeId() {
		return buildingTypeId;
	}


	public void setBuildingTypeId(String buildingTypeId) {
		this.buildingTypeId = buildingTypeId;
	}


    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getNumAffiliation() {
		return numAffiliation;
	}


	public void setNumAffiliation(String numAffiliation) {
		this.numAffiliation = numAffiliation;
	}


	public String getDesc() {
		return desc;
	}


	public void setDesc(String desc) {
		this.desc = desc;
	}

    public Float getDistance(){
        return this.distance;
    }

    public void setDistance(float distance){
        this.distance = distance;
    }

    @Override
    public int compareTo(PollingUnit pollingUnit) {
        return this.distance.intValue() - pollingUnit.distance.intValue();

    }

    public static Comparator<PollingUnit> PollingUnitComparator
            = new Comparator<PollingUnit>() {

        public int compare(PollingUnit pu1, PollingUnit pu2) {

            Float distance1 = pu1.getDistance();
            Float distance2 = pu2.getDistance();

            //ascending order
            return distance1.compareTo(distance2);

            //descending order
            //return fruitName2.compareTo(fruitName1);
        }

    };
}
