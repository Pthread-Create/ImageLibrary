package ImageUtils;

public class FiltrePasse {
    private double[] filtre;
    private int taille;
    
    
    public FiltrePasse(int taille) {
        this.taille = taille;
        filtre = new double[taille];
    }

    public FiltrePasse(double[] filtre){
        this.taille = filtre.length;
        this.filtre = new double[taille];
        for (int i = 0; i < filtre.length; i++) {
                this.filtre[i] = filtre[i];
        }
    }
    
    public double[] getFiltre(){
        return filtre;
    }
    
    public double getSum(){
        double sumpos = 0;
        double sumneg = 0;
        for (int i = 0; i < filtre.length; i++) {
            if(filtre[i] < 0)
                sumneg += -filtre[i];
            else if(filtre[i] > 0)
                sumpos += filtre[i];
        }
        return Math.max(sumpos,sumneg);
    }
}
