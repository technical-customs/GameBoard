package com.techcust.gameboard.piece;

import com.techcust.gameboard.board.BoardGrid;
import com.techcust.gameboard.board.CatMouseGrid;
import com.techcust.gameboard.board.GridRect;
import java.awt.Graphics;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Cat extends AiPiece{
    private int eatenMice = 0;
    
    public Cat(BoardGrid grid, String id, int row, int col, int width, int height){
        super(grid,id,row,col,width,height);
        detectCatEatMouse();
    }
    
    public void increaseEatenMice(){
        ++eatenMice;
    }
    public int getEatenMiceAmount(){
        return eatenMice;
    }
    
    public synchronized void searchForMouse(){
        Cat cat = this;
        CatMouseGrid board = (CatMouseGrid)getBoard();
        new Thread(new Runnable(){
            @Override
            public void run(){
                boolean rand = new Random().nextBoolean();
                while(true){
                    try{
                        Iterator<Mouse> mouseIter = board.getMouseList().iterator();
                        
                        if(mouseIter.hasNext()){
                            //get mouse with closet distance
                            
                            Mouse mouse = mouseIter.next();
                            List<GridRect> pathToMouse = dijkstraSearch(cat.getRow(), cat.getCol(), mouse.getRow(), mouse.getCol());
                            
                            for(GridRect rect: pathToMouse){
                                if(!board.getMouseList().contains(mouse)){
                                    break;
                                }
                                
                                setPositionByRow(rect.getRow(), rect.getCol());
                                Thread.sleep(cat.calculateSpeedInTime(cat.getSpeed()));
                            }


                        }
                    }catch(Exception ex){System.out.println("Mouse search " + ex);}
                }

            }
        }).start();
    }
    private synchronized void detectCatEatMouse(){
        CatMouseGrid board = (CatMouseGrid)getBoard();
        final Cat cat = this;
        
        new Thread(new Runnable(){
            @Override
            public void run(){
                while(true){
                    try{
                        if(!board.getCheeseList().isEmpty()){
                            Iterator<Mouse> mouseIter = board.getMouseList().iterator();
                            
                            if(mouseIter.hasNext()){
                                Mouse mouse = mouseIter.next();
                                
                                if(cat.intersects(mouse)){
                                    mouseIter.remove();
                                    cat.increaseEatenMice();
                                    System.out.println(getId() + " " + getEatenMiceAmount());
                                }
                            }
                        }
                        
                    }catch(Exception ex){System.out.println("Detect " + cat.getId() + " " + ex);}
                }
            }
        }).start();
    }
    
    public List<GridRect> dijkstraSearch(int row1, int col1, int row2, int col2){
        if(getBoard().getSquares()[row1][col1] == null || getBoard().getSquares()[row2][col2] == null){
            return null;
        }
        
        GridRect startRect = getBoard().getSquares()[row1][col1];
        GridRect targetRect = getBoard().getSquares()[row2][col2];
        
        List<GridRect> movingList = new LinkedList<>();
        List<GridRect> visitedList = new LinkedList<>();
        List<GridRect> unvisitedList = new LinkedList<>();
        movingList.clear();
        visitedList.clear();
        unvisitedList.clear();
        
        for(int r = 0; r < getBoard().getRows(); r++){
            for(int c = 0; c < getBoard().getCols(); c++){
                GridRect rect = getBoard().getSquares()[r][c];
                rect.setG(Integer.MAX_VALUE);
                rect.setParent(null);
                unvisitedList.add(rect);
            }
        }
        startRect.setG(0);
        
        while(!unvisitedList.isEmpty()){
            GridRect currentRect = getRectWithLowestDistance(unvisitedList);
            unvisitedList.remove(currentRect);
            
            for(GridRect neighbor: currentRect.getNeighbors()){
                if(neighbor.getBlocked()){
                    continue;
                }
                if(neighbor.equals(targetRect)){
                    visitedList.add(neighbor);
                    //go through list
                    neighbor.setParent(currentRect);
                    movingList.add(0,neighbor);
                        
                    GridRect prevRect = neighbor;
                        
                    while(prevRect.getParent() != null){
                        movingList.add(0,prevRect.getParent());
                        prevRect = prevRect.getParent();
                    }
                    
                    return movingList;
                    
                }else{
                    int distance = calcDistTo(currentRect,neighbor);
                    if(neighbor.getG() > distance + currentRect.getG()){
                        neighbor.setG(distance + currentRect.getG());
                        neighbor.setParent(currentRect);
                    }
                } 
            }
            visitedList.add(currentRect);
        }
        
        return null;
    }
    
    
    @Override
    public void pieceDraw(Graphics g){
        g.setColor(this.getColor());
        g.fillRoundRect(this.x, this.y, this.width, this.height, 0, 0);
    }
}