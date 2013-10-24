package test;
import java.awt.image.BufferedImage;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.objdetect.CascadeClassifier;


public class Main {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.loadLibrary("opencv_java246");
		Mat m = Mat.eye(3, 3, CvType.CV_8UC1);
        System.out.println("m = " + m.dump());
        new DetectFaceDemo().run();
	}
	
}

class DetectFaceDemo {
	public void run() {
		System.out.println("\nRunning DetectFaceDemo");
		
		// Create a face detector from the cascade file in the resources
		// directory.
		CascadeClassifier faceDetector = new CascadeClassifier(getClass().getResource("/lbpcascade_frontalface.xml").getPath());
		Mat image = Highgui.imread(getClass().getResource("/lena.png").getPath());
		
		// Detect faces in the image.
		// MatOfRect is a special container class for Rect.
		MatOfRect faceDetections = new MatOfRect();
		faceDetector.detectMultiScale(image, faceDetections);
		
		System.out.println(String.format("Detected %s faces", faceDetections.toArray().length));
		
		// Draw a bounding box around each face.
		for (Rect rect : faceDetections.toArray()) {
			Core.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0));
		}
		
		// Save the visualized detection.
		String filename = "faceDetection.png";
		System.out.println(String.format("Writing %s", filename));
		Highgui.imwrite(filename, image);
	}
	
	 public static BufferedImage matToBufferedImage(Mat matrix) {  
	     int cols = matrix.cols();  
	     int rows = matrix.rows();  
	     int elemSize = (int)matrix.elemSize();  
	     byte[] data = new byte[cols * rows * elemSize];  
	     int type;  
	     matrix.get(0, 0, data);  
	     switch (matrix.channels()) {  
	       case 1:  
	         type = BufferedImage.TYPE_BYTE_GRAY;  
	         break;  
	       case 3:  
	         type = BufferedImage.TYPE_3BYTE_BGR;  
	         // bgr to rgb  
	         byte b;  
	         for(int i=0; i<data.length; i=i+3) {  
	           b = data[i];  
	           data[i] = data[i+2];  
	           data[i+2] = b;  
	         }  
	         break;  
	       default:  
	         return null;  
	     }  
	     BufferedImage image = new BufferedImage(cols, rows, type);  
	     image.getRaster().setDataElements(0, 0, cols, rows, data);  
	     return image;  
	   }  
}