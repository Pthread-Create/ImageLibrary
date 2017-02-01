package SwingPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Image.Image;

public class ImageViewver extends JFrame{
    private Image[] imageG;
    private float timeimgpass = 1;
    private int imageshow = 0;
    private boolean loadimage = true;
    private Thread paintthread;
    private int distortion = 0;
    
    public ImageViewver(Image... imageG){
        if(imageG == null){
            System.err.println("GreyImage must be initialised.");
            for (StackTraceElement ste : Thread.currentThread().getStackTrace()) {
                System.err.println(ste);
            }
        }else{
            this.imageG = imageG;
            this.setVisible(true);
            this.setMinimumSize(new Dimension(imageG[0].getSizeX()+8, imageG[0].getSizeY()+30));
            this.repaint();
            paintthread = new Thread(new Runnable() {
				
				@Override
				public void run() {
					while(true){
						try {
							Thread.sleep((long)(timeimgpass*1000));
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if(!loadimage && imageG.length > 1){
							loadimage = true;
							imageshow++;
							if(imageshow >= imageG.length)
								imageshow = 0;
							ImageViewver.this.repaint();
						}
					}
				}
			});
            paintthread.start();
        }
    }
    
    @Override
    public void paint(Graphics g) {
        super.paintComponents(g);
        int x = 0;
        int y = 0;
        for (int i = 0; i < imageG[imageshow].getSizeData(); i++) {
            g.setColor(imageG[imageshow].getPixel(i));
            g.drawLine(x+8, y+30, x+8+(int)(Math.random()*distortion-(distortion>>1)), y+30+(int)(Math.random()*distortion-(distortion>>1)));
            //g.drawOval(x+8, y+30, (int)(Math.random()*distortion+1), (int)(Math.random()*distortion+1));
            x++;
            if(x == imageG[imageshow].getSizeX()){
                x=0;
                y++;
            }
        }
        loadimage = false;
    }
}
