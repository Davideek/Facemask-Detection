package facemaskdetection;

import java.util.List;
import com.amazonaws.services.rekognition.model.AmazonRekognitionException;
import com.amazonaws.services.rekognition.model.DetectProtectiveEquipmentResult;
import com.amazonaws.services.rekognition.model.EquipmentDetection;
import com.amazonaws.services.rekognition.model.ProtectiveEquipmentBodyPart;
import com.amazonaws.services.rekognition.model.ProtectiveEquipmentPerson;

public class ProtectionManager {
	
	DetectProtectiveEquipmentResult DPE;
	List <ProtectiveEquipmentPerson> persons;

	public ProtectionManager(DetectProtectiveEquipmentResult DPE) {
		this.DPE = DPE;
		this.persons = DPE.getPersons();
	}
	
	/**Prints detected body parts and protective equipment **/
	public void printProtection() {
		try {
		for(ProtectiveEquipmentPerson person : persons) {
			System.out.println("ID: " + person.getId());
			List<ProtectiveEquipmentBodyPart> bodyParts = person.getBodyParts();
			if(bodyParts.isEmpty()) {
				System.out.println("No body parts detected");
			}
			else {
				for (ProtectiveEquipmentBodyPart bodyPart : bodyParts) {
					System.out.println(bodyPart.getName() + ". Confidence: " + bodyPart.getConfidence());
					List<EquipmentDetection> equipmentDetections = bodyPart.getEquipmentDetections();
					if(equipmentDetections.isEmpty()) {
						System.out.println("No protection detected on : " + bodyPart.getName());
					}
					else {
						for (EquipmentDetection item : equipmentDetections) {
							System.out.println("Item :" + item.getType() + ". Confidence: " + item.getConfidence());
							System.out.println("Covers body part: " + item.getCoversBodyPart().getValue().toString() +
									". Confidence: " + item.getConfidence().toString());
						}
					}
				}
			}
		}
		
    } catch(AmazonRekognitionException e) {
        e.printStackTrace();
    }
	}
	
	/**Returns true if there are no persons in the image missing PPE **/
	public boolean isProtected() {
		return DPE.getSummary().getPersonsWithoutRequiredEquipment().size() == 0;
	}
}
