package org.apogames.help;

import java.awt.AWTException;
import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class ApoSplashScreen implements ActionListener {
	// desktop
	private final BufferedImage background;

	// Your logo
	private final BufferedImage image;

	// actual image
	private BufferedImage currentImage;

	private ApoSplashPainter label;

	private final int speed = 1000 / 50;

	/**
	 * Duration in Time Mills
	 */
	private float duration = 1000.0f;

	private long startTime = 0;

	private boolean isBlendIn = true;

	private final Timer timer;

	public ApoSplashScreen(String path) throws IOException,
			AWTException {
		final URL url = this.getClass().getClassLoader().getResource(path);
		this.image = ImageIO.read(url);

		this.currentImage = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(this.image.getWidth(), this.image.getHeight());

		final Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
		final int x = (int) (screenDimension.getWidth() / 2 - this.image.getWidth() / 2);
		final int y = (int) (screenDimension.getHeight() / 2 - this.image.getHeight() / 2);
		final int w = this.image.getWidth();
		final int h = this.image.getHeight();

		final Robot robot = new Robot();
		final Rectangle rectangle = new Rectangle(x, y, w, h);
		this.background = robot.createScreenCapture(rectangle);
		drawImage(0f);

		this.label = new ApoSplashPainter();
		this.label.setImage(this.background);
		final JWindow f = new JWindow(new JFrame());
		f.getContentPane().add(this.label);
		f.pack();
		f.setLocation(x, y);

		this.timer = new Timer(this.speed, this);
		this.timer.setCoalesce(true);
		this.timer.start();
		this.startTime = System.currentTimeMillis();
		f.setVisible(true);
	}

	public void blendOut() {
		this.isBlendIn = false;
		this.startTime = System.currentTimeMillis();
		this.timer.start();
	}

	public void actionPerformed(ActionEvent e) {
		float percent;

		if (this.isBlendIn) {
			percent = (System.currentTimeMillis() - this.startTime) / duration;
			percent = Math.min(1.0f, percent);
		} else {
			percent = (System.currentTimeMillis() - this.startTime) / duration;
			percent = Math.min(1.0f, percent);
			percent = 1.0f - percent;
		}

		float alphaValue = percent;

		if (percent >= 1.0) {
			this.timer.stop();
			// blendOut(); // Einkommentieren damit die animation
			// sofort wieder
			// ausgeblendet wird
		} else if (alphaValue <= 0.0f) {
			this.timer.stop();
			SwingUtilities.getWindowAncestor(label).dispose();
		}

		drawImage(alphaValue);
		this.label.setImage(currentImage);
		this.label.repaint();
	}

	/**
	 * Draws Background, then draws image over it
	 * 
	 * @param alphaValue
	 */
	private void drawImage(float alphaValue) {
		final Graphics2D g2d = (Graphics2D) currentImage.getGraphics();
		g2d.drawImage(background, 0, 0, null);
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				alphaValue));
		g2d.drawImage(image, 0, 0, null);
		g2d.dispose();
	}
}