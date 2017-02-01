package Image.ImageLoader.PGMLoader;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.IOException;

import Image.Image;
import Image.ImageLoader.ImageLoader;

public class PGMLoader implements ImageLoader {

    @Override
    public String getImageExtensions() {
        // TODO Auto-generated method stub
        return "pgm";
    }

    @Override
    public Image loadImage(String filename) {
        PGMFileIO pgm = new PGMFileIO(filename);
        try {
            pgm.readPGM();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        int[] data = new int[pgm.getData().length];
        for (int i = 0; i < data.length; i++) {
            data[i] = (int) pgm.getData()[i];
        }
        return new Image(pgm.getSizeX(), pgm.getSizeY(), data, this.getImageExtensions());
    }

    @Override
    public boolean saveImage(Image image, String filename) {
        PGMFileIO pgm = new PGMFileIO(filename);
        short[] data = new short[image.getSizeData()];
        for (int i = 0; i < data.length; i++) {
            data[i] = (short) image.getData()[i];
        }
        try {
            pgm.writePGM(image.getSizeX(), image.getSizeY(), data);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Color getPixelColor(Image image, int pixelData) {
        return new Color(pixelData & 255, pixelData & 255, pixelData & 255);
    }
    
    @Override
    public int setPixelColor(Image image, Color color) {
        return (color.getRed()+color.getGreen()+color.getBlue())/3 & 255;
    }

}
