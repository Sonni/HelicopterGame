import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferStrategy;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;


public class main extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;
	public static int WINDOW_WIDTH = 555;
	public static int WINDOW_HEIGHT = 405;
	private String WINDOW_TITLE = "Helicopter";
	

	private Thread thread;
	private JFrame frame;
	private boolean running = false;

	private mouse Mouse;
	private keyboard Keyboard;


	private List<box> upBoxs = new ArrayList<>();
	private List<box> downBoxs = new ArrayList<>();
	private List<box> randBoxs = new ArrayList<>();
	
	private List<smoke> smokes = new ArrayList<>();
	
	private player Player;

	private Random rand = new Random();
	private int gameSpeed = 1;
	
	private float distance = 0;
	private int best;
	
	private Clip clip;
	
	public main() 
	{
		Dimension size = new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT);
		setPreferredSize(size);
		
		frame = new JFrame();
		Mouse = new mouse();
		Keyboard = new keyboard();

		try {
			clip = AudioSystem.getClip();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			Clip mainMusic = AudioSystem.getClip();
			AudioInputStream inputStream;
			try {
				inputStream = AudioSystem.getAudioInputStream(
						getClass().getResource("background.wav"));
				mainMusic.open(inputStream);
				mainMusic.start();
			} catch (UnsupportedAudioFileException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		addMouseListener(Mouse);
		addMouseMotionListener(Mouse);
		addKeyListener(Keyboard);
		
		Player = new player(70, 130);
		
		best = loadBest("best.txt");

		for (int i = 0; i < 16; i++)
		{
			int  n = rand.nextInt(5) + 1;
			upBoxs.add(new box(40 * i, 0, 40, 10 + n));
		}
		
		for (int i = 0; i < 16; i++)
		{
			int  n = rand.nextInt(5) + 1;
			downBoxs.add(new box(40 * i, WINDOW_HEIGHT - 5 * (10 + n), 40, 10 + n));
		}
		
		for (int i = 0; i < 5; i++)
		{
			int x = rand.nextInt(100) + 50;
			int y = rand.nextInt(200);
			y -= 100;
			randBoxs.add(new box(WINDOW_WIDTH + i * x, WINDOW_HEIGHT / 2 - 25/2 + y, 25, 10));
		}
		
	}
	
	private void start() {
		running = true;
		thread = new Thread(this, "Display");
		thread.start();
	}
	
	private void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

	private int tmpSeconds = 0;
	private boolean gameStarted = false;

	public void run() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		double delta = 0;
		requestFocus();

		//Standard game loop
		while (running) {
			long now = System.nanoTime();
			double ns = 1000000000.0 / 60.0;
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1) {
				update();
				delta--;
				render();
			}

			if(System.currentTimeMillis() - timer > 1000) {

				if (gameStarted) {
					tmpSeconds++;
					if (tmpSeconds > 3) {
						if (gameSpeed < 5) gameSpeed += 1;

						tmpSeconds = 0;
					}
				}

				timer += 1000;
			}
		}
		stop();
	}


	 
	private int gravity = 2;
	private int tmpCounter = 0;
	
	private void update()
	{
		if (gameStarted) {
			tmpCounter++;
			if (tmpCounter > 5) {
				smokes.add(new smoke(Player.GetX(), Player.GetY()));
				tmpCounter = 0;
			}

			distance += 0.2f * gameSpeed;

			Keyboard.update();

			if (Keyboard.one) gravity = 1;
			if (Keyboard.two) gravity = 2;
			if (Keyboard.three) gravity = 3;
			if (Keyboard.four) gravity = 4;
			if (Keyboard.enter) end();

			Player.SetGravity(gravity);
			if (Keyboard.space) {
				Player.SetGravity(-gravity);
				if (!clip.isActive()) {
					clip.close();
					try {

						AudioInputStream inputStream = AudioSystem.getAudioInputStream(
								getClass().getResource("fly.wav"));
						clip.open(inputStream);
						clip.start();
					} catch (Exception e) {
						System.err.println(e.getMessage());
					}
				}

			}

			for (int i = 0; i < upBoxs.size(); i++) {
				upBoxs.get(i).Update(gameSpeed);
				if (upBoxs.get(i).GetX() + upBoxs.get(i).GetWidth() < 0) {
					upBoxs.remove(i);
					int n = rand.nextInt(5) + 1;
					upBoxs.add(new box(WINDOW_WIDTH, 0, 45, 10 + n));

				}
			}

			for (int i = 0; i < downBoxs.size(); i++) {
				downBoxs.get(i).Update(gameSpeed);
				if (downBoxs.get(i).GetX() + downBoxs.get(i).GetWidth() < 0) {
					downBoxs.remove(i);
					int n = rand.nextInt(5) + 1;
					downBoxs.add(new box(WINDOW_WIDTH, WINDOW_HEIGHT - 5 * (10 + n), 45, 10 + n));

				}
			}

			for (int i = 0; i < randBoxs.size(); i++) {
				randBoxs.get(i).Update(gameSpeed);
				if (randBoxs.get(i).GetX() + randBoxs.get(i).GetWidth() < 0) {
					randBoxs.remove(i);
					int x = rand.nextInt(100) + 50;
					int y = rand.nextInt(200);
					y -= 100;
					randBoxs.add(new box(WINDOW_WIDTH + x * i, WINDOW_HEIGHT / 2 - 50 / 2 + y, 25, 10));
				}
			}

			for (int i = 0; i < smokes.size(); i++) {
				smokes.get(i).Update(gameSpeed);
				if (smokes.get(i).GetX() + smokes.get(i).GetWidth() < 0) smokes.remove(i);
			}

			Player.Update();

			for (int i = 0; i < upBoxs.size(); i++) {
				if (Collision(upBoxs.get(i))) end();
			}

			for (int i = 0; i < downBoxs.size(); i++) {
				if (Collision(downBoxs.get(i))) end();
			}

			for (int i = 0; i < randBoxs.size(); i++) {
				if (Collision(randBoxs.get(i))) end();
			}
		} else {
			Keyboard.update();

			if (Keyboard.space) gameStarted = true;
			if (Keyboard.enter) end();
		}
		
	}
	
	private void end()
	{
		clip.stop();
		clip.close(); 
		SetBest("best.txt", (int) distance);
		System.exit(0);
	}


	private void render() {
		//Preparation for rendering
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(2);
			return;
		}
	
	
		Graphics2D g = (Graphics2D) bs.getDrawGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(Color.black);
		g.clearRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
		
		g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
		//Render
		if (gameStarted) {
			for (int i = 0; i < upBoxs.size(); i++) {
				upBoxs.get(i).Render(g);
			}

			for (int i = 0; i < downBoxs.size(); i++) {
				downBoxs.get(i).Render(g);
			}

			for (int i = 0; i < randBoxs.size(); i++) {
				randBoxs.get(i).Render(g);
			}

			for (int i = 0; i < smokes.size(); i++) {
				smokes.get(i).Render(g);
			}

			Player.Render(g);

			g.setColor(Color.BLACK);
			g.drawString("Distance: " + (int) distance, 40, WINDOW_HEIGHT - 20);
			g.drawString("Best: " + best, 300, WINDOW_HEIGHT - 20);
		} else {
			g.setColor(Color.WHITE);
			g.drawString("Press space to start. Hold space to move up. Pres 1, 2, 3, 4 to change falling speed", 40, WINDOW_HEIGHT - 60);
			g.drawString("Press enter or die to exit", 40, WINDOW_HEIGHT - 40);
			g.drawString("Made by Sonni Hedelund Jensen for the 24 hour IT exam at Nordfyns", 40, WINDOW_HEIGHT - 20);

		}
		
		//Cleaning
		g.dispose();
		bs.show();
	}
	
	private boolean Collision(box b)
	{
		if (Player.GetX() + Player.GetWidth() > b.GetX() && Player.GetX() < b.GetX() + b.GetWidth() &&
			Player.GetY() + (Player.GetHeight() / 2) > b.GetY() && 
			Player.GetY() - (Player.GetHeight() / 2) < b.GetY() + b.GetWidth()) return true;
		
		return false;
	}
	
	private int loadBest(String file)
	{
		BufferedReader br;
		String fullText = "";
		try {
			br = new BufferedReader(new InputStreamReader(
                    this.getClass().getResourceAsStream(file)));
			String line;
			while ((line = br.readLine()) != null) {
			   fullText += line;
			}
			
			br.close();
			return Integer.parseInt(fullText);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	    
	private void SetBest(String fileName, int best)
	{
		if (best > loadBest("best.txt"))
		{
			FileWriter fw;
			try {
				fw = new FileWriter("res/best.txt");
				fw.write(Integer.toString(best));
				fw.close();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
	}

	public static void main(String[] args) {
		main Main = new main();
		Main.frame.setResizable(false);
	    Main.frame.setUndecorated(true);
		//Main.frame.setTitle(WINDOW_TITLE);
		Main.frame.add(Main);
		Main.frame.pack();
		Main.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Main.frame.setLocationRelativeTo(null);
		Main.frame.setVisible(true);
		
		Main.start();
	}
}
	
