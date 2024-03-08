package apoSoccer;

import java.awt.Color;
import java.awt.image.ColorModel;
import java.awt.image.ImageConsumer;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;

/**
 * Eine Klasse, um ein Bild mit einer anderen Farbe zu überdecken
 * @author Dirk Aporius
 *
 */
public class ApoDynamicFilter extends ImageFilter {
	
	private Color overlapColor;
	private int imageWidth;
	private int imageHeight;
	private int iterations;

	public ApoDynamicFilter(int iterations, Color color) {
		this.iterations = iterations;
		this.overlapColor = color;
	}

	public void setDimensions(int width, int height) {
		this.imageWidth = width;
		this.imageHeight = height;
		this.consumer.setDimensions(width, height);
	}

	public void setHints(int hints) {
		this.consumer.setHints(ImageConsumer.RANDOMPIXELORDER);
	}

	public void resendTopDownLeftRight(ImageProducer ip) {
	}

	public void imageComplete(int status) {
		if ((status == IMAGEERROR) || (status == IMAGEABORTED)) {
			this.consumer.imageComplete(status);
			return;
		} else {
			int xWidth = this.imageWidth / this.iterations;
			if (xWidth <= 0)
				xWidth = 1;
			int newPixels[] = new int[xWidth * this.imageHeight];
			int iColor = this.overlapColor.getRGB();
			for (int x = 0; x < (xWidth * this.imageHeight); x++)
				newPixels[x] = iColor;
			int t = 0;
			for (; t < (this.imageWidth - xWidth); t += xWidth) {
				this.consumer.setPixels(t, 0, xWidth, this.imageHeight, ColorModel.getRGBdefault(), newPixels, 0, xWidth);
				this.consumer.imageComplete(ImageConsumer.SINGLEFRAMEDONE);
			}
			int left = this.imageWidth - t;
			if (left > 0) {
				this.consumer.setPixels(this.imageWidth - left, 0, left, this.imageHeight, ColorModel.getRGBdefault(), newPixels, 0, xWidth);
				this.consumer.imageComplete(ImageConsumer.SINGLEFRAMEDONE);
			}
			this.consumer.imageComplete(STATICIMAGEDONE);
		}
	}
}
