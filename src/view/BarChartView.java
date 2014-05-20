package view;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class BarChartView extends JPanel {
	private static final long serialVersionUID = 1L;

	private Map<Color, Integer> bars = new LinkedHashMap<Color, Integer>();
	private JFrame frame;

	public BarChartView() {
		initFrame();
	}

	private void initFrame() {
		frame = new JFrame("Bar Chart");
		frame.getContentPane().add(this);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		frame.setSize(700, 300);
	}

	/**
	 * Add new bar to chart
	 * 
	 * @param color
	 *            color to display bar
	 * @param value
	 *            size of bar
	 */
	public void addBar(Color color, int value) {
		bars.put(color, value);
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		// determine longest bar

		int max = Integer.MIN_VALUE;
		for (Integer value : bars.values()) {
			max = Math.max(max, value);
		}

		// paint bars
		int width = 0;
		try {
			width = (getWidth() / bars.size()) - 2;
		} catch (java.lang.ArithmeticException e) {
			return;
		}
		int x = 1;
		for (Color color : bars.keySet()) {
			int value = bars.get(color);
			int height = (int) ((getHeight() - 5) * ((double) value / max));
			g.setColor(color);
			g.fillRect(x, getHeight() - height, width, height);
			g.setColor(Color.black);
			g.drawRect(x, getHeight() - height, width, height);
			x += (width + 2);
		}
	}
}
