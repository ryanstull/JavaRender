package Engine;
import Objects.Square;



public class Main{

	static final int NUM_OBJECTS = 10000;
	static Square[] objects = new Square[NUM_OBJECTS];
	static Camera cam;
	static Window wind;
	static boolean running = true;
	
	public static void main(String[] args){
		int offset = -50;
		int root = (int) Math.sqrt(NUM_OBJECTS);
		for (int i = 0; i < root; i++) {
			for (int j = 0; j < root; j++) {
			objects[i*root+j]=new Square('x',offset+i*2,offset+j*2);
			}
		}
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
		cam = new Camera(new Vector3D(0,0,0),new Vector3D(1,0,0));
		wind = new Window(new int[]{1000,1000},false);
	}

}