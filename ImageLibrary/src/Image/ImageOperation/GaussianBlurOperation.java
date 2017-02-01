package Image.ImageOperation;

import java.awt.Color;

import Image.Image;
import ImageUtils.FiltrePasse;

public class GaussianBlurOperation implements Image1POperation{
    double delta;
    int size;
    FiltrePasse filtre;
    
    public GaussianBlurOperation(double delta, int size){
    	if(size < 0)
    		size = -size;
        this.delta = delta;
        this.size = 1+size*2;
        int temp5 = this.size*this.size;
        filtre = new FiltrePasse(temp5);
        double temp = 1/(2*Math.PI*delta*delta);
        int temp2 = -size;
        int temp3 = size;
        double temp4 = 1/(2*delta*delta);
        int x = temp2;
        int y = temp2;
        for (int i = 0; i < temp5; i++) {
            filtre.getFiltre()[i] = Math.exp(-(x*x+y*y)*temp4);
            x++;
            if(x>temp3){
                x=temp2;
                y++;
            }
        }
        double sum = filtre.getSum();
        for (int i = 0; i < temp5; i++) {
        	filtre.getFiltre()[i] = filtre.getFiltre()[i] / sum;
		}
        
    }
    
    @Override
    public Image operation(Image picture) {
    	Image operationResult = new Image(picture.getSizeX(), picture.getSizeY(), picture.getType());

        int[][] pixel = new int[3][size*size];
        double[] pixs = new double[3];
        int[] res = new int[3];
        
        int x = 0;
        int y = 0;
        int temp2 = -size/2;
        int temp3 = size/2;
        int temp5 = size*size;
        
        for (int i = 0; i < picture.getSizeData(); i++) {
            int tempx = temp2;
            int tempy = temp2;
            for (int j = 0; j < temp5; j++) {
                int px = x+tempx;
                int py = y+tempy;
                if(px<0)
                	px=0;
                if(px >= picture.getSizeX())
                	px = picture.getSizeX()-1;
                if(py<0)
                	py=0;
                if(py >= picture.getSizeY())
                	py = picture.getSizeY()-1;
                
                Color c = picture.getPixel(px, py);
                pixel[0][j] = c.getRed();
                pixel[1][j] = c.getGreen();
                pixel[2][j] = c.getBlue();
                
                tempx++;
                if(tempx>temp3){
                    tempx=temp2;
                    tempy++;
                }
            }
            
            for (int k = 0; k < pixs.length; k++) {
            	pixs[k] = 0;
                for (int l = 0; l < pixel[0].length; l++) {
                    pixs[k] += filtre.getFiltre()[l]*pixel[k][l];
                }
                res[k] = (int) pixs[k];
                if(res[k] < 0)
                    res[k] = 0;
                else if(res[k] > 255)
                    res[k] = 255;
            }
            
            
            operationResult.setPixel(i, new Color(res[0],res[1],res[2]));
            x++;
            if(x == picture.getSizeX()){
                x=0;
                y++;
            }
        }
        
        return operationResult;
    }

}
