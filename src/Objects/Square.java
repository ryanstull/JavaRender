package Objects;
import Engine.Vector3D;


public class Square extends GameObject {

public Vector3D[] points = new Vector3D[4];
	
	public Square(Vector3D[] points) {
		this.points = points;
	}
	
	public Square(char axis,int yoffset,int zoffset){
		switch(axis){
			case 'x': points = new Vector3D[]{new Vector3D(10,1+yoffset,-1+zoffset),new Vector3D(10,1+yoffset,1+zoffset),new Vector3D(10,-1+yoffset,1+zoffset),new Vector3D(10,-1+yoffset,-1+zoffset)}; break;
			case 'z': points = new Vector3D[]{new Vector3D(1+yoffset,-1+zoffset,10),new Vector3D(1+yoffset,1+zoffset,10),new Vector3D(-1+yoffset,1+zoffset,10),new Vector3D(-1+yoffset,-1+zoffset,10)}; break;
		}
		
	}
}
