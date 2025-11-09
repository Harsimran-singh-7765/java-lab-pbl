package gamehub.highstakesshowdown;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class HighStakesShowdown extends JFrame {

   class Card {
      char suit;
      int rank;

      Card(char suit, int rank) {
         this.suit = suit;
         this.rank = rank;
      }

      public String toString() {
         String rankStr;
         switch (this.rank) {
            case 1: rankStr = "A"; break;
            case 11: rankStr = "J"; break;
            case 12: rankStr = "Q"; break;
            case 13: rankStr = "K"; break;
            default: rankStr = String.valueOf(this.rank);
         }
         return rankStr + "-" + this.suit;
      }

      public ImageIcon getImageIcon() {
         String path = "/cards/" + this.toString() + ".png";
         URL imgUrl = HighStakesShowdown.this.getClass().getResource(path);
         if (imgUrl != null) {
            ImageIcon icon = new ImageIcon(imgUrl);
            Image img = icon.getImage().getScaledInstance(80, 120, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
         } else {
            System.err.println("Couldn't find file: " + path);
            return null;
         }
      }
   }

   private static final Color BG_GREEN = new Color(34, 139, 34);
   private static final Color BG_BROWN = new Color(139, 69, 19);
   private static final Color TEXT_LIGHT = Color.WHITE;
   private static final int INITIAL_CREDITS = 1000;
   private int playerCredits = 1000;
   private int computerCredits = 1000;
   private List<Card> deck = new ArrayList<>();
   private List<Card> playerHand = new ArrayList<>();
   private List<Card> computerHand = new ArrayList<>();
   private JLabel statusLabel;
   private JPanel playerPanel;
   private JPanel computerPanel;
   private JTextField betField;
   private JButton playButton;
   private int currentBet;
   private int selectedPlayerCardIndex = -1;
   private Card selectedComputerCard = null;

   public HighStakesShowdown() {
      this.setTitle("High Stakes Showdown");
      
      // --- CHANGE 1: Window close hone par sirf ye game band hoga, main hub nahi ---
      this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Was 3 (EXIT_ON_CLOSE)
      
      this.setSize(800, 600);
      this.setLayout(new BorderLayout());
      this.statusLabel = new JLabel("Welcome to High Stakes Showdown!", 0);
      this.statusLabel.setOpaque(true);
      this.statusLabel.setBackground(BG_BROWN);
      this.statusLabel.setForeground(TEXT_LIGHT);
      this.statusLabel.setFont(new Font("Arial", 1, 16));
      this.add(this.statusLabel, "North");
      JPanel var1 = new JPanel(new GridLayout(2, 1));
      var1.setBackground(BG_GREEN);
      this.computerPanel = new JPanel();
      this.computerPanel.setBorder(BorderFactory.createTitledBorder("Computer's Cards"));
      this.computerPanel.setBackground(BG_GREEN);
      var1.add(this.computerPanel);
      this.playerPanel = new JPanel();
      this.playerPanel.setBorder(BorderFactory.createTitledBorder("Your Hand"));
      this.playerPanel.setBackground(BG_GREEN);
      var1.add(this.playerPanel);
      this.add(var1, "Center");
      JPanel var2 = new JPanel();
      var2.setBackground(BG_BROWN);
      var2.add(new JLabel("Enter your bet (min 100):"));
      this.betField = new JTextField(5);
      var2.add(this.betField);
      this.playButton = new JButton("Play Round");
      this.playButton.setBackground(new Color(0, 100, 0));
      this.playButton.setForeground(Color.WHITE);
      var2.add(this.playButton);
      this.add(var2, "South");
      this.playButton.addActionListener((var1x) -> {
         this.playRound();
      });
      this.initializeGame();
      this.setLocationRelativeTo(null); // Game ko center mein dikhao
      this.setVisible(true);
   }

   private void showGameOverPopup(String var1, Color var2) {
      JOptionPane var3 = new JOptionPane(var1, 1);
      JDialog var4 = var3.createDialog("Game Over");
      var4.getContentPane().setBackground(var2);
      var4.setModal(true);
      var4.setAlwaysOnTop(true);
      var4.setVisible(true);
   }

   private void initializeGame() {
      this.initializeDeck();
      this.shuffleDeck();
      this.dealInitialHands();
      this.selectedPlayerCardIndex = -1;
      this.selectedComputerCard = null;
      this.updateHandsDisplay();
      this.updateStatus("Game started! Place your bet and select a card.");
   }

   private void initializeDeck() {
      this.deck.clear();
      char[] var1 = new char[]{'H', 'D', 'S', 'C'};
      char[] var2 = var1;
      int var3 = var1.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         char var5 = var2[var4];

         for(int var6 = 1; var6 <= 13; ++var6) {
            this.deck.add(new Card(var5, var6));
         }
      }

   }

   private void shuffleDeck() {
      Collections.shuffle(this.deck);
   }

   private void dealInitialHands() {
      this.playerHand.clear();
      this.computerHand.clear();

      for(int var1 = 0; var1 < 3; ++var1) {
         this.playerHand.add(this.drawCard());
         this.computerHand.add(this.drawCard());
      }

   }

   private Card drawCard() {
      if (this.deck.isEmpty()) {
         this.initializeDeck();
         this.shuffleDeck();
      }

      return (Card)this.deck.remove(0);
   }

   private ImageIcon getBackCardIcon() {
      String var1 = "/cards/BACK.png";
      URL var2 = this.getClass().getResource(var1);
      if (var2 != null) {
         ImageIcon var3 = new ImageIcon(var2);
         Image var4 = var3.getImage().getScaledInstance(80, 120, 4);
         return new ImageIcon(var4);
      } else {
         System.err.println("Couldn't find file: " + var1);
         return null;
      }
   }

   private void updateHandsDisplay() {
      this.playerPanel.removeAll();

      for(int i = 0; i < this.playerHand.size(); ++i) {
         final int index = i;
         Card card = (Card)this.playerHand.get(index);
         ImageIcon cardIcon = card.getImageIcon();
         JButton cardButton = (cardIcon != null) ? new JButton(cardIcon) : new JButton(card.toString());
         
         if (index == this.selectedPlayerCardIndex) {
            cardButton.setBackground(Color.YELLOW);
            cardButton.setOpaque(true);
         } else {
            cardButton.setBackground((Color)null);
            cardButton.setOpaque(false);
         }

         cardButton.addActionListener((var2x) -> {
            this.selectedPlayerCardIndex = index;
            this.updateHandsDisplay();
            this.updateStatus("Selected card: " + this.playerHand.get(index).toString());
         });
         this.playerPanel.add(cardButton);
      }

      this.computerPanel.removeAll();
      Iterator<Card> var6 = this.computerHand.iterator();

      while(var6.hasNext()) {
         Card var2 = (Card)var6.next();
         if (var2.equals(this.selectedComputerCard)) {
            JLabel var7 = new JLabel(var2.getImageIcon());
            var7.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
            this.computerPanel.add(var7);
         } else {
            this.computerPanel.add(new JLabel(this.getBackCardIcon()));
         }
      }

      this.revalidate();
      this.repaint();
   }

   private void updateStatus(String var1) {
      this.statusLabel.setText(var1 + " | Your Credits: " + this.playerCredits + " | Computer Credits: " + this.computerCredits);
   }

   private void playRound() {
      if (this.currentBet != 0) {
         this.updateStatus("Round already in progress. Please finish it.");
      } else {
         String var1 = this.betField.getText();

         try {
            this.currentBet = Integer.parseInt(var1);
            if (this.currentBet < 100 || this.currentBet > this.playerCredits || this.currentBet > this.computerCredits) {
               this.updateStatus("Invalid bet amount. Please enter a valid bet.");
               this.currentBet = 0;
               return;
            }

            if (this.selectedPlayerCardIndex == -1) {
               this.updateStatus("Please select a card to play.");
               this.currentBet = 0;
               return;
            }

            this.selectedComputerCard = (Card)this.computerHand.get((new Random()).nextInt(this.computerHand.size()));
            this.updateStatus("Bet placed: " + this.currentBet + ". You played " + this.playerHand.get(this.selectedPlayerCardIndex).toString() + ". Dealer's card flipped!");
            this.updateHandsDisplay();
            Timer var2 = new Timer(1200, (var1x) -> {
               this.resolveRound();
            });
            var2.setRepeats(false);
            var2.start();
         } catch (NumberFormatException var3) {
            this.updateStatus("Please enter a numeric bet amount.");
            this.currentBet = 0;
         }

      }
   }

   private void resolveRound() {
      final Card playerCard = (Card)this.playerHand.get(this.selectedPlayerCardIndex);
      final Card computerCard = this.selectedComputerCard;
      final int playerCardIdx = this.selectedPlayerCardIndex;
      final int computerCardIdx = this.computerHand.indexOf(computerCard);

      if (playerCard.rank > computerCard.rank) {
         this.playerCredits += this.currentBet;
         this.computerCredits -= this.currentBet;
         this.updateStatus("You win this round!");
         
         this.offerCardChoiceAfterWin(playerCard, computerCard, playerCardIdx); 
         
         this.computerHand.set(computerCardIdx, this.drawCard());
         this.updateHandsDisplay();
         this.cleanupAfterRound();

      } else if (playerCard.rank < computerCard.rank) {
         this.playerCredits -= this.currentBet;
         this.computerCredits += this.currentBet;
         this.updateStatus("Computer wins this round! Swapping cards...");
         
         Timer lossTimer = new Timer(1500, (e) -> { 
             this.playerHand.set(playerCardIdx, this.drawCard());
             this.computerHand.set(computerCardIdx, this.drawCard());
             this.updateHandsDisplay();
             this.cleanupAfterRound();
         });
         lossTimer.setRepeats(false);
         lossTimer.start();

      } else {
         this.updateStatus("It's a tie! Swapping cards...");
         
         Timer tieTimer = new Timer(1500, (e) -> { 
             this.playerHand.set(playerCardIdx, this.drawCard());
             this.computerHand.set(computerCardIdx, this.drawCard());
             this.updateHandsDisplay();
             this.cleanupAfterRound();
         });
         tieTimer.setRepeats(false);
         tieTimer.start();
      }
   }

private void cleanupAfterRound() {
       this.currentBet = 0;
       this.betField.setText("");
       this.selectedPlayerCardIndex = -1;
       this.selectedComputerCard = null;

       if (this.playerCredits <= 0 || this.computerCredits <= 0) {
           this.playButton.setEnabled(false);
           this.betField.setEnabled(false);
           
          
           String endMessage = (this.playerCredits <= 0) ? "You ran out of credits. Game Over!" : "Computer ran out of credits. You Win!";
           
           this.updateStatus(endMessage);

         
           Timer closeTimer = new Timer(1500, (e) -> {
               this.dispose(); 
           });
           closeTimer.setRepeats(false);
           closeTimer.start();
           
       }
   }

   private void offerCardChoiceAfterWin(Card var1, Card var2, int var3) {
      Card var4 = this.drawCard();
      Card[] var5 = new Card[]{var1, var2, var4};

      final JDialog var6 = new JDialog(this, "Pick One Card to Keep (Auto-picks in 2s)", true);
      var6.setSize(400, 200);
      var6.setLayout(new GridLayout(1, 3));
      var6.getContentPane().setBackground(BG_BROWN);

      final Timer autoCloseTimer = new Timer(2000, (e) -> {
          if (var6.isShowing()) {
              this.playerHand.set(var3, var1); 
              var6.dispose();
              this.updateHandsDisplay();
              this.updateStatus("Auto-kept winning card: " + var1.toString() + "!");
          }
      });
      autoCloseTimer.setRepeats(false);


      for(int var9 = 0; var9 < var5.length; ++var9) {
         final Card var10 = var5[var9]; 
         JButton var11 = var10.getImageIcon() != null ? new JButton(var10.getImageIcon()) : new JButton(var10.toString());
         
         var11.addActionListener((var4x) -> {
            autoCloseTimer.stop();
            this.playerHand.set(var3, var10);
            var6.dispose();
            this.updateHandsDisplay();
            this.updateStatus("You picked " + var10.toString() + " to keep!");
         });
         var6.add(var11);
      }

      var6.setLocationRelativeTo(this);
      autoCloseTimer.start();
      var6.setVisible(true);
   }

   public static void main(String[] var0) {
      SwingUtilities.invokeLater(() -> {
         new HighStakesShowdown();
      });
   }
}