package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.KeyStroke;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;

import models.FuelEntry;
import models.FuelEntryTableModel;
import view.BarChartView;
import view.MainView;
import dao.FileDao;

public class MainViewController implements ItemListener {

	private MainView view;
	private FuelEntryTableModel tableModel;
	private List<FuelEntry> fuelEntries;
	private TableRowSorter<FuelEntryTableModel> sorter;
	private RowFilter<FuelEntryTableModel, Object> fuelTypeFilter;
	private RowFilter<FuelEntryTableModel, Object> fuelDateFilter;
	protected String fuelNameFilterText = "all";
	protected Date filterMonthText;
	
	public MainViewController(MainView view){
		this.view = view;
	}
	public void control(){
		// Load data file to model elements
		getData(FileDao.DEFAULT_FILE_LOCATION);
		// Create table model from list elements
		tableModel = new FuelEntryTableModel(fuelEntries);
		// Init GUI components with data and control options
		initMenuBar();
		initFilteringComponents();
		initTable();
		
		// Add actionlistener to reload button
		view.getReloadDataButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new MainViewController(new MainView()).control();
				view.dispose();
			}
		});
	}
	private void initTable(){
        // Create custom sorter, to sort table columns
		sorter = new TableRowSorter<FuelEntryTableModel>(tableModel);
		// This comparator sorts double values of fuel price and amount
		Comparator<Double> doubleComparator = new Comparator<Double>() {
			@Override
			public int compare(Double d1, Double d2) {
				return Double.compare(d1, d2);
			}
		};
		// This comparator sorts dates column, because they need to be in
		// custom dd.MM.yyyy format, it needed to be string, which means we need to
		// reparse it back to date and compare
		Comparator<String> dateComparator = new Comparator<String>() {
			@Override
			public int compare(String s1, String s2) {
				Date d1,d2;
				try {
					d1 = new SimpleDateFormat("dd.MM.yyyy").parse(s1);
					d2 = new SimpleDateFormat("dd.MM.yyyy").parse(s2);
				} catch (ParseException e) {
					e.printStackTrace();
					return 0;
				}
				return d1.compareTo(d2);
			}
		};
		sorter.setComparator(1, doubleComparator);
		sorter.setComparator(2, doubleComparator);
		sorter.setComparator(3, dateComparator);
		
		view.getTable().setModel(tableModel);
		view.getTable().setRowSorter(sorter);
	}
	private void initMenuBar(){
		// Open file dialog
		view.getOpenMenuItem().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
                JFileChooser fileOpen = new JFileChooser();
                fileOpen.showDialog(view.getPanel(), "Open file");
                getData(fileOpen.getSelectedFile().getPath());
			}
		});
		// Exit app
		view.getExitMenuItem().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		view.getExitMenuItem().setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.CTRL_MASK));
		
		// Show statusbar checkbox
		view.getShowStatusBar().setState(true);
		// Is visible
		view.getShowStatusBar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				if (view.getStatusbar().isVisible()) {
					view.getStatusbar().setVisible(false);
				} else {
					view.getStatusbar().setVisible(true);
				}
			}
		});
		view.getShowChartView().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent event) {
				List<FuelEntry> data = new ArrayList<FuelEntry>();
				for (FuelEntry entry : fuelEntries){
					if (entry.getFuelName().equals(fuelNameFilterText)){
						data.add(entry);
					}
					else if (filterMonthText != null 
							&& new SimpleDateFormat("MMM").format(entry.getRefuelingDate())
								.equals(new SimpleDateFormat("MMM").format(filterMonthText))){
						data.add(entry);
					}
					else if (fuelNameFilterText.equals("all"))
						data.add(entry);
				}
				BarChartViewController controller = new BarChartViewController(new BarChartView(), data);
				controller.control();
			}
		});
	}
	private void initFilteringComponents(){
		// Lets create a new list from fuelnames and add a combobox
		// TODO: needs improvement, quick and dirty workaround ATM
		List<String> fuelTypes = new ArrayList<String>();
		List<String> refuelingDates = new ArrayList<String>();
		for (FuelEntry item : fuelEntries){
			if (!fuelTypes.contains(item.getFuelName())){
				fuelTypes.add(item.getFuelName());
				view.getComboBox().addItem(item.getFuelName());
			}
			if (!refuelingDates.contains(new SimpleDateFormat("MMM").format(item.getRefuelingDate()))){
				refuelingDates.add(new SimpleDateFormat("MMM").format(item.getRefuelingDate()));
				view.getComboBox2().addItem(new SimpleDateFormat("MMM").format(item.getRefuelingDate()));
			}
		}
		view.getComboBox().addItemListener(this);
		view.getComboBox2().addItemListener(this);
		
		fuelTypeFilter = new RowFilter<FuelEntryTableModel, Object>(){
			@SuppressWarnings("rawtypes")
			@Override
			public boolean include(RowFilter.Entry entry) {
				if (fuelNameFilterText.equals("all"))
					return true;
				return entry.getValue(0).equals(fuelNameFilterText.toString());
			}
		};
		fuelDateFilter = new RowFilter<FuelEntryTableModel, Object>(){
			@Override
			public boolean include(Entry<? extends FuelEntryTableModel, ? extends Object> entry) {
				try {
					String d1 = new SimpleDateFormat("MMM").
							format(new SimpleDateFormat("dd.MM.yyyy").parse((String) entry.getValue(3)));
					return d1.equals(new SimpleDateFormat("MMM").format(filterMonthText));
				} catch (ParseException e) {
					return true;
				}
			}
		};
	}
	public void getData(String fileLocation){
		FileDao fd = new FileDao();
		try {
			fuelEntries = fd.getFuelEntries(fd.openFile(fileLocation));
			view.getStatusbar().setText("File parsed succesfully");
		}catch (ParseException e) {
			view.getStatusbar().setText("Error parsing values from file");
		}catch (FileNotFoundException e){
			view.getStatusbar().setText("File not found");
		}catch (IOException e) {
			view.getStatusbar().setText("File IO error");
			e.printStackTrace();
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	@Override
	public void itemStateChanged(ItemEvent event) {
		if (event.getStateChange() == ItemEvent.SELECTED) {
			try {
				filterMonthText = new SimpleDateFormat("MMM").parse(event.getItem().toString());
				sorter.setRowFilter(fuelDateFilter);
			} catch (ParseException e1) {
				filterMonthText = null;
				fuelNameFilterText = (String)event.getItem();
				sorter.setRowFilter(fuelTypeFilter);
			}
        }
	}
}
