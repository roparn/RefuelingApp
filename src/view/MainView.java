package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EtchedBorder;

public class MainView extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private JPanel panel;
	private JTable table;
	private final FlowLayout layout = new FlowLayout();
	private JLabel statusbar;
	private JLabel sumLabel;
	private JButton reloadDataButton;
	private JMenuItem openMenuItem;
	private JMenuItem exitMenuItem;
	private JMenu viewMenu;
	private JCheckBoxMenuItem showStatusBar;
	private JComboBox<Object> comboBox;
	private JComboBox<Object> comboBox2;

	private JMenuItem showChartView;
	
	public JMenuItem getShowChartView(){
		return this.showChartView;
	}
	public JCheckBoxMenuItem getShowStatusBar() {
		return showStatusBar;
	}
	public void setShowStatusBar(JCheckBoxMenuItem showStatusBar) {
		this.showStatusBar = showStatusBar;
	}
	public JComboBox<Object> getComboBox() {
		return comboBox;
	}
	public void setComboBox(JComboBox<Object> comboBox) {
		this.comboBox = comboBox;
	}
	public JComboBox<Object> getComboBox2() {
		return comboBox2;
	}
	public void setComboBox2(JComboBox<Object> comboBox2) {
		this.comboBox2 = comboBox2;
	}
	public JMenuItem getExitMenuItem() {
		return exitMenuItem;
	}
	public void setExitMenuItem(JMenuItem exitMenuItem) {
		this.exitMenuItem = exitMenuItem;
	}
	public JMenuItem getOpenMenuItem() {
		return openMenuItem;
	}
	public void setOpenMenuItem(JMenuItem openMenuItem) {
		this.openMenuItem = openMenuItem;
	}
	public JButton getReloadDataButton() {
		return reloadDataButton;
	}
	public void setReloadData(JButton reloadData) {
		this.reloadDataButton = reloadData;
	}
	public JPanel getPanel() {
		return panel;
	}
	public void setPanel(JPanel panel) {
		this.panel = panel;
	}
	public JTable getTable() {
		return table;
	}
	public void setTable(JTable table) {
		this.table = table;
	}
	public FlowLayout getLayout() {
		return layout;
	}
	public JLabel getStatusbar() {
		return statusbar;
	}
	public void setStatusbar(JLabel statusbar) {
		this.statusbar = statusbar;
	}
	public JLabel getSumLabel() {
		return sumLabel;
	}
	public void setSumLabel(JLabel sumLabel) {
		this.sumLabel = sumLabel;
	}
	
	public MainView(){
		initFrame();
		initPanel();
		initStatusbar();
		initMenuBar();
		initFilteringComponents();
		initTable();
	}
	private void initTable(){
        table = new JTable();
        table.setPreferredScrollableViewportSize(new Dimension(450, 300));
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane);
	}
	private void initFilteringComponents(){
		
		comboBox = new JComboBox<Object>(new String[]{"all"});
		comboBox2 = new JComboBox<Object> (new String[]{"all"});
		panel.add(comboBox);
		panel.add(comboBox2);
	}
	private void initFrame(){
		setVisible(true);
		setTitle("Refuel App");
		setSize(500, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	private void initStatusbar() {
		// Add statusbar as a label
        statusbar = new JLabel("Ready");
        statusbar.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        add(statusbar, BorderLayout.SOUTH);
	}
	private void initMenuBar() {
		// Menu bar
		JMenuBar menubar = new JMenuBar();
		// "File" menu
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		// "View" menu
		viewMenu = new JMenu("View");
		// Menu items
		// Open file
		openMenuItem = new JMenuItem("Open");
		openMenuItem.setToolTipText("Open file");
		// Exit app
		exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.setToolTipText("Exit application");
		// Add "View" menu item
		showStatusBar = new JCheckBoxMenuItem("Show StatusBar");
		showStatusBar.setState(true);
		showChartView = new JMenuItem("Show chart view");
		// Add menu items to menu
		fileMenu.add(openMenuItem);
		fileMenu.add(exitMenuItem);
		viewMenu.add(showStatusBar);
		viewMenu.add(showChartView);
		// Add menu to menubar
		menubar.add(fileMenu);
		menubar.add(viewMenu);
		setJMenuBar(menubar);
	}
	private void initPanel(){
		// Initialize panel
		panel = new JPanel();
		panel.setLayout(layout);
        // Adding load default file button
        reloadDataButton = new JButton("Reload");
        reloadDataButton.setToolTipText("Reload data");
		panel.add(reloadDataButton);
		add(panel);
	}
}
