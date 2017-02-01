package Image.ImageLoader.PPMLoader;

import java.io.*;


// Lecture/ ecriture d'images 8 bits au format PGM

class PPMFileIO {
    
    private static String MAGIC_PGM = "P6\n";
    private char c;
    private int dimX;
    private int dimY;
    private int[] data;
    private String filename;
    FileInputStream filein;
    FileOutputStream fileout;

    public PPMFileIO(String filename)
    {
        this.filename=filename;
    }
        
    void readPGM() throws FileNotFoundException,IOException
    {
        filein=new FileInputStream(filename);
        if ( !matchKey(MAGIC_PGM) )
            throw new IOException(filename + " : wrong magic number");
        skipComment('#');
        dimX = getInt();
        dimY = getInt();
        skipLine();
        skipComment('#');
        skipLine();
        data = loadData(dimX * dimY);
            filein.close();
    }
    
    void writePGM(int dx, int dy, int[] data) throws IOException
    {
        fileout=new FileOutputStream(filename);
        byte[] buffer=new byte[dx*dy*3];
        for(int i=0; i<dx*dy; i++)
        {
            buffer[i*3]=(byte)((data[i] >> 16) & 255);
            buffer[i*3+1]=(byte)((data[i] >> 8) & 255);
            buffer[i*3+2]=(byte)((data[i]) & 255);
        }
        put(MAGIC_PGM);
        put("#\n");
        put(dx + " " + dy + "\n255\n");
        fileout.write(buffer);
        fileout.close();
    }

     private void put(String data) throws IOException 
        {
            fileout.write(data.getBytes());
    }
    
    private boolean matchKey(String key) throws IOException 
    {
        byte[] buf = new byte[key.length()];
        filein.read(buf, 0, key.length());
        return key.compareTo(new String(buf)) == 0;
    }

    private void getChar() throws IOException 
    {
            c = (char)filein.read();
    }

    private int getInt() throws IOException 
    {
        String s = "";
        while ( (c != '\n') && Character.isSpaceChar(c) ) 
            getChar();
        while ( (c != '\n') && !Character.isSpaceChar(c) ) 
            {
            s = s + c;
            getChar();
            }      
        return Integer.parseInt(s);
    }
    
    private void skipLine() throws IOException 
    {
        while ( c != '\n' )
        getChar();
    }
    
    private void skipComment(char code) throws IOException 
    {
        getChar();
        while ( c == code ) 
            {
            skipLine();
            getChar();
            }
    }
    
    private int[] loadData(int size) throws IOException 
    {
        byte[] data = new byte[size*3];
        filein.read(data, 0, size*3);
        int[] res=new int[size];

        for(int i=0; i<size; i++){
            int r = (int)(data[i*3]) & 255;
            int g = (int)(data[i*3+1]) & 255;
            int b = (int)(data[i*3+2]) & 255;
            r = r << 16;
            g = g << 8;
            res[i]= r|g|b;
        } //unsighed !
        return res;
    }
    
    public int getSizeX() {return dimX;}
    public int getSizeY() {return dimY;}
    public int[] getData() {return data;}
}