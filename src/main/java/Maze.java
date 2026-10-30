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
        "path", "R" //R for reserved
    ));

    // Remove now that HashMaps allowed
    //String[] blocks = {"#", ".", "S", "F", "T", "@"};

    private String[][] maze; 
    private int size;
    private Random random = new Random();
    private int[] start;
    private int[] finish;

    public Maze(int size){
        this.size = size;
        this.maze = new String[this.size][this.size];
        generate();
    }

    private void generate(){

        fillWithEmpty();
        buildOuterWall();
        createWallRandomly();
        createWallConnectedToOtherWalls();

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

        createSolution();
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

    private boolean checkAbove(int row, int column){
        return this.maze[row - 1][column].equals(BLOCKS.get("wall"));
    }

    private boolean checkBelow(int row, int column){
        return this.maze[row + 1][column].equals(BLOCKS.get("wall"));
    }

    private boolean checkLeft(int row, int column){
        return this.maze[row][column - 1].equals(BLOCKS.get("wall"));
    }

    private boolean checkRight(int row, int column){
        return this.maze[row][column + 1].equals(BLOCKS.get("wall"));
    }

    private boolean chanceTen(){
        return this.random.nextInt(10) > 8;
    }
    private boolean chanceFifty(){
        return this.random.nextInt(10) > 4;
    }

    private void createSolution(){
        int currRow = start[0];
        int currCol = start[1];
        
        /* 
        * A 10% chance to move in the right direction seems to work great for bulldozing a bath
        * Can create super direct routes sometimes, can freak out sometimes
        * Should definitely make it so that it would never loop infinitely but the chances are so mathematically low that I don't think it matters.
        */

        while (currRow != finish[0] || currCol != finish[1]){
            if (chanceTen()){
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
                if ((currRow < this.size - 2) && (currRow > 2)){
                currRow = currRow + (random.nextInt(3) - 1);
                }
                if ((currCol < this.size - 2) && (currCol > 2)){
                currCol = currCol + (random.nextInt(3) - 1);
                }
            }

            boolean isNotStart = (currRow != this.start[0] || currCol != this.start[1]);
            boolean isNotFinish = (currRow != this.finish[0] || currCol != this.finish[1]);

            if (isNotStart && isNotFinish) {
                this.maze[currRow][currCol] = BLOCKS.get("hall");
            }
        }
    }

    @Override
    public String toString(){
        String str = "";

        int size = this.maze.length;

        for (int row = 0; row < size; row++){
                for (int column = 0; column < size; column++){
                    str += this.maze[row][column];
            }
        str += "\n";
        }
    return str;
    }
}