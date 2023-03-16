package pathFinder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        // maze:
        int [][] maze =

                {
                        {1,1,1,1,1,1,1,1,1,1,1,1,1}, // start coordinate: (1,1)
                        {1,0,1,0,1,0,1,0,0,0,0,0,1},
                        {1,0,1,0,0,0,1,0,1,1,1,0,1},
                        {1,0,0,0,1,1,1,0,0,1,0,0,1},
                        {1,0,1,0,0,0,0,0,1,1,1,0,1},
                        {1,0,1,0,1,1,1,0,1,0,0,0,1},
                        {1,0,1,0,1,0,0,0,1,1,1,0,1},
                        {1,0,1,1,1,1,1,1,1,0,1,0,1},
                        {1,0,1,9,0,0,0,0,0,0,0,0,1}, // 9: end coordinate
                        {1,1,1,1,1,1,1,1,1,1,1,1,1}

                };


        System.out.println();

        findPath(maze);

    }

    public static void drawMaze(int [][] maze, List<Node> path){
        System.out.println("--------SOLUTION---------");
        for(int i = 0; i < path.size(); i++){

            maze[path.get(i).getY()][path.get(i).getX()] = 2;
        }

        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {

                if(maze[i][j] == 2){
                    System.out.print("\u001b[1;32m" + maze[i][j]+ "\u001b[0m" + " "); //greeen
                } else {
                    System.out.print(maze[i][j] + " ");
                }

            }
            System.out.println();
        }
        System.out.println("path's coordinates: ");
        Collections.reverse(path);
        // list of solve coordinates
        for(int i = 0; i < path.size(); i++){
            System.out.print("("+path.get(i).getX() + " " + path.get(i).getY()+ ")" + " ") ;

        }
        System.out.println();
        System.out.println("path size: "+path.size());

    }


    public static void findPath(int maze[][]){
        int[][]copiedMaze = copy(maze);
        List<Node> path = new ArrayList<>();
        if(searchPath(maze,1, 1, path)){ // start coordinate: (1,1)
            System.out.println("There is a solution:\n");
            drawMaze(copiedMaze,path);
        } else {
            System.out.println("No solution.");
        }
    }

    //2D array copy
    static int[][] copy(int[][] array){
        int[][] copy = new int[array.length][array[0].length];
        for (int row = 0; row < array.length; row++) {
            for (int column = 0; column < array[0].length; column++) {
                copy[row][column] = array[row][column];
            }
        }
        return copy;

    }


    //---------------------------Searching algorithm------------------------------

    public static boolean searchPath(int maze[][], int x, int y, List<Node> path){

        int a = maze.length;

        if(maze[y][x] == 9){
            Node node = new Node(x, y);
            path.add(node);
            return true;
        }

        if(maze[y][x] == 0) {
            maze[y][x] = 2;

            if (searchPath(maze, x-1, y, path)) {
                Node node = new Node(x, y);
                path.add(node);
                return true;
            }
            if (searchPath(maze, x + 1, y, path)) {
                Node node = new Node(x, y);
                path.add(node);
                return true;
            }
            if (searchPath(maze, x, y - 1, path)) {
                Node node = new Node(x, y);
                path.add(node);
                return true;
            }
            if (searchPath(maze, x, y + 1, path)) {
                Node node = new Node(x, y);
                path.add(node);
                return true;
            }

        }
        if(path.size() == 0){

        }
        return false;

    }

    // Node class

    private static class Node{
        private int x;
        private int y;

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }
}
