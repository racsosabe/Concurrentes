
package exam0301;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ManejadorDeConHilos implements Runnable
{
    private static final int N = 40;
    private static final double G = 9.81;
    private static final int M = 50;
    private Socket entrante;
    private int contador;
    int nclientes = 10;
    String[][] mapa;
    int[] posicion = new int[15]; 
    boolean[] vida = new boolean[15];
    private int before;
    
    public ManejadorDeConHilos(Socket i,int c,String[][] tabla, int[] pos, boolean[] v, int bef)
    {
        entrante = i; contador = c; 
        mapa = tabla;
        posicion = pos;
        vida = v;
        before = bef;
    }
    public void run()
    {
        try 
        {
            try 
            {
                InputStream secuenciaDeEntrada = entrante.getInputStream();
                OutputStream secuenciaDeSalida = entrante.getOutputStream();
             
                Scanner in = new Scanner(secuenciaDeEntrada);
                PrintWriter out = new PrintWriter(secuenciaDeSalida,true);

                out.println("Hola Jugador:" + contador + " Escriba ADIOS para salir");
                out.println("Cantidad de jugadores: " + (Thread.activeCount()-before));
                mapa[0][posicion[contador]] = ""+(char)('a'+contador-1);
                boolean terminado = false;
                vida[contador] = true;
                boolean first_turn = true;
                
                while( !terminado )
                {
                    String linea;
                    String arrayString[];
                    double angle;
                    if( vida[contador] == false )
                    { 
                        out.println("Perdió el juego");
                        terminado = true;
                    }
                    else{
                        if(Thread.activeCount() - before == 1 && !first_turn){
                            out.println("Ganó el juego");
                            terminado = true;
                        }
                    }
                    if(terminado){
                        break;
                    }
                    do
                    {
                        out.println("Ingrese un angulo entre [20.00-160.00] grados:");
                        in.hasNextLine();
                        linea = in.nextLine();
                        arrayString = linea.split("\\s+");
                        angle = Integer.parseInt(arrayString[0]);
                    } while( angle < 20.01 || angle > 160.01);
                    
                    angle = angle*Math.PI/180;

                    String linea2;
                    String arrayString2[];
                    int force;
                    do
                    {
                        out.println("Ingrese la velocidad de la bala [2-30]");
                        in.hasNextLine();
                        linea2 = in.nextLine();
                        arrayString2 = linea2.split("\\s+");
                        force = Integer.parseInt(arrayString2[0]);
                    } while( force < 2 || force > 30 );

                    System.out.println("Eco de:"+contador+" dice:"+linea);
		    int force2 = force*force;
                    double SinA = Math.sin(angle);
                    int H = (int)(force2*SinA*SinA/(2*G));
                    if(H > N-1) H = N-1;
                    int L = posicion[contador] + (int)(force2*Math.sin(2*angle)/G);
                    out.println("altura maxima = "+H);
                    int l = M-1, r = 0;
                    boolean X[] = new boolean[M];
                    for( int i = 0; i < M; i++ ) X[i] = false;

                    for( int i = 1; i <= H; i++ ){
                        int x1 = (int)Math.round(force*Math.cos(angle)*((force*SinA+Math.sqrt(force2*SinA*SinA-2*G*i))/G));
                        int x2 = (int)Math.round(force*Math.cos(angle)*((force*SinA-Math.sqrt(force2*SinA*SinA-2*G*i))/G));
                        l = Math.max(Math.min(Math.min(x1+posicion[contador],x2+posicion[contador]),l),0);
                        r = Math.min(Math.max(Math.max(x1+posicion[contador],x2+posicion[contador]),r),M-1);
                        if( x1+posicion[contador]<M && x1+posicion[contador]>=0 ) 
                        {
                            mapa[i][x1+posicion[contador]] = ""+(char)('a' + contador-1);
                            X[x1+posicion[contador]] = true;
                        }
                        if( x2+posicion[contador]<M && x2+posicion[contador]>=0 ) 
                        {
                            mapa[i][x2+posicion[contador]] = ""+(char)('a' + contador-1);
                            X[x2+posicion[contador]] = true;
                        }
                    }
                    for( int j = l; j <= r; j++ )
                    {
                        if( X[j] == false )
                        {
                            int k = (int)Math.round((j-posicion[contador])*Math.tan(angle)-((G*(j-posicion[contador])*(j-posicion[contador]))/(2*Math.pow(force*Math.cos(angle),2))));
                            if( k<N && k>=0 ) mapa[k][j] = ""+(char)('a'+contador-1);
                        }
                    }
                    if( L >= 0 && L < M )
                    {
                        mapa[0][L] = "x";      
                        for( int i = 1; i <= nclientes; i++ )
                        {
                            if( posicion[i] == L && vida[i] == true )
                            {
                                out.println("1 KILL: Player "+ i);
                                vida[i] = false;
                            }
                        }
                    }
                    String rpta ="";
                    rpta = rpta + "\r\n";
                    for( int i = N-1; i >= 0; i-- )
                    {
                        for( int j = 0; j < M; j++ )
                        {
                            out.print(mapa[i][j]);
                            rpta = rpta + mapa[i][j];
                            if( i!=0 ) mapa[i][j] = ".";
                        }
                        rpta = rpta + "\r\n";
                        out.println("");
                    }
                    if( L >= 0 && L < M ) mapa[0][L] = ".";				
                    if(linea.trim().equals("ADIOS"))
                    {
                        terminado = true;
                    }
                    first_turn = false;
                }                
            }
            finally 
            {
                entrante.close();
            }
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }
}
