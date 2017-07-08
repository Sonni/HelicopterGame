import java.awt.Graphics2D;

import javax.swing.ImageIcon;


public class player 
{
	
	private int m_x, m_y;
	private ImageIcon m_sprite;
	private int m_gravity = 1;
	
	public player(int x, int y)
	{
		m_x = x;
		m_y = y;
		
		m_sprite = new ImageIcon(getClass().getResource("copter2.png"));
	}
	
	public void Render(Graphics2D g)
	{
		g.drawImage(m_sprite.getImage(), m_x, m_y, null);
	}
	
	public void Update()
	{
		m_y += m_gravity;
	}

	public int GetX() {
		return m_x;
	}

	public void SetX(int x) {
		m_x = x;
	}

	public int GetY() {
		return m_y;
	}

	public void SetY(int y) {
		m_y = y;
	}
	
	
	public int GetWidth()
	{
		return m_sprite.getIconWidth();
	}
	
	public int GetHeight()
	{
		return m_sprite.getIconHeight();
	}
	
	public void SetGravity(int gravity)
	{
		m_gravity = gravity;
	}
	

}
