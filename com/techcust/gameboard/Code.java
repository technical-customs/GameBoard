/*

private void addPiece(Piece piece, int row, int col){
        int xRow = row - 1;
        int yCol = col - 1;
        if(xRow >= ROWS || yCol >= COLS || xRow <= 0 || yCol <= 0){
            return;
        }
        
        if(boardArray[xRow][yCol] != null){
            //object in position
            return;
        }
        boardArray[xRow-1][yCol-1] = piece;
        pieces.add(piece);
        
}

private void removePiece(int row, int col){
        int xRow = row;
        int yCol = col;
        if(xRow >= ROWS || yCol >= COLS || xRow <= 0 || yCol <= 0){
            return;
        }
        
        boardArray[row][col] = null;
}

private void movePiece(int row1, int col1, int row2, int col2){
        if(row1 >= ROWS || col1 >= COLS || row2 >= ROWS || col2 >= COLS){
            return;
        }
        
        if(boardArray[row1][col1] != null){
            
            if(boardArray[row2][col2] != null){
                //piece in spot
                return;
            }
            boardArray[row2][col2] = boardArray[row1][col1];
            boardArray[row1][col1] = null;
            
        }
}

public void displayGrid(){
        for(int rowX = 0; rowX < ROWS; rowX++){
            
            for(int colY = 0; colY < COLS; colY++){
                System.out.print("[");
                
                if(boardArray[rowX][colY] != null){
                    System.out.print("X");
                }
                
                System.out.print("] ");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();
}
    
private void createBoard(){
        board = new JFrame();
        board.setTitle("Game Board");
        board.setSize(650,600);
        board.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent we){
                System.exit(0);
            }
        });
        
        
        board.setVisible(true);
    }

*/