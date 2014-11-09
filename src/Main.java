import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.Timer;

public class Main extends JFrame implements KeyListener,ActionListener{

	private static final long serialVersionUID = 3206847208968227199L;

	static final int NUM_OBJECTS = 100;
	static final int[] screenDims = {700,700};
	static boolean initialized = false;
	static Square[] objects = new Square[NUM_OBJECTS];
	static ArrayList<Point[]> renderPoints = new ArrayList<Point[]>();
	static Camera cam;

	public static void main(String[] args){
//		int offset = -1;
//		int root = (int) Math.sqrt(NUM_OBJECTS);
//		for (int i = 0; i < root; i++) {
//			for (int j = 0; j < root; j++) {
//			objects[i*root+j]=new Square('x',offset+i*2,offset+j*2);
//			}
//		}	
//		for (int i = 0; i < root; i++) {
//			for (int j = 0; j < root; j++) {
//			objects[100+i*root+j]=new Square('x',offset+i*2,offset+j*2);
//			}
//		}	
		
//		cam = new Camera(new Vector3D(0,0,0),new Vector3D(-1,0,0));
//		System.out.println(cam.getCameraCoords(new Vector3D(-3,0,0)));
		
//		objects[0] = new Square(new Vector3D[]{new Vector3D(2,0,1),new Vector3D(1, 0,2),new Vector3D(1,1,2),new Vector3D(2,1,1)});
//		objects[1] = new Square(new Vector3D[]{new Vector3D(12,1,-1),new Vector3D(12,1,1),new Vector3D(12,-1,1),new Vector3D(12,-1,-1)});
		new Main();
	}

	public Main() {
		super("Class Paint");
		Timer t = new Timer(10,this);
		cam = new Camera(new Vector3D(0,0,0),new Vector3D(1,0,0));
		addKeyListener(this);
		setSize(screenDims[0],screenDims[1]);    
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setFocusable( true );
		setVisible(true);
		t.start();
	}

	public static Point getPaneCoords(Vector3D in){
		Point rtrn = new Point();
		if(in.z<0){
			return null;
		}
		double xs = in.x/(in.z*Math.tan(Camera.FOV_angle)+1)*screenDims[0];
		double ys = in.y/(in.z*Math.tan(Camera.FOV_angle)+1)*screenDims[1];
		rtrn.setLocation(Math.round(xs)*-1,Math.round(ys)*-1);
		return rtrn;
	}

	public void paint(Graphics g) {
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

	public void generateRenderPoints(){
		for (int i = 0; i < objects.length; i++) {
			Point[] points = new Point[4];
			for (int j = 0; j < points.length; j++) {
				Vector3D camCoords = cam.getCameraCoords(objects[i].points[j]);
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

	public void processMove(KeyEvent e){

		switch(e.getKeyCode()){
		case KeyEvent.VK_UP: cam.position.add(cam.angle); break;
		case KeyEvent.VK_DOWN: cam.position.add(cam.negAngles); break;
		case KeyEvent.VK_RIGHT: cam.position.add(cam.rightAngle); break;
		case KeyEvent.VK_LEFT: cam.position.add(cam.negRightAngle); break;
		case KeyEvent.VK_P: cam.position.add(new Vector3D(0,1,0)); break;
		case KeyEvent.VK_H: cam.angle = new Vector3D(1,0,0); break;
		case KeyEvent.VK_L: cam.position.add(new Vector3D(0,-1,0)); break;
		case KeyEvent.VK_Q: cam.angle = Quaternion.rotationQuaternion(Math.PI/32,Vector3D.Y_AXIS).rotateVector(cam.angle); break;
		case KeyEvent.VK_E: cam.angle = Quaternion.rotationQuaternion(-Math.PI/32,Vector3D.Y_AXIS).rotateVector(cam.angle); break;
		}
		cam.setOtherAngles();
		System.out.println(cam);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		renderPoints.clear();
		generateRenderPoints();
		repaint();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyPressed(KeyEvent e) {
		processMove(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	} 
}