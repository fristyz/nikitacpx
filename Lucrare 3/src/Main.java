import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {

        // Debugging
        //info.fileInfo(FOLDER_PATH,"Test.txt");
        //infoText.fileInfo(FOLDER_PATH, "Test.txt");
        //infoImage.fileInfo(FOLDER_PATH, "Image.jpeg");
        //infoProgram.fileInfo(FOLDER_PATH, "Info.java");

        //TODO: Add error messages instead of just crashing program etc.
        //TODO: Add proper documentation to all functions
        //TODO: Improve the scheduler and console availability function, using Swing

        Timer timer = new Timer();

        final String CLOUD_PATH = "Z:\\catalin\\OOP\\Lucrari OOP\\Lucrari_OOP\\Lucrare 3\\Cloud"; // Folder used for storing files after commit (Emulates your GitHub repository)
        final String FOLDER_PATH = "Z:\\catalin\\OOP\\Lucrari OOP\\Lucrari_OOP\\Lucrare 3\\LocalMachine"; // Local folder used for keeping files you can edit (Emulates your local machine)

        // Initializing
        Info info = new Info();
        TextFiles infoText = new TextFiles();
        ImageFiles infoImage = new ImageFiles();
        ProgramFiles infoProgram = new ProgramFiles();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                        FolderMonitor.compareFolders(FOLDER_PATH,CLOUD_PATH, true);

                    //System.out.println("Please choose one of the above options: ");
                } catch (IOException | NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                }
            }
        },0, 5000);


        // Interactive command line
        Scanner scanner = new Scanner(System.in);
        boolean shouldFinish = false;

        while (!shouldFinish){
            System.out.println("""
                    GIT CLI\s
                    \t1. Commit\s
                    \t2. Info <fileName.extension>\s
                    \t3. Status
                    \t4. Quit
                    Please choose one of the above options:\s""");
            int choice = scanner.nextInt();
            switch (choice){
                case 1:
                    Commit.commit(FOLDER_PATH, CLOUD_PATH);
                    break;
                case 2:
                    System.out.println("Please enter a file name: ");
                    String fileName = scanner.next();
                    int index = fileName.lastIndexOf('.');
                    String extension = (index > 0) ? fileName.substring(index + 1) : "NONE";
                    switch (extension) {
                        case "txt" -> infoText.fileInfo(FOLDER_PATH, fileName);
                        case "jpeg", "jpg", "png" -> infoImage.fileInfo(FOLDER_PATH, fileName);
                        case "java" -> {
                            infoProgram.fileInfo(FOLDER_PATH, fileName);
                            System.out.println("May not be 100% accurate due to certain limitations");
                        }
                        default -> info.fileInfo(FOLDER_PATH,fileName);
                    }
                    break;

                case 3:
                    FolderMonitor.compareFolders(FOLDER_PATH, CLOUD_PATH, false);
                    break;
                case 4:
                    shouldFinish = true;
                    break;
                default:
                    System.out.println("hello");
            }
        }
    }
}