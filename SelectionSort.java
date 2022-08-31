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

public class SelectionSort extends JPanel {
	private static final long serialVersionUID = 1L;
	private final int WIDTH = 1000, HEIGHT = WIDTH * 9 / 16;
	private final int SIZE = 100;
	private final float BAR_WIDTH = (float)WIDTH / SIZE;
	private float[] bar_height = new float[SIZE];
	private SwingWorker<Void, Void> shuffler, sorter;
	private int current_index, traversing_index, min;
	
	private SelectionSort() {
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
				for(current_index = 0; current_index < SIZE-1; current_index++) {
					// min=bar_height[current_index];
					//System.out.print(bar_height[current_index]+" ");
					for(traversing_index=current_index+1; traversing_index<SIZE; traversing_index++){
				
						if(bar_height[traversing_index+1]<bar_height[traversing_index]) {
						
						swap(traversing_index, traversing_index + 1);
					
						
						Thread.sleep(10);
						repaint();
						
					}
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
					
					Thread.sleep(2);
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
			JFrame frame = new JFrame("BubbleSort Visualizer");
			frame.setResizable(false);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setContentPane(new SelectionSort());
			frame.validate();
			frame.pack();
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		});
	}
}

