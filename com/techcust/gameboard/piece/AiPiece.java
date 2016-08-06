package com.techcust.gameboard.piece;


import com.techcust.gameboard.board.BoardGrid;
import com.techcust.gameboard.board.GridRect;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AiPiece extends BoardPiece{
    private BoardGrid board;
    
    public AiPiece(BoardGrid board, String id, int row, int col, int width, int height){
        super(board,id,row,col,width,height);
        //lookForOthers();
    }
    public final int minSpeed = 1, maxSpeed = 5;
    private int speed = 3;
    private volatile boolean walk = false;
    
    
    public int getSpeed(){
        return speed;
    }
    public void setSpeed(int speed){
        this.speed = speed;
    }
    public void setWalk(boolean walk){
        this.walk = walk;
    }
    
    public void walkMazeByNeighbor(int speed){
        if(speed < minSpeed || speed > maxSpeed){
            return;
        }
        new Thread(new Runnable(){
            @Override
            public void run(){
                walk = false;
                
                List<GridRect> neighborList = new ArrayList();

                GridRect currentRect = getBoard().getSquares()[getRow()][getCol()];
                GridRect prevRect = null;
                
                walk = true;
                while(walk){
                    //try{
                    neighborList.clear();
                        
                    setPositionByRow(currentRect.getRow(), currentRect.getCol());
                    try {
                        Thread.sleep(calculateSpeedInTime(speed));
                    } catch (InterruptedException ex) {
                        Logger.getLogger(AiPiece.class.getName()).log(Level.SEVERE, null, ex);
                    }
                        

                        for(GridRect neighbor: currentRect.getNeighbors()){
                            if(prevRect != null){
                                if(neighbor.equals(prevRect)){
                                    continue;
                                }
                            }
                            if(neighbor.getBlocked()){
                                continue;
                            }
                            neighborList.add(neighbor);
                        }

                        if(neighborList.size() <= 0){//no neighbors and prev rect is only neighbor
                            if(prevRect != null){
                                GridRect tempRect = currentRect;
                                currentRect = prevRect;
                                prevRect = tempRect;
                            }
                        }
                        if(neighborList.size() == 1){ //move to only neighbor and set prev to current
                            prevRect = currentRect;
                            currentRect = neighborList.get(0);
                        }
                        if(neighborList.size() > 1){//choose random neighbor
                            int randNeighborIndex = new Random().nextInt(neighborList.size());
                            prevRect = currentRect;
                            currentRect = neighborList.get(randNeighborIndex);
                        }
                        
                    //}catch(Exception ex){System.out.println(ex);}
                }
                
            }
        }).start();
    }
    private void lookForOthers(){
        new Thread(new Runnable(){
            @Override
            public void run(){
                int maxDepth = 3;
                while(true){
                    try{
                        //System.out.println(getId() + " In " + getRow() + " " + getCol());
                        
                        
                        //look right
                        for(int depth = 1; depth <= maxDepth; depth++){}
                        for(int depth = 1; depth <= maxDepth; depth++){}
                        for(int depth = 1; depth <= maxDepth; depth++){}
                        for(int depth = 1; depth <= maxDepth; depth++){}
                        
                        
                        
                        for(int depth = 1; depth <= maxDepth; depth++){
                            
                            
                            if(getCol() + depth <= getBoard().getCols()){ //searchesDepth for looking right
                                //if()
                                if(getBoard().getPiece(getRow(), getCol()+depth) != null){ //look right
                                    //piece there
                                    System.out.println("Piece On Right at " + getRow() + " " + (getCol()+depth));
                                }
                            }
                            if(getCol() - depth >= 0){
                                if(getBoard().getPiece(getRow(),getCol()-depth) != null){ //look left
                                    //piece there
                                    System.out.println("Piece On Left at "  + getRow() + " " + (getCol()-depth));
                                }
                            }
                            if(getRow() + depth <= getBoard().getRows()){
                                if(getBoard().getPiece(getRow()+depth,getCol()) != null){ //look down
                                    //piece there
                                    System.out.println("Piece On Bottom at " + (getRow()+depth) + " " + getCol());
                                }
                            }
                            
                            
                            if(getRow() - depth >= 0){
                                if(getBoard().getPiece(getRow()-depth,getCol()) != null){ //look up
                                    //piece there
                                    System.out.println("Piece On Top at " + (getRow()-depth) + " " + getCol());
                                }
                            }
                            
                        }
                        
                        
                    }catch(Exception ex){System.out.println("Looking " + ex);}
                }
            }
        }).start();
    }
    
    public int calculateSpeedInTime(int speed){
        if(speed < minSpeed){ //return default speeds
            speed = minSpeed;
        }
        if(speed > maxSpeed){ //return default speeds
            speed = maxSpeed;
        }
        
        switch(speed){
            case 1: return 2000;
            case 2: return 1200;
            case 3: return 1000;
            case 4: return 600;
            case 5: return 200;
            default : return 0;//something went wrong
        }
    }
    
    public int calcDistTo(GridRect start, GridRect end){
        if(start == null || end == null){
            return Integer.MAX_VALUE;
        }
        return (int) Math.sqrt( Math.pow((start.getRow()-end.getRow()),2) + Math.pow((start.getCol()-end.getCol()),2));
    }
    
    public GridRect getRectWithLowestDistance(List<GridRect> list){
        GridRect low = null;
        
        for(GridRect rect: list){
            
            if(low == null){
                low = rect;
            }else{
                if(rect.getG() < low.getG()){
                    low = rect;
                }
            }
            
        }
        return low;
    }
    
    public List<GridRect> bfs(int row1, int col1, int row2, int col2){
        if(getBoard().getSquares()[row1][col1] == null || getBoard().getSquares()[row2][col2] == null){
            return null;
        }
        
        GridRect startRect = getBoard().getSquares()[row1][col1];
        GridRect targetRect = getBoard().getSquares()[row2][col2];
        
        Queue<GridRect> q = new LinkedList();
        List<GridRect> beenList = new LinkedList();
        List<GridRect> movingList = new LinkedList();
        
        q.add(startRect);
        beenList.add(startRect);
        
        while(!q.isEmpty()){
            GridRect currentRect = q.peek();
            
            if(currentRect.equals(targetRect)){
                return movingList;
            }else{
                for(GridRect neighbor: currentRect.getNeighbors()){
                    if(neighbor.equals(targetRect)){
                        neighbor.setParent(currentRect);
                        movingList.add(0,neighbor);
                        
                        GridRect prevRect = neighbor;
                        
                        while(prevRect.getParent() != null && !prevRect.getParent().getBlocked()){
                            movingList.add(0,prevRect.getParent());
                            prevRect = prevRect.getParent();
                        }
                        return movingList;
                    }
                    if(beenList.contains(neighbor) || neighbor.getBlocked()){
                        continue;
                    }
                    neighbor.setParent(currentRect);
                    beenList.add(neighbor);
                    q.add(neighbor);
                }
                q.remove();
            }
        }
        return null;
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
    
    public void optSearch(int initRow1, int initCol1, int initRow2, int initCol2){
        //generates shortest path to object using astar, after the first move, checks if objects location has changed
        //or if the conditionn of any of the neighbors has changed and recalculates the shortest path.
        
        if(getBoard().getSquares()[initRow1][initCol1] == null || getBoard().getSquares()[initRow2][initCol2] == null){
            return;
        }
        
        getBoard().log("Search and Avoid");
        GridRect currentRect = getBoard().getSquares()[initRow1][initCol1]; //Searcher track
        GridRect targetRect = getBoard().getSquares()[initRow2][initCol2]; //Searchee track
        List<GridRect> path = new LinkedList<>(); //final path to object
        
        boolean searching = true;
        while(searching){
            
            //loop until found path to object, break out of loop and go to travel path
            
            boolean pathFound = false;
            while(!pathFound){
                
                path = dijkstraSearch(initRow1,initCol1, initRow2, initCol2);
                
                if(path != null || !path.isEmpty()){
                    pathFound = true;
                }
                
                
            }//end path found loop
            getBoard().log("Search and Avoid: Found Path Searching");
            //travel and check
            
            for(GridRect rect: path){
                getBoard().log("At " + currentRect.getRow() + ", " + currentRect.getCol());
                getBoard().log("Moving to " + rect.getRow() + ", " + rect.getCol());
                currentRect.setRow(rect.getRow());
                currentRect.setCol(rect.getCol());
                
                try {
                    Thread.sleep(calculateSpeedInTime(getSpeed()));
                } catch (InterruptedException ex) {
                    //Logger.getLogger(AiPiece.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                if(targetRect.getRow() != initRow2 || targetRect.getCol() != initCol2){
                    System.out.println("Not in same position");
                    break;
                }
                if(currentRect.getRow() == targetRect.getRow() || currentRect.getCol() == targetRect.getCol()){
                    getBoard().log("Captured");
                }
            }
            searching = false;
        }
        
    }
    
    //dont use
    public List<GridRect> astarsearch(int row1, int col1, int row2, int col2){
        if(getBoard().getSquares()[row1][col1] == null || getBoard().getSquares()[row2][col2] == null){
            return null;
        }
        
        GridRect currentRect = getBoard().getSquares()[row1][col1];
        GridRect targetRect = getBoard().getSquares()[row2][col2];
        
        
        Queue<GridRect> openList = new LinkedList();
        List<GridRect> closedList = new LinkedList<>();
        List<GridRect> path = new LinkedList<>();
        
        openList.clear();
        closedList.clear();
        
        currentRect.setF(0);
        currentRect.setG(0);
        openList.add(currentRect);
        
        System.out.println("Start " + currentRect.getRow() + " " + currentRect.getCol());
        while(!openList.isEmpty()){
            currentRect = openList.peek(); 
            closedList.add(currentRect);
            
            System.out.println("Current " + currentRect.getRow() + " " + currentRect.getCol());
            if(currentRect.equals(targetRect)){
                path.add(currentRect); //found target
                System.out.println("Found target " + currentRect.getRow() + " " + currentRect.getCol());
                break;
            }else{
                for(GridRect neighbor: currentRect.getNeighbors()){
                    System.out.print("Neighbor " + neighbor.getRow() + " " + neighbor.getCol() + " ");
                    
                    if(neighbor.equals(targetRect)){
                        System.out.println("Found Target " + neighbor.getRow() + " " + neighbor.getCol());
                        path.add(neighbor);
                        break;
                    }
                    if(neighbor.getBlocked()){
                        System.out.println("Blocked");
                        continue;
                    }
                    if(closedList.contains(neighbor)){
                        continue;
                    }
                    if(!openList.contains(neighbor)){
                        System.out.println("Not in open list");
                        openList.add(neighbor);
                        
                        int distanceToGoal = 0;
                        int distanceBetweenRects = distanceToGoal = 0;
                        
                        
                        neighbor.setParent(currentRect);
                        neighbor.setG(currentRect.getG() + distanceBetweenRects);
                        neighbor.setH(distanceToGoal);
                        neighbor.setF(neighbor.getG()+neighbor.getH());
                        
                    }else{
                        System.out.println("In open list");
                        
                        if(neighbor.getG() > currentRect.getG()){
                            neighbor.setParent(currentRect);
                            neighbor.setG(currentRect.getG());
                        }
                    }
                    openList.remove();
                    
                    System.out.println();
                }
                System.out.println();
            }
            if(openList.isEmpty()){ //no path exists
                System.out.println("No path found");
                return new LinkedList<>();
            }
            
        }
        return null;
    }
}