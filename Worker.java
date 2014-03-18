/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package readwrite;

/**
 *
 * @author skas
 */

import java.util.concurrent.*;
public class Worker extends Thread {
    private static int ID = 1;
    private static float CHANCE = 0.5f;
    //Should have a probability to see if it should read or write.
    private enum State {
        READING, WRITING, NONE
    }
    private State state;
    private Book book;
    private int id;
    public Worker(Book book)
    {
        state = State.NONE;
        this.book = book;
        this.id = ID;
        ID++;
        this.setReadOrWrite();
    }
    
    @Override
    public void run()
    {
        try
        {
            while(!Thread.interrupted())
            {
                if(state.equals(State.READING))
                {
                    this.book.read(this);
                }
                else
                {
                    this.book.write(this);
                }
                this.setReadOrWrite();
            }
        }catch(InterruptedException e){
            System.out.println("Worker"+this+" is Interrupted!");
        };
    }
    
    public void setReadOrWrite()
    {
        if(Math.random() <= CHANCE)
        {
            this.state = State.WRITING;
        }
        else
        {
            this.state = State.READING;
        }
    }
    
    public String toString()
    {
        return ""+this.id;
    }
}
