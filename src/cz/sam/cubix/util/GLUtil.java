package cz.sam.cubix.util;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;

public class GLUtil {
	
	public static IntBuffer getMouseCursor() throws Exception {
		/*Image img = Toolkit.getDefaultToolkit().getImage(FileLocation.getResourceLocation("gui", "cursor.png"));
		BufferedImage cursor = new BufferedImage(32 ,32, BufferedImage.TYPE_INT_ARGB);
		while(!cursor.createGraphics().drawImage(img, 2, 31, 31, 0, 0, 0, 31, 31, null)) {
			Thread.sleep(5);
		}
		
		int[] data = cursor.getRaster().getPixels(0, 0, 32, 32, (int[])null);
		IntBuffer ib = BufferUtils.createIntBuffer(32 * 32);
		for(int i = 0; i < data.length; i += 4) {
			ib.put(data[i] | data[i + 1] << 8 | data[i + 2] << 16 | data[i + 3] << 32);
		}
		ib.flip();*/
		IntBuffer ib = BufferUtils.createIntBuffer(32 * 32);
		return ib;
	}
	
	public static ByteBuffer[] loadIcon() throws Exception {
		ByteBuffer[] iconList = new ByteBuffer[2];
		iconList[0] = createIconBuffer(ImageIO.read(FileLocation.getFileResourceLocation("icon", "icon_16.png")));
		iconList[1] = createIconBuffer(ImageIO.read(FileLocation.getFileResourceLocation("icon", "icon_32.png")));
		return iconList;
	}
	
	public static ByteBuffer createIconBuffer(BufferedImage image) {
		byte[] buffer = new byte[image.getWidth() * image.getHeight() * 4];
		int counter = 0;
		for(int x = 0; x < image.getHeight(); x++) {
			for(int y = 0; y < image.getWidth(); y++) {
				int pixel = image.getRGB(y, x);
				buffer[counter + 0] = (byte) ((pixel << 8) >> 24);
				buffer[counter + 1] = (byte) ((pixel << 16) >> 24);
				buffer[counter + 2] = (byte) ((pixel << 24) >> 24);
				buffer[counter + 3] = (byte) (pixel >> 24);
				counter += 4;
			}
		}
		return ByteBuffer.wrap(buffer);
	}
	
}
