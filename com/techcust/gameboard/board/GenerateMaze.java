package com.techcust.gameboard.board;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

public class GenerateMaze{
    final private BoardGrid grid;
    
    public GenerateMaze(BoardGrid grid){
        this.grid = grid;
        generateMaze();
    }
    
    private void getNeighborOnTop(GridRect rect){
        if(rect.getRow() - 1 < 0){
            
        }
    }
    private void getNeighborOnBottom(GridRect rect){
        if(rect.getRow() - 1 < 0){
            
        }
    }
    private void getNeighborOnRight(GridRect rect){
        if(rect.getRow() - 1 < 0){
            
        }
    }
    private void getNeighborOnLeft(GridRect rect){
        if(rect.getRow() - 1 < 0){
            
        }
    }
    
    private synchronized void generateMaze(){//generate maze from 0,0 to row,col
        
        //start by initializing all grid squares to blocked
        for(int r = 0; r < grid.getRows(); r++){
            for(int c = 0; c < grid.getCols(); c++){
                grid.getSquares()[r][c].setBlocked(false);
            }
        }
        Random rand = new Random();
        
        GridRect start = grid.getSquares()[0][0];
        
        new Thread(new Runnable(){//start walking maze generating neighbors and creating path to end
            @Override
            public void run(){
                //create two lists, one for the traveled path, one for untravelled other a q of the neighbors to travel
                
                List<GridRect> neighborList = new ArrayList();
                
                List<GridRect> traveled = new ArrayList<>();
                Queue<GridRect> checking = new LinkedList<>();
                List<GridRect> unchecked = new LinkedList<>();
                
                traveled.clear();
                checking.clear();

                GridRect startRect = grid.getSquares()[0][0];
                checking.add(startRect);
                
                while(!checking.isEmpty()){
                    try{
                        neighborList.clear();
                        
                        GridRect currentRect = checking.peek();
                        traveled.add(currentRect);
                        
                        System.out.println("Current " + currentRect.getRow() + " " + currentRect.getCol());
                        
                        for(GridRect neighbor: currentRect.getNeighbors()){
                            if(traveled.contains(neighbor)){
                                continue;
                            }
                            System.out.println("Neighbor " + neighbor.getRow() + " " + neighbor.getCol());
                            neighborList.add(neighbor);
                        }
                        System.out.println("Current Neighbors " + neighborList.size());

                        
                        if(neighborList.isEmpty()){
                            System.out.println("No neighbors");
                            
                            
                        }else if(neighborList.size() == 1){//no neighbors and prev rect is only neighbor
                            System.out.println("One neighbor");
                            for(GridRect n: neighborList){
                                checking.add(n);
                            }
                        }else if(neighborList.size() == 2){ //move to only neighbor and set prev to current
                            System.out.println("Two neighbors");
                            
                            if(traveled.size() <= 1){
                                System.out.println("Traveled once add all to check");
                                
                                for(GridRect n: neighborList){
                                    if(checking.contains(n)){
                                        continue;
                                    }
                                    checking.add(n);
                                }
                            }else{
                                System.out.println("Compare two");
                                
                                GridRect r1 = neighborList.get(0);
                                GridRect r2 = neighborList.get(1);
                                List<GridRect> r1List = new ArrayList();
                                List<GridRect> r2List = new ArrayList();
                                int r1Size;
                                int r2Size;
                                
                                for(GridRect n: r1.getNeighbors()){
                                    if(traveled.contains(n)){
                                        continue;
                                    }
                                    r1List.add(r2);
                                }
                                r1Size = r1List.size();
                                System.out.println("R1 size " + r1Size);
                                
                                for(GridRect n: r2.getNeighbors()){
                                    if(traveled.contains(n)){
                                        continue;
                                    }
                                    r2List.add(n);
                                }
                                r2Size = r2List.size();
                                System.out.println("R2 size " + r2Size);
                                
                                if(r1Size == r2Size){
                                    if(new Random().nextBoolean()){//choose random
                                        int randIndex = new Random().nextInt(2);
                                        for(int x = 0; x < neighborList.size(); x++){
                                            if(x == randIndex){
                                                if(checking.contains(neighborList.get(x))){
                                                    checking.remove(neighborList.get(x));
                                                }
                                                neighborList.get(x).setBlocked(true);
                                                continue;
                                            }
                                            checking.add(neighborList.get(x));
                                        }
                                        
                                    }else{//add all
                                        for(GridRect n: neighborList){
                                            if(checking.contains(n)){
                                                continue;
                                            }
                                            checking.add(n);
                                        }
                                    }
                                }else if(r1Size < r2Size){
                                    if(checking.contains(r1)){
                                        continue;
                                    }
                                    checking.add(r1);
                                    r2.setBlocked(true);
                                }else if(r1Size > r2Size){
                                    if(checking.contains(r2)){
                                        continue;
                                    }
                                    checking.add(r2);
                                    r1.setBlocked(true);
                                }
                            }
                            
                        }else if(neighborList.size() == 3){//create a split between two
                            System.out.println("Three neighbors");
                            
                            int randIndex = new Random().nextInt(neighborList.size());
                            
                            for(int rand = 0; rand < neighborList.size(); rand++){
                                if(rand == randIndex){
                                    System.out.println("Neighbor " 
                                                + neighborList.get(randIndex).getRow() + " " + neighborList.get(randIndex).getCol() 
                                                + " Blocked");
                                    neighborList.get(randIndex).setBlocked(true);
                                }else{
                                    if(checking.contains(neighborList.get(rand))){
                                        continue;
                                    }
                                    checking.add(neighborList.get(rand));
                                }
                                
                            }
                        }else if(neighborList.size() == 4){
                        }
                        checking.remove(currentRect);
                        
                        //Thread.sleep(1000);
                    }catch(Exception ex){System.out.println("Generate maze " + ex);}
                }
                System.out.println("No more to check");
            }
        }).start();
    }
    
    private void genMaze(){
        for(int r = 0; r < grid.getRows(); r++){
            for(int c = 0; c < grid.getCols(); c++){
                grid.getSquares()[r][c].setBlocked(true);
            }
        }
        //grid.generateNeighborList();
        Random rand = new Random();
        
        new Thread(new Runnable(){
            @Override
            public void run(){
                try{
                    recursiveMove(0,0);
                    recursiveMove(grid.getRows()-1,grid.getCols()-1);
                }catch(Exception ex){

                }
            }
        }).start();
        new Thread(new Runnable(){
            @Override
            public void run(){
                try{
                    recursiveMove(grid.getRows()-1,grid.getCols()-1);
                }catch(Exception ex){

                }
            }
        }).start();
        
        
        
    }
    
    private void recursiveMove(int row, int col){
        Integer[] dirs = genRandomDirs();
        grid.getSquares()[row][col].setBlocked(false);
        for(int x = 0; x < dirs.length; x++){
            System.out.println("Moving");
            switch(dirs[x]){
                
                case 1://up
                    System.out.println("Moving up");
                    if(row-2 < 0){
                        System.out.println("Cant go up");
                        continue;
                    }
                    if(grid.getSquares()[row-2][col].getBlocked()){
                        grid.getSquares()[row-2][col].setBlocked(false);
                        grid.getSquares()[row-1][col].setBlocked(false);
                        recursiveMove(row-2,col);
                    }
                    break;
                case 2://right
                    System.out.println("Moving right");
                    if(col+2 > grid.getCols()){
                        System.out.println("Cant go right");
                        continue;
                    }
                    
                    if(grid.getSquares()[row][col+2].getBlocked()){
                        grid.getSquares()[row][col+2].setBlocked(false);
                        grid.getSquares()[row][col+1].setBlocked(false);
                        recursiveMove(row,col+2);
                    }
                    break;
                case 3://down
                    System.out.println("Moving down");
                    if(row+2 > grid.getRows()){
                        System.out.println("Cant go down");
                        continue;
                    }
                    if(grid.getSquares()[row+2][col].getBlocked()){
                        grid.getSquares()[row+2][col].setBlocked(false);
                        grid.getSquares()[row+1][col].setBlocked(false);
                        recursiveMove(row+2,col);
                    }
                    break;
                case 4://left
                    System.out.println("Moving left");
                    if(col-2 < 0){
                        System.out.println("Cant go left");
                        continue;
                    }
                    if(grid.getSquares()[row][col-2].getBlocked()){
                        grid.getSquares()[row][col-2].setBlocked(false);
                        grid.getSquares()[row][col-1].setBlocked(false);
                        recursiveMove(row,col-2);
                    }
                    break;
            }
            try{
                Thread.sleep(100);
            }catch(Exception ex){}
        }
    }
    private Integer[] genRandomDirs(){
        List<Integer> rands = new ArrayList<>();
        for(int x = 0; x < 4; x++){
            rands.add(x+1);
        }
        Collections.shuffle(rands);
        return rands.toArray(new Integer[20]);
    }
}