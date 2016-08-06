package com.techcust.gameboard.board;

public class Edge{
    GridRect a;
    GridRect b;
    int cost;
    
    public Edge(GridRect a, GridRect b, int cost){
        this.a = a;
        this.b = b;
        this.cost = cost;
    }
}