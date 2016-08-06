package com.techcust.gameboard;

public interface Piece{   
    public void setId(String id);
    public void setRow(int row);
    public void setCol(int col);
    
    public String getId();
    public int getRow();
    public int getCol();
    
    public void moveUp();
    public void moveDown();
    public void moveRight();
    public void moveLeft();
}