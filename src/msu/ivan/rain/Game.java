package msu.ivan.rain;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import msu.ivan.rain.entity.mob.Player;
import msu.ivan.rain.graphics.Screen;
import msu.ivan.rain.graphics.Sprite;
import msu.ivan.rain.graphics.SpriteSheet;
import msu.ivan.rain.input.Keyboard;
import msu.ivan.rain.input.Mouse;
import msu.ivan.rain.level.Level;
import msu.ivan.rain.level.TileCoordinate;

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1l;

	private static int width = 300;
	private static int height = width / 16 * 9;
	private static int scale = 3;

	private Thread thread;
	private JFrame frame;
	private boolean running = false;


	private Keyboard keyboard;
	private Mouse mouse;
	private Player player;

	private Screen screen;
	private Level level;
	private BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) (bufferedImage.getRaster().getDataBuffer())).getData(); // for raster

	public Game() {
		setPreferredSize(new Dimension(width * scale, height * scale));

		screen = new Screen(width, height);
		frame = new JFrame();
		keyboard = new Keyboard();
		level = Level.spawn;
		TileCoordinate playerSpawn = new TileCoordinate(19, 62);
		player = new Player(playerSpawn.getX(), playerSpawn.getY(), keyboard);
		player.setLevel(level);
//		IMP
		addKeyListener(keyboard);
		mouse = new Mouse();
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
	}

	public static int getWindowWidth() {
		return width * scale;
	}

	public static int getWindowHeight() {
		return height * scale;
	}

	public synchronized void start() {
		if (running) return;
		running = true;
		thread = new Thread(this, "threadInGame");
		thread.start();
	}

	public synchronized void stop() {
		if (!running) return;
		running = false;
		try {
			thread.join();
		} catch (Exception ex) {
			System.err.println("com.ivan.rain::Game::stop");
			ex.printStackTrace();
		}
	}

	public void run() {
		int fps = 0, ups = 0;
		long lastTime = System.nanoTime();
		long curTime = System.currentTimeMillis();
		final double nenoSec = Math.pow(10, 9) / 60.0;
		double delta = 0;

		requestFocus();
		while (running) {
			delta += (System.nanoTime() - lastTime) / nenoSec;
			lastTime = System.nanoTime();
			while (delta >= 1) {
				update();
				delta--;
				ups++;
			}
			render();
			fps++;
			if (System.currentTimeMillis() - curTime > 1000) {
				curTime += 1000;
				frame.setTitle(String.format("Rain ||%5d fps,%5d ups", fps, ups));
				fps = 0;
				ups = 0;
			}
		}
		stop();
	}

//	change = 60 times per second to be run at maximum
	public void update() {
		keyboard.update();
		player.update();
		level.update();
	}

//	change = unlimited times per second
	public void render() {
		BufferStrategy bufferStrategy = getBufferStrategy();
		if (bufferStrategy == null) {
			createBufferStrategy(3);
			return;
		}

		screen.clear();
		int xScroll = player.x - screen.width / 2;
		int yScroll = player.y - screen.height / 2;
		level.render(xScroll, yScroll, screen);
		player.render(screen);
		screen.renderSpriteSheet(40, 40, SpriteSheet.player_down, false);

//		Sprite sprite = new Sprite(40, height, 0xff0000);
//		screen.renderSprite(width - 40, 0, sprite, false);
		for (int i = 0; i < screen.pixels.length; ++i) {
			pixels[i] = screen.pixels[i];
		}

		Graphics graphics = bufferStrategy.getDrawGraphics();
		graphics.drawImage(bufferedImage, 0, 0, getWidth(), getHeight(), null);
		graphics.setColor(Color.white);
		graphics.setFont(new Font("Consolas", Font.BOLD, 30));
//		if (Mouse.getMouseButton() != -1)
//			graphics.drawString(String.format("x: %d, y : %d", Mouse.getMouseX(), Mouse.getMouseY()), 90, 90);
		graphics.dispose();
		bufferStrategy.show();
	}

	public static void main(String[] args) {
		Game game = new Game();
		game.frame.setResizable(false);
		game.frame.setTitle("Rain");
		game.frame.add(game);
		game.frame.pack();
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null);
		game.frame.setVisible(true);
		game.start();
	}
}
