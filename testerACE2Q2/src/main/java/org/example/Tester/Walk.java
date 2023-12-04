package org.example.Tester;//Name: Quah E Jia
//Student ID: 20414778

import java.util.PriorityQueue;
import java.util.Scanner;

public class Walk {
    private int k, x, y;
    private int totalSteps;
    private int[] destination = new int[3];
    //move to Right, move to Left, move to Up, move to Down, move leftDown, move leftUp, move rightUp, move rightDown
    private final int[][] direction = {{1,0}, {-1,0}, {0,1}, {0,-1}, {-1,-1}, {-1,1}, {1,-1}, {1,1}};
    private Cell[][] cell;
    private PriorityQueue<Cell> priorityQueue = new PriorityQueue<>();

    static class Cell implements Comparable<Cell>{
        private final int x, y;
        private final boolean blocked;
        private int step;
        private boolean visited = false;

        private Cell(int x, int y, boolean blocked){
            this.x=x;
            this.y=y;
            this.blocked=blocked;
        }
        @Override
        public int compareTo(Cell otherCell) {
            return Integer.compare(this.step, otherCell.step);
        }
    }

    private int Dijkstra(int currentX, int currentY){
        //initialise step and cell
        Cell currentCell = cell[currentX][currentY];

        while(true){
            if(currentCell.x==destination[0] && currentCell.y==destination[1]){
                return totalSteps;
            }
            //loop to move in all(8) directions
            for(int i=0; i<8; i++) {
                int nextX = currentCell.x + direction[i][0];
                int nextY = currentCell.y + direction[i][1];

                //if the next cell is at least (1,1) and at most size of input row(x) and column(y)
                if (nextX > 0 && nextY > 0 && nextX <= x && nextY <= y) {
                    Cell nextCell = cell[nextX][nextY];
                    if (!nextCell.blocked) {
                        addToPriorityQueue(nextCell);
                        if(k>0){
                            jump(i, k, nextCell);
                        }
                    }else{
                        if(k>0){
                            jump(i, k-1, nextCell);
                        }
                    }
                }
            }
            if(!priorityQueue.isEmpty()){
                Cell next = priorityQueue.poll();
                currentCell = next;
                totalSteps = next.step;
            }else{
                break;
            }
        }
        return -1;
    }

    public void addToPriorityQueue(Cell nextCell){
        int step = totalSteps + 1;
        if (!nextCell.visited) {
            nextCell.visited = true;
            nextCell.step = step;
            priorityQueue.add(nextCell);
        } else {
            if (step < nextCell.step) {
                nextCell.step = step;
                priorityQueue.add(nextCell);
            }
        }
        //update cell status
        cell[nextCell.x][nextCell.y] = nextCell;
    }

    private void jump(int i, int jumps, Cell currentCell) {
        while (jumps >= 0) {
            int nextX = currentCell.x + direction[i][0];
            int nextY = currentCell.y + direction[i][1];
            if (nextX > 0 && nextY > 0 && nextX <= x && nextY <= y) {
                Cell nextCell = cell[nextX][nextY];
                if (!nextCell.blocked) {
                    addToPriorityQueue(nextCell);
                } else {
                    // Decrease jumps when encountering a blocked cell
                    jumps--;
                }
                // Update current cell for the next iteration
                currentCell = nextCell;
            } else {
                // Break the loop if the next position is out of bounds
                break;
            }
        }
    }

    public static void main(String args[]){
        String regex = "[(),\\s]+";

        Walk walk = new Walk();
        Scanner scan = new Scanner(System.in);
        walk.y = scan.nextInt(); // n = column(Y)
        walk.x = scan.nextInt(); // m = row(X)

        scan.nextLine(); // Get rid of useless line
        String[] input = scan.nextLine().split(regex); // get input of the start and destination cell

        int[] start = new int[3];
        start[0] = Integer.parseInt(input[2]);
        start[1] = Integer.parseInt(input[1]);
        walk.destination[0] = Integer.parseInt(input[4]);
        walk.destination[1] = Integer.parseInt(input[3]);

        walk.k = scan.nextInt();
        walk.cell = new Cell[walk.x+1][walk.y+1];
        for(int row=1; row<=walk.x; row++){
            for(int column=1; column<=walk.y; column++){
                int blocked = scan.nextInt();
                if(blocked==0){
                    walk.cell[row][column] = new Cell(row, column, false);
                }else if(blocked==1){
                    walk.cell[row][column] = new Cell(row, column, true);
                }
            }
        }
        walk.totalSteps = walk.Dijkstra(start[0],start[1]);
        System.out.print(walk.totalSteps);
    }
}
