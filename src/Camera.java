
public class Camera {

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
	}

	private void setOtherAngles(){
		negAngles = new Vector3D(angle).negate();
		rightAngle = new Vector3D(angle).rotateAboutAxis(-Math.PI/2,new Vector3D(0,1,0));
		negRightAngle = new Vector3D(rightAngle).negate();
		upAngle = new Vector3D(rightAngle).crossProduct(angle);
		negUpAngle = new Vector3D(upAngle).negate();
	}

	public Vector3D getCameraCoords(Vector3D in){
		Vector3D rtrn = new Vector3D(in);
//		double d = rtrn.magnitude();
//		double[] camAngles = angle.angles();
//		double[] angles = rtrn.angles();
		rtrn.subtract(position);
		Vector3D crs = new Vector3D(Vector3D.Z_AXIS).crossProduct(angle);
		double ang = -angle.angleWith(Vector3D.Z_AXIS);
		Quaternion q = Quaternion.rotationQuaternion(ang,crs);
//		rtrn.x = d * Math.sin(-camAngles[0]+angles[0]) * Math.cos(-camAngles[1]+angles[1]);
//		rtrn.y = d * Math.sin(-camAngles[1]+angles[1]);
//		rtrn.z = d * Math.cos(-camAngles[0]+angles[0]) * Math.cos(-camAngles[1]+angles[1]);
		
		return q.rotateVector(rtrn);
	}

	public void rotate(double theta,Vector3D axis){
		angle.rotateAboutAxis(theta, axis);
		setOtherAngles();
	}

	@Override
	public String toString() {
		return "Camera [position=" + position + ", angle=" + angle
				+ ", negAngles=" + negAngles + ", rightAngle=" + rightAngle
				+ ", negRightAngle=" + negRightAngle + ", upAngle=" + upAngle
				+ ", negUpAngle=" + negUpAngle + "]";
	}
}
