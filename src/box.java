import java.awt.Color;
import java.awt.Graphics2D;


public class box 
{
	private int m_width, m_height;
	private int m_x, m_y;
	
	public box(int x, int y, int width, int height) 
	{
		m_x = x;
		m_y = y;
		m_width = width;
		m_height = height;
	}
	
	public void Render(Graphics2D g)
	{
		g.setColor(new Color(146, 242, 104));
		g.fillRect(m_x, m_y, m_width, m_height * 5);
		
		g.setColor(new Color(119, 197, 85));
		for (int i = 0; i < m_height + 1; i++)	
		{
			g.fillRect(m_x, m_y + i * 5, m_width, 1);
		}
	}
	
	public void Update(int gameSpeed)
	{
		m_x -= gameSpeed;
	}

	public int GetWidth() {
		return m_width;
	}

	public void SetWidth(int width) {
		m_width = width;
	}

	public int GetHeight() {
		return m_height;
	}

	public void SetHeight(int height) {
		m_height = height;
	}
	
	public int GetX() {
		return m_x;
	}
	
	public int GetY() {
		return m_y;
	}
}
