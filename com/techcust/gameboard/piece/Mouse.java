package com.techcust.gameboard.piece;

import com.techcust.gameboard.board.CatMouseGrid;
import com.techcust.gameboard.board.GridRect;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
public class Mouse extends AiPiece{
    private int eatenCheese = 0;
    private volatile boolean alive = false;
    private volatile Iterator<Cheese> cheeseIter = getBoard().getCheeseList().iterator();
    public Mouse(CatMouseGrid board, String id, int row, int col, int width, int height) {
        super(board, id, row, col, width, height);
        alive = true;
        detectMouseEatCheese();
        //determineWalk();
    }
    
    @Override
     public CatMouseGrid getBoard(){
        return (CatMouseGrid) super.getBoard();
    }
     
    public void increaseEatenCheese(){
        ++eatenCheese;
    }
    public int getEatenCheeseAmount(){
        return eatenCheese;
    }
    
    public synchronized void searchForCheese(){
        Mouse mouse = this;
        CatMouseGrid board = (CatMouseGrid)getBoard();
        new Thread(new Runnable(){
            @Override
            public void run(){
                boolean rand = new Random().nextBoolean();
                while(true){
                    try{
                        Iterator<Cheese> cheeseIter = board.getCheeseList().iterator();
                        if(cheeseIter.hasNext()){
                            Cheese cheese = cheeseIter.next();
                            List<GridRect> pathToCheese = null;
                            //if(rand){
                              //  System.out.println(getId() + " using dijkstra");
                              if(getBoard().getGridCat() != null){
                                  pathToCheese = searchAwayFromCat(mouse.getRow(), mouse.getCol(), cheese.getRow(), cheese.getCol());   
                              }else if(getBoard().getGridCat() == null){
                                  pathToCheese = dijkstraSearch(mouse.getRow(), mouse.getCol(), cheese.getRow(), cheese.getCol()); 
                              }
                                
                            //}else{
                              //  System.out.println(getId() + " using bfs");
                                //pathToCheese = bfs(getRow(), getCol(), cheese.getRow(), cheese.getCol());
                            //}
                            
                            for(GridRect rect: pathToCheese){
                                if(!board.getCheeseList().contains(cheese)){
                                    break;
                                }
                                setPositionByRow(rect.getRow(), rect.getCol());
                                Thread.sleep(mouse.calculateSpeedInTime(mouse.getSpeed()));
                            }


                        }
                    }catch(Exception ex){System.out.println("Cheese search " + ex);}
                }

            }
        }).start();
    }
    
    public synchronized void searchForCheese2(){
        Mouse mouse = this;
        CatMouseGrid board = (CatMouseGrid)getBoard();
        new Thread(new Runnable(){
            @Override
            public void run(){
                while(alive){
                    try{
                        if(!board.getCheeseList().isEmpty()){
                            Iterator<Cheese> cheeseIter = board.getCheeseList().iterator();
                            if(cheeseIter.hasNext()){
                                Cheese cheese = cheeseIter.next();
                                optSearch(mouse.getRow(), mouse.getCol(), cheese.getRow(), cheese.getCol());
                            }
                        }
                        
                    }catch(Exception ex){System.out.println("Cheese search " + ex);}
                }

            }
        }).start();
    }
    
    private synchronized void detectMouseEatCheese(){
        CatMouseGrid board = (CatMouseGrid)getBoard();
        
        new Thread(new Runnable(){
            @Override
            public void run(){
                while(alive){
                    try{
                        if(!board.getCheeseList().isEmpty()){
                            cheeseIter = board.getCheeseList().iterator();
                            
                            if(cheeseIter.hasNext()){
                                Cheese cheese = cheeseIter.next();
                                
                                if(intersects(cheese)){
                                    cheeseIter.remove();
                                    increaseEatenCheese();
                                    System.out.println(getId() + " " + getEatenCheeseAmount());
                                }
                            }
                        }
                        
                    }catch(Exception ex){System.out.println("Detect " + getId() + " " + ex);}
                }
            }
        }).start();
    }
    
    volatile boolean walkGrid = false;
    
    private void determineWalk(){
        new Thread(new Runnable(){
            @Override
            public void run(){
                while(true){
                    walkGrid = getBoard().getCheeseList().isEmpty();
                }
            }
        }).start();
    }
    @Override
    public void optSearch(int initRow1, int initCol1, int initRow2, int initCol2){
        //generates shortest path to object using astar, after the first move, checks if objects location has changed
        //or if the conditionn of any of the neighbors has changed and recalculates the shortest path.
        
        if(getBoard().getSquares()[initRow1][initCol1] == null || getBoard().getSquares()[initRow2][initCol2] == null){
            return;
        }
        
        GridRect currentRect = getBoard().getSquares()[initRow1][initCol1]; //Searcher track
        GridRect targetRect = getBoard().getSquares()[initRow2][initCol2]; //Searchee track
        List<GridRect> path = new LinkedList<>(); //final path to object
        
        boolean searching = true;
        while(searching){
            
            //loop until found path to object, break out of loop and go to travel path
            
            boolean pathFound = false;
            while(!pathFound){
                try{
                    path = dijkstraSearch(initRow1,initCol1, initRow2, initCol2);
                }catch(Exception ex){System.out.println(ex);}
                
                
                if(path != null || !path.isEmpty()){
                    pathFound = true;
                }
                
                
            }//end path found loop
            //travel and check
            Iterator<GridRect> pathIter = path.iterator();
            
            while(pathIter.hasNext()){
                GridRect rect = pathIter.next();
                
                if(getBoard().getSquares()[rect.getRow()][rect.getCol()].getBlocked()){ //checks if position is now blocked
                    break;
                }else{
                    setPositionByRow(rect.getRow(),rect.getCol());
                    try {
                        Thread.sleep(calculateSpeedInTime(getSpeed()));
                    } catch (InterruptedException ex) {}
                }
                
                
                if(getBoard().getCheese(targetRect.getRow(), targetRect.getCol()) == null){
                    break;
                }
                
            }
            searching = false;
        }
        
    }
    
    
    public List<GridRect> searchAwayFromCat(int row1, int col1, int row2, int col2){
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
                        
                    while(prevRect.getParent() != null && !prevRect.getParent().getBlocked()){
                        movingList.add(0,prevRect.getParent());
                        prevRect = prevRect.getParent();
                    }
                    
                    return movingList;
                    
                }else{
                    int distance = calcDistTo(currentRect,neighbor);
                    
                    Cat cat = null;
                    GridRect catSquare = null;
                    if(getBoard().getGridCat() != null){
                        cat = getBoard().getGridCat(); 
                        catSquare = getBoard().getSquares()[cat.getRow()][cat.getCol()];
                    }
                    int catScore = calcDistTo(neighbor,catSquare);
                    
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
    public GridRect getRectFarFromCat(List<GridRect> list){
        if(getBoard().getGridCat() == null){
            //dijkstraSearch();
        }
        GridRect low = null;
        
        
        int cost = 0;
        
        Cat cat = null;
        GridRect catSquare = null;
        if(!getBoard().getCatList().isEmpty()){
            cat = getBoard().getCatList().get(0); 
            catSquare = getBoard().getSquares()[cat.getRow()][cat.getCol()];
        }
        for(GridRect rect: list){
            
            if(low == null){
                low = rect;
            }else{
                if(rect.getG() < low.getG() && calcDistTo(rect,catSquare) > calcDistTo(low,catSquare)){
                    low = rect;
                }
            }
            
        }
        return low;
    }
}