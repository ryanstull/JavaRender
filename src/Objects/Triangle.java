package Objects;
import Engine.Vector3D;




public class Triangle extends GameObject {

	public Vector3D[] points = new Vector3D[3];
	
	public Triangle(Vector3D[] points) {
		this.points = points;
	}
}
