package Image.ImageOperation;

import java.awt.Color;

import Image.Image;

public class HistogramOperation implements Image1POperation {
	private int[][] resH;
	
    @Override
    public Image operation(Image picture) {
    	Image operationResult = new Image(256*3+3, 256, picture.getType());
    	resH = calculateHistogram(picture);
    	double maxred = 0;
    	double maxgreen = 0;
    	double maxblue = 0;
    	
    	for (int i = 0; i < 256; i++) {
			if(resH[0][i] > maxred)
				maxred = resH[0][i];
			if(resH[1][i] > maxgreen)
				maxgreen = resH[1][i];
			if(resH[2][i] > maxblue)
				maxblue = resH[2][i];
			//System.out.println(i+ "=" +resH[0][i]);
		}
    	maxred *= 1.25;
    	maxgreen *= 1.25;
    	maxblue *= 1.25;
    	//System.out.println(maxred);
    	for (int i = 0; i < 256; i++) {
    		operationResult.setPixel(256, i, Color.BLACK);
    		operationResult.setPixel(513, i, Color.BLACK);
			int red = (int)((double)resH[0][i]/(double)maxred*255.)+1;
			int green = (int)((double)resH[1][i]/(double)maxgreen*255.)+1;
			int blue = (int)((double)resH[2][i]/(double)maxblue*255.)+1;
			//System.out.println(i+"="+red);
			
			for (int j = 0; j < 256; j++) {
				if(j<red)
					operationResult.setPixel(i, 255-j, Color.RED);
				else
					operationResult.setPixel(i, 255-j, Color.WHITE);
				if(j<green)
					operationResult.setPixel(i+257, 255-j, Color.GREEN);
				else
					operationResult.setPixel(i+257, 255-j, Color.WHITE);
				if(j<blue)
					operationResult.setPixel(i+514, 255-j, Color.BLUE);
				else
					operationResult.setPixel(i+514, 255-j, Color.WHITE);
			}
    	}
    	
        return operationResult;
    }
    
    public static int[][] calculateHistogram(Image picture){
    	int[][] res = new int[3][256];
    	for (int i = 0; i < picture.getSizeData(); i++) {
			Color c = picture.getPixel(i);
			res[0][c.getRed()]++;
			res[1][c.getGreen()]++;
			res[2][c.getBlue()]++;
		}
    	return res;
    }

    
}
