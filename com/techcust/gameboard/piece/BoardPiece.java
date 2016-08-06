package com.techcust.gameboard.piece;

import com.techcust.gameboard.board.BoardGrid;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Collections;
import java.util.List;

public class BoardPiece extends Rectangle{
    private Thread positionThread;
    
    private String id;
    private volatile int row, col;
    private Color color = Color.blue;
    
    private final BoardGrid board;
    public BoardPiece(BoardGrid board, String id, int row, int col, int width, int height){
        this.board = board;
        this.id = id;
        
        this.row = row;
        this.col = col;
        
        Point p = board.centerPiece(row, col, width, height);
        this.x = p.x;
        this.y = p.y;
        
        this.width = width;
        this.height = height;
        
        
        //setPositioning();
    }
    
    //------------------GETTERS SETTERS-----------------
    public BoardGrid getBoard(){
        return board;
    }
    public void setId(String id){
        this.id = id;
    }
    public String getId(){
        return id;
    }
    
    public void setColor(Color color){
        this.color = color;
    }
    public Color getColor(){
        return color;
    }
    
    public int getCol(){
        return col;
    }
    public void setCol(int col){
        this.col = col;
    }
    public int getRow(){
        return row;
    }
    public void setRow(int row){
        this.row = row;
    }
    
    
    public void moveX(int x){
        this.x += x;
        
        if(x > 0){
            col = col + 1;
        }
        if(x < 0){
            col = col - 1;
        }
    }
    public void moveY(int y){
        this.y += y;
        
        if(y > 0){
            row = row + 1;
        }
        if(y < 0){
            row = row - 1;
        }
    }
    
    public void movePieceX(int dir){
        if(dir > 0){
            if((getCol() + 1) > board.getBoardWidth()){
                return;
            }
            if(board.getSquares()[getRow()][getCol()+1].getBlocked()){
                return;
            }
            moveX(board.getGridWidth());
        }
        if(dir < 0){
            if((getCol() - 1) < 0){
                return;
            }
            if(board.getSquares()[getRow()][getCol()-1].getBlocked()){
                return;
            }
            moveX(-board.getGridWidth());
        }
    }//
    public void movePieceY(int dir){
        if(dir > 0){
            if((getRow() + 1) > getBoard().getRows()){
                return;
            }
            if(board.getSquares()[getRow()+1][getCol()].getBlocked()){
                return;
            }
            moveY(getBoard().getGridHeight());
        }
        if(dir < 0){
            if((getRow() - 1) < 0){
                return;
            }
            if(board.getSquares()[getRow()-1][getCol()].getBlocked()){
                return;
            }
            moveY(-getBoard().getGridHeight());
        }
        //recordMove();
    }
    
    public void setPosition(int x, int y){
        this.x = x;
        this.y = y;
    }
    public void setPositionByRow(int row, int col){
        this.row = row;
        this.col = col;
        
        setPosition(board.centerPiece(row, col, width, height));
    }
    public void setPosition(Point point){
        this.x = point.x;
        this.y = point.y;
    }
    public synchronized Point getPosition(){
        if(this.x == 0 && this.y == 0){
            return null;
        }
        return  new Point(this.x, this.y);
    }
    //-----------------END GET SET---------------------------
    
    public void followPath(List list){
        if(list == null){
            return;
        }
        List tempList = list; 
        Collections.reverse(tempList);
        
        for(Object obj: tempList){
            //System.out.println("Next move " + obj.getRow() + " " + obj.getCol());
            //this.setPositionByRow(obj.getRow(),obj.getCol());
            try{
                Thread.sleep(2000);
            }catch(Exception ex){}
        }
    }
    public void pieceDraw(Graphics g){
        g.setColor(this.color);
        g.fillOval(this.x, this.y, this.width, this.height);
    }
}