package Image;
import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JFrame;
import Image.ImageLoader.ImageLoader;
import Image.ImageLoader.PGMLoader.PGMLoader;
import Image.ImageLoader.PPMLoader.PPMLoader;
import Image.ImageOperation.GaussianBlurOperation;
import Image.ImageOperation.HistogramOperation;
import Image.ImageOperation.Image1POperation;
import Image.ImageOperation.NegativeOperation;
import Image.ImageOperation.OutlineDetectionOperation;
import Image.ImageOperation.UniformBlurOperation;
import SwingPanel.ImageViewver;


public class Image {
    private int dimX;
    private int dimY;
    private int size;
    private int[] data;
    private String type;
    
    private static HashMap<String, ImageLoader> imageLoaderAlgorithm = new HashMap<>();
    
    
    public Image(int dimX, int dimY, String type){
        this.dimX = dimX;
        this.dimY = dimY;
        this.size = dimX*dimY;
        this.type = type;
        this.data = new int[this.size];
    }
    public Image(int dimX, int dimY, int[] data, String type){
        this(dimX,dimY, type);
        if(size == data.length){
            for (int i = 0; i < data.length; i++) {
                this.data[i] = data[i];
            }
        }else{
            System.err.println("Data is not the same size as dimX*dimY size.");
            for (StackTraceElement ste : Thread.currentThread().getStackTrace()) {
                System.err.println(ste);
            }
        }
    }
    public int getSizeX() {
        return dimX;
    }
    public int getSizeY() {
        return dimY;
    }
    public int getSizeData() {
        return size;
    }
    
    public int[] getData(){
        return data;
    }
    
    public Color getPixel(int x, int y){
        if(x < 0 || x >= dimX || y < 0 || y >= dimY){
            System.err.println("Position must be inside of the picture");
            for (StackTraceElement ste : Thread.currentThread().getStackTrace()) {
                System.err.println(ste);
            }
            return null;
        }
        ImageLoader iml = imageLoaderAlgorithm.get(this.type);
        if(iml != null){
            return iml.getPixelColor(this, data[y*dimX+x]);
        }else{
            System.err.println("Type loader must exist.");
            for (StackTraceElement ste : Thread.currentThread().getStackTrace()) {
                System.err.println(ste);
            }
            return null;
        }
    }
    
    public Color getPixel(int offset){
        if(offset < 0 || offset >= size){
            System.err.println("Position must be inside of the picture");
            for (StackTraceElement ste : Thread.currentThread().getStackTrace()) {
                System.err.println(ste);
            }
            return null;
        }
        ImageLoader iml = imageLoaderAlgorithm.get(this.type);
        if(iml != null){
            return iml.getPixelColor(this, data[offset]);
        }else{
            System.err.println("Type loader must exist.");
            for (StackTraceElement ste : Thread.currentThread().getStackTrace()) {
                System.err.println(ste);
            }
            return null;
        }
    }
    
    public int getPixelData(int x, int y){
        if(x < 0 || x >= dimX || y < 0 || y >= dimY){
            System.err.println("Position must be inside of the picture");
            for (StackTraceElement ste : Thread.currentThread().getStackTrace()) {
                System.err.println(ste);
            }
            return 0;
        }
        return data[y*dimX+x];
    }
    
    public int getPixelData(int offset){
        if(offset < 0 || offset >= size){
            System.err.println("Position must be inside of the picture");
            for (StackTraceElement ste : Thread.currentThread().getStackTrace()) {
                System.err.println(ste);
            }
            return 0;
        }
        return data[offset];
    }
    
    public void setPixel(int x, int y, int pixeldata){
        if(x < 0 || x >= dimX || y < 0 || y >= dimY){
            System.err.println("Position must be inside of the picture");
            for (StackTraceElement ste : Thread.currentThread().getStackTrace()) {
                System.err.println(ste);
            }
            return;
        }
        data[y*dimX+x] = pixeldata;
    }
    
    public void setPixel(int offset, int pixeldata){
        if(offset < 0 || offset >= size){
            System.err.println("Position must be inside of the picture");
            for (StackTraceElement ste : Thread.currentThread().getStackTrace()) {
                System.err.println(ste);
            }
            return;
        }
        data[offset] = pixeldata;
    }
    
    public void setPixel(int offset, Color color){
        if(offset < 0 || offset >= size){
            System.err.println("Position must be inside of the picture");
            for (StackTraceElement ste : Thread.currentThread().getStackTrace()) {
                System.err.println(ste);
            }
            return;
        }
        ImageLoader iml = imageLoaderAlgorithm.get(this.type);
        if(iml != null){
            data[offset] = iml.setPixelColor(this, color);
        }else{
            System.err.println("Type loader must exist.");
            for (StackTraceElement ste : Thread.currentThread().getStackTrace()) {
                System.err.println(ste);
            }
        }
    }
    
    public void setPixel(int x, int y, Color color){
        if(x < 0 || x >= dimX || y < 0 || y >= dimY){
            System.err.println("Position must be inside of the picture");
            for (StackTraceElement ste : Thread.currentThread().getStackTrace()) {
                System.err.println(ste);
            }
            return;
        }
        ImageLoader iml = imageLoaderAlgorithm.get(this.type);
        if(iml != null){
            data[y*dimX+x] = iml.setPixelColor(this, color);
        }else{
            System.err.println("Type loader must exist.");
            for (StackTraceElement ste : Thread.currentThread().getStackTrace()) {
                System.err.println(ste);
            }
        }
    }
    
    public boolean isPosValid(int offset){
        return offset > 0 && offset < size;
    }
    
    public boolean isPosValid(int x,int y){
        return isPosValid(y*dimX+x);
    }
    
    public boolean savePGM(String filename){
        ImageLoader iml = imageLoaderAlgorithm.get(this.type);
        if(iml != null){
            return iml.saveImage(this, filename);
        }else{
            System.err.println("Type loader must exist.");
            for (StackTraceElement ste : Thread.currentThread().getStackTrace()) {
                System.err.println(ste);
            }
            return false;
        }
    }
    
    public String getType() {
		return type;
	}
    
	public Image operate(Image1POperation op){
        if(op == null){
            return this;
        }else{
            return op.operation(this);
        }
    }
    
    public static void addImageLoader(ImageLoader iml){
        imageLoaderAlgorithm.put(iml.getImageExtensions(), iml);
    }
    
    public static void removeImageLoader(ImageLoader iml){
        imageLoaderAlgorithm.remove(iml.getImageExtensions());
    }
    
    public static Image loadPGM(String filename){
        File f = new File(filename);
        if(!f.exists()){
            System.err.println("File must exist.");
            for (StackTraceElement ste : Thread.currentThread().getStackTrace()) {
                System.err.println(ste);
            }
            return null;
        }
        String extension = "";

        int i = filename.lastIndexOf('.');
        if (i > 0) {
            extension = filename.substring(i+1);
        }
        extension = extension.toLowerCase();
        
        for (String iterable_element : imageLoaderAlgorithm.keySet()) {
            for(String ext : iterable_element.split(" ")){
                if(ext.equals(extension)){
                    ImageLoader iml = imageLoaderAlgorithm.get(iterable_element);
                    return iml.loadImage(filename);
                }
            }
        }
        return null;
    }
    
    public static void main(String[] args){
        Image.addImageLoader(new PGMLoader());
        Image.addImageLoader(new PPMLoader());
        Image GI = Image.loadPGM("imgs/ocean.ppm");
        Image[] finale = new Image[2];
        finale[0] = GI;
        //for (int i = 1; i < finale.length; i++) {
        	HistogramOperation gbo = new HistogramOperation();
        	//finale[i] = ;
		//}
        ImageViewver IM = new ImageViewver(GI);
        ImageViewver IM2 = new ImageViewver(GI.operate(gbo));
        IM.setTitle("test");
    }
    
}


