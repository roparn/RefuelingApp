package swing;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;
import javax.swing.table.TableRowSorter;

import controller.MainViewController;

import view.MainView;

import models.FuelEntry;
import models.FuelEntryTableModel;

import dao.FileDao;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
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

public class MainAppMenu extends JFrame implements ItemListener {

	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private JTable table;
	private FlowLayout layout;
	// Filter vars
	private String fuelNameFilterText;
	private RowFilter<FuelEntryTableModel, Object> fuelTypeFilter;
	private RowFilter<FuelEntryTableModel, Object> fuelDateFilter;
	private Date filterMonthText;
	// Tablemodel and sorter vars
	private TableRowSorter<FuelEntryTableModel> sorter;
	private FuelEntryTableModel tableModel;
	public FuelEntryTableModel getTableModel(){
		return tableModel;
	}
	private List<FuelEntry> fuelEntries;
    public List<FuelEntry> getFuelEntries() {
		return fuelEntries;
	}
	private JLabel statusbar;
	private JLabel sumLabel;
	public JLabel getStatusbar() {
		return statusbar;
	}

	public MainAppMenu() {
		initUI();
	}
	
	public void initUI(){
		// Initialize statusbar
		initStatusbar();
        // Get data
        getData(FileDao.DEFAULT_FILE_LOCATION);
		// Initialize panel and its contents
		initPanel();
		initFilteringComponents();
		// Initialize menu bar
		initMenuBar();
        // Initialize table view
        initTable();
        // Create label for sum
        sumLabel = new JLabel(String.valueOf(tableModel.getTotalCosts()));
        panel.add(sumLabel);
        
		// Set mainwindow basic params
		setTitle("Refueling app");
		setSize(500, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);  
	}
	private void initTable(){
		tableModel = new FuelEntryTableModel(fuelEntries);
        table = new JTable(tableModel);
        table.setPreferredScrollableViewportSize(new Dimension(450, 300));
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
		table.setRowSorter(sorter);
		
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane);
	}
	private void initFilteringComponents(){
		// Set up row filters
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
		// Lets create a new list from fuelnames and add a combobox
		List<String> fuelTypes = new ArrayList<String>();
		List<String> refuelingDates = new ArrayList<String>();
		fuelTypes.add("all");
		refuelingDates.add("all");
		for (FuelEntry item : fuelEntries){
			if (!fuelTypes.contains(item.getFuelName()))
				fuelTypes.add(item.getFuelName());
			if (!refuelingDates.contains(new SimpleDateFormat("MMM").format(item.getRefuelingDate())))
				refuelingDates.add(new SimpleDateFormat("MMM").format(item.getRefuelingDate()));
		}
		JComboBox<?> comboBox = new JComboBox<Object>(fuelTypes.toArray());
		JComboBox<?> comboBox2 = new JComboBox<Object> (refuelingDates.toArray());
		comboBox.addItemListener(this);
		comboBox2.addItemListener(this);
		panel.add(comboBox);
		panel.add(comboBox2);
	}
	private void initStatusbar() {
		// Add statusbar as a label
        statusbar = new JLabel("Ready");
        statusbar.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        add(statusbar, BorderLayout.SOUTH);
	}
	private void initPanel(){
		// Initialize panel
		layout = new FlowLayout();
		panel = new JPanel();
		panel.setLayout(layout);
		getContentPane().add(panel);
		panel.setToolTipText("asdasd");
        // Adding load default file button
        JButton reloadData = new JButton("Reload");
        reloadData.setToolTipText("Reload data");
        reloadData.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
            	initUI();
           }
        });
		panel.add(reloadData);
		add(panel);
	}
	private void initMenuBar() {
		// Menu bar
		JMenuBar menubar = new JMenuBar();
		// "File" menu
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		// "View" menu
		JMenu viewMenu = new JMenu("View");
		// Menu items
		// Open file
		JMenuItem oMenuItem = new JMenuItem("Open");
		oMenuItem.setToolTipText("Open file");
		oMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
                JFileChooser fileOpen = new JFileChooser();
                fileOpen.showDialog(panel, "Open file");
                getData(fileOpen.getSelectedFile().getPath());
			}
		});
		// Exit app
		JMenuItem eMenuItem = new JMenuItem("Exit");
		eMenuItem.setToolTipText("Exit application");
		eMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		});
		eMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.CTRL_MASK));
		// Add "View" menu item
		JCheckBoxMenuItem sbar = new JCheckBoxMenuItem("Show StatuBar");
		sbar.setState(true);
		// Is visible
		sbar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				if (statusbar.isVisible()) {
					statusbar.setVisible(false);
				} else {
					statusbar.setVisible(true);
				}
			}
		});
		// Add menu items to menu
		fileMenu.add(oMenuItem);
		fileMenu.add(eMenuItem);
		viewMenu.add(sbar);
		// Add menu to menubar
		menubar.add(fileMenu);
		menubar.add(viewMenu);
		setJMenuBar(menubar);
	}
	public void getData(String fileLocation){
		FileDao fd = new FileDao();
		try {
			fuelEntries = fd.getFuelEntries(fd.openFile(fileLocation));
			statusbar.setText("File parsed succesfully");
		}catch (ParseException e) {
			statusbar.setText("Error parsing values from file");
		}catch (FileNotFoundException e){
			statusbar.setText("File not found");
		}catch (IOException e) {
			statusbar.setText("File IO error");
			e.printStackTrace();
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) {
			try {
				filterMonthText = new SimpleDateFormat("MMM").parse(e.getItem().toString());
				sorter.setRowFilter(fuelDateFilter);
			} catch (ParseException e1) {
				fuelNameFilterText = (String)e.getItem();
				sorter.setRowFilter(fuelTypeFilter);
			}	
        }		
	}
	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
//				MainAppMenu mainMenu = new MainAppMenu();
//				mainMenu.setVisible(true);
				MainView view = new MainView();
				MainViewController controller = new MainViewController(view);
				controller.control();
			}
		});
	}
}
