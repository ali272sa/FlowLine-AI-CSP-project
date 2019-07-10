/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package other;

import javafx.util.Pair;

/**
 *
 * @author Ali
 */
public class Board {
    
    private Pair<Integer,Integer> Coordinate;
    private int hCode;
    private boolean Socket;
    private  int SocketNum;

    /**
     * Coordinate of this block of array game
     * @return the Coordinate
     */
    public Pair<Integer,Integer> getCoordinate() {
        return Coordinate;
    }

    /**
     * @param x
     * @param y
     */
    public void setCoordinate(int x,int y) {
        Coordinate=new Pair(x,y);
        hCode=Coordinate.hashCode();
    }

    /**
     * its hash code of this block
     * @return the hCode
     */
    public int gethCode() {
        return hCode;
    }

    /**
     * @param hCode the hCode to set
     */
    public void sethCode(int hCode) {
        this.hCode = hCode;
    }

    /**
     * is this block a socket ?
     * @return the Socket
     */
    public boolean isSocket() {
        return Socket;
    }

    /**
     * @param Socket the Socket to set
     */
    public void setSocket(boolean Socket) {
        this.Socket = Socket;
    }

    /**
     * @return the SocketNum
     */
    public int getSocketNum() {
        return SocketNum;
    }

    /**
     * @param SocketNum the SocketNum to set
     */
    public void setSocketNum(int SocketNum) {
        this.SocketNum = SocketNum;
    }
    
}
