
public class Quaternion {
	
	double a;
	double b;
	double c;
	double d;
	
	public Quaternion() {
		a=b=c=d=1;
	}

	public Quaternion(Quaternion q){
		a=q.a;
		b=q.b;
		c=q.c;
		d=q.d;
	}
	
	public Quaternion(double a, double b, double c, double d) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
	}
	
	public Quaternion(double a,Vector3D v){
		this.a=a;
		b=v.x;
		c=v.y;
		d=v.z;
	}
	
	public static Quaternion rotationQuaternion(double theta,double x,double y,double z){
		Quaternion rtrn = new Quaternion();
		rtrn.a = Math.cos(theta/2);
		rtrn.b = x* Math.sin(theta/2);
		rtrn.c = y* Math.sin(theta/2);
		rtrn.d = z* Math.sin(theta/2);
		return rtrn.unitQuaternion();
	}
	
	public static Quaternion rotationQuaternion(double theta,Vector3D in){
		return rotationQuaternion(theta, in.x,in.y,in.z);
	}
	
	public Vector3D toVector(){
		return new Vector3D(b,c,d);
	}
	
	public Quaternion add(Quaternion q){
		a+=q.a;
		b+=q.b;
		c+=q.c;
		d+=q.d;
		return this;
	}
	
	public Quaternion substract(Quaternion q){
		a-=q.a;
		b-=q.b;
		c-=q.c;
		d-=q.d;
		return this;
	}
	
	public Quaternion multiply(double num){
		a*=num;
		b*=num;
		c*=num;
		d*=num;
		return this;
	}
	
	public Quaternion multiply(Quaternion q){
		double na = a * q.a - b * q.b - c * q.c - d * q.d;
        double nb = a * q.b + b * q.a + c * q.d - d * q.c;
        double nc = a * q.c - b * q.d + c * q.a + d * q.b;
        double nd = a * q.d + b * q.c - c * q.b + d * q.a;
        a=na;b=nb;c=nc;d=nd;
		return this;
	}
	
	public Quaternion divide(double num){
		a/=num;
		b/=num;
		c/=num;
		d/=num;
		return this;
	}
	
	public Quaternion divide(Quaternion q){
		multiply(new Quaternion(q).inverse());
		return this;
	}
	
	public Quaternion inverse(){
		Quaternion rtrn = new Quaternion(this);
		rtrn.b*=-1;
		rtrn.c*=-1;
		rtrn.d*=-1;
		rtrn.divide(Math.pow(magnitude(),2));
		return rtrn;
	}
	
	public Quaternion conjugate(){
		b*=-1;
		c*=-1;
		d*=-1;
		return this;
	}
	
	public Quaternion unitQuaternion(){
		return new Quaternion(this).divide(magnitude());
	}
	
	public double magnitude(){
		return Math.sqrt(Math.pow(a,2)+Math.pow(b,2)+Math.pow(c,2)+Math.pow(d,2));
	}
	
	public Vector3D rotateVector(Vector3D v){
		Quaternion q = new Quaternion(this);
		Quaternion conj = new Quaternion(this).conjugate();
		q = q.multiply(new Quaternion(0,v).multiply(conj));
		return q.toVector();
	}

	@Override
	public String toString() {
		return "Quaternion [a=" + a + ", b=" + b + ", c=" + c + ", d=" + d
				+ "]";
	}
}
