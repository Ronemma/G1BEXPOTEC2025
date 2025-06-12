
package control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public final class Teclado implements KeyListener {
    private final static int numeroTeclas = 120;
    private final boolean[] teclas = new boolean[numeroTeclas];
    
    public boolean arriba;
    public boolean abajo;
    public boolean izquierda;
    public boolean derecha;
    
    public void actualizar(){
        //Controles                 //VK es palabra y W es la tecla a pulsar
        arriba = teclas[KeyEvent.VK_W];
        abajo = teclas[KeyEvent.VK_S];
        izquierda = teclas[KeyEvent.VK_A];
        derecha = teclas[KeyEvent.VK_D]; 
    }
    
    public void mostrar(){
        
    }
    
    //Metodo de tecla pulsada, si se deja presionada la tecla hará un evento
    public void keyPressed(KeyEvent e) {
      teclas[e.getKeyCode()]= true;
      
    }

    //Metodo tecla soltada, si se suelta la tecla hará un evento
    public void keyReleased(KeyEvent e) {
     teclas[e.getKeyCode()]= false;
    }
    
    //Metodo que se realiza al presionar y tocar la tecla
    public void keyTyped(KeyEvent e) {
      
    }
    

    
}
