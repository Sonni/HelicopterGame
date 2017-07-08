
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class mouse implements MouseListener, MouseMotionListener {

	public static int mouseX;
	public static int mouseY;
	public static int startX;
	public static int startY;
	public static int xOnScreen;
	public static int yOnScreen;
	public static int mouseB = -1;
	public static boolean drag = false;
	public static boolean click = false;
	public static int grabed = -1;
	
	
	public void mouseDragged(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		xOnScreen = e.getXOnScreen();
		yOnScreen = e.getYOnScreen();
		drag = true;
		click = false;
	}

	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		click = false;
	}

	public void mouseClicked(MouseEvent e) {
		click = true;
	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {
		mouseB = e.getButton();
		startX = e.getX();
		startY = e.getY();
		
	}

	public void mouseReleased(MouseEvent e) {
		mouseB = -1;
		drag = false;
		grabed = -1;
	}

}
