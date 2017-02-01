package Image.ImageOperation;

import java.awt.Color;

import Image.Image;

public class NegativeOperation implements Image1POperation {

    @Override
    public Image operation(Image picture) {
    	Image operationResult = new Image(picture.getSizeX(), picture.getSizeY(), picture.getType());

        for (int i = 0; i < picture.getSizeData(); i++) {
            Color c = picture.getPixel(i);
            operationResult.setPixel(i, new Color(255-c.getRed(),255-c.getGreen(),255-c.getBlue()));
        }
        
        return operationResult;
    }

}
