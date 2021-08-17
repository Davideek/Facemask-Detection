package facemaskdetection;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;

public class ImageRekognition {
	
	private static String region = "eu-central-1";
	private AmazonRekognition rekognitionClient;
	private RemoteCamera remoteCamera;
	private FileHandler fileHandler;
	private ImageManager imageManager;
	
	public ImageRekognition(RemoteCamera remoteCamera) {
		 this.rekognitionClient = AmazonRekognitionClientBuilder.standard().withRegion(region).build();
		 this.remoteCamera = remoteCamera;
		 this.fileHandler = new FileHandler();	
	}
	
	/**Takes snapshot from camera**/
	public void takeSnapshot(){
		remoteCamera.snapshot();
	}
	
	/**Analyzes the image from the snapshot**/
	public void analyzeImage() {
		this.imageManager = new ImageManager(rekognitionClient, remoteCamera.getAWSImage());
	}
	/**Save image on your local computer**/
	public void saveImage( String path, String name) {
		fileHandler.saveImage(path, name , remoteCamera.getBufferedImage());
	}
	
	/**Prints result to file **/
	public void printToResultFile(String path, String name, String input) {
		fileHandler.createResultFile(path, name, input );		
	}
	
	/**Prints the PPE found in the snapshot image**/
	public void printProtectionEquipment() {
		this.imageManager.getProtectionManager().printProtection();
	}
	
	/**Prints if the required PPE is missing **/	
	public void printMissingProtectionEquipment() {
		boolean protection = this.imageManager.getProtectionManager().isProtected();
		if(protection){
			System.out.println("No protection missing");
		}
		else {
			System.out.println("Missing protection");
		}
	}	
}
