public class MazeDemo {
    public static void main(String[] args) {
        boolean debugMode = false;
        boolean nogui = false;

        for (String arg : args) {
            if (arg.equals("--debug")) {
                debugMode = true;
            }
            if (arg.equals("--nogui")) {
                nogui = true;
            }
        }

        if (debugMode) System.out.println("Printing debug to console.");

        String mazeSize;
        Object[] options = MazeGui.MazeDialog();

        String dpi = (String) options[0];
        int size = (int) options[1];

        if (debugMode) System.out.println("DPI choice: " + dpi + "\nMaze Size: " + size);

        Maze maze1 = new Maze(size, debugMode);
        if (debugMode) System.out.println(maze1);
        
        MazeGui gui = new MazeGui("Maze Demo", dpi, size);
        Search.bfs(maze1, debugMode);
        gui.println(maze1.toString());
    }

}