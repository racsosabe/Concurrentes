/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package exam0301;


public class IniciaServidorConHilos {
    public static void main(String[] args) {
        ServidorDeEcoConHilos server = new ServidorDeEcoConHilos();
        new Thread(server).start();
    }
}
