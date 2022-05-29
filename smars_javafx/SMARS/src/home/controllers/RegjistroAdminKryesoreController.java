package home.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

	
	public class RegjistroAdminKryesoreController implements Initializable {
		 String url = "jdbc:mysql://localhost:3306/smars";
		 String user = "root";
		 String pass = "0303";

	    @FXML
	    private Button Shtoid;
	    
	    @FXML
	    private TextField adminidfield;
	    
	    @FXML
	    private TextField firstnamefield;
	    
	    @FXML
	    private TextField lastnamefield;
	    
	    @FXML
	    private TextField emailfield;
	    
	    @FXML
	    private PasswordField txtPassword;
	    

		public void AddInfo(ActionEvent event) throws Exception {
			Stage primaryStage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/home/fxml/RegjistroAdminKryesore.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
		}
		
		public void AddToDatabase(ActionEvent event) throws JSONException{
			JSONObject json = new JSONObject();
	    	json.put("adminId", adminidfield.getText());
	    	json.put("firstName", firstnamefield.getText());
	    	json.put("lastName", lastnamefield.getText());
	    	json.put("email", emailfield.getText());
	    	json.put("password", txtPassword.getText());
	    	
	    	
	    	try {
	    		if(	validateEmail()) {
	    		URL url = new URL("http://localhost:8000/api/registerAdmin");
	    		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	    		conn.setUseCaches(false);
	    		conn.setDoInput(true);
	    		conn.setRequestProperty("Content-Type", "application/json");
	    		conn.setRequestProperty("Accept", "application/json");
	    		conn.setDoOutput(true);
	    		conn.setRequestMethod("POST");
	    		conn.connect();
	    		try(OutputStream os = conn.getOutputStream()) {
	    		    byte[] input = json.toString().getBytes("utf-8");
	    		    os.write(input, 0, input.length);    
	    		}
	    		
	    		try(BufferedReader br = new BufferedReader(
	    				  new InputStreamReader(conn.getInputStream(), "utf-8"))) {
	    				    StringBuilder response = new StringBuilder();
	    				    String responseLine = null;
	    				    while ((responseLine = br.readLine()) != null) {
	    				        response.append(responseLine.trim());
	    				    }
	    				    clearForm();
	    				    Alert alert = new Alert(AlertType.WARNING);
							alert.setTitle("Shto te dhenat");
							alert.setHeaderText(null);
							alert.setContentText("Te dhenat u shtuan!");
							alert.showAndWait();
	    				}}
	    	} catch (Exception ex) {
				ex.printStackTrace();
			}

		}


	    @Override
	    public void initialize(URL location, ResourceBundle resources) {

	    }
	    private void loadStage(String fxml) {
	        try {
	            Parent root = FXMLLoader.load(getClass().getResource(fxml));
	            Stage stage = new Stage();
	            stage.setScene(new Scene(root));
	            stage.setTitle("SMARS");
	            stage.getIcons().add(new Image("/home/icons/icon.png"));
	            stage.initModality(Modality.APPLICATION_MODAL);
	            stage.show();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    public void clearForm() {
	    	adminidfield.setText("");
	    	firstnamefield.setText("");
	    	lastnamefield.setText("");
	    	emailfield.setText("");
	    	txtPassword.setText("");
   		
   		}
	    
	    public boolean validateEmail() {
	    	  Pattern p = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");
	    	  Matcher m = p.matcher(emailfield.getText());
	    	  if(m.find() && m.group().equals(emailfield.getText())) {
	    		  return true;
	    	  }else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Validimi i Email");
				alert.setHeaderText(null);
				alert.setContentText("Ju lutem shenoni nje email valide!");
				alert.showAndWait();
				return false;
			
	    	  }
	}
	 
	}



