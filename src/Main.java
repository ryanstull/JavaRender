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

	static final int NUM_OBJECTS = 2;
	static final float FOV_angle = 45;
	static final int[] screenDims = {1000,1000};

	static boolean initialized = false;

	static Triangle[] objects = new Triangle[NUM_OBJECTS];
	static ArrayList<Point[]> renderPoints = new ArrayList<Point[]>();

	Camera cam;

	public static void main(String[] args){
//		renderPoints = new ArrayList<Point[]>();
//		for (int i = 0; i < NUM_OBJECTS; i++) {
//			renderPoints.add(new Point[3]);
//		}
//		objects[0] = new Triangle(new Vector3D[]{new Vector3D(10,1,0),new Vector3D(10, 0, 1),new Vector3D(10, -1, 0)});
//		objects[1] = new Triangle(new Vector3D[]{new Vector3D(12,0,10),new Vector3D(12,-10,0),new Vector3D(12,0,-10)});
//		new Main();
//		
//		Quaternion s = Quaternion.rotationQuaternion(-Math.PI/2,1,0,0);
//		Quaternion k = Quaternion.rotationQuaternion(-Math.PI/2,1,0,0);
//		System.out.printf("%s",z.rotateVector(new Vector3D(0,0,1)));
		
		Camera cam = new Camera(new Vector3D(0,0,0),new Vector3D(1,0,0));
		Vector3D x = new Vector3D(0,0,5);
		System.out.printf("%f ,  %f%n",x.angles()[0],x.angles()[1]);
		System.out.println(cam.getCameraCoords(x));
		
		//		Vector3D x = new Vector3D(1,0,0);
		//		System.out.println(x.rotateBLAH(Math.PI/4));
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

	public Point getPaneCoords(Vector3D in){
		Point rtrn = new Point();
		double xs = in.x/in.z*(1/Math.tan(FOV_angle))*screenDims[0];
		double ys = in.y/in.z*(1/Math.tan(FOV_angle))*screenDims[1];
		rtrn.setLocation(Math.round(xs),Math.round(ys)*-1);
		return rtrn;
	}

	public void paint(Graphics g) {
		if(initialized){
			int[][] xPoints = new int[objects.length][3];
			int[][] yPoints = new int[objects.length][3];
			for (int i = 0; i < objects.length; i++) {
				for (int j = 0; j < 3; j++) {
					xPoints[i][j] = renderPoints.get(i)[j].x+screenDims[0]/2;
					yPoints[i][j] = renderPoints.get(i)[j].y+screenDims[1]/2;
				}
			}
			g.clearRect(0, 0, screenDims[0],screenDims[1]);
			for (int i = 0; i < objects.length; i++) {
				g.drawPolygon(xPoints[i], yPoints[i], 3);
			}
		}
	}

	public void transformPoints(){
		for (int i = 0; i < objects.length; i++) {
			renderPoints.set(i,new Point[3]);
			Vector3D camCoords;
			for (int j = 0; j < objects[i].points.length; j++) {
				camCoords = cam.getCameraCoords(objects[i].points[j]);
				renderPoints.get(i)[j]=getPaneCoords(camCoords);
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
		case KeyEvent.VK_P: cam.position.add(new Vector3D(0,.1,0)); break;
		case KeyEvent.VK_L: cam.position.add(new Vector3D(0,-.1,0)); break;
		case KeyEvent.VK_Q: cam.rotate(Math.PI/50,cam.upAngle); break;
		case KeyEvent.VK_E: cam.rotate(-Math.PI/50,cam.upAngle); break;
		}
		System.out.println(cam);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		transformPoints();
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