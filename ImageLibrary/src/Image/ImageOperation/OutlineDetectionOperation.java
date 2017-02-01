package Image.ImageOperation;

import java.awt.Color;

import Image.Image;
import ImageUtils.FiltrePasse;

public class OutlineDetectionOperation implements Image1POperation{
    private static FiltrePasse hfiltre;
    private static FiltrePasse vfiltre;
    
    {
    	System.out.println("juuj");
    	double[] h = {-1./3.,-1./3.,-1./3.,0.,0.,0.,1./3.,1./3.,1./3.};
    	hfiltre = new FiltrePasse(h);
    	double[] v = {-1./3.,0,1./3.,-1./3.,0,1./3.,-1./3.,0,1./3.};
    	vfiltre = new FiltrePasse(v);
    }
    
    public OutlineDetectionOperation(){
    }
    
    @Override
    public Image operation(Image picture) {
    	Image operationResult = new Image(picture.getSizeX(), picture.getSizeY(), picture.getType());

        int[][] pixel = new int[3][9];
        double[] pixs = new double[3];
        int[] res = new int[3];
        
        int x = 0;
        int y = 0;
        int temp2 = -1;
        int temp3 = 1;
        int temp5 = 9;
        
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
                    pixs[k] += hfiltre.getFiltre()[l]*pixel[k][l];
                    pixs[k] += vfiltre.getFiltre()[l]*pixel[k][l];
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
