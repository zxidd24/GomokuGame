package gomokugame;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

/* Gomoku Main Framework Class, Program Startup Class */
public class StartChess extends JFrame {
  private ChessBoard chessBoard;
  private JPanel toolbar;
  private JButton startButton, backButton, exitButton, rulesButton;

  private JMenuBar menuBar;
  private JMenu sysMenu;
  private JMenuItem startMenuItem, exitMenuItem, backMenuItem, rulesMenuItem;
  // restart, exit, regret chess, and rules menu items
  public StartChess() {
    setTitle("Gomoku Game"); // Set the title
    chessBoard = new ChessBoard();

    Container contentPane = getContentPane();
    contentPane.add(chessBoard);
    chessBoard.setOpaque(true);

    // Create and add menu
    menuBar = new JMenuBar(); // Initialize the menu bar
    sysMenu = new JMenu("System"); // Initialize the menu
    // Set menu icon
    ImageIcon sysIcon = new ImageIcon("image/1.png");
    Image sysImage = sysIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
    sysIcon = new ImageIcon(sysImage);
    sysMenu.setIcon(sysIcon);
    // Initialize menu items
    startMenuItem = new JMenuItem("restart");
    exitMenuItem = new JMenuItem("exit");
    backMenuItem = new JMenuItem("regret chess");
    rulesMenuItem = new JMenuItem("rules Introduction");
    // Add menu items to the menu
    sysMenu.add(startMenuItem);
    sysMenu.add(exitMenuItem);
    sysMenu.add(backMenuItem);
    sysMenu.add(rulesMenuItem);
    // Initialize button event listener inner class
    MyItemListener lis = new MyItemListener();
    // Register menu items to the event listener
    startMenuItem.addActionListener(lis);
    exitMenuItem.addActionListener(lis);
    backMenuItem.addActionListener(lis);
    rulesMenuItem.addActionListener(lis);
    menuBar.add(sysMenu); // Add the system menu to the menu bar
    setJMenuBar(menuBar); // Set the menuBar as the menu bar

    toolbar = new JPanel(); // Instantiate the tool panel
    // Initialize the four buttons
    startButton = new JButton("restart", new ImageIcon(new ImageIcon("image/2.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
    exitButton = new JButton("exit", new ImageIcon(new ImageIcon("image/3.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
    backButton = new JButton("regret chess", new ImageIcon(new ImageIcon("image/4.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
    rulesButton = new JButton("rules Introduction", new ImageIcon(new ImageIcon("image/5.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
    // Set FlowLayout for the tool panel buttons
    toolbar.setLayout(new FlowLayout(FlowLayout.LEFT));
    // Add the four buttons to the tool panel
    toolbar.add(startButton);
    toolbar.add(exitButton);
    toolbar.add(backButton);
    toolbar.add(rulesButton);
    // Register button event listeners
    startButton.addActionListener(lis);
    exitButton.addActionListener(lis);
    backButton.addActionListener(lis);
    rulesButton.addActionListener(lis);
    // Layout the tool panel at the "south" of the interface, i.e., at the bottom
    add(toolbar, BorderLayout.SOUTH);
    add(chessBoard); // Add the panel object to the frame
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    pack(); // Auto-adjust size
  }

  private class MyItemListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      Object obj = e.getSource(); // Get the event source
      if (obj == StartChess.this.startMenuItem || obj == startButton) {
        // Restart
        System.out.println("restart");
        chessBoard.restartGame();
      } else if (obj == exitMenuItem || obj == exitButton)
        System.exit(0);
      else if (obj == backMenuItem || obj == backButton) {
        System.out.println("regret chess...");
        chessBoard.goback();
      } else if (obj == rulesMenuItem || obj == rulesButton) {
        // Display rules Introduction
        String rules = "The two sides take turns playing black and white chess at the intersection of the straight line and the horizontal line on the chessboard, and the person who first forms a 5-piece connection on the horizontal line, straight line or diagonal line wins.";
        JTextArea textArea = new JTextArea(rules);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(300, 200));
        JOptionPane.showMessageDialog(null, scrollPane);
      }
    }
  }

  public static void main(String[] args) {
    StartChess f = new StartChess(); // Create the main frame
    f.setVisible(true); // Display the main frame
  }
}

