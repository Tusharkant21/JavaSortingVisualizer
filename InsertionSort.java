package algorithmVisulizer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

public class InsertionSort extends JPanel {
	private static final long serialVersionUID = 1L;
	private final int WIDTH = 1000, HEIGHT = WIDTH * 9 / 16;
	private final int SIZE = 100;
	private final float BAR_WIDTH = (float)WIDTH / SIZE;
	private float[] bar_height = new float[SIZE];
	private SwingWorker<Void, Void> shuffler, sorter;
	private int current_index, traversing_index;
	
	private InsertionSort() {
		setBackground(Color.BLACK);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		initBarHeight();
		initSorter();
		initShuffler();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D)g;
		g2d.setColor(Color.cyan);
//		Graphics2D g3d = (Graphics2D)g;
//		g3d.setColor(Color.PINK);
		Rectangle2D.Float bar;
		for(int i = 0; i < SIZE; i++) {
			bar = new Rectangle2D.Float(i * BAR_WIDTH, 4, BAR_WIDTH, bar_height[i]);
			g2d.draw(bar);
			//g3d.fill(bar);
		}
		
		g2d.setColor(Color.RED);
		bar = new Rectangle2D.Float(current_index * BAR_WIDTH, 
									0, 
									BAR_WIDTH, 
									bar_height[current_index]);
		g2d.fill(bar);
		
		g2d.setColor(Color.GREEN);
		bar = new Rectangle2D.Float(traversing_index * BAR_WIDTH, 
									0, 
									BAR_WIDTH, 
									bar_height[traversing_index]);
		g2d.fill(bar);
	}
	
	private void initSorter() {
		sorter = new SwingWorker<>() {
			@Override
			public Void doInBackground() throws InterruptedException {
				for(current_index = 1; current_index < SIZE; current_index++) {
					traversing_index = current_index;
					while(traversing_index > 0 && 
						bar_height[traversing_index] < bar_height[traversing_index - 1]) {
						
						swap(traversing_index, traversing_index - 1);
						traversing_index--;
						
						Thread.sleep(10);
						repaint();
					}
				}
				current_index = 0;
				traversing_index = 0;
				
				return null;
			}
		};
	}
	
	private void initShuffler() {
		shuffler = new SwingWorker<>() {
			@Override
			public Void doInBackground() throws InterruptedException {
				int middle = SIZE / 2;
				for(int i = 0, j = middle; i < middle; i++, j++) {
					int random_index = new Random().nextInt(SIZE);
					swap(i, random_index);
					
					random_index = new Random().nextInt(SIZE);
					swap(j, random_index);
					
					Thread.sleep(10);
					repaint();
				}
				
				return null;
			}
			
			@Override
			public void done() {
				super.done();
				sorter.execute();
			}
		};
		shuffler.execute();
	}
	
	private void initBarHeight() {
		float interval = (float)HEIGHT / SIZE;
		for(int i = 0; i < SIZE; i++)
			bar_height[i] = i * interval;
	}

	private void swap(int indexA, int indexB) {
		float temp = bar_height[indexA];
		bar_height[indexA] = bar_height[indexB];
		bar_height[indexB] = temp;
	}
	
	public static void main(String args[]) {
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new JFrame("Insertion Sort Visualizer");
			frame.setResizable(false);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setContentPane(new InsertionSort());
			frame.validate();
			frame.pack();
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		});
	}
}
