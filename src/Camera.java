
public class Camera {

	static final double FOV_angle = Math.PI/4;
	
	public Vector3D position;

	public Vector3D angle;
	public Vector3D negAngles;
	public Vector3D rightAngle;
	public Vector3D negRightAngle;
	public Vector3D upAngle;
	public Vector3D negUpAngle;

	public Camera(Vector3D position, Vector3D angle) {
		this.position = position;
		this.angle = angle;
		setOtherAngles();
		System.out.println(this);
	}

	public void setOtherAngles(){
		negAngles = new Vector3D(angle).negate();
		rightAngle = Quaternion.rotationQuaternion(-Math.PI/2,Vector3D.Y_AXIS).rotateVector(angle);
		negRightAngle = new Vector3D(rightAngle).negate();
		upAngle = new Vector3D(rightAngle).crossProduct(angle);
		negUpAngle = new Vector3D(upAngle).negate();
	}

	public Vector3D getCameraCoords(Vector3D in){
		Vector3D rtrn = new Vector3D(in);
		rtrn.subtract(position);
		Vector3D crs = new Vector3D(Vector3D.Z_AXIS).crossProduct(angle).normalize();
		double ang = -angle.angleWith(Vector3D.Z_AXIS);
		return Quaternion.rotationQuaternion(ang,crs).rotateVector(rtrn);
	}

	public void normalize() {
		angle.normalize();
		negAngles.normalize();
		rightAngle.normalize();
		negRightAngle.normalize();
		upAngle.normalize();
		negUpAngle.normalize();
	}
	
	@Override
	public String toString() {
		return "Camera [position=" + position + ", angle=" + angle
				+ ", negAngles=" + negAngles + ", rightAngle=" + rightAngle
				+ ", negRightAngle=" + negRightAngle + "]";
	}

}
