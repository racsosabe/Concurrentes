/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package exam0301;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
public class ServidorDeEcoConHilos implements Runnable{
    private int nrocli = 10;
    private static final int N = 40;
    private static final int M = 50;
    private ManejadorDeConHilos[] cli = new ManejadorDeConHilos[nrocli];
    private String[][] tablero = new String[N][M];
    private int[] H = new int[15];
    private boolean[] vidas = new boolean[15];
    private boolean[] used = new boolean[M];

    public ServidorDeEcoConHilos() {                
        for(int i=0; i<N; i++){
            for(int j=0; j<M; j++){
                tablero[i][j]=".";
                used[j] = false;
            }
        }
        for( int i=1; i<=10; i++ )
	   {
            vidas[i] = false;
            int val = (int)(Math.random()*(M-i));
            int pos = 1;
            while(val > 0){
                if(!used[pos]){
                    val-=1;
                }
                pos += 1;
            }
            pos -= 1;
            H[i] = pos;
        }
    }
    
    public void run(){
        try {
            int i = 1;
            ServerSocket s = new ServerSocket(8180);
            int before = Thread.activeCount();
            while (i < 8) {                
                Socket entrante = s.accept();
                System.out.println("Engendrado " + i);
                //Runnable 
                cli[i] = new ManejadorDeConHilos(entrante , i,tablero, H, vidas, before);
                
                Thread t = new Thread(cli[i]);
                t.start();
                i++;                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
     
    //public class Servidor(){
    
    
    //}

    private void ManejadorDeConHilos(Socket entrante, int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
            
}
//telnet 127.0.0.1 8189
