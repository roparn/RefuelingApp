package models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class FuelEntryTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	private final String[] columnNames = { "Fuel type", "Fuel price", "Fuel amount","Date" };
	private List<FuelEntry> data;
	public List<FuelEntry> getData() {
		return data;
	}
	public FuelEntryTableModel(List<FuelEntry> data) {
		this.data = data;
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return data.size();
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		FuelEntry entry = data.get(arg0);
		switch (arg1) {
		case 0:
			return entry.getFuelName();
		case 1:
			return entry.getFuelPrice();
		case 2:
			return entry.getFuelAmount();
		case 3:
			return new SimpleDateFormat("dd.MM.yyyy").format(entry.getRefuelingDate());
		default:
			return null;
		}
	}
    public String getColumnName(int col) {
        return columnNames[col];
    }

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		super.setValueAt(aValue, rowIndex, columnIndex);
		FuelEntry entry = data.get(rowIndex);
		switch (columnIndex) {
		case 0:
			entry.setFuelName((String) aValue);
			break;
		case 1:
			entry.setFuelPrice((double) aValue);
			break;
		case 2:
			entry.setFuelAmount((double) aValue);
			break;
		case 3:
			entry.setRefuelingDate((Date) aValue);
			break;
		}
	}
}
