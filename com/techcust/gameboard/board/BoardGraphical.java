package com.techcust.gameboard.board;


import com.techcust.gameboard.piece.AiPiece;
import com.techcust.gameboard.piece.BoardPiece;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

final public class BoardGraphical extends JFrame{
    final static protected int BOARDWIDTH = 640, BOARDHEIGHT = 640;
    
    private BoardGrid bg;
    
    public BoardGraphical(){
        
    }
    public BoardGraphical(BoardGrid grid){
        try {
            SwingUtilities.invokeAndWait(new Runnable(){
                @Override
                public void run(){
                    createBoard(grid);
                }
            });
        } catch (InterruptedException ex) {
            Logger.getLogger(BoardGraphical.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(BoardGraphical.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void createBoard(BoardGrid grid){
        if(grid == null){
            return;
        }
        this.setTitle("Game Board");
        this.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent we){
                System.exit(0);
            }
        });
        this.setResizable(false);
        grid.createGrid();
        
        //for(Object square: bg.getGridList()){
            //GridRect rect = (GridRect)square;
            //if(square.equals(bg.getGridList().get(0))){
                //bg.addPiece(1,1);
                //bp = bg.getPiece();
                //bg.getPiece(1,1).startPositioning();
            //}
        //}
        this.getContentPane().add(grid);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        
    }
    
    protected BoardGrid getGrid(){
        return bg;
    }
    
    public void log(String string){
        System.out.println(string);
    }
    public static void main(String[] args){
        //BoardGraphical bg = new BoardGraphical();
        
        new Thread(new Runnable(){
            @Override
            public void run(){
                BoardGrid grid = null;
                
                while(true){
                    try{
                        //grid = bg.getGrid();
                        
                        grid.getSquares()[0][6].setBlocked(true);
                        grid.getSquares()[0][7].setBlocked(true);
                        
                        grid.getSquares()[1][0].setBlocked(true);
                        grid.getSquares()[1][1].setBlocked(true);
                        grid.getSquares()[1][2].setBlocked(true);
                        grid.getSquares()[1][4].setBlocked(true);
                        grid.getSquares()[1][7].setBlocked(true);
                        grid.getSquares()[1][9].setBlocked(true);
                        
                        grid.getSquares()[2][0].setBlocked(true);
                        grid.getSquares()[2][7].setBlocked(true);
                        grid.getSquares()[2][9].setBlocked(true);
                        
                        grid.getSquares()[3][0].setBlocked(true);
                        grid.getSquares()[3][2].setBlocked(true);
                        grid.getSquares()[3][3].setBlocked(true);
                        grid.getSquares()[3][5].setBlocked(true);
                        grid.getSquares()[3][9].setBlocked(true);
                        
                        grid.getSquares()[4][0].setBlocked(true);
                        grid.getSquares()[4][2].setBlocked(true);
                        grid.getSquares()[4][7].setBlocked(true);
                        
                        grid.getSquares()[5][0].setBlocked(true);
                        grid.getSquares()[5][4].setBlocked(true);
                        grid.getSquares()[5][5].setBlocked(true);
                        grid.getSquares()[5][6].setBlocked(true);
                        grid.getSquares()[5][7].setBlocked(true);
                        grid.getSquares()[5][8].setBlocked(true);
                        
                        grid.getSquares()[6][2].setBlocked(true);
                        grid.getSquares()[6][4].setBlocked(true);
                        grid.getSquares()[6][7].setBlocked(true);
                        
                        grid.getSquares()[7][1].setBlocked(true);
                        grid.getSquares()[7][5].setBlocked(true);
                        grid.getSquares()[7][7].setBlocked(true);
                        grid.getSquares()[7][9].setBlocked(true);
                        
                        grid.getSquares()[8][1].setBlocked(true);
                        grid.getSquares()[8][3].setBlocked(true);
                        grid.getSquares()[8][5].setBlocked(true);
                        grid.getSquares()[8][9].setBlocked(true);
                        
                        grid.getSquares()[9][3].setBlocked(true);
                        grid.getSquares()[9][7].setBlocked(true);
                        
                        //for(int x = 0; x < new Random().nextInt((grid.getRows()*grid.getCols())/2)+5; x++){
                            boolean pass = false;
                            
                            //while(!pass){
                                int randRow = new Random().nextInt(7);
                                int randCol = new Random().nextInt(7);
                                
                                if(randRow == 0 && randCol == 0){
                                    randRow = new Random().nextInt(7);
                                    randCol = new Random().nextInt(7);
                                }
                                if(grid.getSquares()[randRow][randCol].getBlocked()){
                                    randRow = new Random().nextInt(7);
                                    randCol = new Random().nextInt(7);
                                }
                                //grid.getSquares()[randRow][randCol].setBlocked(true);
                                //pass = true; 
                            //}
                                
                            
                            //grid.addPiece("P"+x,new Random().nextInt(7),new Random().nextInt(7),0,0);
                            
                        //}
                        grid.addPiece("Target",9,9,0,0);
                        BoardPiece target = grid.getPiece("Target");
                        target.setColor(Color.GREEN);
                        
                        //BoardPiece p2 = grid.getPiece("p2");
                        //p2.setColor(Color.yellow);
                        break;
                    }catch(Exception ex){
                        //Logger.getLogger(BoardGraphical.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }).start();
        
        new Thread(new Runnable(){
            @Override
            public void run(){
                BoardGrid grid = null;
                boolean runner = true;
                
                while(runner){
                    try{
                        //grid = bg.getGrid();
                        grid.addAIPiece("AI",0,0,0,0);
                        runner = false;
                        //ai.walkThroughGrid();
                        //ai.lookForOthers();
                          
                    }catch(Exception ex){
                        //Logger.getLogger(BoardGraphical.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
                AiPiece ai = (AiPiece)grid.getPiece("AI");
                ai.setColor(Color.red);
                List<GridRect> testPath = ai.bfs(ai.getRow(),ai.getCol(), 9,9);
                
                try{
                    Thread.sleep(2000);
                }catch(Exception ex){}
                
                System.out.println("Testing shortest path");
                for(GridRect rect: testPath){
                    try{
                        Thread.sleep(2000);
                        System.out.println(rect.getRow() + " " + rect.getCol());
                        ai.setPositionByRow(rect.getRow(), rect.getCol());
                    }catch(Exception ex){}
                    
                }
            }
        }).start();
        /* 
        new Thread(new Runnable(){
            
            @Override
            public void run(){
                BoardPiece p1 = null;
                bg.runner = true;
                List pieceMoveList = new LinkedList();
                while(bg.runner){
                    try{
                        pieceMoveList.clear();
                        
                        
                        if(bg.getGrid().getPiece(1,1) == null){
                            continue;
                        }else{
                            if(p1 == null){
                                p1 = bg.getGrid().getPiece(1,1);
                            }
                        }
                        pieceMoveList.addAll(p1.moveList);
                        Point pp = (Point) pieceMoveList.get(0);
                        System.out.println(pp);
                        
                        
                        
                        
                        Thread.sleep(1000);
                        Boolean randDir1 = new Random().nextBoolean();
                        Boolean randDir2 = new Random().nextBoolean();
                        int dirNum = 0;
                        if(randDir2){
                            dirNum = 1;
                        }else{
                            dirNum = -1;
                        }
                        
                        //have to track the piece
                        
                        
                        if(randDir1){
                            bg.getGrid().movePieceX(p1,dirNum);
                        }else{
                            bg.getGrid().movePieceY(p1,dirNum);
                        }
                        
                        
                    }catch(InterruptedException | NullPointerException ex){
                        //Logger.getLogger(BoardGraphical.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }).start();
        
        new Thread(new Runnable(){
            public void run(){
                //while(true){
                    try{
                        Thread.sleep(1000 * 10);
                        bg.runner = false;
                        int x = 0;
                        for(Object obj: bg.getGrid().getPiece(1,1).moveList){
                            if(obj == null){
                                continue;
                            }
                            x++;
                        }
                        for(Object obj: bg.getGrid().getPiece(1,1).moveList){
                            if(obj == null){
                                continue;
                            }
                            Point objPoint = (Point) obj;
                            testMoves.add(objPoint);
                            
                            System.out.println("Move " + x-- + " - " + objPoint);
                        }
                        
                       
                        //System.exit(0);
                    }catch(Exception ex){}
                //}
            }
        }).start();
        */
    }
    
}