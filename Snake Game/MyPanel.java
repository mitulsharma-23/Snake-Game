import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class MyPanel extends JPanel implements KeyListener, ActionListener {
    //Importing an image from this folder
    //Writing the name of the image that we want to import
    ImageIcon SnakeTitle = new ImageIcon(getClass().getResource("snaketitle.jpg")); //Importing snake title icon
    ImageIcon snakeimage = new ImageIcon(getClass().getResource("snakeimage.png")); //Importing snake image icon (body)
//    ImageIcon snakeimage = new ImageIcon(getClass().getResource("blueshape.png"));
    ImageIcon right = new ImageIcon((getClass().getResource("rightmouth.png"))); //Importing right face snake icon
    ImageIcon up = new ImageIcon((getClass().getResource("upmouth.png"))); //Importing up face snake icon
    ImageIcon down = new ImageIcon((getClass().getResource("downmouth.png"))); //Importing down face snake icon
    ImageIcon left = new ImageIcon((getClass().getResource("leftmouth.png"))); //Importing left face snake icon
    ImageIcon food = new ImageIcon(getClass().getResource("enemy.png")); //Importing food icon

    boolean isup = false; //Initially snake head will not face up
    boolean isright = true; //Initially snake head will face right
    boolean isdown = false; //Initially snake head will not face down
    boolean isleft = false; //Intially snake head will not face left

    //Creating 2 arrays xpos and ypos from which the food will appear randomly using these indices
    int []xpos = {25, 50, 75, 100, 125, 150, 175, 200, 225, 250, 275, 300, 325, 350, 375, 400, 425, 450, 475, 500, 525, 550, 575, 600, 625, 650, 675, 700, 725, 750, 775, 800, 825, 850};
    int []ypos = {75, 100, 125, 150, 175, 200, 225, 250, 275, 300, 325, 350, 375, 400, 425, 450, 475, 500, 525, 550, 575, 600, 625};

    Random random = new Random(); //Creating a random function for the food

    //Initially the food icon would be at this x and y position
    int foodx = 150;
    int foody = 150;

    //Creating array of size 750 (size of panel) to show snake's body using x and y axis
    int []snakex = new int[750];
    int []snakey = new int[750];
    int move = 0; //Total moves by snake initially
    int snakelength = 3; //Total length of snake initially

    Timer time; //Creating time function
    boolean gameover = false; //Setting boolean value to indicate whether game is over or not
    int score = 0; //Score to be measured during the game
    MyPanel(){
        addKeyListener(this);
        setFocusable(true);
        time = new Timer(150, this); //Delay of movement of snake
        time.start(); //Starting the game
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.white);
        //Setting up the title rectangle
        g.drawRect(24, 10 ,851, 55);
        //Setting up the main rectangle where we want to play the snake game
        g.drawRect(24, 74, 851, 576);
        //Calling the image function and pasting it in the title rectangle
        SnakeTitle.paintIcon(this, g, 25, 11);
        //Setting the color for the main rectangle as black
        g.setColor(Color.BLACK);
        //Filling the rectangle with the color specifying the size
        g.fillRect(25, 75, 850, 575);

        //Initially setting up the snake body at a particular location in the panel for both x-axis and y-axis
        if(move == 0){
            snakex[0] = 100;
            snakex[1] = 75;
            snakex[2] = 50;

            snakey[0] = 100;
            snakey[1] = 100;
            snakey[2] = 100;
        }


        if(isright) right.paintIcon(this, g, snakex[0], snakey[0]);
        if(isleft) left.paintIcon(this, g, snakex[0], snakey[0]);
        if(isup) up.paintIcon(this, g, snakex[0], snakey[0]);
        if(isdown) down.paintIcon(this, g, snakex[0], snakey[0]);

        //Setting the rest of the body of the snake
        for(int i=1; i<snakelength; i++){
            snakeimage.paintIcon(this, g, snakex[i], snakey[i]);
        }
        food.paintIcon(this, g, foodx, foody); //Showing the food icon in the panel at desired coordinates

        //If the game is over
        if(gameover){
            g.setColor(Color.WHITE); //Setting the color of the font for the text appearing after game over
            g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30)); //Setting the font and font style and font size for the text appearing after game over
            g.drawString("GAME OVER!!!", 300, 350); //Setting the text and location appearing after game over
            g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20)); //Setting the font and font style and font size for the text to play again
            g.drawString("Click space to try again.", 300, 380); //Setting the string at desired location to restart the game
        }
        //Setting score and length of the snake to appear above the panel in text to keep note during the game
        g.setColor(Color.white);
        g.setFont(new Font("ITALIC", Font.PLAIN, 15));
        g.drawString("Score " + score, 750, 30);
        g.drawString("Length " + snakelength, 750, 50);
        g.dispose(); //End
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //No idea what this does but was automatically added
    }

    @Override
    //Incase of pressing any key then adding the functionality
    public void keyPressed(KeyEvent e) {

        //If game over and want to restart the press the space bar
        if(e.getKeyCode() == KeyEvent.VK_SPACE && (gameover)){
            restart(); //Calling the restart function
        }

        //If pressed the right key or d and the snake is not facing left
        if((e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) && (!isleft)){
            //Setting the location of the snake
            isright = true;
            isleft = false;
            isup = false;
            isdown = false;
            move++;
        }

        //If pressed the left key or d and the snake is not facing right
        if((e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) && (!isright)){
            //Setting the location of the snake
            isleft = true;
            isright = false;
            isup = false;
            isdown = false;
            move++;
        }

        //If pressed the up key or w and the snake is not facing down
        if((e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) && (!isdown)){
            //Setting the location of the snake
            isup = true;
            isleft = false;
            isright = false;
            isdown = false;
            move++;
        }

        //If pressed the down key or s and the snake is not facing up
        if((e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) && (!isup)){
            //Setting the location of the snake
            isdown = true;
            isleft = false;
            isright = false;
            isup = false;
            move++;
        }
    }

    private void restart() { //Incase of pressing the space bar to restart the game
        //Setting the parameters for a fresh game
        gameover = false;
        move = 0;
        score = 0;
        snakelength = 3;
        isleft = false;
        isright = true; //Again the snake head facing the right
        isup = false;
        isdown = false;
        time.start(); //Again starting the time
        newfood(); //Calling this function so that the food does not appear at the same location like in the last game
        repaint(); //Calling the paint function to execute the game
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //No idea what this does but was automatically added
    }

    @Override
    //Incase of pressing any key then adding the functionalities
    public void actionPerformed(ActionEvent e) {
        //Increasing the length of the snake by this 2 line simple logic
        for(int i = snakelength-1; i>0; i--){
            snakex[i] = snakex[i-1];
            snakey[i] = snakey[i-1];
        }

        //Depending on the direction of the head of the snake, the length of the snake is added accordingly
        if(isleft) snakex[0] = snakex[0] - 25;
        if(isright) snakex[0] = snakex[0] + 25;
        if(isup) snakey[0] = snakey[0] - 25;
        if(isdown) snakey[0] = snakey[0] + 25;

        //Making sure that when the snake crosses the boundary then it appears from the opposite side
        if(snakex[0] > 850) snakex[0] = 25;
        if(snakex[0] < 25) snakex[0] = 850;
        if(snakey[0] > 625) snakey[0] = 75;
        if(snakey[0] < 75) snakey[0] = 625;

        CollisionWithFood(); //Incase the snake collides with the food
        CollosionWithSelf(); //Incase the snake collides with itself
        repaint(); //Calling the repaint function to keep the game going
    }

    private void CollosionWithSelf() {
        for(int i=snakelength-1; i>0; i--){ //Checking if the head of the snake and the rest of the body of the snake is in the same location or not (since the snake collided with itself)
            if(snakex[i] == snakex[0] && snakey[i] == snakey[0]){
                time.stop(); //Stopping the game
                gameover = true;
            }
        }
    }

    private void CollisionWithFood() {
        if(snakex[0] == foodx && snakey[0] == foody){ //Checking if the head of the snake and the food is in the same location or not
            newfood(); //Calling the new food function so that the food can appear in a new random location and disappear from the current location
            //Initially length = 3
            snakelength++; //Increasing the snake length because that's how the game works
            //Now length = 4
            score++; //Increasing score because what's a game without a score
            //Adding the snake icon in the snake body by this simple logic
            snakex[snakelength-1] = snakex[snakelength-2]; //snakex[3] = snakex[2];
            snakey[snakelength-1] = snakey[snakelength-2]; //snakey[3] = snakey[2];
        }
    }

    private void newfood() { //When the food is consumed by the snake then new food has to appear in a random place
        foodx = xpos[random.nextInt(xpos.length-1)]; //Setting the x-axis of the food from the xpos array randomly choosing an index
        foody = ypos[random.nextInt(ypos.length-1)]; //Setting the y-axis of the food from the ypos array randomly choosing an index
        //Incase the food appears where the snake body is
        for(int i=snakelength-1; i>=0; i--){
            if(snakex[i] == foodx && snakey[i] == foody){
                newfood(); //Calling the new food function again as this case is not acceptable
            }
        }
    }
}
