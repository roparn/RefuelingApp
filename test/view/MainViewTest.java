package view;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.junit.Before;
import org.junit.Test;

public class MainViewTest {

	private MainView view;
	private ArrayList<Object> componentList;
	
	@Before
	public void initView(){
		this.view = new MainView();
		componentList = new ArrayList<Object>();
		for (Object o : view.getPanel().getComponents())
			componentList.add(o);
	}
	
	@Test
	public void MainViewPanelTest(){
		int size = componentList.size();
		assertTrue("Panel is not in the main container",
				view.getPanel().getParent().getParent().getParent().getParent() == view);
		assertTrue(String.format("Panel must have 4 UI components, was %d", size), size == 4);
		assertTrue("First item must be JButton",componentList.get(0) == view.getReloadDataButton());
		assertTrue("Second item must be JComboBox",componentList.get(1) == view.getComboBox());
		assertTrue("Third item must be JComboBox",componentList.get(2) == view.getComboBox2());
		assertTrue("Fourth item must be JScrollPane",componentList.get(3).getClass() == JScrollPane.class);
	}
	@Test
	public void MainViewTableTest(){
		JTable table = view.getTable();
		assertTrue("Table not in correct container",table.getParent().getParent()
				== componentList.get(3));
	}
	@Test
	public void MainViewMenuBarTest(){
		JMenuBar menubar = view.getJMenuBar();
		int size = menubar.getComponentCount();
		assertTrue(String.format("There must be 2 MenuBar components, was %d", size),size == 2);
		JMenu file = (JMenu) menubar.getComponent(0);
		JMenu viewMenu = (JMenu) menubar.getComponent(1);
		assertTrue("No file menu in menubar", file.getText() == "File");
		assertTrue(String.format("There must be 2 FileMenu items, was %d", file.getItemCount()), 
				file.getItemCount() == 2);
		assertTrue("No view menu in menubar", viewMenu.getText() == "View");
		assertTrue(String.format("There must be 1 FileMenu item, was %d", viewMenu.getItemCount()), 
				viewMenu.getItemCount() == 1);
		assertTrue("No open file in file menu", file.getItem(0).getText() == "Open");
		assertTrue("No exit app in file menu", file.getItem(1).getText() == "Exit");
		assertTrue("No statusbar toggle in view menu", viewMenu.getItem(0).getText() == "Show StatusBar");
	}
	@Test
	public void MainViewStatusbarTest(){
		assertTrue("Statusbar not in main view",
				view.getStatusbar().getParent().getParent().getParent().getParent() == view);
	}
}
