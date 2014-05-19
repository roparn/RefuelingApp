package swing;

import javax.swing.SwingUtilities;

import view.MainView;
import controller.MainViewController;

public class MainAppMenu {

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				MainView view = new MainView();
				MainViewController controller = new MainViewController(view);
				controller.control();
			}
		});
	}
}
