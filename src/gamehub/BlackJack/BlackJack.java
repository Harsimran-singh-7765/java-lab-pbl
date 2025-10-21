package gamehub.BlackJack;
import java.awt.*;
import java.awt.event.*;
import java.net.URL; // Added for getResource
import java.util.ArrayList;
import java.util.Random;
// import java.util.random.*; // Removed unused import
import javax.swing.*;
// Make sure Sound_Manager is in the same package or imported correctly
// import gamehub.BlackJack.Sound_Manager; // Assuming it is in the same package

public class BlackJack {
    private class Card{
        String value;
        String type;
        Card(String v,String type) {
            this.value = v;
            this.type = type;
        }
        public String toString() {
            return value+"-"+type;
        }
        public int getValue() {
            if("AKQJ".contains(value)) {
                if(value=="A") {
                    return 11;
                } 
                return 10;
            }
            return Integer.parseInt(value);
        }

        public boolean isAce() {
            return  value.equals("A");
        }

        // --- FIX 1: Use absolute path from classpath root ---
        public  String getImagePath() {
            // Path should be /cards/CARD_VALUE-TYPE.png
            return "/cards/"+toString() + ".png";
        }
    }

    ArrayList<Card> deck;
    Random random = new Random();

    Card hiddenCard;
    ArrayList<Card> DealerHand;
    int DealerSum;
    int DealerAceCount;

    ArrayList<Card> playerHand;
    int playerSum;
    int playerAceCount;

    int Board_width = 600 ;
    int Board_hight = 600 ;
    int card_width = 110;
    int card_hight = 154;
    JFrame frame = new JFrame("BlackJack");
    JPanel GamePanel = new JPanel() {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            try{
                Image hiddenCardImg;
                
                // --- FIX 2: Load resources using BlackJack.class.getResource() ---
                
                // Load the BACK card image
                URL backCardUrl = BlackJack.class.getResource("/cards/BACK.png");
                if (backCardUrl == null) {
                    System.err.println("CRITICAL ERROR: BACK.png not found at /cards/BACK.png");
                    return; // Stop drawing if critical resource missing
                }
                
                hiddenCardImg = new ImageIcon(backCardUrl).getImage();

                if (!stayButton.isEnabled()) {
                    // Load the actual hidden card when STAY is clicked
                    URL actualHiddenCardUrl = BlackJack.class.getResource(hiddenCard.getImagePath());
                    if (actualHiddenCardUrl == null) {
                         System.err.println("CRITICAL ERROR: Hidden card image not found at " + hiddenCard.getImagePath());
                        return;
                    }
                    hiddenCardImg = new ImageIcon(actualHiddenCardUrl).getImage();
                }
                g.drawImage(hiddenCardImg, 20, 20, card_width, card_hight, null);

                //draw dealers hand
                for(int i=0;i<DealerHand.size();i++) {
                    Card card = DealerHand.get(i);
                    // --- FIX 3: Load card images using BlackJack.class.getResource() ---
                    URL cardUrl = BlackJack.class.getResource(card.getImagePath());
                    if (cardUrl == null) {
                        System.err.println("CRITICAL ERROR: Card image not found for dealer: " + card.getImagePath());
                        return;
                    }
                    Image cardImg = new ImageIcon(cardUrl).getImage();
                    g.drawImage((cardImg), card_width+25+(card_width+5)*i, 20,card_width,card_hight,null);
                }

                //draw player hand
                for(int i=0;i<playerHand.size();i++) {
                    Card card = playerHand.get(i);
                    // --- FIX 4: Load card images using BlackJack.class.getResource() ---
                    URL cardUrl = BlackJack.class.getResource(card.getImagePath());
                    if (cardUrl == null) {
                        System.err.println("CRITICAL ERROR: Card image not found for player: " + card.getImagePath());
                        return;
                    }
                    Image cardImg = new ImageIcon(cardUrl).getImage();
                    g.drawImage((cardImg), 20+(card_width+5)*i, 320,card_width,card_hight,null);
                }    

                //result
                if(!stayButton.isEnabled())  {

                    DealerSum = reduceDealearAce();
                    playerSum = reducePlayerAce();
                    System.out.println("STAY: ");
                    System.out.println("DEALER SUM :"+ DealerSum);
                    System.out.println("Player SUM :"+ playerSum);

                    String message= "";
                    if(playerSum > 21 ) {
                        message = "YOU LOSE!!!";
                        Sound_Manager.playloss();
                    } else if(DealerSum>21) {
                        message = "YOU WIN!!!";
                        Sound_Manager.playCelebration();
                    } else if(playerSum == DealerSum) {
                        message = "TIE!!!";
                        Sound_Manager.playloss();
                    } else if(playerSum>DealerSum) {
                        message = "YOU WIN!!!";
                        Sound_Manager.playCelebration();
                    } else {
                        message = "YOU LOSE!!!";
                        System.out.println("LOSS");
                        Sound_Manager.playloss();
                    }

                    g.setFont(new Font("Arial",Font.PLAIN,30));
                    g.setColor(Color.white);
                    g.drawString(message, 220, 250);  
                    
                    System.out.println("GOING BACK");
                    new java.util.Timer().schedule(new java.util.TimerTask() {
                        public void run() {
                            SwingUtilities.invokeLater(() -> {
                                frame.dispose();
                                System.err.println("ALMOST THERE");
                                // Assuming Opening_Screen is correctly importing
                                // and handles its own sound loading now.
                                new Opening_Screen();
                            });
                        }
                    }, 5000);


 
                }
            } catch (Exception e) {
                System.err.println("An unexpected error occurred during painting:");
                e.printStackTrace();
            }
        }
    };
    JPanel ButtonPanel = new JPanel();
    JButton hitButton = new JButton("HIT");
    JButton stayButton = new JButton("STAY");
    BlackJack() {
        startGame();
        
        frame.setSize(Board_width,Board_hight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GamePanel.setLayout(new BorderLayout());
        GamePanel.setBackground(new Color(53,101,77));
        frame.add(GamePanel);

        hitButton.setFocusable(false);
        stayButton.setFocusable(false);
        ButtonPanel.add(hitButton);
        ButtonPanel.add(stayButton);
        frame.add(ButtonPanel,BorderLayout.SOUTH);
        

        frame.setVisible(true);


        hitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Sound_Manager.playCardHit();
                Card card = deck.remove(deck.size()-1);
                playerSum += card.getValue();
                playerAceCount += card.isAce() ? 1 : 0;
                playerHand.add(card);
                if (reducePlayerAce() > 21) { //A + 2 + J --> 1 + 2 + J
                    hitButton.setEnabled(false); 
                }
                GamePanel.repaint();
            }
        });
        GamePanel.repaint();
        stayButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                hitButton.setEnabled(false);
                stayButton.setEnabled(false);

                while (DealerSum < 17) {
                    Card card = deck.remove(deck.size()-1);
                    DealerSum += card.getValue();
                    DealerAceCount += card.isAce() ? 1 : 0;
                    DealerHand.add(card);
                }
                GamePanel.repaint();
            }
        });

        GamePanel.repaint();
    }

    void startGame() {
        BuildDeck();
        ShuffleDeck();

        DealerHand = new ArrayList<Card>();
        DealerSum=0;
        DealerAceCount=0;

        hiddenCard = deck.remove(deck.size()-1);

        DealerSum += hiddenCard.getValue();
        DealerAceCount +=hiddenCard.isAce() ? 1 : 0; 


        Card card = deck.remove(deck.size()-1);
        DealerSum += card.getValue();
        DealerAceCount += card.isAce() ? 1 : 0;

        DealerHand.add(card);

        System.out.println("DEALER");  
        System.out.println(hiddenCard); 
        System.out.println(DealerHand);
        System.err.println(DealerSum);


        //player
        playerHand = new ArrayList<Card>();
        playerSum = 0;
        playerAceCount = 0;

        

        for (int i = 0; i < 2; i++) {
            card = deck.remove(deck.size()-1);
            playerSum += card.getValue();
            playerAceCount += card.isAce() ? 1 : 0;
            playerHand.add(card);
        }

        System.out.println("PLAYER: ");
        System.out.println(playerHand);
        System.out.println(playerSum);
        System.out.println(playerAceCount);
        
    }

    public void BuildDeck( ) {
        deck = new ArrayList<Card>();
        String[] values = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        String[] types = {"C", "D", "H", "S"};
        for(int i=0;i<types.length;i++) {
            for(int j=0;j<values.length;j++) {
                Card card = new Card(values[j],types[i]);
                deck.add(card);
            }
        }


        System.out.println("BUILD DECK: ");
        System.out.println(deck);

    }

    void ShuffleDeck(){
        for(int i=0;i<deck.size();i++) {
            Card  currcard = deck.get(i);
            int j = random.nextInt(deck.size());
            Card randomCard = deck.get(j);
            deck.set(j,currcard);
            deck.set(i,randomCard);
        }
        System.err.println("SHUFFLED");
        System.out.println(deck);
    }

    public int reducePlayerAce() {
        while(playerSum>21 && playerAceCount >0) {
            playerSum-=10;
            playerAceCount-=1;
        }
        return playerSum;
    }
    public int reduceDealearAce() {
        while(DealerSum>21 && DealerAceCount >0) {
            DealerSum-=10;
            DealerAceCount-=1;
        }
        return DealerSum;
    }
}