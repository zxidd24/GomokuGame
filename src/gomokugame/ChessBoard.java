package gomokugame;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;

/* Gomoku - Chessboard Class */

public class ChessBoard extends JPanel implements MouseListener {
   public static final int MARGIN=30; // Margin
   public static final int GRID_SPAN=35; // Grid spacing
   public static final int ROWS=15; // Number of rows on the chessboard
   public static final int COLS=15; // Number of columns on the chessboard
   
   Point[] chessList=new Point[(ROWS+1)*(COLS+1)]; // Each array element is initially null
   boolean isBlack=true; // Black chess starts by default
   boolean gameOver=false; // Indicates if the game is over
   int chessCount; // Number of chess pieces on the current chessboard
   int xIndex,yIndex; // Index of the most recently placed chess piece
   
   Image img;
   Color colortemp;
   
   public ChessBoard(){
      
      // Set background image
      img=Toolkit.getDefaultToolkit().getImage("image/board.jpg");
      addMouseListener(this);
      addMouseMotionListener(new MouseMotionListener(){
         public void mouseDragged(MouseEvent e){
            
         }
         
         public void mouseMoved(MouseEvent e){
            int x1=(e.getX()-MARGIN+GRID_SPAN/2)/GRID_SPAN;
            // Convert the coordinates of the mouse click into grid indices
            int y1=(e.getY()-MARGIN+GRID_SPAN/2)/GRID_SPAN;
            // Cannot place a chess piece if the game is over
            // Cannot place a chess piece outside the chessboard
            // Cannot place a chess piece on a position that already has a chess piece
            if(x1<0||x1>ROWS||y1<0||y1>COLS||gameOver||findChess(x1,y1))
               setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            // Set cursor to default state
            else setCursor(new Cursor(Cursor.HAND_CURSOR));
            
         }
      });
   } 

   
  
 
//Drowing
   public void paintComponent(Graphics g){
	 
	   super.paintComponent(g);// Draw the chessboard
	 
	   int imgWidth= img.getWidth(this);
	   int imgHeight=img.getHeight(this);
	   int FWidth=getWidth();
	   int FHeight=getHeight();
	   int x=(FWidth-imgWidth)/2;
	   int y=(FHeight-imgHeight)/2;
	   g.drawImage(img, x, y, null);
	
	   
	   for(int i=0;i<=ROWS;i++){//Draw horizontal lines
		   g.drawLine(MARGIN, MARGIN+i*GRID_SPAN, MARGIN+COLS*GRID_SPAN, MARGIN+i*GRID_SPAN);
	   }
	   for(int i=0;i<=COLS;i++){//Draw vertical lines
		   g.drawLine(MARGIN+i*GRID_SPAN, MARGIN, MARGIN+i*GRID_SPAN, MARGIN+ROWS*GRID_SPAN);
		   
	   }

	   
	   
	// Draw chess pieces
	   for(int i=0;i<chessCount;i++){
	      // Coordinates of the intersection point of the grid
	      int xPos=chessList[i].getX()*GRID_SPAN+MARGIN;
	      int yPos=chessList[i].getY()*GRID_SPAN+MARGIN;
	      g.setColor(chessList[i].getColor());// Set color
	      
	      colortemp=chessList[i].getColor();
	      if(colortemp==Color.black){
	          RadialGradientPaint paint = new RadialGradientPaint(xPos-Point.DIAMETER/2+25, yPos-Point.DIAMETER/2+10, 20, new float[]{0f, 1f}
	          , new Color[]{Color.WHITE, Color.BLACK});
	          ((Graphics2D) g).setPaint(paint);
	          ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	          ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_DEFAULT);

	      }
	      else if(colortemp==Color.white){
	          RadialGradientPaint paint = new RadialGradientPaint(xPos-Point.DIAMETER/2+25, yPos-Point.DIAMETER/2+10, 70, new float[]{0f, 1f}
	          , new Color[]{Color.WHITE, Color.BLACK});
	          ((Graphics2D) g).setPaint(paint);
	          ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	          ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_DEFAULT);

	      }

	      Ellipse2D e = new Ellipse2D.Float(xPos-Point.DIAMETER/2, yPos-Point.DIAMETER/2, 34, 35);
	      ((Graphics2D) g).fill(e);
	      // Mark the red rectangle for the last chess piece
	      
	      if(i==chessCount-1){// If it is the last chess piece
	          g.setColor(Color.red);
	          g.drawRect(xPos-Point.DIAMETER/2, yPos-Point.DIAMETER/2,
	                     34, 35);
	      }
	      
	   }
	   }

   
   public void mousePressed(MouseEvent e){// Called when the mouse is pressed on the component

	   // Cannot place a chess piece if the game is over
	   if(gameOver) return;

	   String colorName=isBlack?"black chess":"White chess";

	   // Convert the coordinates of the mouse click into grid indices
	   xIndex=(e.getX()-MARGIN+GRID_SPAN/2)/GRID_SPAN;
	   yIndex=(e.getY()-MARGIN+GRID_SPAN/2)/GRID_SPAN;

	   // Cannot place a chess piece outside the chessboard
	   if(xIndex<0||xIndex>ROWS||yIndex<0||yIndex>COLS)
	       return;

	   // Cannot place a chess piece on a position that already has a chess piece
	   if(findChess(xIndex,yIndex))return;

	   // Processing when it is allowed to place a chess piece
	   Point ch=new Point(xIndex,yIndex,isBlack?Color.black:Color.white);
	   chessList[chessCount++]=ch;
	    repaint(); // Notify the system to redraw

	   // If a player wins, display a message and the game cannot continue

	   if(isWin()){
	       String msg=String.format("Congratulations, %s wins!", colorName);
	       JOptionPane.showMessageDialog(this, msg);
	       gameOver=true;
	   }
	   isBlack=!isBlack;
	}

// Override methods of mouseListener
public void mouseClicked(MouseEvent e){
   // Called when the mouse button is clicked on the component
}

public void mouseEntered(MouseEvent e){
   // Called when the mouse enters the component
}

public void mouseExited(MouseEvent e){
   // Called when the mouse exits the component
}

public void mouseReleased(MouseEvent e){
   // Called when the mouse button is released on the component
}

// Check if there is a chess piece with the index (x, y) in the chess array
private boolean findChess(int x, int y){
   for(Point c : chessList){
       if(c != null && c.getX() == x && c.getY() == y)
           return true;
   }
   return false;
}

private boolean isWin(){
   int continueCount = 1; // Count of consecutive chess pieces

   // Search westward horizontally
   for(int x = xIndex - 1; x >= 0; x--){
       Color c = isBlack ? Color.black : Color.white;
       if(getChess(x, yIndex, c) != null){
           continueCount++;
       }else
           break;
   }
   // Search eastward horizontally
   for(int x = xIndex + 1; x <= COLS; x++){
       Color c = isBlack ? Color.black : Color.white;
       if(getChess(x, yIndex, c) != null){
           continueCount++;
       }else
           break;
   }
   if(continueCount >= 5){
         return true;
   }else 
   continueCount = 1;

   // Continue searching vertically
   // Search upward
   for(int y = yIndex - 1; y >= 0; y--){
       Color c = isBlack ? Color.black : Color.white;
       if(getChess(xIndex, y, c) != null){
           continueCount++;
       }else
           break;
   }
   // Search downward vertically
   for(int y = yIndex + 1; y <= ROWS; y++){
       Color c = isBlack ? Color.black : Color.white;
       if(getChess(xIndex, y, c) != null)
           continueCount++;
       else
          break;
   }
   if(continueCount >= 5)
       return true;
   else
       continueCount = 1;

   // Continue searching diagonally
   // Search northeast
   for(int x = xIndex + 1, y = yIndex - 1; y >= 0 && x <= COLS; x++, y--){
       Color c = isBlack ? Color.black : Color.white;
       if(getChess(x, y, c) != null){
           continueCount++;
       }
       else break;
   }
   // Search southwest
   for(int x = xIndex - 1, y = yIndex + 1; x >= 0 && y <= ROWS; x--, y++){
       Color c = isBlack ? Color.black : Color.white;
       if(getChess(x, y, c) != null){
           continueCount++;
       }
       else break;
   }
   if(continueCount >= 5)
       return true;
   else continueCount = 1;

   // Continue searching diagonally
   // Search northwest
   for(int x = xIndex - 1, y = yIndex - 1; x >= 0 && y >= 0; x--, y--){
       Color c = isBlack ? Color.black : Color.white;
       if(getChess(x, y, c) != null)
           continueCount++;
       else break;
   }
   // Search southeast
   for(int x = xIndex + 1, y = yIndex + 1; x <= COLS && y <= ROWS; x++, y++){
       Color c = isBlack ? Color.black : Color.white;
       if(getChess(x, y, c) != null)
           continueCount++;
       else break;
   }
   if(continueCount >= 5)
       return true;
   else continueCount = 1;

   return false;
 }

private Point getChess(int xIndex, int yIndex, Color color){
   for(Point p : chessList){
       if(p != null && p.getX() == xIndex && p.getY() == yIndex
               && p.getColor() == color)
           return p;
   }
   return null;
}

public void restartGame(){
   // Clear chess pieces
   for(int i = 0; i < chessList.length; i++){
       chessList[i] = null;
   }
   // Reset game-related variables
   isBlack = true;
   gameOver = false; // Is the game over?
   chessCount = 0; // Current number of chess pieces on the board
   repaint();
}

// Undo a move
public void goback(){
   if(chessCount == 0)
       return;
   chessList[chessCount - 1] = null;
   chessCount--;
   if(chessCount > 0){
       xIndex = chessList[chessCount - 1].getX();
       yIndex = chessList[chessCount - 1].getY();
   }
   isBlack = !isBlack;
   repaint();
}

// Rectangle Dimension
public Dimension getPreferredSize(){
   return new Dimension(MARGIN * 2 + GRID_SPAN * COLS, MARGIN * 2 + GRID_SPAN * ROWS);
} 
}

