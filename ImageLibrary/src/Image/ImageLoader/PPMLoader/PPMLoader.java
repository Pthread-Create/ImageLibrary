package Image.ImageLoader.PPMLoader;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.IOException;

import Image.Image;
import Image.ImageLoader.ImageLoader;

public class PPMLoader implements ImageLoader {

    @Override
    public String getImageExtensions() {
        // TODO Auto-generated method stub
        return "ppm";
    }

    @Override
    public Image loadImage(String filename) {
        PPMFileIO ppm = new PPMFileIO(filename);
        try {
            ppm.readPGM();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        int[] data = ppm.getData();
        return new Image(ppm.getSizeX(), ppm.getSizeY(), data, this.getImageExtensions());
    }

    @Override
    public boolean saveImage(Image image, String filename) {
        PPMFileIO ppm = new PPMFileIO(filename);
        try {
            ppm.writePGM(image.getSizeX(), image.getSizeY(), image.getData());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Color getPixelColor(Image image, int pixelData) {
        return new Color((pixelData >> 16) & 255, (pixelData >> 8) & 255, (pixelData) & 255);
    }
    
    @Override
    public int setPixelColor(Image image, Color color) {
        int r = color.getRed() & 255;
        int g = color.getGreen() & 255;
        int b = color.getBlue() & 255;
        r = r << 16;
        g = g << 8;
        return r|g|b;
    }

}
