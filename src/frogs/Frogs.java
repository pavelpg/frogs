/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frogs;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Павел
 */
public class Frogs {

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        actionsList = new ArrayList<Action[]>();
        Item[] state = new Item[7];
        state[0] = state[1] = state[2] = Item.greenFrog;
        state[3] = Item.empty;
        state[4] = state[5] = state[6] = Item.yellowFrog;
        Item[] finalState = new Item[7];
        finalState[0] = finalState[1] = finalState[2] = Item.yellowFrog;
        finalState[3] = Item.empty;
        finalState[4] = finalState[5] = finalState[6] = Item.greenFrog;
        Action[] actions = new Action[0];
        solve(state,actions,finalState);
        System.out.print(nSuccessfulJumps);
   }
    enum Direction {left, right};
    private static class Action 
    {
        public int position;
        public Direction direction;
        public Action(int position, Direction direction){
            this.position = position;
            this.direction = direction;
        }
    };

    enum Item {empty,greenFrog,yellowFrog};
    static class ImpossibleJumpException extends Exception{
        

    
    };
    
    private static void exchange(Item[] state, int pos1, int pos2){
        Item tmp;
        tmp = state[pos1];
        state[pos1] = state[pos2];
        state[pos2] = tmp;
    }
    private static int nSuccessfulJumps = 0, nTotalBranches = 0, nSuccesfulBranches = 0;
    private static Item[] jump(Item[] state,int position, Direction dir)
    throws ImpossibleJumpException
    {
        Item[] s = state.clone();
        if(dir == Direction.left && state[position] == Item.yellowFrog){
            if(position == 0) throw new ImpossibleJumpException();
            else if(s[position - 1] == Item.empty) exchange(s,position - 1,position);
            else if(position - 2 >= 0 && s[position - 2] == Item.empty)
                  exchange(s,position - 2, position);
            else throw new ImpossibleJumpException();
        }else if(dir == Direction.right && state[position] == Item.greenFrog) {
            if(position == 6) throw new ImpossibleJumpException();
            else if(s[position + 1] == Item.empty) exchange(s, position, position + 1);
            else if(position + 2 <= 6 && s[position + 2] == Item.empty)
                exchange(s, position, position + 2);
            else throw new ImpossibleJumpException();
        } else throw new ImpossibleJumpException();
        nSuccessfulJumps ++;
        return s;
    }
    private static void copyArray(Object[] from, Object[] to){
        for(int i = 0; i < from.length; i++) to[i] = from[i];
    }
    private static List<Action[]> actionsList;
    private static void solve(Item[] state, Action[] actions, Item[] finalState){
        
        Item[] s;
        Action[] a  = new Action[actions.length + 1];
        boolean differs = false;
        for(int j = 0 ; j <= 6; j++){
            if(state[j] != finalState[j]){
                differs = true;
                break;
            }
        }
        if(!differs){
            actionsList.add(actions.clone());
            nSuccesfulBranches ++;
            nTotalBranches ++; 
        }else{
            boolean bFinalStep = true;
            for(int i = 0; i <= 6; i++){
                try{
                    s = jump(state, i, Direction.left);
                    bFinalStep = false;
                    copyArray(actions,a);
                    a[a.length - 1] = new Action(i, Direction.left);
                    solve(s, a, finalState);
                    
                }catch(ImpossibleJumpException e){   
                }
                try{
                    s = jump(state, i, Direction.right);
                    bFinalStep = false;
                    copyArray(actions,a);
                    a[a.length - 1] = new Action(i, Direction.right);
                    solve(s, a, finalState);
                }catch(ImpossibleJumpException e){   
                }  
                if(bFinalStep) nTotalBranches ++;

            }
        }
    }
    
}
