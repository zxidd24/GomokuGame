package gomokugame;

import java.awt.*;
/* Chess Piece Class */

public class Point {
  private int x; // x index in the chessboard
  private int y; // y index in the chessboard
  private Color color; // color
  public static final int DIAMETER = 30; // diameter
  
  public Point(int x, int y, Color color){
      this.x = x;
      this.y = y;
      this.color = color;
  } 
  
  public int getX(){ // Get the x index in the chessboard
      return x;
  }
  public int getY(){
      return y;
  }
  public Color getColor(){ // Get the color of the chess piece
      return color;
  }
}
