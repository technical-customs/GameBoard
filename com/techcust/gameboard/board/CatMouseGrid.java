package com.techcust.gameboard.board;

import com.techcust.gameboard.piece.Cat;
import com.techcust.gameboard.piece.Cheese;
import com.techcust.gameboard.piece.Mouse;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class CatMouseGrid extends BoardGrid{
    private volatile  List<Cheese> cheeseList = new ArrayList<>();
    private volatile List<Mouse> mouseList = new ArrayList<>();
    private volatile List<Cat> catList = new ArrayList<>();
    private Cat cat;
    
    public CatMouseGrid(){
        super();
    }
    public CatMouseGrid(int row, int col){
        super(row,col);
    }
    
    
    public synchronized List<Cheese> getCheeseList(){
        return this.cheeseList;
    }
    public Cheese getCheese(String id) throws NullPointerException{
        Iterator<Cheese> iter = cheeseList.iterator();
        
        while(iter.hasNext()){
            Cheese p = iter.next();
            
            if(p.getId().equals(id.toUpperCase())){
                return p;
            }
        }
        return null;
    }
    public Cheese getCheese(int row, int col) throws NullPointerException{
        Iterator<Cheese> iter = cheeseList.iterator();
        
        while(iter.hasNext()){
            Cheese p = iter.next();
            
            if(p.getRow() == row && p.getCol() == col){
                return p;
            }
        }
        return null;
    }
    public void addCheese(String id, int row, int col, int width, int height) throws Exception{
        if(row >= getRows() || col >= getCols() || row < 0 || col < 0){
            return;
        }
        if(getPiece(row,col) != null || getPiece(id.toUpperCase()) != null){
            throw new Exception();
        }
        if(getPiece(row,col) != null || getPiece(id.toUpperCase()) != null){
            return;
        }
        
        if(width == 0){
            width = getGridWidth()/2;
        }
        if(height == 0){
            height = getGridHeight()/2;
        }
        
        Cheese cheese = new Cheese(this,id.toUpperCase(),row,col,width,height);
        
        cheeseList.add(cheese);
    }
    public void addRandomCheese(){
        int randX = new Random().nextInt(getRows());
        int randY = new Random().nextInt(getCols());
        StringBuilder cheeseId = new StringBuilder();
        
        boolean notBlocked = false;
        
        while(!notBlocked){
            if(getSquares()[randX][randY].getBlocked()){
                randX = new Random().nextInt(getRows());
                randY = new Random().nextInt(getCols());
            }else{
                notBlocked = true;
            }
        }
        
        boolean notIn = false;
        int idNum = cheeseList.size()-1;
        
        while(!notIn){
            if(cheeseList.contains((Cheese)getPiece("Cheese"+idNum))){
                idNum++;
            }else{
                notIn = true;
            }
        }
        
        try{
            addCheese("Cheese"+idNum, randX, randY,0,0);
        }catch(Exception ex){
            System.out.println(ex);
        }
        
    }
    public synchronized void generateCheese(){
        new Thread(new Runnable(){
            @Override
            public void run(){
                while(true){
                    if(cheeseList.isEmpty()){
                        //System.out.println("Empty");
                        addRandomCheese();
                    }
                }
            }
        }).start();
    }
    
    public synchronized List<Mouse> getMouseList(){
        return this.mouseList;
    }
    public Mouse getMouse(String id) throws NullPointerException{
        Iterator<Mouse> iter = mouseList.iterator();
        
        while(iter.hasNext()){
            Mouse p = iter.next();
            
            if(p.getId().equals(id.toUpperCase())){
                return p;
            }
        }
        return null;
    }
    public void addMouse(String id, int row, int col, int width, int height) throws Exception{
        if(row >= getRows() || col >= getCols() || row < 0 || col < 0){
            return;
        }
        if(getPiece(row,col) != null || getPiece(id.toUpperCase()) != null){
            throw new Exception();
        }
        if(getPiece(row,col) != null || getPiece(id.toUpperCase()) != null){
            return;
        }
        
        if(width == 0){
            width = getGridWidth()/2;
        }
        if(height == 0){
            height = getGridHeight()/2;
        }
        
        Mouse mouse = new Mouse(this,id.toUpperCase(),row,col,width,height);
        mouseList.add(mouse);
    }
    
    public synchronized List<Cat> getCatList(){
        return this.catList;
    }
    public Cat getCat(String id) throws NullPointerException{
        Iterator<Cat> iter = catList.iterator();
        
        while(iter.hasNext()){
            Cat p = iter.next();
            
            if(p.getId().equals(id.toUpperCase())){
                return p;
            }
        }
        return null;
    }
    public Cat getGridCat() throws NullPointerException{
        if(cat == null){
            return null;
        }
        return cat;
    }
    public void addCat(String id, int row, int col, int width, int height) throws Exception{
        if(row >= getRows() || col >= getCols() || row < 0 || col < 0){
            return;
        }
        if(getPiece(row,col) != null || getPiece(id.toUpperCase()) != null){
            throw new Exception();
        }
        if(getPiece(row,col) != null || getPiece(id.toUpperCase()) != null){
            return;
        }
        
        if(width == 0){
            width = getGridWidth()/2;
        }
        if(height == 0){
            height = getGridHeight()/2;
        }
        
        Cat cat1 = new Cat(this,id.toUpperCase(),row,col,width,height);
        catList.add(cat);
    }
    public void addGridCat(String id, int row, int col, int width, int height) throws Exception{
        if(row >= getRows() || col >= getCols() || row < 0 || col < 0){
            return;
        }
        if(getPiece(row,col) != null || getPiece(id.toUpperCase()) != null){
            throw new Exception();
        }
        if(getPiece(row,col) != null || getPiece(id.toUpperCase()) != null){
            return;
        }
        
        if(width == 0){
            width = getGridWidth()/2;
        }
        if(height == 0){
            height = getGridHeight()/2;
        }
        
        cat = new Cat(this,id.toUpperCase(),row,col,width,height);
        catList.add(cat);
    }
   
    //Draws
    @Override
    protected void boardDraws(Graphics g){
        try{
            for(int r = 0; r < getRows(); r++){
                for(int c = 0; c < getCols(); c++){
                    //if(squares[r][c].getBlocked()){
                        //g.setColor(Color.darkGray);
                    //}
                    if(getSquares()[r][c] != null){
                        getSquares()[r][c].rectDraw(g);
                    }

                }
            }
            if(!cheeseList.isEmpty()){
                cheeseDraw(g);
            }
            if(!mouseList.isEmpty()){
                mouseDraw(g);
            }
            if(!catList.isEmpty()){
                catDraw(g);
            }
        }catch(Exception ex){
            System.out.println(ex);
        }
    }
    protected void cheeseDraw(Graphics g){
        for(Cheese piece: cheeseList){
            if(piece == null){
                continue;
            }
            piece.pieceDraw(g);
        }
    }
    protected void mouseDraw(Graphics g){
        for(Mouse piece: mouseList){
            if(piece == null){
                continue;
            }
            piece.pieceDraw(g);
        }
    }
    protected void catDraw(Graphics g){
        for(Cat piece: catList){
            if(piece == null){
                continue;
            }
            piece.pieceDraw(g);
        }
    }
}