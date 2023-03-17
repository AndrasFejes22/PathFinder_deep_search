package pathFinder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        // maze:
        int [][] maze =

                {
                        {1,1,1,1,1,1,1,1,1,1,1,1,1},
                        {1,0,1,0,1,0,1,0,0,0,0,0,1},
                        {1,0,1,0,0,0,1,0,1,1,1,0,1},
                        {1,0,0,0,1,1,1,0,0,0,0,0,1},
                        {1,0,1,0,0,0,0,0,1,1,1,0,1},
                        {1,0,1,0,1,1,1,0,1,0,0,0,1},
                        {1,0,1,0,1,0,0,0,1,1,1,0,1},
                        {1,0,1,0,1,1,1,0,1,0,1,0,1},
                        {1,0,0,0,0,0,0,0,0,0,1,0,1},
                        {1,1,1,1,1,1,1,1,1,1,1,1,1}

                };




        findPath(maze, new Node(1,8), new Node(11, 8));

    }

    public static void drawMaze(int [][] maze, List<Node> path){
        System.out.println("--------SOLUTION---------");
        for(int i = 0; i < path.size(); i++){

            maze[path.get(i).y()][path.get(i).x()] = 2;
        }

        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {

                if(maze[i][j] == 2){
                    System.out.print("\u001b[1;32m" + maze[i][j]+ "\u001b[0m" + " "); //green
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
            System.out.print("("+path.get(i).x + " " + path.get(i).y+ ")" + " ") ;

        }
        System.out.println();
        System.out.println("path size: "+path.size());

    }


    public static void findPath(int maze[][], Node startNode, Node goalNode){
        int x = startNode.x;
        int y = startNode.y;
        int[][]copiedMaze = copy(maze);
        List<Node> path = new ArrayList<>();
        if(searchPath(maze,x, y, path, goalNode)){ // start coordinate: (x,y)
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

    public static boolean searchPath(int maze[][], int x, int y, List<Node> path, Node goalNode){

        maze[goalNode.y][goalNode.x] = 9;

        if(maze[y][x] == 9){
            Node node = new Node(x, y);
            path.add(node);
            return true;
        }

        if(maze[y][x] == 0) {
            maze[y][x] = 2;

            if (searchPath(maze, x-1, y, path, goalNode)) {
                Node node = new Node(x, y);
                path.add(node);
                return true;
            }
            if (searchPath(maze, x + 1, y, path, goalNode)) {
                Node node = new Node(x, y);
                path.add(node);
                return true;
            }
            if (searchPath(maze, x, y - 1, path, goalNode)) {
                Node node = new Node(x, y);
                path.add(node);
                return true;
            }
            if (searchPath(maze, x, y + 1, path, goalNode)) {
                Node node = new Node(x, y);
                path.add(node);
                return true;
            }

        }

        return false;
    }

    // Node class

    private static record Node (int x, int y) {}

    /*
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
    */
}
