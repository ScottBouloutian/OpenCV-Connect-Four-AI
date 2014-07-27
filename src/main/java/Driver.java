
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.imageio.ImageIO;

import org.opencv.core.Core;

public class Driver {

	static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }

	public static void main(String[] args) {
		if (args.length == 1) {
			System.out.println("Starting Connect Four Analysis");
			try {
				BufferedImage image = ImageIO.read(new File(args[0]));
				int bestMove = ConnectFourVision.getMoveForImage(image, true);
				System.out.println("<BEST_MOVE>" + bestMove + "</BEST_MOVE>");
			} catch (IOException e) {
				e.printStackTrace();
			} catch (VisionException e) {
				e.printStackTrace();
			}
			System.out.println("Done");
		}
	}

}
