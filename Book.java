/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package readwrite;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author skas
 */
public class Book 
{
    private ArrayList<Worker> readers = new ArrayList<>();
    public enum State
    {
        READ, WRITE, NONE
    }
    private Lock l = new ReentrantLock();

    private State state;
    
    public Book()
    {
        state = State.NONE;
    }
    
    public boolean isReadLock()
    {
        if(state.equals(State.READ))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public boolean isWriteLock()
    {
        if(state.equals(State.WRITE))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    /*
    * Returns true on successful read
    * otherwise returns false.
    */
    public String read(Worker worker) throws InterruptedException
    {
        this.getReadBlock(worker);
        System.out.println("Worker "+worker+" is reading");
        Thread.sleep((long) (Math.random() * 1000));
        System.out.println("Worker "+worker+" is done reading.");
        
        //shouldnt i synchronize this?
        this.readers.remove(worker);
        this.setState(State.NONE);
        return " ";
    }
    public synchronized void getReadBlock(Worker worker) throws InterruptedException
    {
        while(isWriteLock())
        {
                System.out.println("Worker "+worker+" is requesting to read"); 
                wait();
        }
        this.state = State.READ;
        this.readers.add(worker);
    }
    
//    public synchronized void getWriteBlock(Worker worker) throws InterruptedException
//    {
//        while(isWriteLock() || isReadLock())
//        {
//                System.out.println("Worker: "+worker+" is requesting to read"); 
//                wait();
//        }
//        this.state = State.WRITE;
//    }
    public synchronized void setState(State state)
    {
        this.state = state;
        notifyAll();
    }
    
    /*
    * Returns true on successful write
    * otherwise returns false.
    */
    
    public synchronized void write(Worker worker) throws InterruptedException
    {
        while(isWriteLock() || isReadLock() || !readers.isEmpty())
        {
                System.out.println("Worker:"+worker+" is requesting to write"); 
                wait();
        }
        System.out.println("Worker: "+worker+" is writing");
        this.state = State.WRITE;
        Thread.sleep((long) (Math.random() * 1000));

            //Dont notify all since only one can write anyway!
            
            
            
       
        System.out.println("Worker: "+worker+" is done writing");
        this.state = State.NONE;
        notifyAll();

    }
}
