
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package threads;

/**
 *
 * @author laboratorio
 */
public class ExemploThread {
    public static void main(String[] args) {
        MinhaThread thread1 = new MinhaThread("Thread 1 - Mensagem a cada 1 segundo", 1000);
        MinhaThread thread2 = new MinhaThread("Thread 1 - Mensagem a cada 2 segundo", 2000);
        
        
        thread1.start();
        thread2.start();
        
        for(int i=0; i < 5; i++){
        System.out.println("Thread principal - interação " + i);
        try{
            Thread.sleep(1500);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
     }
        thread1.interrupt();
        thread2.interrupt();
   }
 
}
