package Engine;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JFrame;

public class Window extends JFrame implements Runnable{

	private static final long serialVersionUID = 1909974731597801896L;
	Thread t;
	int[] screenDims;
	boolean initialized = false;
	ArrayList<Point[]> renderPoints = new ArrayList<Point[]>();
	BufferedImage im1;
	BufferedImage im2;
	BufferedImage im;
	boolean running = true;

	public Window(int[] screenDims, boolean initialized) {
		this.screenDims = screenDims;
		this.initialized = initialized;
		renderPoints = new ArrayList<Point[]>();
		im1 = new BufferedImage(screenDims[0],screenDims[1],BufferedImage.TYPE_3BYTE_BGR);
		im2 = new BufferedImage(screenDims[0],screenDims[1],BufferedImage.TYPE_3BYTE_BGR);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addKeyListener(new KeyHandler());
		setSize(screenDims[0],screenDims[1]);
		setFocusable( true );
		setVisible(true);
		t= new Thread(this);
		t.start();
	}

	public Point getPaneCoords(Vector3D in){
		Point rtrn = new Point();
		if(in.z<=0){
			return null;
		}
		double xs = in.x/(in.z*Math.tan(Camera.FOV_angle)+1)*screenDims[0];
		double ys = in.y/(in.z*Math.tan(Camera.FOV_angle)+1)*screenDims[1];
		rtrn.setLocation(Math.round(xs)*-1,Math.round(ys)*-1);
		return rtrn;
	}

	public void generateRenderPoints(){
		for (int i = 0; i < Main.objects.length; i++) {
			Point[] points = new Point[4];
			for (int j = 0; j < points.length; j++) {
				Vector3D camCoords = Main.cam.getCameraCoords(Main.objects[i].points[j]);
				Point panCoords = getPaneCoords(camCoords);
				if(panCoords==null){
					continue;
				}
				points[j]=panCoords;
				if(j==points.length-1){
					renderPoints.add(points);
				}
			}
		}
		initialized = true;
	}

	public void drawImage(Graphics g){
		int numPoints = 4;
		if(initialized){
			int[][] xPoints = new int[renderPoints.size()][numPoints];
			int[][] yPoints = new int[renderPoints.size()][numPoints];
			for (int i = 0; i < renderPoints.size(); i++) {
				for (int j = 0; j < numPoints; j++) {
					xPoints[i][j] = renderPoints.get(i)[j].x+screenDims[0]/2;
					yPoints[i][j] = renderPoints.get(i)[j].y+screenDims[1]/2;
				}
			}
			g.clearRect(0, 0, screenDims[0],screenDims[1]);
			for (int i = 0; i < renderPoints.size(); i++) {
				g.drawPolygon(xPoints[i], yPoints[i], numPoints);
			}
		}
	}

	@Override
	public void paint(Graphics g) {
		if(initialized)
			g.drawImage(im, 0, 0, this);
	};

	@Override
	public void run() {
		Graphics g1 = im1.getGraphics();
		Graphics g2 = im2.getGraphics();
		Graphics g = g1;
		im = im1;
		while(running){
			renderPoints.clear();
			generateRenderPoints();
			drawImage(g);
			repaint();
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			g = g==g1 ? g2 : g1;
			im = im==im1 ? im2 : im1;
		}
	}
}
