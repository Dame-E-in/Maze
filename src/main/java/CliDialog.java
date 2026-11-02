import java.util.Scanner;

public class CliDialog extends Config{
    public CliDialog(int size, String search_alg){
        this.dpi = null;
        this.size = size;
        this.search_alg = search_alg;

        CliConfig();
    }
    String curr;

    private void trySize(Scanner scanner){
        try{
            System.out.print("Enter maze size: ");
            curr = scanner.nextLine();
            if (!curr.isBlank()){
                size = Integer.parseInt(curr);
            }else if(curr.isBlank()){
                // Uhh it throws an error if this isn't here lmao
            }else if (Integer.parseInt(curr) <= 0){
                System.out.println("Input an integer greater than 0.");
            }
        }catch(NumberFormatException e){
            System.out.println("Not an integer.");
        }
    }

    private void CliConfig(){
        Scanner scanner = new Scanner(System.in);

        trySize(scanner);
        while (size <= 0){
            trySize(scanner);
        }


    while (true){
        System.out.print("Which search algorithm to use (bfs/dfs)? Leave blank for bfs. ");
        curr = scanner.nextLine();
        if (curr.equals("dfs")){
            search_alg = "dfs";
            break;
        }else if (!curr.equals("bfs") && !curr.isBlank()) {
            System.out.println("Make sure to type \"bfs\" or \"dfs\" (or leave blank).");
        }else{
            break;
        }
    }

        scanner.close();
    }
}
