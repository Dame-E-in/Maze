public class MazeDemo {
    public static void main(String[] args) {
        int size = 25;
        String mazeSize;

        Maze maze1 = new Maze(size);

        System.out.println(maze1);

        if (size > 25){
            mazeSize = "big";
        }else{
            mazeSize = "small";
        }
        MazeGui gui = new MazeGui("Maze Demo", "high_dpi", mazeSize);
        gui.println(maze1.toString());
    }

}