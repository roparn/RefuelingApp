package swing;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.DateFormatter;

import models.FuelEntry;
import models.FuelEntryTableModel;
import models.FuelEntryTableModelTest;

import dao.FileDao;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class MainAppMenu extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private JTable table;
	private FlowLayout layout;
	private FuelEntryTableModel tableModel;
	public FuelEntryTableModel getTableModel(){
		return tableModel;
	}
	private List<FuelEntry> fuelEntries;
    public List<FuelEntry> getFuelEntries() {
		return fuelEntries;
	}
	private JLabel statusbar;
	public JLabel getStatusbar() {
		return statusbar;
	}

	public MainAppMenu() {
		initUI();
	}
	
	public void initUI(){
		// Initialize panel and its contents
		initPanel();
		// Initialize menu bar
		initMenuBar();
		// Initialize statusbar
		initStatusbar();
        // Get data
        getData(FileDao.DEFAULT_FILE_LOCATION);
        // Initialize table view
        initTable();
//        add(panel);
		// Set mainwindow basic params
		setTitle("Refueling app");
		setSize(500, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);  
	}
	private void initTable(){
		tableModel = new FuelEntryTableModel(fuelEntries);
        table = new JTable(tableModel);
//        table.setPreferredScrollableViewportSize(new Dimension(400, 200));
        // Create custom sorter, to sort table columns
		TableRowSorter<FuelEntryTableModel> sorter 
		    = new TableRowSorter<FuelEntryTableModel>((FuelEntryTableModel) table.getModel());
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
                getData(FileDao.DEFAULT_FILE_LOCATION);
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

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				MainAppMenu mainMenu = new MainAppMenu();
				mainMenu.setVisible(true);
			}
		});
	}
}
