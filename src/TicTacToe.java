import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/** TicTacToe Game with AI
 *  reference 1: http://www.ntu.edu.sg/home/ehchua/programming/java/JavaGame_TicTacToe_AI.html
 *  reference 2: https://www.youtube.com/watch?v=Db3cC5iPrOM
 * @author Dezso Janos
 */
public class TicTacToe{
    
    // Frame and panel for the main board
    private final JFrame f;
    private final JPanel p;
    
    // Bottons on the bottom
    private final JPanel pGombok;
    private final JButton bKezd;
    private final JButton bGepKezd;
    
    // Label helper
    private final JLabel lab;
 
    /** Enumeration (inner class) for reading the actual game state */
    public enum GameState {
            PLAYING, DRAW, CROSS_WON, NOUGHT_WON
    }
    public GameState currentState;  
  
    // Enumeration (inner class) for the buttons and for coming player
    public enum Seed {
            EMPTY, CROSS, NOUGHT
        }    
    private Seed currentPlayer;     
 
    /** buttons array */
    public static XOButton buttons[];
    
    // To clear fields and easy check if a cell is empty
    ImageIcon empty;
        
    public static void main(String args[]){
        TicTacToe ticTacToe = new TicTacToe();
    }
    
    /** Initialize the game-board contents and the status */
    public final void initGame() {
	// clearing button contents
	for (int i = 0; i < 9; i++) {
            buttons[i].setIcon(empty);
        }
        currentState = GameState.PLAYING; // ready to play
    }
    
    /** Checks if the current state is draw.
     * @return  */
    public boolean isDraw() {
        for (int i = 0; i < 9; i++) {
            if ( buttons[i].getIcon() == empty ) 
                return false;
            if ( i == 8 && buttons[i].getIcon() != empty) {
                currentState = GameState.DRAW;
                lab.setText("It's a DRAW!");
                return true;
            }
        }
        return false;
    }
    
    /** Checks if the given icon's player has won.
     * @param ficon
     * @return  */
    public boolean hasWon(Icon ficon) {
        // Horizontal checks
        for (int i = 0; i < 7; i += 3) {
            if (buttons[i].getIcon() == ficon && buttons[i+1].getIcon() == ficon && buttons[i+2].getIcon() == ficon) {
                if (ficon == XOButton.X) {
                    System.out.println("X won!");
                    lab.setText("X won!");
                    currentState = GameState.CROSS_WON;
                } else {
                    System.out.println("O won!");
                    lab.setText("O won!");
                    currentState = GameState.NOUGHT_WON;
                }
                return true;
            }
        }
        // Vertical checks
        for (int i = 0; i < 3; i++) {
            if (buttons[i].getIcon() == ficon && buttons[i+3].getIcon() == ficon && buttons[i+6].getIcon() == ficon) {
                if (ficon == XOButton.X) {
                    System.out.println("X won!");
                    lab.setText("X won!");
                    currentState = GameState.CROSS_WON;
                } else {
                    System.out.println("O won!");
                    lab.setText("O won!");
                    currentState = GameState.NOUGHT_WON;
                }
                return true;
            }
        }
        
        // Diagonal checks
        if (buttons[0].getIcon() == ficon && buttons[4].getIcon() == ficon && buttons[8].getIcon() == ficon) {
                if (ficon == XOButton.X) {
                    System.out.println("X won!");
                    lab.setText("X won!");
                    currentState = GameState.CROSS_WON;
                } else {
                    System.out.println("O won!");
                    lab.setText("O won!");
                    currentState = GameState.NOUGHT_WON;
                }
                return true;
            }
        
        if (buttons[2].getIcon() == ficon && buttons[4].getIcon() == ficon && buttons[6].getIcon() == ficon) {
                if (ficon == XOButton.X) {
                    System.out.println("X won!");
                    lab.setText("X won!");
                    currentState = GameState.CROSS_WON;
                } else {
                    System.out.println("O won!");
                    lab.setText("O won!");
                    currentState = GameState.NOUGHT_WON;
                }
                return true;
            }
        
        return false;   
    }
    
    /** Count the score from helperFicon and helperEmpty
     * @param helperFicon
     * @param helperEmpty
     * @return
     */
    public int scoreCount(int helperFicon, int helperEmpty) {
        int score = 0;
        int helperOpponent;
        if ( helperFicon == 3) 
            score += 100;
            else if ( helperFicon == 2 && helperEmpty == 1 )
                score += 10;
                else if (helperFicon == 1 && helperEmpty == 2)
                    score += 1;
        helperOpponent = 3 - helperEmpty - helperFicon;
        if ( helperOpponent == 3) 
            score -= 100;
            else if ( helperOpponent == 2 && helperEmpty == 1 )
                score -= 10;
                else if (helperOpponent == 1 && helperEmpty == 2)
                    score -= 1;        
        return score;
    }
    
    
    /** Giving back score from counting given player symbol in rows:
     *      3 in a row: +100,
     *      2 in a row with 1 empty: +10,
     *      1 in a row with 2 empty: +1
     * @param ficon
     * @param ffbuttons
     * @return
     */
    public int rowCount(Icon ficon, XOButton[] ffbuttons) {
        int helperFicon;
        int helperEmpty;
        int actualScore;
        int score = 0;
        for ( int i = 0; i < 7; i += 3 ) {
            helperFicon = 0;
            helperEmpty = 0;
            
            for (int j = i; j < i+3; j++) {
                if (ffbuttons[j].getIcon() == empty) helperEmpty++;
                else if (ffbuttons[j].getIcon() == ficon) helperFicon++;
            }
            
            actualScore = scoreCount(helperFicon,helperEmpty);
            score += scoreCount(helperFicon,helperEmpty);
            
            //System.out.println(i + "-al kezdodo sorban helperEmpty: " + helperEmpty);
            //System.out.println(i + "-al kezdodo sorban helperFicon: " + helperFicon);
            //System.out.println(i + "'s row score: " + actualScore);
        }
        //System.out.println("All row's score in this try: " + score);
        return score;
    }
    
    /** Giving back score from counting given player symbol in columns:
     *      3 in a column: +100,
     *      2 in a column with 1 empty: +10,
     *      1 in a column with 2 empty: +1
     * @param ficon
     * @param ffbuttons
     * @return
     */
    public int colCount(Icon ficon, XOButton[] ffbuttons) {
        int helperFicon;
        int helperEmpty;
        int actualScore;
        int score = 0;
        for ( int i = 0; i < 3; i++ ) {
            helperFicon = 0;
            helperEmpty = 0;
            for (int j = i; j < i+7; j += 3) {
                if (ffbuttons[j].getIcon() == empty) helperEmpty++;
                else if (ffbuttons[j].getIcon() == ficon) helperFicon++;
            }
            
            actualScore = scoreCount(helperFicon,helperEmpty);
            score += scoreCount(helperFicon,helperEmpty);
            
            //System.out.println(i + "-al kezdodo oszlopban helperEmpty: " + helperEmpty);
            //System.out.println(i + "-al kezdodo oszlopban helperFicon: " + helperFicon);
            //System.out.println(i + "'s column score: " + actualScore);
        }
        //System.out.println("All column's score in this try: " + score);
        return score;
    }    
    
    
    /** Giving back score from counting given player symbol in diagonals:
     *      3 in a diagonal: +100,
     *      2 in a diagonal with 1 empty: +10,
     *      1 in a diagonal with 2 empty: +1
     * @param ficon
     * @param ffbuttons
     * @return
     */
    public int diaCount(Icon ficon, XOButton[] ffbuttons) {
        int helperFicon = 0;
        int helperEmpty = 0;
        int actualScore;
        int score;
        int i;
        
        for (i = 0; i < 9; i += 4) {
            if (ffbuttons[i].getIcon() == empty) helperEmpty++;
                else if (ffbuttons[i].getIcon() == ficon) helperFicon++;
        }
        
        actualScore = scoreCount(helperFicon,helperEmpty);
        score = scoreCount(helperFicon,helperEmpty);
        
        //System.out.println(i-12 + "-al kezdodo atloban helperEmpty: " + helperEmpty);
        //System.out.println(i-12 + "-al kezdodo atloban helperFicon: " + helperFicon);
        //System.out.println(i-12 + "'s diagonal score: " + actualScore);
        
        helperFicon = 0;
        helperEmpty = 0;
        
        for ( i = 2; i < 7; i += 2 ) {
            if (ffbuttons[i].getIcon() == empty) helperEmpty++;
                else if (ffbuttons[i].getIcon() == ficon) helperFicon++;
        }
        
        actualScore = scoreCount(helperFicon,helperEmpty);
        score += scoreCount(helperFicon,helperEmpty);
        
        //System.out.println(i-6 + "-al kezdodo atloban helperEmpty: " + helperEmpty);
        //System.out.println(i-6 + "-al kezdodo atloban helperFicon: " + helperFicon);
        //System.out.println(i-6 + "'s diagonal score: " + actualScore);
        //System.out.println("All diagonal's score in this try: " + score);
        return score;
    }
    
    /** Only for debugging: This is a dump step from PC, puts on the first empty cell
     * @param ficon
     * @param ffbuttons
     */
    public void pcFirstEmptyStep(Icon ficon, XOButton[] ffbuttons) {
        // Helper variable to check if the human already won and actual checking
        Icon oppIcon = (ficon == XOButton.X) ? XOButton.O : XOButton.X;
        if (hasWon(oppIcon)) System.out.println("Human is winning!");
        else {
            // Checking if it is a draw
            if (isDraw()) System.out.println("It's a draw before PC's step!");
        }
        // The Dump step
	for (int i = 0; i < 9; i++) {
            if ( ffbuttons[i].getIcon() == empty && currentState == GameState.PLAYING ) {
                ffbuttons[i].setIcon(ficon);
                System.out.println("PC put this on the first cell: " + i);
                // Checkin wining and draw after PC has put
                if (hasWon(ficon)) System.out.println("TestPC is winning!");
                else
                    if (isDraw()) System.out.println("It's a draw before TestPC's step!");
                    else {
                       // score[i] = scoreCount(ficon);
                        // Switch player
                        currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
                        break;
                    }
            }
        }
    }
    
    /** Score counting for Minimax algorithm
     * @param ficon    
     * @param ffbuttons    
     * @return */    
    public int scoreCountOfBoard(Icon ficon, XOButton[] ffbuttons) {
        int score;
        score = rowCount(ficon,ffbuttons) + colCount(ficon,ffbuttons) + diaCount(ficon,ffbuttons);
        System.out.println("         row, column and diagonal sum score: " + score);
        return score;
    }    
    
    /** This gives back empty spots on the board.
     * @param ffbuttons
     * @return
     */
    public List<Integer> getValidMoves(XOButton[] ffbuttons) {
        List<Integer> moves = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            if (ffbuttons[i].getIcon() == empty) {
                moves.add(i);
            }
        }
        return moves;
    }
    
    /** Minimax algorithm
     * @param fdepth
     * @param ficon    
     * @return */    
    public int[] miniMax(int fdepth, Icon ficon) {
        //List[int] nextMoves;// = generateMoves();
        XOButton[] fbuttons;
        fbuttons = buttons;
        int score;
        int bestScore = (ficon == XOButton.X) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int currentScore;
        int bestStep = 7;
        Icon oppIcon = (ficon == XOButton.X) ? XOButton.O : XOButton.X;

        if (currentState == GameState.PLAYING) {
            // Get the possible moves
            List<Integer> validMoves = getValidMoves(buttons);
            //System.out.println("Valid steps: " + Arrays.toString(validMoves));
            System.out.println("  Valid steps: " + validMoves);
        
            if ( validMoves == null || fdepth == 0 ) {
                // No more empty space -> game over, or depth reached. Let's count the score:
                bestScore = scoreCountOfBoard(ficon, fbuttons);
            } else {
                for ( int move : validMoves ) {
                    // Check if it is the end of the list.
                    // Try a valid move
                    fbuttons[move].setIcon(oppIcon);
                    if (ficon == XOButton.X) {
                        System.out.println("    PC is trying X to here: " + move);
                        currentScore = miniMax(fdepth - 1, oppIcon)[0];
                        //currentScore = scoreCount(oppIcon, fbuttons);
                        if (currentScore > bestScore) {
                            bestScore = currentScore;
                            bestStep = move;
                        }
                    } else {
                        System.out.println("    PC is trying O to here: " + move);
                        currentScore = miniMax(fdepth - 1, ficon)[0];
                        //currentScore = scoreCount(ficon, fbuttons);
                        if (currentScore < bestScore) {
                            bestScore = currentScore;
                            bestStep = move;
                        }
                    }
                    // Undo move
                    fbuttons[move].setIcon(empty);
                }
            }
        }
        return new int[] {bestScore, bestStep};
    }

    /** Machine step method - DUMP, puts on the first empty cell
     * @param ficon
     * @param ffbuttons*/
    public void pcStep(Icon ficon, XOButton[] ffbuttons) {
        // Helper variable to check if the human already won and actual checking
        Icon oppIcon = (ficon == XOButton.X) ? XOButton.O : XOButton.X;
        if (hasWon(oppIcon)) System.out.println("Human is winning!");
        else {
            // Checking if it is a draw
            if (isDraw()) System.out.println("It's a draw before PC's step!");
        }
        
        // MINIMAX step
        if (currentState == GameState.PLAYING) {
            int theBestStep = miniMax(2,ficon)[1];
            ffbuttons[theBestStep].setIcon(ficon);
            System.out.println("Minimax made this step: " + theBestStep + "\n");
        
            // Checkin wining and draw after PC has put
            if (hasWon(ficon)) {
                System.out.println("PC is winning!");
                currentState = (ficon == XOButton.X) ? GameState.CROSS_WON : GameState.NOUGHT_WON;
            } else
                if (isDraw()) {
                    System.out.println("It's a draw after PC's step!");
                    currentState = GameState.DRAW;
                } else {
                    // Switch player
                    currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
                }
        }
    }
            
    /** The Main Class */
    public TicTacToe(){
	// Buttons initialization
	TicTacToe.buttons = new XOButton[9];
        
        // Frame creating and setup
        f = new JFrame("Tic Tac Toe");
        f.setVisible(true);
	f.setSize(400, 450);
	f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Panel setup for XO buttons     
        p = new JPanel();
	p.setLayout(new GridLayout(3, 3));
	
        // ActionListener: if clicked than sets icon
        ActionListener ble = (new ActionListener () {
            @Override
            public void actionPerformed (ActionEvent e) {
                JButton gomb;
                gomb = (JButton)e.getSource();
                System.out.println("Human pushed this button: " + gomb.getName());
                int pressedButton = Integer.parseInt(gomb.getName());
            
                if (currentState == GameState.PLAYING && buttons[pressedButton].getIcon() != XOButton.X && buttons[pressedButton].getIcon() != XOButton.O ) {
                    if (currentPlayer == Seed.CROSS) {
                        buttons[pressedButton].setIcon(XOButton.O);
                        currentPlayer = Seed.CROSS;
                        pcStep(XOButton.X,buttons);
                        //pcFirstEmptyStep(XOButton.O,buttons);
                    } else {
                        buttons[pressedButton].setIcon(XOButton.O);
                        currentPlayer = Seed.CROSS;
                        pcStep(XOButton.X,buttons);
                        //pcFirstEmptyStep(XOButton.X,buttons);
                    }
                }
            }   
        });
           
        // creating buttons and adding actionlistener to them by each
        for (int i = 0; i < 9; i++) {
            buttons[i]=new XOButton();
            buttons[i].setName(Integer.toString(i));
            buttons[i].setPreferredSize(new Dimension(130, 130));
            buttons[i].addActionListener(ble);
            p.add(buttons[i]);
	}
                
        // pGombok panel creation
        pGombok = new JPanel();
	pGombok.setLayout(new GridLayout(1, 3));
                
        // Setting up labels on lower bottons
        bKezd = new JButton("Human starts");
        lab = new JLabel("<- New Game ->");
        bGepKezd = new JButton("PC starts");
                
        // Adding lower buttons to pGombok panel and aligning them
        pGombok.add(bKezd);
        bKezd.setVerticalAlignment(SwingConstants.CENTER);
        pGombok.add(lab);
        lab.setHorizontalAlignment(SwingConstants.CENTER);
        pGombok.add(bGepKezd);
        bGepKezd.setVerticalAlignment(SwingConstants.CENTER);
               
        // Defining empty
        empty=new ImageIcon(buttons.getClass().getResource("empty.png"));
                                
        // "On kezd" button's ActionListener:
        bKezd.addActionListener(new ActionListener () {
        	public void actionPerformed (ActionEvent e) {
            initGame();
            System.out.println("You started a new game, you start.");
            lab.setText("It's your turn!");
        	}});
                
        // "Gep kezd" button's ActionListener:
        bGepKezd.addActionListener(new ActionListener () {
        	public void actionPerformed (ActionEvent e) {
            initGame();
            currentPlayer = Seed.CROSS;
            pcStep(XOButton.X,buttons);
            System.out.println("You started a new game, the PC has done its step, it's your turn.");
            lab.setText("it's your turn!");
        	}});
               
        // Adding panles to f frame
        f.add(p, BorderLayout.NORTH);
        f.add(new JSeparator(), BorderLayout.CENTER);
        f.add(pGombok, BorderLayout.SOUTH);
                
        // Making sure that everything is visible
        f.pack();
        f.setVisible(true);
        // Initializing the Game;
        initGame();
    }
}
