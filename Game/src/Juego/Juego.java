
package Juego;
//Importaciones de libreriras 
import control.Teclado;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import javax.swing.JFrame;

//Usamos la clase Juego y heredamos de Canvas
//Agregamos la palabra "implements" - Runnable para diferentes hilos
public class Juego extends Canvas implements Runnable {
    //Identificador unico de serie 
    private  static final long  serialVersionUID = 1L; //Sirve para identificar las clases si son usadas en un futuro y esta cambia 
                
    //Creación de el tamaño de mi ventana
    //La palabra "final" nos dice que será un valor de tamaño unico y no cambiará en toda la ejecución del programa, mejorando ejecución
    //Declaración de variables constantes (deben ir escritas en Mayusculas)
    private static final int ANCHO = 800;
     private static final int ALTO = 600;
     
     //Variable boolean para indicar el funcionamiento, metodos Iniciar y Detener
     //La palabra "volatile" nos dice que una variable no puede ser ejecutada por 2 threads a la vez, unicamente por una
     private static volatile boolean  enFuncionamiento = false;
    
     //Declaramos el nombre de la ventana  
     private static final String NOMBRE = "Juego";
     
     //Variables que se mostraran en el titulo para el temporizador y contador
     private static int aps = 0;
     private static int  fps = 0;
    
    //Creación de la ventana 
    private static JFrame ventana;
    //Creación de thread
    private static Thread thread;
    
    private static Teclado teclado;
    
    //Constructor de la clase Juego
    private Juego(){
        setPreferredSize(new Dimension(ANCHO, ALTO));
        
        //Inicializamos el apartado de teclado
        teclado = new Teclado();
        addKeyListener(teclado);
        
        //Vamos a iniciar nuestro objeto ventana
        ventana = new JFrame(NOMBRE);
        //Cerrar la ventana para finalizar la ejecución
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Que el usuario no pueda cambiar el tamaño de la ventana
        ventana.setResizable(false);
        //Diseño de la ventana
        ventana.setLayout(new BorderLayout());
        //Dar diseño y centrar 
        ventana.add(this, BorderLayout.CENTER);
        //Commando para que todo se ajuste y no salga todo desordenado (Como antierrores)
        ventana.pack();
        //Establecer la posición de la ventana dentro del escritorio
        ventana.setLocationRelativeTo(null);
        //Para que la ventana sea visible (IMPORTANTE NUNCA OLVIDAR)
        ventana.setVisible(true);
    }
    
    //Metodo Main (Siempre public)
    public static void main(String[] args){
        //Cree una instancia
        //Tipo de dato, Identificador, Creador de espacio en la memoria, Constructor de la clase
        Juego juego = new Juego();
        //Llamar el metodo iniciar para que se ejecute
        juego.iniciar();
    }

    //Metodo para iniciar el segundo thread
     // la palabra "synchornized" hace que los metodos no puedan modificar a la vez la variable "enFuncionamiento"
    private synchronized void iniciar(){
        //Variable en verdadero porque se estará ejecutando 
        enFuncionamiento = true;
        
        //Instanciar la variable thread
        thread = new Thread(this, "Graficos");
        //Metodo para ejecutar
        thread.start();
    }
    
    //Metodo para detener el segundo thread
    // la palabra "synchornized" hace que los metodos no puedan modificar a la vez la variable "enFuncionamiento"
    private synchronized void detener(){
        
            //Variable en falso porque se tendrá que detener
            enFuncionamiento = false;
            
            //Anti error al momento de intentar ejecutar el thread.join();
         try {   
             //Se usa join porque este solo para el thread hasta que este termine su funcionamiento a diferencia de "stop"
            thread.join();
            //Si hay un error lo va a "Capturar" para que no se pete el programa
        } catch (InterruptedException e) {
            //Nos imprime en consola los detalles si hubiera un error
           e.printStackTrace();
        }
        
    }
    
    //Metodos para poder interactuar con el juego que actualizará las acciones y mostrará
    private void actualizar(){
        teclado.actualizar();
        
        if(teclado.arriba){
            //Pruebas de teclado
            System.out.println("Se pulso arriba W");
        }
        if(teclado.abajo){
            
            System.out.println("Se pulso abajo S");
        }
        if(teclado.izquierda){
            System.out.println("Se pulso izquierda A");
        }
        if(teclado.derecha){
            System.out.println("Se pulso derecha D");
        }
        
        
        aps++;
        
    }
    
    private void mostrar(){
        fps++;
    }
    
//Metodo de lineas o threads
    public void run() {
        //Declaracion de equivalencia de cuantos nanosegundos hay en un segundo
        final int NS_POR_SEGUNDO = 1000000000;
        //Declaracion de cuantas actualizaciones llevar por segundo 
        final byte APS_OBJETIVO = 60; 
        //Cuantos nanosegundos van a transcurrir por actualización
        final double NS_POR_ACTUALIZACION = NS_POR_SEGUNDO / APS_OBJETIVO;
        //Se le atribuye una cantidad de tiempo en nanosegundos
        long referenciaActualizacion = System.nanoTime();
        //se da la referencia de contador en graficos
        long referenciaContador = System.nanoTime();
        
        //Solo definimos una variable sin valor
        double tiempoTranscurrido;
        //definimos la variable delta con valor de 0
        double delta = 0;
        
        requestFocus();
        
        
        //Bucle que se detendrá si la variable es false
        while (enFuncionamiento){
            //Tomamos la referencia del tiempo exacto
            final long inicioBucle = System.nanoTime();
            //Mediremos cuanto tiempo ha pasado entre ambos nanoTime
            tiempoTranscurrido = inicioBucle - referenciaActualizacion;
            //Vamos a devolver el valor 
            referenciaActualizacion = inicioBucle;
            //Datos de nanosegundos almacenados en delta
            delta += tiempoTranscurrido / NS_POR_ACTUALIZACION;
            //Bucle que se va a ejecutar si delta es mayor o igual a 1
            while(delta >= 1){
                //Si se cumple la condición el bucle va a actualizar el juego
                actualizar();
                //Le restará 1 a delta
                delta--;
            }
            //Implementamos metodo para que muestre los gráficos actualizados
            mostrar();
            
            //Condición para poder mostrar en ventana los aps y fps
            if (System.nanoTime() - referenciaContador > NS_POR_SEGUNDO){
                ventana.setTitle(NOMBRE + " | | APS: "+ aps + " | | FPS: "+fps);
                aps = 0;
                fps =0;
                referenciaContador = System.nanoTime();
            }
        }
    }
    
    
    
}
