package Engine;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class KeyHandler implements KeyListener{
	
	public KeyHandler(){}
	
	public void processMove(KeyEvent e){

		switch(e.getKeyCode()){
		case KeyEvent.VK_UP: Main.cam.position.add(Main.cam.angle); break;
		case KeyEvent.VK_DOWN: Main.cam.position.add(Main.cam.negAngles); break;
		case KeyEvent.VK_RIGHT: Main.cam.position.add(Main.cam.rightAngle); break;
		case KeyEvent.VK_LEFT: Main.cam.position.add(Main.cam.negRightAngle); break;
		case KeyEvent.VK_P: Main.cam.position.add(new Vector3D(0,1,0)); break;
		case KeyEvent.VK_H: Main.cam.angle = new Vector3D(1,0,0); break;
		case KeyEvent.VK_L: Main.cam.position.add(new Vector3D(0,-1,0)); break;
		case KeyEvent.VK_Q: Main.cam.angle = Quaternion.rotationQuaternion(-Math.PI/32,Vector3D.Y_AXIS).rotateVector(Main.cam.angle); break;
		case KeyEvent.VK_E: Main.cam.angle = Quaternion.rotationQuaternion(Math.PI/32,Vector3D.Y_AXIS).rotateVector(Main.cam.angle); break;
		}
		Main.cam.setOtherAngles();
		System.out.println(Main.cam);
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
