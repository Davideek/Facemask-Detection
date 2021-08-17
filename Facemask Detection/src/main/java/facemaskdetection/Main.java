package facemaskdetection;

public class Main {
	
	private static String url = "http://192.168.1.126/axis-cgi/jpg/image.cgi?resolution=1920x1080&compression=25&camera=1";
	private static String username = "root";
	private static String pwd = "TEST123+-+";

	public static void main(String[] args) throws Exception {
		Thread.sleep(10000);
		RemoteCamera rc = new RemoteCamera(url, username, pwd);
		ImageRekognition imageRekognition = new ImageRekognition(rc);
		imageRekognition.takeSnapshot();
		imageRekognition.analyzeImage();
		imageRekognition.printProtectionEquipment();
		imageRekognition.printMissingProtectionEquipment();
		imageRekognition.saveImage("C://Cam/image", "test" );	
	}
}
