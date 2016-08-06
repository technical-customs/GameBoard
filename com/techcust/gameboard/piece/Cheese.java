package com.techcust.gameboard.piece;

import com.techcust.gameboard.board.CatMouseGrid;
import java.awt.Color;
import java.awt.Graphics;

public class Cheese extends BoardPiece{

    public Cheese(CatMouseGrid board, String id, int row, int col, int width, int height) {
        super(board, id, row, col, width, height);
        super.setColor(Color.yellow);
    }
    
    @Override
    public void pieceDraw(Graphics g){
        g.setColor(this.getColor());
        g.fillRect(this.x, this.y, this.width, this.height);
    }
    
}