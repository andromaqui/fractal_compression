package fractal_compression;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import javafx.beans.value.ChangeListener ;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class AppController {
	
	private static final String initialFileName = "LenaGrey.png";
	private static File fileOpenPath = new File(".");

	private RasterImage sourceImage;
	private String sourceFileName;
		
	private RasterImage rleImage;

	ObservableList list = FXCollections.observableArrayList();    
	
	@FXML
    private ImageView sourceImageView;

    @FXML
    private Label sourceInfoLabel;

    @FXML
    private ImageView rleImageView;

    @FXML
    private ImageView decodedImageView;
    
    @FXML
    private ImageView bestFitCollage;
    
    @FXML
    private Label rleInfoLabel;

    @FXML
    private Label messageLabel;
    
    @FXML
    private Slider blockSize;
    @FXML
    private Slider searchWidth;
    
    @FXML
    private Label mse;
    
    private StringConverter<Double> sliderFormatter = new StringConverter<Double>() {
        @Override
        public String toString(Double n) {
            if (n <= 1) return "2";
            if (n <= 2) return "4";
            if (n <= 3) return "8";
            if (n <= 4) return "16";



            return "-";
        }

        @Override
        public Double fromString(String s) {
            switch (s) {
                case "2":
                    return 2d;
                case "4":
                    return 4d;
                case "8":
                    return 8d;
                case "16":
                    return 16d;

                default:
                    return 8d;
            }
        }
    };

    @FXML
    void openImage() {
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.setInitialDirectory(fileOpenPath); 
    	fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Images (*.jpg, *.png, *.gif)", "*.jpeg", "*.jpg", "*.png", "*.gif"));
    	File selectedFile = fileChooser.showOpenDialog(null);
    	if(selectedFile != null) {
    		fileOpenPath = selectedFile.getParentFile();
    		loadAndDisplayImage(selectedFile);
    		messageLabel.getScene().getWindow().sizeToScene();;
    	}
    }

	@FXML
	public void initialize() {
		loadAndDisplayImage(new File(initialFileName));	
		
		//initialize slider
		blockSize.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable,
					Number oldValue, Number newValue) {
				
				System.out.println(("Slider Value Changed (newValue: " + newValue.intValue() + ")\n"));
				FractalCompression.blockSize= (int) Math.pow(2d, (double) newValue.intValue());
				openDecodedImage();
			}
		});
		
		searchWidth.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable,
					Number oldValue, Number newValue) {
				
				System.out.println(("Slider Value Changed (newValue: " + newValue.intValue() + ")\n"));
				FractalCompression.widthKernel= (int) Math.pow(2d, (double) newValue.intValue());
				openDecodedImage();
			}
		});
		blockSize.setLabelFormatter(sliderFormatter);
		
		searchWidth.setLabelFormatter(sliderFormatter);
	}
	
	private void loadAndDisplayImage(File file) {
		sourceFileName = file.getName();
		messageLabel.setText("Opened image " + sourceFileName);
		sourceImage = new RasterImage(file);
		sourceImage.setToView(sourceImageView);
		sourceInfoLabel.setText("");
		rleImage = new RasterImage(sourceImage.width, sourceImage.height);
		rleImage.setToView(rleImageView);
	}
	
	@FXML
	public void saveRLEImage() {
		//FractalCompression.decoder(sourceImage.width, sourceImage.height);
	}
	
	@FXML
	public void openRLEImage() {
		FractalCompression.showCodebuch(sourceImage).setToView(rleImageView);
	}
	
	@FXML
	public void openDecodedImage() {
		
		try {
			RasterImage tmp = sourceImage;
			DataOutputStream ouputStream = new DataOutputStream(new FileOutputStream("unknown.run"));
			FractalCompression.encode(sourceImage, ouputStream).setToView(bestFitCollage);
			DataInputStream inputStream = new DataInputStream(new FileInputStream("unknown.run"));
			FractalCompression.decode(inputStream).setToView(decodedImageView);
			mse.setText("MSE " + FractalCompression.getAvgError());
			sourceImage = tmp;
		}

		catch (Exception e) {
	 			e.printStackTrace();
	 		}
			
	}
	
	@FXML
	public void adjustBlockSize(){
		System.out.println(blockSize.getValue());		 
	}
	

}
