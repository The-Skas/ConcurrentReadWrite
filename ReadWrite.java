/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package readwrite;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author skas
 */
public class ReadWrite {
    public static int MAX_WORKERS = 4;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Book book = new Book();
        ArrayList<Thread> workers = new ArrayList<>();
        
     
        
        for(int i = 0; i < MAX_WORKERS;i++)
        {
            Worker worker = new Worker(book);
            Thread thread = new Thread(worker);
            thread.start();
            workers.add(thread);
        }
        
        
        try {
            Thread.sleep((long) (5000 + Math.random() * 1000));
        } catch (InterruptedException ex){};
        
        for(int i = 0; i < MAX_WORKERS; i++)
        {
            workers.get(i).interrupt();
        }

    }
    
}
