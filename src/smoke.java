import java.awt.Graphics2D;

import javax.swing.ImageIcon;


public class smoke 
{
	private ImageIcon m_sprite;
	private int m_x, m_y;
	
	public smoke(int x, int y)
	{
		m_x = x;
		m_y = y;
		m_sprite = new ImageIcon(getClass().getResource("smoke.png"));
	}
	
	public void Update(int gameSpeed)
	{
		m_x -= gameSpeed * 3;
	}
	
	public void Render(Graphics2D g)
	{
		g.drawImage(m_sprite.getImage(), m_x, m_y, null);
	}
	
	public int GetX() {
		return m_x;
	}
	
	public int GetWidth()
	{
		return m_sprite.getIconWidth();
	}

}
