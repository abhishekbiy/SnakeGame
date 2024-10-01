
package snakegame;

import javax.swing.*;
import java.awt.*; // color class
import java.awt.event.*;
import static java.awt.font.TextAttribute.FONT;

/*{
ActionListener is interface which basicallt capture the action like click , keypress (we have to override the abstract method
Timer class constructor takes delay and actionListener
*/
public class Board extends JPanel implements ActionListener{ // dividing the frame into small panels like parent <div> to many other children <div>
    private Image apple;
    private Image dot;  // setting variables globally with class image;
    private Image head;
    
    private final int ALL_DOTS = 900;
    private final int DOT_SIZE = 10;
    
    private int RANDOM_POSITION = 19; // apple random position 
    
    private int apple_x;
    private int apple_y;
    
    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;
    
    private boolean inGame = true;
    
    private Timer timer;
    
    private final int x[] = new int[ALL_DOTS]; //dots x axis array
    private final int y[] = new int[ALL_DOTS]; //dots y axis array
    
    private int dots; // intializing dots globally so anybody can use/know the value ;
    
    Board(){
        
        addKeyListener(new TAdapter());
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(300,300));
        setFocusable(true); // capable of receiving focus
        
        loadImages(); // loading images which are basically snake dots running after eact other
        intiGame(); // refering to intialization of game
    }
    
    public void loadImages(){
        // loading image using ImageIcon class which is in JFrame and also using classLoader 
        // which basically helping to get resource from the system (the images)
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("snakegame/icons/apple.png"));
        apple = i1.getImage(); // getting the apple image from the folder;
        
        ImageIcon i2 = new ImageIcon(ClassLoader.getSystemResource("snakegame/icons/dot.png"));
        dot = i2.getImage(); // getting the dot image from the folder;

        ImageIcon i3 = new ImageIcon(ClassLoader.getSystemResource("snakegame/icons/head.png"));
        head = i3.getImage(); // getting the head image from the folder;
    }
    
    public void intiGame(){
        dots = 3; // 1 for head and 2 for body of snake
        
        for (int i = 0; i < dots; i++) {
            y[i] = 50;
            x[i] = 50 - i*DOT_SIZE;
        }
        
        locateApple();
        
        timer = new Timer(140,this);
        timer.start();
    }
    
    public void locateApple(){ // apple at random position
        int r = (int)(Math.random()*RANDOM_POSITION);
        apple_x = r*DOT_SIZE;
        
        r = (int)(Math.random() * RANDOM_POSITION);
        apple_y = r*DOT_SIZE;
    }
    
    public void paintComponent(Graphics g){ // to paint to images on the frame (Graphic class method)
        super.paintComponent(g); // the component is cleared and properly set up before your custom drawing code runs
        
        
        draw(g);
    }
    
    public void draw(Graphics g){
        if(inGame){
            g.drawImage(apple, apple_x, apple_y, this); // drawing apple at random position
            for (int i = 0; i < dots; i++) {
                if(i==0){
                    g.drawImage(head, x[i], y[i], this); // drawing head of the snake
                }else{
                    g.drawImage(dot, x[i], y[i], this); // drawing body of the snake
                }

            }
            Toolkit.getDefaultToolkit().sync(); //graphics pipeline, ensuring that any pending drawing operations are executed and the display is in sync
        }else{
            gameOver(g);
        }
    }
    
    public void gameOver(Graphics g){
        String msg = "Game Over";
        String score = "Score: "+(dots-3);
        Font font = new Font("SAN SERIF",Font.BOLD,16);
        FontMetrics metrices = getFontMetrics(font);
        
        g.setColor(Color.RED);
        g.setFont(font);
        g.drawString(msg, ((300 - metrices.stringWidth(msg))/2), 300/2);
        g.drawString(score, ((300 - metrices.stringWidth(score))/2), 170);
        
    }
        
    
    public void move(){
        for (int i = dots; i > 0; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        
        if(leftDirection){
            x[0] = x[0]-DOT_SIZE;
        }
        if(rightDirection){
            x[0] = x[0]+DOT_SIZE;
        }
        if(upDirection){
            y[0] = y[0]-DOT_SIZE;
        }
        if(downDirection){
            y[0] = y[0]+DOT_SIZE;
        }
        
    }
    
    public void checkApple(){
        if((x[0] == apple_x) &&(y[0] == apple_y)){
            dots++;
            locateApple();
        }
    }
    
    public void checkCollision(){
        for(int i=dots;i>0;i--){ // checking if the head collided with rest of the body
            if((i>4) && (x[0]==x[i]) && (y[0]==y[i])) {
                inGame = false;
            }
        }
        
        if(x[0]<0) inGame = false;
        if(x[0]>=300) inGame = false;
        if(y[0]<0) inGame = false;
        if(y[0]>=300) inGame = false;
        
        if(!inGame) timer.stop(); //if collided stop the snake
    }
    
    
    public void actionPerformed(ActionEvent ae){
        if(inGame){
            checkApple();
            checkCollision();
            move();
        }
        
        repaint();
    }
    
    public class TAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            
            int key = e.getKeyCode();
            
            if(key == KeyEvent.VK_LEFT && (!rightDirection)){
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }
            
            if(key == KeyEvent.VK_RIGHT && (!leftDirection)){
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }
            if(key == KeyEvent.VK_UP && (!downDirection)){
                upDirection = true;
                leftDirection = false;
                rightDirection = false;
            }
            if(key == KeyEvent.VK_DOWN&& (!upDirection)){
                downDirection = true;
                leftDirection = false;
                rightDirection = false;
            }
        }
        
    }
    
}
