/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package flappyBird;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.Timer;

/**
 * @author Eduardo Ernesto
 * @author Karen Lopez
 * Entrega: 15/05/2022
 * Numero de revisiones: 4
 */

public class FlappyBird implements ActionListener, MouseListener, KeyListener
{
//atributos
    public static FlappyBird flappyBird;
    
    public Renderizacion renderizacion;
    public Rectangle pajaro;
    public int ticks, ymotion;
    public int puntaje;
    public int mov;
    public boolean gameOver;
    public Random rand;
    
    Facil facil = new Facil();
    Normal normal = new Normal();
    int W = facil.getWIDTH();
    int H = facil.getHEIGHT();
    boolean started= facil.getinicio();
    ArrayList<Rectangle> columnas = facil.getCol();
    
    public FlappyBird()
    {
        JFrame jframe = new JFrame();
        Timer timer = new Timer(20, this);
        
        renderizacion = new Renderizacion();
        
        jframe.add(renderizacion);
        jframe.setTitle("Flappy Bird");
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setSize(W,H);
        jframe.setResizable(false);
        jframe.setVisible(true);
        
        pajaro = new Rectangle(W/2 - 10, H /2 - 10, 20, 20);
        
        facil.agregarColumna(true);
        facil.agregarColumna(true);
        facil.agregarColumna(true);
        facil.agregarColumna(true);
        timer.start();
    }
    
    public void addColumna(){
        facil.agregarColumna(started);
    }

    public void pintarColumna(Graphics g, Rectangle columna)
        {
            g.setColor(Color.green.darker());
            g.fillRect(columna.x, columna.y, columna.width, columna.height);
        }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        int speed = 10;

        ticks++;

        if(started){

            for (int i = 0; i < columnas.size(); i++)
            {
                Rectangle columna = columnas.get(i);

                columna.x -= speed;
            }
            if(ticks % 2 == 0 && ymotion < 15)
            {
                ymotion += 2;
            }

            for (int i = 0; i < columnas.size(); i++)
            {
                Rectangle columna = columnas.get(i);

                if (columna.x + columna.width < 0)
                {
                    columnas.remove(columna);

                    if (columna.y == 0)
                    {
                        facil.agregarColumna(false);
                    }
                }       
            }

            pajaro.y += ymotion;

            for (Rectangle columna : columnas)
            {
                if (columna.intersects(pajaro))
                {
                    gameOver = true;

                    pajaro.x = columna.x - pajaro.width;
                }
            }

            if (pajaro.y > H- 120 || pajaro.y < 0)
            {

                gameOver = true;
            }

            if (gameOver){
                pajaro.y = H  - 120 - pajaro.height;
            }
        }

        renderizacion.repaint(); 
    }

    public void saltar(){
        if(gameOver==true){
            pajaro = new Rectangle(W/ 2 - 10, H / 2 - 10, 20, 20);
            columnas.clear();
            mov = 0;
	    puntaje = 0;

	    facil.agregarColumna(true);
            facil.agregarColumna(true);
            facil.agregarColumna(true);
            facil.agregarColumna(true);

            gameOver = false;
	}
        
        if (!started)
            {
                started= true;
            }
        else if (!gameOver)
            {
                if (mov > 0)
                {
                    mov = 0;
                }

                    mov -= 10;
            }
    }

//Metodo de ordenacion burbuja
   public static String[] ordenBurbuja(String[] p)
    {
       String auxiliar;
        for(int i = 0;i < (p.length - 1); i++){
            for(int j = 0;j < (p.length - 1); j++){
                if(p[j].compareTo(p[j+1]) > 0){
                    auxiliar = p[j];
                    p[j] = p[j+1];
                    p[j+1] = auxiliar;
                }       
            }
        }
        return p;
}
 //Metodo de busqueda binaria
   public static int busqBin(int[] b, int elemento) {
        int inicio = 0;
        int fin = b.length - 1;
        int pos;
        while (inicio <= fin) {
            pos = (inicio+fin) / 2;
            if ( b[pos] == elemento )
              return pos;
            else if ( b[pos] < elemento) {
         inicio = pos+1;
            } else {
         fin = pos-1;
            }
        }
        return -1;
     } 
 
//Metodo de ordenamiento por Insercion
public static void ordenInsercion(int[] q)
    {
        int i, aux, N=q.length;
        for(int j=1;j<N;j++){
          aux= q[j];
          i= j-1;
          while(i>-1 && q[i]>aux){
              q[i+1]=q[i];
              i=i-1;
          }
          q[i+1]=aux;
        }
    }
   
//Metodo de busqueda secuencial
 public static  int busquedaSecuencial(String c[], String Elemento){
    int posicion = -1;
  for(int i = 0; i < c.length; i++){
      if(Elemento.equals(c[i])){
    posicion = i;
    break;
   }
 }
 return posicion;
}  
 
public static void mostrarArreglo(int a[]){
     for (int i=1; i<=a.length; i++){
         System.out.print(a[i-1]+" ");
     }
     System.out.print("\n");
 }
   
// Encapsulación del entorno del juego
    void color(Graphics g) 
    {
       
        facil.agregarColumna(started);
        
        //Seleccionar fondo
        g.setColor(Color.cyan);
        g.fillRect(0, 0, W, H);
        
        //Seleccionar color del piso
        g.setColor(Color.orange);
        g.fillRect(0, H - 120, W,120);
        
        //Seleccionar color del césped
        g.setColor(Color.green);
        g.fillRect(0, H - 120, W,20);
        
        //Seleccionar color de la figura
        g.setColor(Color.red);
        g.fillRect(pajaro.x, pajaro.y, pajaro.width, pajaro.height); 
    }
   

    public static void main(String[] args)
    {
        flappyBird = new FlappyBird();  
        /*
        //Objetos de las clases hijas    
        Facil objFacil = new Facil();
        Normal objNormal = new Normal();
        //variable polimorfica
        Bird objBird[] = new Bird[2];
        objBird[0]=objFacil;
        objBird[1]=objNormal;
        
        objBird[0].saltar();
        objBird[1].saltar();
        
        Scanner teclado = new Scanner(System.in);
        String nombres[];
        int nElementos;
        
        nElementos = Integer.parseInt(JOptionPane.showInputDialog("Digite la cantidad de usuarios a jugar: "));
        nombres = new String[nElementos];
        
        for(int i = 0;i < nElementos; i++){
            System.out.println("Digite el nombre del Usuario: ");
            nombres[i] = teclado.next();
        }
        
        System.out.println("");
        System.out.println("El arreglo desordenado: ");
        for(int i = 0;i< nElementos; i++){
            System.out.println(nombres[i] + " ");
        }
        
        System.out.println("");

        ordenBurbuja(nombres);
        System.out.println("El arreglo ordenado es: ");
        for(int i = 0; i < nElementos; i++){
            System.out.println(nombres[i] + " ");
        }
        
        //Busqueda
        Scanner palabr = new Scanner(System.in);
        System.out.println("digite el nombre a buscar: ");
        String palabra = palabr.nextLine();
        int posi=busquedaSecuencial(nombres, palabra);
        
        if (posi != -1) {
                System.out.println ( "El nombre esta dentro del arreglo");
            } else {
                System.out.println ( "El nombre NOE esta dentro del arreglo" );
            }
        
            int puntajes[]= new int[5];

            puntajes[0]=objBird[0].getPuntaje(); 
            puntajes[1]=0;
            puntajes[2]=0;
            puntajes[3]=0;
            puntajes[4]=0;
            
            //Este for existe solo para mostrar el arreglo antes del orden, 
            //esta parte no estará en el codigo final (es solo para comparar)
            System.out.println(" " );
            System.out.println("arreglo original" );
            mostrarArreglo(puntajes);
            //Se aplica el ordenamiento de insercion
            ordenInsercion(puntajes);
           
            //mostar arreglo ya ordenado
            System.out.println("arreglo ordenado" );
            mostrarArreglo(puntajes);
            
            //Ingresar dato a buscar y metodo de busqueda binario
            Scanner bq = new Scanner(System.in);
            System.out.println(" " );
            System.out.println("ingrese el puntaje a buscar: ");
            int buscar = bq.nextInt();
            int buscando= busqBin(puntajes,buscar);

            if (buscando != -1) {
                System.out.println ( "El puntaje esta dentro de los 5 mejores puestos");
            } else {
                System.out.println ( "El puntaje NO esta dentro de los 5 mejores puestos" );
            }*/
    }   

    @Override
    public void mouseClicked(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mousePressed(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mouseExited(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void keyTyped(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void keyPressed(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void keyReleased(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
