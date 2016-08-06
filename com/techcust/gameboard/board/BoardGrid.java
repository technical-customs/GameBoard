package com.techcust.gameboard.board;

import com.techcust.gameboard.piece.AiPiece;
import com.techcust.gameboard.piece.BoardPiece;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JPanel;

public class BoardGrid extends JPanel{
    private GridRect[][] squares = null; //Nodes List
    ArrayList<BoardPiece> boardPieces = new ArrayList<>(); //piece list
    ArrayList<AiPiece> aiPieces = new ArrayList<>(); //piece list
    boolean checkered = false;
    private int gridWidth = 0, gridHeight = 0;
    private int rows = 0, cols = 0;
    
    public BoardGrid(){
        super();
        this.setPreferredSize(new Dimension(BoardGraphical.BOARDWIDTH, BoardGraphical.BOARDHEIGHT));
        this.setDoubleBuffered(true); 
    }
    public BoardGrid(int rows, int cols){
        super();
        this.rows = rows;
        this.cols = cols;
        squares = new GridRect[rows][cols];
        createGrid();
        this.setPreferredSize(new Dimension(BoardGraphical.BOARDWIDTH, BoardGraphical.BOARDHEIGHT));
        this.setDoubleBuffered(true); 
    }
    
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        
        Graphics2D g2 = (Graphics2D) g;
        
        g.setColor(Color.black);
        g.fillRect(0,0,this.getWidth(),this.getHeight());
        
        
        boardDraws(g);
        repaint();
        g2.dispose();
    }
    
    public void setCheckered(boolean checkered){
        this.checkered = checkered;
    }
    public int getRows(){
        return rows;
    }
    public void setRows(int rows){
        this.rows = rows;
    }
    public int getCols(){
        return cols;
    }
    public void setCols(int cols){
        this.cols = cols;
    }
    public int getGridWidth(){
        return this.gridWidth;
    }
    public int getGridHeight(){
        return this.gridHeight;
    }
    public int getBoardWidth(){
        return BoardGraphical.BOARDWIDTH;
    }
    public int getBoardHeight(){
        return BoardGraphical.BOARDHEIGHT;
    }
    
    
    public Point centerPiece(int row, int col, int width, int height){
        GridRect rect = squares[row][col];
        
        int x = ((rect.x + rect.width)-(rect.width/2)) - (width/2);
        int y = ((rect.y + rect.height)-(rect.height/2)) - (height/2);
        
        return new Point(x,y);
    }
    
    public GridRect[][] getSquares(){
        return squares;
    }
    public ArrayList<BoardPiece> getPiecesList(){
        return boardPieces;
    }
    public ArrayList<AiPiece> getAiPiecesList(){
        return aiPieces;
    }
    
    public void createGrid(){
        try{
            
            if(rows == 0 || cols == 0){
                return;
            }
            if(squares == null){
                squares = new GridRect[rows][cols];
            }
            gridWidth = BoardGraphical.BOARDWIDTH / cols;
            gridHeight = BoardGraphical.BOARDHEIGHT / rows;
            int colPos,rowPos;

            for(int c = 0; c < cols; c++){

                for(int r = 0; r < rows; r++){

                    if(c == 0){
                        colPos = 0;
                    }else{
                        colPos = c*gridWidth;
                    }

                    if(r == 0){
                        rowPos = 0;
                    }else{
                        rowPos = r*gridHeight;
                    }


                    boolean white = (r % 2 == 0) == (c % 2 == 0);
                    GridRect gr = null;
                    
                    if(checkered){
                        if(white){
                            gr = new GridRect(colPos, rowPos, gridWidth, gridHeight, Color.white);

                        }else{
                            gr = new GridRect(colPos, rowPos, gridWidth, gridHeight, Color.black);
                        }
                    }else{
                        gr = new GridRect(colPos, rowPos, gridWidth, gridHeight, Color.white);
                    }
                    

                    gr.setRow(r);
                    gr.setCol(c);
                    this.squares[r][c] = gr;
                }
            }
            generateNeighborList();
        }catch(Exception ex){
            
        }
        
    }
    
    public void generateNeighborList(){
        
        for(int r = 0; r < rows; r++){
            for(int c = 0; c < cols; c++){
                GridRect rect = squares[r][c];
                
                List<GridRect> neighbors = rect.getNeighbors();
                
                if(r > 0){ // has north
                    neighbors.add(squares[r-1][c]);
                    
                }
                if(r < rows-1){ // has south=
                    neighbors.add(squares[r+1][c]);
                }
                if(c > 0){ // has west
                    neighbors.add(squares[r][c-1]);
                }
                if(c < cols-1){ // has east
                    neighbors.add(squares[r][c+1]);
                }
            }
        }
    }
    public void displayNeighborList(){
        for(int r = 0; r < rows; r++){
            for(int c = 0; c < cols; c++){
                GridRect rect = squares[r][c];
                
                System.out.println("Rect " + (r+1) + " " + (c+1));
                
                int neighborCount = 0;
                for(GridRect neighbor: rect.getNeighbors()){
                    System.out.println("Neighbor " + ++neighborCount +". " + (neighbor.getRow()+1) + " " + (neighbor.getCol()+1));
                }
                System.out.println();
            }
        }
    }
    public void displayPieceList(){
        if(boardPieces.isEmpty()){
            return;
        }
        int x = 0;
        for(BoardPiece piece: boardPieces){
            System.out.println(++x + ".  " + piece.getId() + " - " + piece.getPosition());
        }
        System.out.println();
    }
    public void displayAiPieceList(){
        if(aiPieces.isEmpty()){
            return;
        }
        int x = 0;
        for(BoardPiece piece: aiPieces){
            System.out.println(++x + ".  " + piece.getId() + " - " + piece.getPosition());
        }
        System.out.println();
    }
    
    //Piece
    
    public void addPiece(String id, int row, int col, int width, int height) throws Exception{
        if(row >= rows || col >= cols || row < 0 || col < 0){
            return;
        }
        
        if(getPiece(row,col) != null || getPiece(id.toUpperCase()) != null){
            throw new Exception();
        }
        if(squares[row][col].getBlocked()){
            return;
        }
        
        //row = --row;
        //col = --col;
        int w = width;
        int h = height;
        if(w == 0){
            w = gridWidth/2;
        }
        if(h == 0){
            h = gridHeight/2;
        }
        
        BoardPiece piece = new BoardPiece(this,id.toUpperCase(),row,col,w,h);
        boardPieces.add(piece);
        repaint();
    }
    public void addAIPiece(String id, int row, int col, int width, int height) throws Exception{
        if(row >= rows || col >= cols || row < 0 || col < 0){
            return;
        }
        if(getPiece(row,col) != null || getPiece(id.toUpperCase()) != null){
            throw new Exception();
        }
        if(getPiece(row,col) != null || getPiece(id.toUpperCase()) != null){
            return;
        }
        
        if(width == 0){
            width = gridWidth/2;
        }
        if(height == 0){
            height = gridHeight/2;
        }
        
        AiPiece piece = new AiPiece(this,id.toUpperCase(),row,col,width,height);
        aiPieces.add(piece);
    }
    
    public BoardPiece getPiece(int row, int col) throws NullPointerException{
        Iterator<BoardPiece> iter = boardPieces.iterator();
        
        while(iter.hasNext()){
            BoardPiece p = iter.next();
            
            if(p.getRow() == row && p.getCol() == col){
                return p;
            }
        }
        return null;
    }
    public AiPiece getAiPiece(int row, int col) throws NullPointerException{
        Iterator<AiPiece> iter = aiPieces.iterator();
        
        while(iter.hasNext()){
            AiPiece p = iter.next();
            
            if(p.getRow() == row && p.getCol() == col){
                return p;
            }
        }
        return null;
    }
    public BoardPiece getPiece(String id) throws NullPointerException{
        Iterator<BoardPiece> iter = boardPieces.iterator();
        
        while(iter.hasNext()){
            BoardPiece p = iter.next();
            
            if(p.getId().equals(id.toUpperCase())){
                return p;
            }
        }
        return null;
    }
    public AiPiece getAiPiece(String id) throws NullPointerException{
        Iterator<AiPiece> iter = aiPieces.iterator();
        
        while(iter.hasNext()){
            AiPiece p = iter.next();
            
            if(p.getId().equals(id.toUpperCase())){
                return p;
            }
        }
        return null;
    }
    
    
    public boolean isPieceThere(int row, int col){
        return getPiece(row,col) != null;
    }
    public boolean isAiPieceThere(int row, int col){
        return getAiPiece(row,col) != null;
    }
    
    
    protected void movePieceX(BoardPiece piece, int dir){
        Iterator<BoardPiece> iter = boardPieces.iterator();
        
        while(iter.hasNext()){
            BoardPiece p = iter.next();
            
            if(piece.equals(p)){
                if(dir > 0){
                    if(piece.getCenterX() + gridWidth > BoardGraphical.BOARDWIDTH){
                        return;
                    }
                    
                    piece.moveX(gridWidth);
                }
                if(dir < 0){
                    if(piece.getCenterX() - gridWidth < 0){
                        return;
                    }
                    piece.moveX(-gridWidth);
                }
                //recordMove(piece);
            }
        }       
    }
    protected void movePieceY(BoardPiece piece, int dir){
        Iterator<BoardPiece> iter = boardPieces.iterator();
        
        while(iter.hasNext()){
            BoardPiece p = iter.next();
            
            if(piece.equals(p)){
                if(dir > 0){
                    if(p.getCenterY() + gridWidth > BoardGraphical.BOARDWIDTH){
                        return;
                    }
                    p.moveY(gridHeight);
                }
                if(dir < 0){
                    if(p.getCenterY() - gridHeight < 0){
                        return;
                    }
                    p.moveY(-gridHeight);
                }
                //recordMove(piece);
            }
        } 
    }
    
    protected void moveAiPieceX(AiPiece piece, int dir){
        Iterator<AiPiece> iter = aiPieces.iterator();
        
        while(iter.hasNext()){
            AiPiece p = iter.next();
            
            if(piece.equals(p)){
                if(dir > 0){
                    if(piece.getCenterX() + gridWidth > BoardGraphical.BOARDWIDTH){
                        return;
                    }
                    
                    piece.moveX(gridWidth);
                }
                if(dir < 0){
                    if(piece.getCenterX() - gridWidth < 0){
                        return;
                    }
                    piece.moveX(-gridWidth);
                }
                //recordMove(piece);
            }
        }       
    }
    protected void moveAiPieceY(AiPiece piece, int dir){
        Iterator<AiPiece> iter = aiPieces.iterator();
        
        while(iter.hasNext()){
            AiPiece p = iter.next();
            
            if(piece.equals(p)){
                if(dir > 0){
                    if(p.getCenterY() + gridWidth > BoardGraphical.BOARDWIDTH){
                        return;
                    }
                    p.moveY(gridHeight);
                }
                if(dir < 0){
                    if(p.getCenterY() - gridHeight < 0){
                        return;
                    }
                    p.moveY(-gridHeight);
                }
                //recordMove(piece);
            }
        } 
    }
 
    //Draws
    protected void boardDraws(Graphics g){
        try{
            for(int r = 0; r < rows; r++){
                for(int c = 0; c < cols; c++){
                    //if(squares[r][c].getBlocked()){
                        //g.setColor(Color.darkGray);
                    //}
                    if(squares[r][c] != null){
                        squares[r][c].rectDraw(g);
                    }

                }
            }
            if(!boardPieces.isEmpty()){
                piecesDraw(g);
            }
            if(!aiPieces.isEmpty()){
                aiPiecesDraw(g);
            }
        }catch(Exception ex){
            
        }
        
        
    }
    protected void piecesDraw(Graphics g){
        for(BoardPiece piece: boardPieces){
            if(piece == null){
                continue;
            }
            piece.pieceDraw(g);
        }
    }
    protected void aiPiecesDraw(Graphics g){
        for(AiPiece piece: aiPieces){
            if(piece == null){
                continue;
            }
            piece.pieceDraw(g);
        }
    }
    
    public void log(String string){
        System.out.println(string);
    }
}