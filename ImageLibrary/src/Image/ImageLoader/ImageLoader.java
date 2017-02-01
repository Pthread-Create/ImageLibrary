package Image.ImageLoader;
import java.awt.Color;

import Image.Image;

public interface ImageLoader {
    public String getImageExtensions();
    public Image loadImage(String filename);
    public boolean saveImage(Image image, String filename);
    public Color getPixelColor(Image image, int pixelData);
    int setPixelColor(Image image, Color color);
}
