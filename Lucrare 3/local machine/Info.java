import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.nio.file.Files;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Info {
    public void fileInfo(String localMachine ,String fileName) throws IOException {
        // Getting the file
        File localFile = new File(localMachine + File.separator + fileName);

        // Check if the file does exist
        if (!localFile.exists()){
            System.out.println("Files does not exist!");
        }else {
            int index = fileName.lastIndexOf('.');
            String extension = (index > 0) ? fileName.substring(index + 1).toUpperCase() : "NONE";
            System.out.println("File Name: " + localFile.getName() + "\n" +
                    "File Extension: " + extension + "\n" +
                    "Last modified: " + getDateString(Files.getLastModifiedTime(localFile.toPath()).toMillis()) + "\n" +
                    "Date Created: " + Files.getAttribute(localFile.toPath(), "creationTime"));
        }
    }
    private static String getDateString(long timeStamp){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(new Date(timeStamp));
    }

}

class ImageFiles extends Info{
    @Override
    public void fileInfo(String localMachine, String fileName) throws IOException {
        super.fileInfo(localMachine, fileName);
        // Image size
        BufferedImage bimg = ImageIO.read(new File(localMachine + File.separator + fileName));

        System.out.println("Image size: " + bimg.getWidth() + "x" + bimg.getHeight());
    }
}

class TextFiles extends Info{
    @Override
    public void fileInfo(String localMachine, String fileName) throws IOException { //TODO: Optimize code
        System.out.println("This is a text file");
        super.fileInfo(localMachine, fileName);
        // Line count
        Path path = Path.of(localMachine + File.separator + fileName);
        long lineCount;
        try(Stream<String> stream = Files.lines(path,StandardCharsets.UTF_8)) {
            lineCount = stream.count();
            System.out.println("Line count: " + lineCount);
        }catch (IOException e){
            throw new RuntimeException(e);
        }

        // Word count
        try (Stream<String> lines = Files.lines(path, StandardCharsets.UTF_8)) {

            // Word count using streams
            long wordCount = lines
                    .flatMap(line -> Stream.of(line.split("\\s+"))) // Split lines into words
                    .filter(word -> !word.isEmpty()) // Filter empty words
                    .count();

            System.out.println("Word count: " + wordCount);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Character count
        try (Stream<String> lines = Files.lines(path, StandardCharsets.UTF_8)) {
            // Character count (excluding line breaks)
            long charCount = lines
                    .flatMapToInt(String::chars) // Convert lines to int stream of characters
                    .filter(c -> c != '\n') // Filter out newline characters
                    .count();

            System.out.println("Character count (excluding line breaks): " + charCount);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}

class ProgramFiles extends Info{
    @Override
    public void fileInfo(String localMachine, String fileName) throws IOException{
        super.fileInfo(localMachine, fileName);

        // Get the line count
        Path path = Path.of(localMachine + File.separator + fileName);
        long lineCount;
        try(Stream<String> stream = Files.lines(path,StandardCharsets.UTF_8)) {
            lineCount = stream.count();
            System.out.println("Line count: " + lineCount);
        }catch (IOException e){
            throw new RuntimeException(e);
        }

        // Get the class count //TODO: optimize using java parser libraries
        int classCount = 0;
        int methodCount = 0;
        Pattern classPattern = Pattern.compile("class\\s+([A-Z][\\w]*)");
        Pattern methodPattern = Pattern.compile("(?:(public|private|protected)\\s+)?(?:static\\s+)?\\s*(\\([\\w,\\.\\s\\[\\]]*\\))\\s+([A-Za-z_][A-Za-z0-9_]*)\\s*(\\(.*\\))?\\s*");


        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(localMachine + File.separator + fileName))) {
            String line;
            while ((line = bufferedReader.readLine())!=null){
                Matcher classMatcher = classPattern.matcher(line);
                Matcher methodMacther = methodPattern.matcher(line);
                if (classMatcher.find()){
                    classCount++;
                }
                if (methodMacther.find()){
                    methodCount++;
                }
            }
        }catch (IOException e){
            throw new RuntimeException(e);
        }

        System.out.println("Number of classes: " + classCount + "\n" +
                "Number of methods: " + methodCount);
    }
}