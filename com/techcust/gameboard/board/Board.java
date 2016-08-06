package com.techcust.gameboard.board;

import com.techcust.gameboard.Piece;
import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.text.DefaultCaret;

public class Board{
    final public static int ROWS = 8, COLS = 8;
    final private Piece[][] boardArray = new Piece[ROWS][COLS];
    private ArrayList<Piece> pieces = new ArrayList<>();
    
    private JFrame board, textBoard;
    private JTextArea textArea;
    private Thread textThread;
    
    
    public Board(){
        
        try {
            SwingUtilities.invokeAndWait(new Runnable(){
                @Override
                public void run(){
                    createTextBoard();
                }
            });
        }catch(Exception ex) {
            Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    private void createTextBoard(){
        textBoard = new JFrame();
        textBoard.setTitle("Text Board");
        textBoard.setSize(500,500);
        textBoard.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent we){
                System.exit(0);
            }
        });
        
        JPanel textMainPane = new JPanel(new BorderLayout());
        textArea = new JTextArea(300,300);
        JScrollPane scroll = new JScrollPane(textArea,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        textArea.setEditable(false);
        
        textMainPane.add(scroll);
        textBoard.add(textMainPane);
        
        
        textBoard.setVisible(true);
    }
    public void setupThread(){
        textThread = new Thread(new Runnable(){
           @Override
           public void run(){
               while(true){
                   //for(Piece piece: pieces){
                       //System.out.println("Piece: " + piece.getId() + " " + piece.getRow() + " " + piece.getCol());
                   //}
                   
                   updateTextDisplay();
                   try{
                       Thread.sleep(333);
                   }catch(Exception ex){}
                   clearDisplay();
               }
           } 
        });
    }
    public void startTextThread(){
        if(textThread == null){
            return;
        }
        textThread.start();
    }
    
    public void clearDisplay(){
        textArea.setText(null);
    }
    public void writeToDisplay(String string){
        try{
            DefaultCaret caret = (DefaultCaret) textArea.getCaret();
            caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
            textArea.append(string + "\n");
        }catch(NullPointerException ex){
            System.exit(0);
        }
    }
    public void addToDisplay(String string){
        DefaultCaret caret = (DefaultCaret) textArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        textArea.append(string);
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
    }
    public int screenWidth(){
        return textArea.getWidth();
    }
    

    public void updateTextDisplay(){
        for(int rowX = 1; rowX <= ROWS; rowX++){
            
            for(int colY = 1; colY <= COLS; colY++){
                addToDisplay("[");
                addToDisplay(" ");
                //check if any pieces in arrayList have this position
                for(Piece piece: pieces){
                    if(piece == null){
                        addToDisplay(" ");
                    }
                    if(piece.getRow() == rowX && piece.getCol() == colY){
                        addToDisplay(piece.getId());
                        continue;
                    }
                    //
                }
                
                addToDisplay("]");
            }
            addToDisplay("\n");
        }
    }
    
    public Piece getPiece(int row, int col){
        if(row >= ROWS || col >= COLS || row <= 0 || col <= 0){
            return null;
        }
        for(Piece piece: pieces){
            if(piece.getRow() == row && piece.getCol() == col){
                return piece;
            }
        }
        return null;
    }
    
    private void makeGrid(int numOfBounds){
        if(numOfBounds > (ROWS * COLS)){
            return;
        }
        ArrayList<String> gridList = new ArrayList<>();
        
        //createGrid
        for(int x = 0; x < numOfBounds; x++){
            int row = new Random().nextInt(7)+1;
            int col = new Random().nextInt(7)+1;
            
            String rowCol = row + " " + col;
            
            boolean notIn = false;
            while(!notIn){
                for(Piece piece: pieces){
                    if(piece.getRow() == row && piece.getCol() == col){
                        row = new Random().nextInt(7)+1;
                        col = new Random().nextInt(7)+1;
                    
                        rowCol = row + " " + col;
                    }
                }
                if(gridList.contains(rowCol)){
                    row = new Random().nextInt(7)+1;
                    col = new Random().nextInt(7)+1;
                    
                    rowCol = row + " " + col;
                }else{
                    gridList.add(rowCol);
                    
                    //addPiece(new Bound("X", row, col));
                    notIn = true;
                }
                /*
                for(String string: gridList){
                    if(rowCol.equals(string)){
                        continue;
                    }
                    gridList.add(rowCol);
                    
                    
                }
                */
            }
            
        }
    }
    
    private void addPiece(Piece piece){
        pieces.add(piece);
    }
    
    private void removePiece(Piece piece){
         if(piece == null){
            return;
        }
        
        pieces.remove(piece);
    }
    
    private void movePiece(Piece piece, int row, int col){
        int xRow = row;
        int yCol = col;
        if(xRow > ROWS || yCol > COLS || xRow < 0 || yCol < 0){
            return;
        }
        
        if(piece != null){
            
            for(Piece p: pieces){
                if(p.getRow() == xRow && p.getCol() == yCol){
                    return;
                }
            }
            piece.setRow(xRow);
            piece.setCol(yCol);
        }
    }
    
    private void movePiece(Piece piece, int dir){
        if(piece == null){
            return;
        }
        
        switch(dir){
            //case 1 = up, 2 = down, 3 = left, 4 = right
            case 1 :
                piece.moveUp();
                break;
            case 2 :
                piece.moveDown();
                break;
            case 3 :
                piece.moveLeft();
                break;
            case 4 :
                piece.moveRight();
                break;
            default:
                break;
        }
    }
    
    
    public static void main(String[] args){
        Board board = new Board();
        board.setupThread();
        board.startTextThread();
        
        
        
        
        //AiPiece aiPiece = new AiPiece(board,"AI", 1,1);
        
        
        //board.addPiece(aiPiece);
        
        board.makeGrid(20);
        
        
        //aiPiece.createMoveThread();
        //aiPiece.startMoveThread();
        
        /*
        while(true){
        
           





            try{
                

                board.movePiece(j, new Random().nextInt(7)+1, new Random().nextInt(7)+1);
                Thread.sleep(1000);

                board.movePiece(j, new Random().nextInt(7)+1, new Random().nextInt(7)+1);
                Thread.sleep(1000);

                board.movePiece(piece1, new Random().nextInt(7)+1, new Random().nextInt(7)+1);
                Thread.sleep(1000);




                    board.movePiece(piece1, new Random().nextInt(7)+1, new Random().nextInt(7)+1);
                    Thread.sleep(333);
                    piece1.moveUp();
                    Thread.sleep(333);
                    piece1.moveUp();
                    Thread.sleep(333);
                    piece1.moveDown();
                    Thread.sleep(333);
                    piece1.moveLeft();
                    Thread.sleep(333);
                    piece1.moveLeft();
                    Thread.sleep(333);
                    piece1.moveLeft();
                
                    Thread.sleep(1000);
                    board.removePiece(piece1);
                    Thread.sleep(1000);
                    board.removePiece(j);
                    Thread.sleep(1000);


            }catch(Exception ex){}
            
        }
        */
        
    }
}