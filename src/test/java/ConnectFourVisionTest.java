import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class ConnectFourVisionTest {

    private final String TEST_IMAGE = "connect_four.jpg";

    @Test
    public void getMoveForImage() throws IOException, VisionException {
      URL imageURL = ConnectFourVisionTest.class.getResource(TEST_IMAGE);
      BufferedImage image = ImageIO.read(imageURL);
      int bestMove = ConnectFourVision.getMoveForImage(image, false);
      assertEquals("the correct move should be calculated", bestMove, 2);
    }

}
