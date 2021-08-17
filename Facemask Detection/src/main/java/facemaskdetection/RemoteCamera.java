package facemaskdetection;

import java.net.*;
import java.nio.ByteBuffer;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import javax.imageio.ImageIO;
import com.amazonaws.services.rekognition.model.Image;

public class RemoteCamera {
	
	private Image imageImage;
	private BufferedImage bufferedImage;
	private String username, password;
	private boolean usernamepwd;
	private URL url;

	public RemoteCamera(String url, String username, String pwd) throws MalformedURLException {
		this.url = new URL(url);
		this.username = username;
		this.password = pwd;
		usernamepwd = true;
	}
	
	public RemoteCamera(String url) throws MalformedURLException {
		this.url = new URL(url);
		usernamepwd = false;
	}
	
	/**Returns BufferedImage from the camera snapshot **/
	public BufferedImage getBufferedImage() {
		return bufferedImage;
	}
	
	/**Snapshot the image through http request **/
	public void snapshot() {
		try {
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		if(usernamepwd) {
			String userCredentials = username + ":" + password;
			String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userCredentials.getBytes()));
			con.setRequestProperty ("Authorization", basicAuth);
		}
		bufferedImage = ImageIO.read(con.getInputStream());
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(bufferedImage, "jpg", baos);
		ByteBuffer byteBuffer = ByteBuffer.wrap(baos.toByteArray());
		imageImage = new Image().withBytes(byteBuffer);
		con.disconnect();
		}
		catch (MalformedURLException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace(); 
		}	
	}
	
	/**Returns the AWS rekognition Image **/
	public Image getAWSImage() {
		return imageImage;
	}


	

	
}
