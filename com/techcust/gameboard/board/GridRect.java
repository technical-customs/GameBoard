package com.techcust.gameboard.board;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;
import java.util.List;

public class GridRect extends Rectangle{
    private List<GridRect> neighbors; //edges
    private GridRect parent = null;
    private int f,g,h;
    //f = g + h    g = cost to get from start to current    h = estimate to get to target   
    private int row, col;
    private boolean blocked = false;
    private Color color;
    
    
    public GridRect(int x, int y, int width, int height, Color color){
        super(x,y,width,height);
        this.color = color;
        neighbors = new LinkedList();
    }
    
    public List<GridRect> getNeighbors(){
        return neighbors;
    }
    public GridRect getParent(){
        return parent;
    }
    public void setParent(GridRect parent){
        this.parent = parent;
    }
    public int getF(){
        return f;
    }
    public void setF(int f){
        this.f = f;
    }
    public int getG(){
        return g;
    }
    public void setG(int g){
        this.g = g;
    }
    public int getH(){
        return h;
    }
    public void setH(int h){
        this.h = h;
    }
    //-------------LOCATION--------------------
    public int getXPos(){
        return this.x;
    }
    public int getYPos(){
        return this.y;
    }
    
    //public int getRowCenter(){
        //return ( this.x + (this.width/2) );
    //}
    //public int getColCenter(){
        //return ( this.y + (this.height/2) );
    //}
    //----------END LOCATION------------------
    
    //-----------GETTERS SETTERS---------------------
    public void setBlocked(boolean blocked){
        this.blocked = blocked;
    }
    public boolean getBlocked(){
        return blocked;
    }
    public void setRow(int row){
        this.row = row;
    }
    public int getRow(){
        return row;
    }
    public void setCol(int col){
        this.col = col;
    }
    public int getCol(){
        return col;
    }
    
    
    protected void setColor(Color color){
        this.color = color;
    }
    protected Color getColor(){
        return color;
    }
    //-------------END GET SET------------------------
    
    //-----------------DRAWS-------------------
    protected void markCenter(Graphics g){
        g.setColor(Color.red);
        //g.fillRect( getRowCenter(), getColCenter(), 1, 1);
    }
    protected void rectDraw(Graphics g){
        g.setColor(this.color);
        if(this.blocked){
            g.setColor(Color.darkGray);
        }
        g.fillRect(this.x, this.y, this.width, this.height);
    }
    //-------------END DRAWS-----------------
}