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
	
	public ImageManager(AmazonRekognition rekognitionClient,  Image img){
		this.image = img;
		this.rekognitionClient = rekognitionClient;
	}
	
	/**Returns a LabelManager for the image **/
	public LabelManager getLabelManager(){
		DetectLabelsRequest request = new DetectLabelsRequest().withImage(image).withMinConfidence(75F).withMaxLabels(10);
		DetectLabelsResult result = rekognitionClient.detectLabels(request);
		return new LabelManager(result.getLabels());
	}
	
	/**Returns a ProtectionManager for the image **/
	public ProtectionManager getProtectionManager() {
        ProtectiveEquipmentSummarizationAttributes summaryAttributes = new ProtectiveEquipmentSummarizationAttributes()
                .withMinConfidence(confidence)
                .withRequiredEquipmentTypes("FACE_COVER");
        DetectProtectiveEquipmentRequest request = new DetectProtectiveEquipmentRequest()
                .withImage(image)
                .withSummarizationAttributes(summaryAttributes);
        DetectProtectiveEquipmentResult result = rekognitionClient.detectProtectiveEquipment(request);
        return new ProtectionManager(result);       
	}
	
	/**Returns a TextManager for the image **/
	public TextManager getDetectedTexts(){
		DetectTextRequest request = new DetectTextRequest().withImage(image);
		DetectTextResult result = rekognitionClient.detectText(request);
		return new TextManager(result.getTextDetections());		
	}
		
	
}