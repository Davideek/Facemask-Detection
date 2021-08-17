package facemaskdetection;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import javax.imageio.ImageIO;

public class FileHandler {
	
	private FileWriter myWriter;
	
	public FileHandler() {
		
      }
	
	/**Creates a textfile with selected path, name and input **/
	public void createResultFile(String path, String name, String input) {
		try {
			File myObj = new File(path + name + ".txt");
		    if (myObj.createNewFile()) {
		          System.out.println("File created: " + myObj.getName());
		    } else {
		          System.out.println("File already exists.");
		    }
		this.myWriter = new FileWriter(path + name + ".txt");
        myWriter.write(input);
        myWriter.flush();
        System.out.println("Successfully wrote to the file.");
        myWriter.close();
      } catch (IOException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
      }
	}
	
	/**Saves the image with the selected path and name **/
	public void saveImage(String path, String name, BufferedImage img) {
		File initialImage = new File(path + name + ".jpg");
		try {
		ImageIO.write(img, "jpg", initialImage);
		}
		catch (MalformedURLException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace(); 
		}
	}
}
