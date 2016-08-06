package com.techcust.gameboard;

import com.techcust.gameboard.board.*;
import com.techcust.gameboard.piece.*;
import java.awt.Color;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

class BoardTest{
    
    public static void main(String[] args){
        
        try {
            CatMouseGrid grid = new CatMouseGrid();
            grid.setCheckered(false);
            BoardGraphical gui = new BoardGraphical(grid);
            
            grid.setRows(20);
            grid.setCols(20);
            grid.createGrid();
            
            
            ///*
            //form grid
            grid.getSquares()[0][4].setBlocked(true);
            grid.getSquares()[0][12].setBlocked(true);
            grid.getSquares()[0][13].setBlocked(true);
            grid.getSquares()[0][18].setBlocked(true);
            grid.getSquares()[0][19].setBlocked(true);
            
            grid.getSquares()[1][1].setBlocked(true);
            grid.getSquares()[1][2].setBlocked(true);
            grid.getSquares()[1][4].setBlocked(true);
            grid.getSquares()[1][6].setBlocked(true);
            grid.getSquares()[1][7].setBlocked(true);
            grid.getSquares()[1][10].setBlocked(true);
            grid.getSquares()[1][15].setBlocked(true);
            grid.getSquares()[1][16].setBlocked(true);
            grid.getSquares()[1][19].setBlocked(true);
            
            grid.getSquares()[2][1].setBlocked(true);
            grid.getSquares()[2][8].setBlocked(true);
            grid.getSquares()[2][13].setBlocked(true);
            grid.getSquares()[2][17].setBlocked(true);
            
            grid.getSquares()[3][3].setBlocked(true);
            grid.getSquares()[3][5].setBlocked(true);
            grid.getSquares()[3][6].setBlocked(true);
            grid.getSquares()[3][11].setBlocked(true);
            grid.getSquares()[3][13].setBlocked(true);
            grid.getSquares()[3][14].setBlocked(true);
            grid.getSquares()[3][16].setBlocked(true);
            
            grid.getSquares()[4][1].setBlocked(true);
            grid.getSquares()[4][3].setBlocked(true);
            grid.getSquares()[4][8].setBlocked(true);
            grid.getSquares()[4][10].setBlocked(true);
            grid.getSquares()[4][18].setBlocked(true);
            
            grid.getSquares()[5][0].setBlocked(true);
            grid.getSquares()[5][1].setBlocked(true);
            grid.getSquares()[5][5].setBlocked(true);
            grid.getSquares()[5][8].setBlocked(true);
            grid.getSquares()[5][12].setBlocked(true);
            grid.getSquares()[5][14].setBlocked(true);
            grid.getSquares()[5][16].setBlocked(true);
            grid.getSquares()[5][17].setBlocked(true);
            
            grid.getSquares()[6][3].setBlocked(true);
            grid.getSquares()[6][7].setBlocked(true);
            grid.getSquares()[6][8].setBlocked(true);
            grid.getSquares()[6][9].setBlocked(true);
            grid.getSquares()[6][10].setBlocked(true);
            grid.getSquares()[6][12].setBlocked(true);
            grid.getSquares()[6][14].setBlocked(true);
            grid.getSquares()[6][19].setBlocked(true);
            
            grid.getSquares()[7][1].setBlocked(true);
            grid.getSquares()[7][2].setBlocked(true);
            grid.getSquares()[7][3].setBlocked(true);
            grid.getSquares()[7][5].setBlocked(true);
            grid.getSquares()[7][7].setBlocked(true);
            grid.getSquares()[7][15].setBlocked(true);
            grid.getSquares()[7][17].setBlocked(true);
            grid.getSquares()[7][18].setBlocked(true);
            grid.getSquares()[7][19].setBlocked(true);
            
            grid.getSquares()[8][3].setBlocked(true);
            grid.getSquares()[8][5].setBlocked(true);
            grid.getSquares()[8][7].setBlocked(true);
            grid.getSquares()[8][9].setBlocked(true);
            grid.getSquares()[8][10].setBlocked(true);
            grid.getSquares()[8][11].setBlocked(true);
            grid.getSquares()[8][13].setBlocked(true);
            grid.getSquares()[8][15].setBlocked(true);
            
            grid.getSquares()[9][0].setBlocked(true);
            grid.getSquares()[9][5].setBlocked(true);
            grid.getSquares()[9][16].setBlocked(true);
            grid.getSquares()[9][18].setBlocked(true);
            
            grid.getSquares()[10][2].setBlocked(true);
            grid.getSquares()[10][3].setBlocked(true);
            grid.getSquares()[10][4].setBlocked(true);
            grid.getSquares()[10][7].setBlocked(true);
            grid.getSquares()[10][9].setBlocked(true);
            grid.getSquares()[10][10].setBlocked(true);
            grid.getSquares()[10][11].setBlocked(true);
            grid.getSquares()[10][13].setBlocked(true);
            grid.getSquares()[10][14].setBlocked(true);
            grid.getSquares()[10][16].setBlocked(true);
            grid.getSquares()[10][18].setBlocked(true);
            
            grid.getSquares()[11][1].setBlocked(true);
            grid.getSquares()[11][16].setBlocked(true);
            grid.getSquares()[11][18].setBlocked(true);
            
            
            grid.getSquares()[12][1].setBlocked(true);
            grid.getSquares()[12][2].setBlocked(true);
            grid.getSquares()[12][3].setBlocked(true);
            grid.getSquares()[12][5].setBlocked(true);
            grid.getSquares()[12][7].setBlocked(true);
            grid.getSquares()[12][9].setBlocked(true);
            grid.getSquares()[12][10].setBlocked(true);
            grid.getSquares()[12][11].setBlocked(true);
            grid.getSquares()[12][13].setBlocked(true);
            grid.getSquares()[12][14].setBlocked(true);
            grid.getSquares()[12][19].setBlocked(true);
            
            grid.getSquares()[13][1].setBlocked(true);
            grid.getSquares()[13][7].setBlocked(true);
            grid.getSquares()[13][9].setBlocked(true);
            grid.getSquares()[13][14].setBlocked(true);
            grid.getSquares()[13][16].setBlocked(true);
            grid.getSquares()[13][17].setBlocked(true);
            grid.getSquares()[13][19].setBlocked(true);
            
            grid.getSquares()[14][1].setBlocked(true);
            grid.getSquares()[14][3].setBlocked(true);
            grid.getSquares()[14][4].setBlocked(true);
            grid.getSquares()[14][5].setBlocked(true);
            grid.getSquares()[14][11].setBlocked(true);
            grid.getSquares()[14][12].setBlocked(true);
            grid.getSquares()[14][14].setBlocked(true);
            
            grid.getSquares()[15][3].setBlocked(true);
            grid.getSquares()[15][7].setBlocked(true);
            grid.getSquares()[15][9].setBlocked(true);
            grid.getSquares()[15][15].setBlocked(true);
            grid.getSquares()[15][18].setBlocked(true);
            
            grid.getSquares()[16][1].setBlocked(true);
            grid.getSquares()[16][5].setBlocked(true);
            grid.getSquares()[16][8].setBlocked(true);
            grid.getSquares()[16][9].setBlocked(true);
            grid.getSquares()[16][11].setBlocked(true);
            grid.getSquares()[16][13].setBlocked(true);
            grid.getSquares()[16][15].setBlocked(true);
            
            grid.getSquares()[17][3].setBlocked(true);
            grid.getSquares()[17][5].setBlocked(true);
            grid.getSquares()[17][7].setBlocked(true);
            grid.getSquares()[17][11].setBlocked(true);
            grid.getSquares()[17][17].setBlocked(true);
            grid.getSquares()[17][18].setBlocked(true);
            
            grid.getSquares()[18][0].setBlocked(true);
            grid.getSquares()[18][1].setBlocked(true);
            grid.getSquares()[18][3].setBlocked(true);
            grid.getSquares()[18][5].setBlocked(true);
            grid.getSquares()[18][7].setBlocked(true);
            grid.getSquares()[18][9].setBlocked(true);
            grid.getSquares()[18][11].setBlocked(true);
            grid.getSquares()[18][12].setBlocked(true);
            grid.getSquares()[18][14].setBlocked(true);
            grid.getSquares()[18][16].setBlocked(true);
            grid.getSquares()[18][18].setBlocked(true);
            
            grid.getSquares()[19][9].setBlocked(true);
            grid.getSquares()[19][14].setBlocked(true);
            //*/
                    
            //GenerateMaze gm = new GenerateMaze(grid);
            
            
            grid.generateCheese();
            //grid.addRandomCheese();
           
            for(int miceCount = 0; miceCount < 2; miceCount++){
                boolean created = false;
                int randr = new Random().nextInt(grid.getRows()-1);
                int randc = new Random().nextInt(grid.getCols()-1);
                int r = new Random().nextInt(255);
                int g = new Random().nextInt(255);
                int b = new Random().nextInt(255);
                Color randColor = new Color(r,g,b);
                while(!created){
                    if(!grid.getSquares()[randr][randc].getBlocked()){
                        created = true;
                        grid.addMouse("Mouse"+miceCount, randr, randc, 0, 0);
                        Mouse mouse = grid.getMouse("Mouse"+miceCount);
                        mouse.setColor(randColor);
                        int randSpeed = new Random().nextInt(mouse.maxSpeed - mouse.minSpeed)+mouse.minSpeed;
                        mouse.setSpeed(5);
                        
                        mouse.searchForCheese2();
                        //mouse.searchForCheese();
                        //mouse.walkMazeByNeighbor(mouse.getSpeed());
                        
                    }else{
                        created = false;
                        randr = new Random().nextInt(grid.getRows()-1);
                        randc = new Random().nextInt(grid.getCols()-1);
                    }
                }
            }
            
            for(int catCount = 0; catCount < 0; catCount++){
                boolean created = false;
                int randr = new Random().nextInt(grid.getRows()-1);
                int randc = new Random().nextInt(grid.getCols()-1);
                int r = new Random().nextInt(255);
                int g = new Random().nextInt(255);
                int b = new Random().nextInt(255);
                Color randColor = new Color(r,g,b);
                while(!created){
                    if(!grid.getSquares()[randr][randc].getBlocked()){
                        created = true;
                        grid.addGridCat("Cat", randr, randc, 0, 0);
                        Cat cat = grid.getGridCat();
                        cat.setColor(randColor);
                        int randSpeed = new Random().nextInt(cat.maxSpeed - cat.minSpeed)+cat.minSpeed;
                        cat.setSpeed(3);
                        
                        //cat.searchForMouse();
                        //mouse.walkMazeByNeighbor(mouse.getSpeed());
                        
                    }else{
                        created = false;
                        randr = new Random().nextInt(grid.getRows()-1);
                        randc = new Random().nextInt(grid.getCols()-1);
                    }
                }
            }
            
        } catch (Exception ex) {
            Logger.getLogger(BoardTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}