/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package other;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;

/**
 *
 * @author Ali
 */
public class Game {
    
    public  final int bSize;
    public  final int pSize;
    private Paths paths;
    private Board[][] board;
    public Pair<Integer,Integer>[][] socketsCor;
    
    private ArrayList<Pair<Integer,Integer>>[] solves;
    //private boolean sComplete
    
    
    public Game(int bSize,int pSize){//N , M
        this.bSize=bSize;
        this.pSize=pSize;
        board=new Board[bSize][];
        for(int i=0;i<bSize;i++)
            board[i]=new Board[bSize];
        for(int i=0;i<bSize;i++)
            for(int j=0;j<bSize;j++){
                board[i][j]=new Board();
                board[i][j].setCoordinate(i+1, j+1);//1 to m
                
            }
        socketsCor =new Pair[pSize][2];
        
        paths=new Paths(this);
        
    }
    
    /**
     * get raw array and initialize board and make other variables
     * @param raw 
     */
    public void initial(int[][] raw){//Array
        
        for(int i=0;i<bSize;i++)
            for(int j=0;j<bSize;j++){
                if(raw[i][j]!=0){
                    board[i][j].setSocketNum(raw[i][j]);
                    getBoard()[i][j].setSocket(true);
                    if(socketsCor[raw[i][j]-1][0]==null) socketsCor[raw[i][j]-1][0]=new Pair(i+1,j+1);
                    else socketsCor[raw[i][j]-1][1]=new Pair(i+1,j+1);//value is sync with getpath array+1 !
                    
                }else{
                    
                    board[i][j].setSocketNum(0);
                    getBoard()[i][j].setSocket(false);
                }
            }
        
        setSolves((ArrayList<Pair<Integer, Integer>>[]) new ArrayList[pSize]);
    }
    
    public static int n,m;
    
    /**
     * read file and initialize raw array 
     * @param address
     * @return 
     */
    public  static int[][] initFile(String address){

        int[][] raw;
        Scanner sc;// = null;
        try {
            sc = new Scanner(new File(address));
        } catch (FileNotFoundException ex) {
            System.out.println("readfile error....");
             Logger.getLogger("error   ").log(Level.SEVERE, null, ex);
            return null;
        }
        try{
                
            n=sc.nextInt();
            m=sc.nextInt();
            if(n>30) return null;
            
            raw=new int[n][];
            for(int i=0;i<n;i++)
                raw[i]=new int[n];
            for(int i=0;i<n;i++)
                for(int j=0;j<n;j++)
                    raw[i][j]=sc.nextInt();
            sc.close(); 
        } catch (Exception e) {
            System.out.println("in file part error in read file ");
            System.out.println(e.getMessage());
            return null;
        }
        
        return raw;
    }
    public void writeGame(String Address,String s){
       File file = new File(Address);
       try (PrintWriter pw = new PrintWriter(file)) {
           pw.print(s);
           pw.close();
       } catch (FileNotFoundException ex) {
           Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
       }
        
    }
    

    /**
     * @return the paths
     */
    public Paths getPaths() {
        return paths;
    }

    /**
     * @param paths the paths to set
     */
    public void setPaths(Paths paths) {
        this.paths = paths;
    }

    /**
     * @return the board
     */
    public Board[][] getBoard() {
        return board;
    }

    /**
     * @param board the board to set
     */
    public void setBoard(Board[][] board) {
        this.board = board;
    }

    /**
     * @return the solves
     */
    public ArrayList<Pair<Integer,Integer>>[] getSolves() {
        return solves;
    }

    /**
     * @param solves the solves to set
     */
    public void setSolves(ArrayList<Pair<Integer,Integer>>[] solves) {
        this.solves = solves;
    }

}

