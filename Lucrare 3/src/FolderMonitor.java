import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FolderMonitor {
    public static void compareFolders(String localMachine, String cloud, boolean isLoop) throws IOException, NoSuchAlgorithmException {
        File source = new File(localMachine);
        File destination = new File(cloud);

        // Check if the folders exist
        if (!source.exists() || !destination.exists()){
            System.out.println("One or both folders can't be found or don't exist");
            return;
        }

        // Loop through the localMachine files
        for (File localFile : Objects.requireNonNull(source.listFiles())){
            String localFileName = localFile.getName();
            String localChecksum = getMD5Checksum(localFile);
            File cloudFile = new File(cloud + File.separator + localFileName);

            // Check if file exists in Cloud folder
            if (!cloudFile.exists()){
                System.out.println(localFileName + " - new");
            }else {
                try {
                    String cloudChecksum = getMD5Checksum(cloudFile);
                    if (localChecksum.equals(cloudChecksum) && !isLoop){
                        System.out.println(localFileName + " - unchanged");
                    }else if (!localChecksum.equals(cloudChecksum)){
                        System.out.println(localFileName + " - changed");
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        // Loop through cloud files (to find new files)
        for (File cloudFile : Objects.requireNonNull(destination.listFiles())) {
            String cloudFileName = cloudFile.getName();
            File localFile = new File(localMachine + File.separator + cloudFileName);

            // Check if the file doesn't exist in localMachine
            if (!localFile.exists()){
                System.out.println(cloudFileName + " - deleted");
            }
        }
    }

    public static String getMD5Checksum(File file) throws IOException, NoSuchAlgorithmException { // Calculates MD5 checksum for a given file
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        byte[] bytes = Files.readAllBytes(file.toPath());
        messageDigest.update(bytes);
        byte[] digest = messageDigest.digest();
        return convertByteArrayToHex(digest);
    }

    private static String convertByteArrayToHex(byte[] bytes){ // Convert byte array of checksum to hexadecimal String
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes){
            sb.append(String.format("%02x",b));
        }
        return sb.toString();
    }
}
