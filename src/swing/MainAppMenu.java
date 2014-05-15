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

import models.FuelEntry;
import models.FuelEntryTableModel;

import dao.FileDao;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class MainAppMenu extends JFrame {

	private static final long serialVersionUID = 1L;
	JPanel panel;
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
		// Initialize menu bar
		initMenuBar();
		// Initialize panel and its contents
		initPanel();
		// Initialize statusbar
		initStatusbar();
        // Get data
        getData(FileDao.DEFAULT_FILE_LOCATION);
		// Initialize list view
		//initDataList();

        final JTable table = new JTable(new FuelEntryTableModel(fuelEntries));
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
        table.setAutoCreateRowSorter(true);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);

        
        
		// Set mainwindow basic params
		setTitle("Refueling app");
		setSize(500, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);  
	}
	private void initStatusbar() {
		// Add statusbar as a label
        statusbar = new JLabel("Ready");
        statusbar.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        add(statusbar, BorderLayout.SOUTH);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initDataList(){
        // Add data list as JList
        JList list = new JList(fuelEntries.toArray());
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.getViewport().add(list);
        scrollPane.setPreferredSize(new Dimension(250, 200));
        add(scrollPane);
	}
	private void initPanel(){
		// Initialize panel
		panel = new JPanel();
		getContentPane().add(panel);
		panel.setLayout(null);
        // Adding load default file button
        JButton loadDefault = new JButton("Reload");
        loadDefault.setBounds(0, 0, 130, 30);
        loadDefault.setToolTipText("Load default file for parsing");
        loadDefault.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                getData(FileDao.DEFAULT_FILE_LOCATION);
           }
        });
		panel.add(loadDefault);
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
