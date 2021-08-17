package facemaskdetection;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.model.DetectProtectiveEquipmentRequest;
import com.amazonaws.services.rekognition.model.DetectProtectiveEquipmentResult;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.ProtectiveEquipmentSummarizationAttributes;

public class ImageManager {
	
	private static float confidence = 80f;
	private AmazonRekognition rekognitionClient;
	private Image image;
	private ProtectionManager protectionManager;
	
	public ImageManager(AmazonRekognition rekognitionClient,  Image img){
		this.image = img;
		this.rekognitionClient = rekognitionClient;
	}
	
	/**Creates a ProtectionManager for the image **/
	public void createProtectionManager() {
        ProtectiveEquipmentSummarizationAttributes summaryAttributes = new ProtectiveEquipmentSummarizationAttributes()
                .withMinConfidence(confidence)
                .withRequiredEquipmentTypes("FACE_COVER");
        DetectProtectiveEquipmentRequest request = new DetectProtectiveEquipmentRequest()
                .withImage(image)
                .withSummarizationAttributes(summaryAttributes);
        DetectProtectiveEquipmentResult result = rekognitionClient.detectProtectiveEquipment(request);
        this.protectionManager = new ProtectionManager(result);       
	}
	
	/**Returns a ProtectionManager for the image **/
	public ProtectionManager getProtectionManager() {
		return protectionManager;
	}
		
	
}
