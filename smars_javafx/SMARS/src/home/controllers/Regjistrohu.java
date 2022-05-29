package home.controllers;

import home.model.StudentsModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import com.mysql.cj.xdevapi.Statement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.http.HttpClient;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

public class Regjistrohu implements Initializable {
	
	 String url = "jdbc:mysql://localhost:3306/smars";
	 String user = "root";
	 String pass = "0303";
    
    @FXML
    private TextField nrId;
    
    @FXML
    private TextField mbiemriId;
    
    @FXML
    private TextField emriId;
    
    @FXML
    private TextField moshaId;
    
    @FXML
    private TextField gjiniaId;
    
    @FXML
    private TextField fakultetiId;
    
    @FXML
    private TextField departamentiId;
    
    @FXML
    private TextField emailId;
    
    @FXML
    private TextField telefoniId;
    
    @FXML
    private Button konfirmoID;

   
    //my bad - the freaking mouse event
	public void AddInfo(ActionEvent event) throws Exception {
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/home/fxml/Regjistrohu.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public void AddToDatabase() throws JSONException {
		JSONObject json = new JSONObject();
    	json.put("nid", nrId.getText());
    	json.put("eid", emriId.getText());
    	json.put("mid", mbiemriId.getText());
    	json.put("aid", moshaId.getText());
    	json.put("gid", gjiniaId.getText());
    	json.put("fid", fakultetiId.getText());
    	json.put("did", departamentiId.getText());
    	json.put("eeid", emailId.getText());
    	json.put("tid", telefoniId.getText());
    	
    	try {
    		if( validateEmail() & validateNumber()) {
    		URL url = new URL("http://localhost:8000/api/student");
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
    					alert.setTitle("Konfirmo te dhenat");
    					alert.setHeaderText(null);
    					alert.setContentText("Te dhenat u konfirmuan!");
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
			nrId.setText("");
   			emriId.setText("");
   			mbiemriId.setText("");
   			moshaId.setText("");
   			gjiniaId.setText("");
   			fakultetiId.setText("");
   			departamentiId.setText("");
   			emailId.setText("");
   			telefoniId.setText("");
   			
   		}
		public boolean validateEmail() {
	    	  Pattern p = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");
	    	  Matcher m = p.matcher(emailId.getText());
	    	  if(m.find() && m.group().equals(emailId.getText())) {
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

	      public boolean validateNumber() {
	    	  Pattern p = Pattern.compile("^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]\\d{3}[\\s.-]\\d{3}$");
	    	  Matcher m = p.matcher(telefoniId.getText());
	    	  if(m.find() && m.group().equals(telefoniId.getText())) {
	    		  return true;
	    	  }else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Validimi i Numrit");
				alert.setHeaderText(null);
				alert.setContentText("Ju lutem shenoni nje numer valid!");
				alert.showAndWait();
				return false;
			}
	    	  
	      }

 
}
