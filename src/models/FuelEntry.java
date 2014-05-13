package models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author robert
 *
 */
public class FuelEntry {
	private String fuelName;
	private double fuelPrice;
	private double fuelAmount;
	private Date refuelingDate;
	
	public FuelEntry(String fuelName, double fuelPrice, double fuelAmount,
			Date refuelingDate) {
		super();
		setFuelName(fuelName);
		setFuelPrice(fuelPrice);
		setFuelAmount(fuelAmount);
		setRefuelingDate(refuelingDate);
	}
	/**
	 * This constructor parses the string arguments passed to it.
	 *
	 * @param  fuel name as string
	 * @param  fuel price per unit
	 * @param  fuel amount
	 * @param  date as string
	 */
	public FuelEntry(String fuelName, String fuelPrice, String fuelAmount,
			String refuelingDate) throws ParseException {
		setFuelName(fuelName);
		fuelPrice = fuelPrice.replaceAll(",", ".");
		fuelAmount = fuelAmount.replaceAll(",", ".");
		setFuelPrice(Double.parseDouble(fuelPrice));
		setFuelAmount(Double.parseDouble(fuelAmount));
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		setRefuelingDate(dateFormat.parse(refuelingDate));
	}
	public String getFuelName() {
		return fuelName;
	}
	public void setFuelName(String fuelName) {
		this.fuelName = fuelName;
	}
	public double getFuelPrice() {
		return fuelPrice;
	}
	public void setFuelPrice(double fuelPrice) {
		if (fuelPrice < 0)
			throw new IllegalArgumentException("Price must not be lower than 0!");
		this.fuelPrice = fuelPrice;
	}
	public double getFuelAmount() {
		return fuelAmount;
	}
	public void setFuelAmount(double fuelAmount) {
		if (fuelAmount <= 0)
			throw new IllegalArgumentException("Fuel amount must be greater than 0");
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
		return "FuelEntry [fuelName=" + getFuelName() 
				+ ", fuelPrice=" + getFuelPrice()
				+ ", fuelAmount=" + getFuelAmount() 
				+ ", refuelingDate=" 
				+ new SimpleDateFormat("dd.MM.yyyy").format(getRefuelingDate()) + "]";
	}
	/**
	 * Compares one fuelentry object to another
	 * @param 
	 * entry fuelentry object to be compared to
	 * @return true if values of two objects are the same
	 */
	public boolean equals(FuelEntry entry){
		if (this.getFuelAmount() == entry.getFuelAmount() && 
			this.getFuelPrice() == entry.getFuelPrice() && 
			this.getFuelName().equals(entry.getFuelName()) && 
			this.getRefuelingDate().equals(entry.getRefuelingDate())
				){
			return true;
		}
		else
			return false;
	}
	
}
