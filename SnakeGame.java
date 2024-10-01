package snakegame;

import javax.swing.*; // importing JFrame which is in java extend swing package;

public class SnakeGame extends JFrame {

    SnakeGame(){
        super("Snake game"); // calling the parent constructor and setting the frame title
        add(new Board()); // to add , it should be a component(css) 
        pack(); // refresh the frame even it's open to add the thing and to show the changes on frame;
        
        
//      setLocation(1100,200); // position of the frame;
        
        setLocationRelativeTo(null); // this will align the frame to center of the screen;
        setResizable(true);
        
    }
    
    public static void main(String[] args) {
        new SnakeGame().setVisible(true); // there was always a frame not it will be visible
    }
    
}
