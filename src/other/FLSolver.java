/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package other;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import javafx.util.Pair;

/**
 *
 * @author Ali
 */
public class FLSolver {
    private final Game game;
    private final PriorityQueue<Integer> variables;
    private final int[][] raw;
    public FLSolver(String s){
        raw=Game.initFile(s);//read game make raw board
        game = new Game(Game.n,Game.m); //make game
        game.initial(raw); //make pro map
        
        //----MRV---------------------------return 0;//simple back tracking----
        variables=new PriorityQueue<>((Integer o1, Integer o2) 
              -> game.getPaths().getPrz()[o1].size()-game.getPaths().getPrz()[o2].size() );
    }
    
    //==========================================================================
   
    public void runGame(){
        game.getPaths().makeAllPaths();
        
        for(int i=0;i<game.getSolves().length;i++)
            game.getSolves()[i]=null;
        
        for(int i=0;i<game.pSize;i++){
            variables.add(i);//game.pSize-1-
        }    
       backTracking();
    }//end func
    
   
    public void Successor(){}//end func  implement in main code
    
    private boolean isComplete(){
        for (ArrayList<Pair<Integer, Integer>> solve : game.getSolves()) {    
            if(solve==null || solve.isEmpty()) return false;
        }
        return true;
    }//end func
    
    private int cn=0;
    
    public boolean backTracking(){
        cn++;
        if(isComplete()) return true;
        Integer var=variables.poll();if(var == null) return false;//Use MRV within priority Q
        ArrayList<ArrayList<Pair<Integer,Integer>>>[] delP;
       
       LCV(var);// use lcv to sort the values ----------comment for without LCV-----------
       
        for (ArrayList<Pair<Integer, Integer>> value : game.getPaths().getPrz()[var]) {
            if(consistent(value)) {
                game.getSolves()[var]=value;
                //--------ForwardChecking----------------------------
                // /*---------------------uncoment to without FC
                delP = ForwardChecking(var,value);
                if(!Check()){
                    for(int k=0;k<game.pSize;k++){
                        if(k==var) continue;
                        game.getPaths().getPrz()[k].addAll(delP[k]);
                    }
                    game.getSolves()[var]=null;
                    continue ;
                }
                // ---------------------uncoment to without FC-------*/
                //---------------------------------------------------
                if(backTracking()) return true;
                game.getSolves()[var]=null;
            }
        }
        variables.add(var);
        return false;
    }//end func
    
    private boolean consistent(ArrayList<Pair<Integer, Integer>> value){
        for (ArrayList<Pair<Integer, Integer>> solve : game.getSolves()) {
            if(solve==null || solve.isEmpty()) {
            } else{
                List<Pair<Integer, Integer>> vs = value.subList(1, value.size()-1);
                if (!solve.stream().noneMatch((p) -> (vs.contains(p)))) {
                    return false;
                }
            }
        }
        return true;
    }//end func
    
    //-----------------------------------------------------------------------------------
    public void MRV(){}//end func//choose the variable with the fewest legal values implement in BT code
    
    public void LCV(int p){
        int[] lc=new int[game.getPaths().getPrz()[p].size()];
        for(int i=0;i<game.getPaths().getPrz()[p].size();i++){// number of choices for socket(p)
            lc[i]=0;
            for(int j=0;j<game.pSize;j++){//for all socket
                if(j==p) continue;
                for(ArrayList<Pair<Integer, Integer>> path : game.getPaths().getPrz()[j]){//all pathes
                    if(conflict(path,game.getPaths().getPrz()[p].get(i))) lc[i]++;
                }  
            }
        }
        //use number of conflict as lcv
        game.getPaths().getPrz()[p].sort((ArrayList<Pair<Integer, Integer>> o1, ArrayList<Pair<Integer, Integer>> o2) 
                -> {
            return lc[game.getPaths().getPrz()[p].indexOf(o1)]-lc[game.getPaths().getPrz()[p].indexOf(o2)];
        });

    }//end func

    private boolean conflict(ArrayList<Pair<Integer, Integer>> p1,ArrayList<Pair<Integer, Integer>> p2){    
        return p1.stream().anyMatch((p) -> (p2.contains(p)));
    }
    
    
    public ArrayList<ArrayList<Pair<Integer, Integer>>>[] ForwardChecking(int p,ArrayList<Pair<Integer, Integer>> s){
        ArrayList<ArrayList<Pair<Integer,Integer>>>[] delP = new ArrayList[game.pSize];
        for(int i=0;i<game.pSize;i++){
            delP[i]=new ArrayList<>();
        }

        for(int j=0;j<game.pSize;j++){//for all socket
           
            if(j==p) continue;
            
            for(ArrayList<Pair<Integer, Integer>> path : game.getPaths().getPrz()[j]){//all pathes
                if(conflict(path,s))  delP[j].add(path);
            }
            game.getPaths().getPrz()[j].removeAll(delP[j]);
        }
        return delP;
    }//end func
    
    private boolean Check(){
        for(int i=0;i<game.pSize;i++)
            if(game.getPaths().getPrz()[i].isEmpty() && (game.getSolves()[i]==null ||game.getSolves()[i].isEmpty()) ){
                return false;
            }
        return true;
    }
    
    //--------------------------------------------------------------------------
    public void showResult(String Address){
        System.out.println("loop "+cn);
        for (int i=0;i<game.getSolves().length;i++) {
            if(game.getSolves()[i]==null ){
                System.out.println("no answer");
                return;
            }
            for (Pair<Integer,Integer> pair : game.getSolves()[i]) {
                raw[pair.getKey()-1][pair.getValue()-1]=i+1;
            } 
        }
        String sRaw = new String();
        for(int i=0;i<game.bSize;i++){
            for(int j=0;j<game.bSize;j++){
                sRaw+=raw[i][j]+" ";
                System.out.print(raw[i][j]+" ");
            }
            sRaw+="\r\n";
            System.out.println("");   
        }
        
        game.writeGame(Address, sRaw);
    }//end func
    
    //==========================================================================

}
