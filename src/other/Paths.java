/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package other;

import java.util.ArrayList;
import java.util.Iterator;
import javafx.util.Pair;


/**
 *
 * @author Ali
 */
public class Paths {
 
    private int PRZ_NUM;
    private Game game;
    private final ArrayList<ArrayList<Pair<Integer,Integer>>>[] prz;

    public Paths(Game game){
        this.game=game;
        PRZ_NUM=game.pSize;
        prz=new ArrayList[PRZ_NUM]; /*array of prz each prz has pathes those are set of pair*/
        // making arrayes
        MAX_N=game.bSize+1;
        for(int i=0;i<PRZ_NUM;i++){
            prz[i]=new ArrayList<>();
        }
    }

    /**
     * if there is one common path block
     * @param path1
     * @param path2
     * @return 
     */
    public boolean isThereConflict(ArrayList<Pair<Integer,Integer>> path1 ,ArrayList<Pair<Integer,Integer>> path2){
        
        for (Iterator<Pair<Integer, Integer>> it = path1.iterator(); it.hasNext();) 
            if(path2.contains(it.next())) return true;
        
        return false;
    }
 
    public void makeAllPaths(){
      
        for(int i=0;i<getGame().socketsCor.length;i++){
            //System.out.println("light  "+(i+1));
            //game.getPaths().
            getPathes(i,getGame().socketsCor[i][0],getGame().socketsCor[i][1]);
        }
    }
    
    private int endx, endy; //x and y position of end point
    private final int MAX_N;
    private int startx, starty; //x and y position of start point
    private int m,n;
    private ArrayList<Pair<Integer,Integer>> cur_path;
    private boolean[][] mark;
 
    /**
     * get light number and start and end and make all possible paths
     * @param light
     * @param start
     * @param end
     */
    public void getPathes(int light,Pair<Integer,Integer> start,Pair<Integer,Integer> end){

        cur_path=new ArrayList();
        mark=new boolean[MAX_N][];
        for(int i=0;i<MAX_N;i++) mark[i]=new boolean[MAX_N];
        for(int i=0;i<MAX_N;i++)
            for(int j=0;j<MAX_N;j++)
                mark[i][j]=false;
        n=getGame().bSize;m=getGame().bSize;
        startx=start.getKey();starty=start.getValue();
        endx=end.getKey();endy=end.getValue();
        
        go(startx,starty,getPrz()[light], m, n);
    }
    
    //==========================================================================
    private boolean valid(int x, int y,boolean[][] mark,int m,int n) {

	if(x <= 0 || x > n || y <= 0 || y > m)
            return false;
        if((x==startx &&y==starty) || (x==endx && y==endy)){//if wasn't start and end if was socket return
        }else if(getGame().getBoard()[x-1][y-1].isSocket()) return false;
        return !mark[x][y];
    }
    
    private void go(int x, int y,ArrayList<ArrayList<Pair<Integer,Integer>>> paths,int m,int n) {
        
	mark[x][y] = true;//determine this point is visited
	cur_path.add(getGame().getBoard()[x-1][y-1].getCoordinate());//we add this point to our current path

	if(x == endx && y == endy) {//if this point is the end point
		paths.add((ArrayList<Pair<Integer,Integer>>)cur_path.clone());//add this current path to paths
		return;
	}

	if(valid(x+1, y,mark, m, n)) { 
		go(x+1, y,paths, m, n);
		cur_path.remove(cur_path.size()-1);
		mark[x+1][y] = false;
	}
	if(valid(x-1, y,mark, m, n)) {
		go(x-1, y,paths, m, n);
		cur_path.remove(cur_path.size()-1);
		mark[x-1][y] = false;
	}
	if(valid(x, y+1,mark, m, n)) {
		go(x, y+1,paths, m, n);
		cur_path.remove(cur_path.size()-1);
		mark[x][y+1] = false;
	}
	if(valid(x, y-1,mark,m, n)) {
		go(x, y-1,paths, m, n);
		cur_path.remove(cur_path.size()-1);
		mark[x][y-1] = false;
	}
        
    }
    //==========================================================================
    /**
     * @return the PRZ_NUM
     */
    public int getPRZ_NUM() {
        return PRZ_NUM;
    }

    /**
     * @param PRZ_NUM the PRZ_NUM to set
     */
    public void setPRZ_NUM(int PRZ_NUM) {
        this.PRZ_NUM = PRZ_NUM;
    }

    /**
     * @return the game
     */
    public Game getGame() {
        return game;
    }

    /**
     * @param game the game to set
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**array of all paths for each sockets
     * @return the prz
     */
    public ArrayList<ArrayList<Pair<Integer,Integer>>>[] getPrz() {
        return prz;
    }

}
