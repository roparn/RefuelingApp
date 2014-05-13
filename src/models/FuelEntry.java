package models;

import java.util.Date;

public class FuelEntry {
	private String fuelName;
	private float fuelPrice;
	private float fuelAmount;
	private Date refuelingDate;
	
	public FuelEntry(String fuelName, float fuelPrice, float fuelAmount,
			Date refuelingDate) {
		super();
		this.fuelName = fuelName;
		this.fuelPrice = fuelPrice;
		this.fuelAmount = fuelAmount;
		this.refuelingDate = refuelingDate;
	}
	public String getFuelName() {
		return fuelName;
	}
	public void setFuelName(String fuelName) {
		this.fuelName = fuelName;
	}
	public float getFuelPrice() {
		return fuelPrice;
	}
	public void setFuelPrice(float fuelPrice) {
		this.fuelPrice = fuelPrice;
	}
	public float getFuelAmount() {
		return fuelAmount;
	}
	public void setFuelAmount(float fuelAmount) {
		this.fuelAmount = fuelAmount;
	}
	public Date getRefuelingDate() {
		return refuelingDate;
	}
	public void setRefuelingDate(Date refuelingDate) {
		this.refuelingDate = refuelingDate;
	}
	@Override
	public String toString() {
		return "FuelEntry [fuelName=" + fuelName + ", fuelPrice=" + fuelPrice
				+ ", fuelAmount=" + fuelAmount + ", refuelingDate="
				+ refuelingDate + "]";
	}
	
	
}
