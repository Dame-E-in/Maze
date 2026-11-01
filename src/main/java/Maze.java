import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Maze{

    // Used now that I asked :)
    // Better to use Character for the value but I don't want to do a ton of casting
    Map<String,String> BLOCKS = new HashMap<>(Map.of(
        "wall", "#",
        "hall", ".",
        "start", "S",
        "finish", "F",
        "test", "T",
        "scan", "@",
        "path", "+" 
    ));

    // Remove now that HashMaps allowed
    //String[] blocks = {"#", ".", "S", "F", "T", "@"};

    private String[][] maze; 
    private int size;
    private Random random = new Random();
    private int[] start;
    private int[] finish;
    boolean debugMode;

    public Maze(int size, boolean debugMode){
        this.size = size;
        this.maze = new String[this.size][this.size];
        this.debugMode = debugMode;
        generate();
    }

    private void generate(){

        if (debugMode) System.out.println(toString());
        fillWithEmpty();
        if (debugMode) System.out.println(toString());
        buildOuterWall();
        if (debugMode) System.out.println(toString());
        createWallRandomly();
        if (debugMode) System.out.println(toString());
        createWallConnectedToOtherWalls();
        if (debugMode) System.out.println(toString());

        this.start = new int[] {this.random.nextInt(this.size - 2) + 1, this.random.nextInt(this.size - 2) + 1};
        this.maze[this.start[0]][this.start[1]] = BLOCKS.get("start");
        this.finish = new int [] {this.random.nextInt(this.size - 2) + 1, this.random.nextInt(this.size - 2) + 1};

        // This while loop was added because I discovered an edge case where Finish can just replace Start lmao
        // Extremely annoying comparison because Java evil
        while (this.finish[0] == this.start[0] && this.finish[1] == this.start[1]){
            this.finish[0] = this.random.nextInt(this.size - 2) + 1;
            this.finish[1] = this.random.nextInt(this.size - 2) + 1;
        }
        
        this.maze[this.finish[0]][this.finish[1]] = BLOCKS.get("finish");

        if (debugMode) System.out.println(toString());
        createSolution();
        if (debugMode) System.out.println(toString());;
    }

    private void fillWithEmpty(){
        for (int row = 0; row < this.size; row++){
                for (int column = 0; column < this.size; column++){
                    this.maze[row][column] = BLOCKS.get("hall");
                }
            }
    }

    private void buildOuterWall(){
        for (int row = 0; row < this.size; row++){
            if ((row == 0) || (row == this.size -1)){
                for (int column = 0; column < this.size; column++){
                    this.maze[row][column] = BLOCKS.get("wall");
                }
            }else{
                    this.maze[row][0] = BLOCKS.get("wall");
                    this.maze[row][this.size - 1] = BLOCKS.get("wall");

            }
        }
    }

    private void createWallRandomly(){
        for (int row = 1; row < this.size - 1; row++){
            for (int column = 1; column < this.size - 1; column++){
                if (chanceTen()){
                    this.maze[row][column] = BLOCKS.get("wall");
                }
            }
        }
    }

    private void createWallConnectedToOtherWalls(){
        for (int row = 1; row < size - 1; row++){
            for (int column = 1; column < size - 1; column++){
                    if (chanceFifty()){
                        if (this.checkAbove(row, column) || this.checkBelow(row, column) || this.checkLeft(row, column) || this.checkRight(row, column)){
                        this.maze[row][column] = BLOCKS.get("wall");
                        }
                    }
                }
        }
    }

    public boolean checkAbove(int row, int column){
        return this.maze[row - 1][column].equals(BLOCKS.get("wall"));
    }

    public boolean checkAbove(int[] curr){
        return checkAbove(curr[0], curr[1]);
    }

    public boolean checkBelow(int row, int column){
        return this.maze[row + 1][column].equals(BLOCKS.get("wall"));
    }

    public boolean checkLeft(int row, int column){
        return this.maze[row][column - 1].equals(BLOCKS.get("wall"));
    }

    public boolean checkRight(int row, int column){
        return this.maze[row][column + 1].equals(BLOCKS.get("wall"));
    }

    public boolean isFinish(int row, int column){
        return this.maze[row][column].equals(BLOCKS.get("finish"));
    }

    public boolean isFinish(int[] curr){
        return isFinish(curr[0], curr[1]);
    }

    public boolean scan(int row, int column){

        if(this.maze[row][column].equals(BLOCKS.get("hall"))){
            this.maze[row][column] = BLOCKS.get("scan");
            return true;
        }else if(isFinish(row, column)){
            return true;
        }
        return false;
    }

    public boolean scan(int[] curr){
        return scan(curr[0], curr[1]);
    }

    public boolean pave(int row, int column){
        if(this.maze[row][column] == BLOCKS.get("scan")){
            this.maze[row][column] = BLOCKS.get("path");
            return true;
        }
        return false;
    }

    public boolean pave(int[] curr){
        return pave(curr[0], curr[1]);
    }

    public int[] getStart(){
        return this.start;
    }

    private boolean chanceTen(){
        return this.random.nextInt(10) > 8;
    }
    private boolean chanceFifty(){
        return this.random.nextInt(10) > 4;
    }
    // private boolean chanceOne(){
    //     return this.random.nextInt(100) > 98;
    // }

    private void createSolution(){
        int currRow = start[0];
        int currCol = start[1];
        
        /* 
        * A 10% chance to move in the right direction seems to work great for bulldozing a bath
        * Can create super direct routes sometimes, can freak out sometimes
        * Should definitely make it so that it would never loop infinitely but the chances are so mathematically low that I don't think it matters.
        */

        int counter = 0;

        while (currRow != finish[0] || currCol != finish[1]){
            if (chanceTen() || counter > 1000000){
                if (currRow < finish[0]){
                    currRow++;
                }else if (currRow > finish[0]){
                    currRow--;
                }else if (currCol < finish[1]){
                    currCol++;
                }else if (currCol > finish[1]){
                    currCol--;
                }
            }else{
                boolean inBounds = (currRow < this.size - 2) && (currRow > 2);
                boolean moveRow = chanceFifty();

                if (inBounds && moveRow){
                currRow = currRow + (random.nextInt(3) - 1);
                }else if (inBounds && !moveRow){
                currCol = currCol + (random.nextInt(3) - 1);
                }
            }

            boolean isNotStart = (currRow != this.start[0] || currCol != this.start[1]);
            boolean isNotFinish = (currRow != this.finish[0] || currCol != this.finish[1]);

            if (isNotStart && isNotFinish) {
                this.maze[currRow][currCol] = BLOCKS.get("hall");
            }
            counter++;

            if (debugMode) System.out.println("Create solution counter: " + counter);
        }
    }

    @Override
    public String toString(){
        // String str = "";

        int size = this.maze.length;

        // for (int row = 0; row < size; row++){
        //         for (int column = 0; column < size; column++){
        //             str += this.maze[row][column];
        //     }
        // str += "\n";

        // return str;

        // Didn't work for huge mazes

        StringBuilder str = new StringBuilder();
        for (int row = 0; row < size; row++){
            for (int column = 0; column < size; column++){
                str.append(this.maze[row][column]);
            }
            str.append("\n");
        }
        return str.toString();
    }
}