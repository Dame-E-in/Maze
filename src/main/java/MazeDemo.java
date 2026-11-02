public class MazeDemo {
    public static void main(String[] args) {
        String DEFAULT_DPI = "high_dpi";
        int DEFAULT_SIZE = 25;
        String DEFAULT_SEARCH = "bfs";
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

        Config config;
        if (!nogui) config = new MazeDialog(DEFAULT_DPI, DEFAULT_SIZE, DEFAULT_SEARCH);
        else config = new CliDialog(DEFAULT_SIZE, DEFAULT_SEARCH);

        String dpi = config.getDpi();
        int size = config.getSize();

        if (debugMode) System.out.println("DPI choice: " + dpi + "\nMaze Size: " + size + "\nAlg: " + config.getSearch());

        Maze maze1 = new Maze(size, debugMode);
        if (debugMode) System.out.println(maze1);
        
        MazeGui gui = null;

        if (!nogui){
            gui = new MazeGui("Maze Demo", dpi, size);
        }

        if (config.getSearch().equals("bfs")) Search.bfs(maze1, debugMode);
        else Search.dfs(maze1, debugMode);

        if (!nogui) gui.println(maze1.toString());
        else System.out.println(maze1.toString());
    }

}