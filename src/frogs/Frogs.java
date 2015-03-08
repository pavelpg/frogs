/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frogs;

/**
 *
 * @author Павел
 */
public class Frogs {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Item[] state = new Item[9];
        state[0] = state[1] = state[2] = Item.greenFrog;
        state[3] = Item.empty;
        state[4] = state[5] = state[7] = Item.yellowFrog;
        Action[] actions = new Action[0];
        solve(state,actions);
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
    private static Item[] jump(Item[] state,int position, Direction dir)
    throws ImpossibleJumpException
    {
        Item[] s = state.clone();
        if(dir == Direction.left){
            if(position == 0) throw new ImpossibleJumpException();
            else if(s[position - 1] == Item.empty) exchange(s,position - 1,position);
            else if(position - 2 >= 0 && s[position - 2] == Item.empty)
                  exchange(s,position - 2, position);
            else throw new ImpossibleJumpException();
        }else{
            if(position == 8) throw new ImpossibleJumpException();
            else if(s[position + 1] == Item.empty) exchange(s, position, position + 1);
            else if(position + 2 <= 8 && s[position + 2] == Item.empty)
                exchange(s, position, position + 2);
            else throw new ImpossibleJumpException();
        }
        return s;
    }
    private static void copyArray(Object[] from, Object[] to){
        for(int i = 0; i < from.length; i++) to[i] = from[i];
    }
    private static void solve(Item[] state, Action[] actions){
        Item[] s;
        Action[] a  = new Action[actions.length + 1];
        
        for(int i = 0; i <= 8; i++){
            try{
                s = jump(state, i, Direction.left);
                copyArray(actions,a);
                a[a.length - 1] = new Action(i, Direction.left);
                solve(s, a);
            }catch(ImpossibleJumpException e){   
            }
            try{
                s = jump(state, i, Direction.right);
                copyArray(actions,a);
                a[a.length - 1] = new Action(i, Direction.right);
                solve(s, a);
            }catch(ImpossibleJumpException e){   
            }    
            
        }
    }
    
}
