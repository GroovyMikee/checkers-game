package draughts;

import java.io.Serializable;

public class Board implements Serializable{
   private DraughtPosition[] board = new DraughtPosition[32];
   private MoveList history = new MoveList();  // History of moves.
   protected Board next = null;     
 
     
   public Board copy() {
      Board newBoard = new Board();
      Coordinate temp = null;
      newBoard.setHistory(history.copy());
      for (int i = 1; i < 33; i++) {
         temp = new Coordinate(i);
         if (getDraught(temp) != null)
            newBoard.setDraught(getDraught(temp).copy(), temp);
      }
      return newBoard;
   }

             
   public DraughtPosition getDraught(Coordinate c) {
      return board[c.get() - 1];
   }
   
   
   public void setDraught(DraughtPosition draught, Coordinate c) {
      board[c.get() - 1] = draught;
      draught.setPosition(c);
   }
   
   
   public void removeDraught(DraughtPosition draught) {
      board[draught.getPosition().get() - 1] = null;
   }
   
   
   public boolean vacantCoordinate(Coordinate c) {
      return (getDraught(c) == null);
   }

   
   public int evaluate() {
      int score = 0;
      Coordinate c = null;
      for (int i = 1; i < 33; i++) {
         c = new Coordinate(i);
         if (getDraught(c) != null)
            score = score + getDraught(c).getValue();
      }
      return score;
   }
  

   public void setHistory(MoveList history) {
      this.history = history;
   }
   
   
   public MoveList getHistory() {
      return history;
   }


   public void addMoveToHistory(Move move) {
      history.add(move);
   }
   
   
   public void initialize() {
      initializeTop();
      initializeMiddle();
      initializeBottom();
   }     
  
  
   private void initializeTop() {
      for (int i = 1; i < 13; i++)
         board[i - 1] = new BlackDraught(new Coordinate(i));
   }     
   
   
   private void initializeMiddle() {
      for (int i = 13; i < 21; i++)
         board[i - 1] = null; 
   }   
  
  
   private void initializeBottom() {
      for (int i = 21; i < 33; i++)
         board[i - 1] = new WhiteDraught(new Coordinate(i));
   }
    
   
   // For the BoardList class.
   public void setNext(Board next) { 
      this.next = next;   
   }
   
   
   // For the BoardList class.
   public Board getNext() {
      return next;
   }
  
   
   public String toString() {
      String stringBoard[][] = new String[8][8];
      Coordinate temp = null;
      for (int i = 1;i < 33; i++) {
         temp = new Coordinate(i);
         if (getDraught(temp) != null)
            stringBoard[temp.column()][temp.row()] = getDraught(temp).toString();
      }
      String s = " +---+---+---+---+---+---+---+---+\n ";
      for (int y = 0; y < 8; y++) {
         for (int x = 0; x < 8; x++) {
            if (stringBoard[x][y] != null)
               s = s + "| " + stringBoard[x][y] + " ";
            else
               s = s + "|   ";
         }
         s = s + "| (" + ((y*4)+1) + "-" + ((y*4)+4) + ")";
         s = s + "\n +---+---+---+---+---+---+---+---+\\n ";
      }
      return s;
   }
}
