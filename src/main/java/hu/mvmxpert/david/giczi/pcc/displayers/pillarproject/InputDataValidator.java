package hu.mvmxpert.david.giczi.pcc.displayers.pillarproject;


public class InputDataValidator {


	public static boolean isValidProjectName(String projectName) {
	return !projectName.trim().isBlank() && projectName.length() > 2 && !Character.isDigit(projectName.charAt(0));
	}	
	
	public static boolean isValidPrePostFixValue(String value) {
		return !value.isBlank() && !value.isEmpty();
		}	
	
	public static double isValidInputDoubleValue(String inputNumber) throws NumberFormatException {
		
	Double number = Double.parseDouble(inputNumber);
	
	return number;
	}
	
	public static Double isValidInputPositiveDoubleValue(String inputNumber) throws NumberFormatException {
		Double positiveDouble = Double.parseDouble(inputNumber);
		
			if( 0 > positiveDouble ) 
				throw new NumberFormatException();
			
		return positiveDouble;
	}
	
	public static Integer isValidInputPositiveIntegerValue(String inputNumber) throws NumberFormatException {
		Integer	positiveInteger = Integer.parseInt(inputNumber);
		
			if( 0 > positiveInteger ) 
				throw new NumberFormatException();
			
		return positiveInteger;
	}
	
	public static int isValidAngleValue(String angleValue) throws NumberFormatException {
		
		int angle = Integer.parseInt(angleValue);

		if( 0 > angle && -360 < angle ){
			angle += 360;
		}
		else if(-360 > angle || 360 < angle ){
			throw new NumberFormatException();
		}

		return angle;
	}

	public static int isValidMinSecValue(String minSecValue) throws NumberFormatException {

		int minSec = Integer.parseInt(minSecValue);

		 if( 59 < minSec ){
			throw new NumberFormatException();
		}
		return minSec;
	}
	
	public static boolean isValidID(String id) {
		return !id.isBlank() && !id.isEmpty();
	}
}
