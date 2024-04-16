import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;

public class Commit extends FolderMonitor {

// Commit command
    /**
     * Updates snapshot time. Moves all files from hardcoded "LOCAL" folder into our "CLOUD" folder
     */
     public static void commit(String source, String destination) throws IOException {
         // Update Snapshot Time and store it inside a file. (Commit file could store commit messages together with the date in later versions)
         // Tells you when the last commit was made
         Date snapShotTime = new Date();
         System.out.println("Snapshot created at: " + snapShotTime);
         try{
             Path path = Paths.get("CommitFile.txt");
             Files.writeString(path, String.valueOf(snapShotTime));

         }catch (IOException e){
             throw new RuntimeException(e);
         }

         // Define locations for the LOCAL_FOLDER and CLOUD_FOLDER
         Path localMachine = Paths.get(source);
         Path cloud = Paths.get(destination);

         // Copy directory structure recursively
         Files.walk(localMachine).filter(path -> !path.equals(localMachine)) //Exclude the localMachine folder itself
                 .forEach(path -> {
                     Path target = cloud.resolve(path.getFileName());
                     try{
                         if (Files.isDirectory(path)){
                             Files.createDirectories(target); // Create directories if they don't exist
                         }else {
                             Files.copy(path, target, StandardCopyOption.REPLACE_EXISTING);
                         }
                     }catch (IOException e){
                         throw new RuntimeException(e);
                     }
                 });
         System.out.println("Commit successful!");
     }
}
