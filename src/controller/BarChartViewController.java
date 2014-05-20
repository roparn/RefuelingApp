package controller;

import java.awt.Color;
import java.util.List;
import java.util.Random;

import models.FuelEntry;
import view.BarChartView;

public class BarChartViewController {

	
	private BarChartView view;
	private List<FuelEntry> fuelEntries;

	public BarChartViewController(BarChartView view, List<FuelEntry> data){
		this.view = view;
		fuelEntries = data;
	}
	public void control(){
		Random rand = new Random();
		for (FuelEntry entry : fuelEntries){
			float r = rand.nextFloat();
			float g = rand.nextFloat();
			float b = rand.nextFloat();
			String description = String.format("%.2f",entry.getTotalFuelPrice());
			view.addBar(new Color(r,g,b), (int) entry.getTotalFuelPrice(),description);
		}
	}
}
