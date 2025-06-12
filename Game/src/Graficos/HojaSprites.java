
package Graficos;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class HojaSprites {
    private final int ancho;
    private final int alto;
    
    //Array de Pixeles
    public final int [] pixeles;
    
    //Constructor 
    public HojaSprites(final String ruta, final int ancho, final int alto){
        this.ancho = ancho;
        this.alto = alto;
        
        //Creamos un array para el tamaño de la imagen
        pixeles = new int[ancho * alto];
        BufferedImage imagen;
        try{
        imagen = ImageIO.read(HojaSprites.class.getResource(ruta));
        imagen.getRGB(0,0,ancho,alto,pixeles,0,ancho);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    public int  ObtenAncho(){
        return ancho;
    }
   
}
