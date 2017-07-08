


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class keyboard implements KeyListener {

	private boolean[] keys = new boolean[120];
	public static boolean l, up, down, left, right, escape, m, one, two, three, four,
			five, six, seven, w, s, a, d, space, t, r, c, enter;

	public void update() {
		l = keys[KeyEvent.VK_L];
		up = keys[KeyEvent.VK_UP];
		down = keys[KeyEvent.VK_DOWN];
		left = keys[KeyEvent.VK_LEFT];
		right = keys[KeyEvent.VK_RIGHT];
		w = keys[KeyEvent.VK_W];
		s = keys[KeyEvent.VK_S];
		a = keys[KeyEvent.VK_A];
		d = keys[KeyEvent.VK_D];
		m = keys[KeyEvent.VK_M];
		escape = keys[KeyEvent.VK_ESCAPE];
		one = keys[KeyEvent.VK_1];
		two = keys[KeyEvent.VK_2];
		three = keys[KeyEvent.VK_3];
		four = keys[KeyEvent.VK_4];
		five = keys[KeyEvent.VK_5];
		six = keys[KeyEvent.VK_6];
		seven = keys[KeyEvent.VK_7];
		space = keys[KeyEvent.VK_SPACE];
		t = keys[KeyEvent.VK_T];
		r = keys[KeyEvent.VK_R];
		c = keys[KeyEvent.VK_C];
		enter = keys[KeyEvent.VK_ENTER];
	}

	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}

	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}

	public void keyTyped(KeyEvent e) {

	}

}
