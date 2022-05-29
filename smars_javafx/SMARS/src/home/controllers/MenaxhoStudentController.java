package home.controllers;
import home.model.MenaxhoAdminModel;
import home.model.MenaxhoStudentModel;
import home.model.MenaxhoStudentModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import services.dbConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MenaxhoStudentController implements Initializable {

    @FXML
    private TableView<MenaxhoStudentModel> tbData;
    
    @FXML
    public TableColumn<MenaxhoStudentModel, Integer> nrId;

    @FXML
    public TableColumn<MenaxhoStudentModel, String> firstName;

    @FXML
    public TableColumn<MenaxhoStudentModel, String> lastName;

    @FXML
    public TableColumn<MenaxhoStudentModel, Integer> mosha;
    
    @FXML
    public TableColumn<MenaxhoStudentModel, String> gjinia;
    
    @FXML
    public TableColumn<MenaxhoStudentModel, String> fakulteti;
    
    @FXML
    public TableColumn<MenaxhoStudentModel, String> departamenti;
    
    @FXML
    public TableColumn<MenaxhoStudentModel, String> email;
    
    @FXML
    public TableColumn<MenaxhoStudentModel, String> telefon;
    
    @FXML
    private TextField nrIdtxt;
    
    @FXML
    private TextField firstNametxt;
    
    @FXML
    private TextField lastNametxt;
    
    @FXML
    private TextField moshatxt;
    
    @FXML
    private TextField gjiniatxt; 
    
    @FXML
    private TextField fakultetitxt;
    
    @FXML
    private TextField departamentitxt;
    
    @FXML
    private TextField emailtxt;
    
    @FXML
    private TextField telefonitxt;
    
    
    private ObservableList<MenaxhoStudentModel> data;

    Connection dbconn = dbConnection.getConnection();
   

    public void initialize(URL url, ResourceBundle rb) {
        data = FXCollections.observableArrayList();
       try{
    	   
    	   
    	   URL url1 = new URL("http://localhost:8000/api/students/1");
	   		HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
	   		conn.setUseCaches(false);
	   		conn.setDoInput(true);
	   		conn.setDoOutput(true);
	   		conn.setRequestMethod("GET");
	   		conn.getInputStream();
	   		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	//   		JsonParser parser = new JsonParser();
	//   		JSONArray jsonArray = (JSONArray) parser.parseDoc(in.readLine());
	   		JSONArray obj = new JSONArray(in.readLine().toString());
	   		System.out.println(obj);
	   		
	   		for(int i = 0; i<obj.length(); i++) {
	   			JSONObject jsonObject = new JSONObject(obj.getJSONObject(i).toString());
	   			data.add(new MenaxhoStudentModel(
	   					jsonObject.getInt("nid"),
	   					jsonObject.getString("eid"),
	   					jsonObject.getString("mid"),
	   					jsonObject.getInt("aid"),
	   					jsonObject.getString("gid"),
	   					jsonObject.getString("fid"),
	   					jsonObject.getString("did"),
	   					jsonObject.getString("eeid"),
	   					jsonObject.getString("tid")
	   					));
	   		}
    	   
    	   
           
           
           nrId.setCellValueFactory(new PropertyValueFactory<MenaxhoStudentModel, Integer>("nrId"));
           firstName.setCellValueFactory(new PropertyValueFactory<MenaxhoStudentModel, String>("firstName"));
           lastName.setCellValueFactory(new PropertyValueFactory<MenaxhoStudentModel, String>("lastName"));
           mosha.setCellValueFactory(new PropertyValueFactory<MenaxhoStudentModel, Integer>("mosha"));
           gjinia.setCellValueFactory(new PropertyValueFactory<MenaxhoStudentModel, String>("gjinia"));
           fakulteti.setCellValueFactory(new PropertyValueFactory<MenaxhoStudentModel, String>("fakulteti"));
           departamenti.setCellValueFactory(new PropertyValueFactory<MenaxhoStudentModel, String>("departamenti"));
           email.setCellValueFactory(new PropertyValueFactory<MenaxhoStudentModel, String>("email"));
           telefon.setCellValueFactory(new PropertyValueFactory<MenaxhoStudentModel, String>("telefon"));
           
           nrIdtxt.setDisable(true);
           

           
           tbData.setItems(data); 
           
       }catch (Exception e){
           e.printStackTrace();
           System.out.println(e);
       }

	   		tbData.setRowFactory(tv -> {
	   			
	            TableRow<MenaxhoStudentModel> row = new TableRow<>();
	            row.setOnMouseClicked(event -> {
	            	nrIdtxt.setText( String.valueOf(row.getItem().getNrId()));
           	        firstNametxt.setText(row.getItem().getFirstName());
           	        lastNametxt.setText(row.getItem().getLastName());
           	        moshatxt.setText( String.valueOf(row.getItem().getMosha()));
           	        gjiniatxt.setText(row.getItem().getGjinia());
           	        fakultetitxt.setText(row.getItem().getFakulteti());
           	        departamentitxt.setText(row.getItem().getDepartamenti());
                  	emailtxt.setText(row.getItem().getEmail());
                  	telefonitxt.setText( String.valueOf(row.getItem().getTelefoni()));

	            });
	   			
	   			
	            
	            return row ;
	        });
    }
    
   		
	   	   
	   		public void deleteStudent() throws NumberFormatException, IOException, JSONException {
	   			if(MenaxhoStudentModel.deleteStudent(Integer.parseInt(nrIdtxt.getText()))) {
	   				showStudent();
	   				clearForm();
	   				
	   			}
	   		}
	   		

	   		public void updateStudent() throws NumberFormatException, IOException, JSONException {
	   			if(MenaxhoStudentModel.updateStudent(Integer.parseInt(nrIdtxt.getText()), firstNametxt.getText(), lastNametxt.getText(),gjiniatxt.getText(),Integer.parseInt(moshatxt.getText()),fakultetitxt.getText(),departamentitxt.getText(),emailtxt.getText(), telefonitxt.getText()) & validateEmail()) {
	   				showStudent();
	   				clearForm();
	   				
	   			}
	   		}
	   		
	   		public void showStudent() throws IOException, JSONException {
	   			List<MenaxhoStudentModel> students = MenaxhoStudentModel.getStudents();
	   			
	   			ObservableList<MenaxhoStudentModel> studentList = FXCollections.observableArrayList();
	   			
	   			for(int i = 0; i < students.size(); i++) {
	   				studentList.add(students.get(i));
	   			}
	   			
	   			tbData.setItems(studentList);
	   		}
	   		
	   		public void clearForm() {
	   			nrIdtxt.setText("");
	   			firstNametxt.setText("");
	   			lastNametxt.setText("");
	   			moshatxt.setText("");
	   			gjiniatxt.setText("");
	   			fakultetitxt.setText("");
	   			departamentitxt.setText("");
	   			emailtxt.setText("");
	   			telefonitxt.setText("");
	   			
	   		}
	   		
	   		public boolean validateEmail() {
	  	  	  Pattern p = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");
	  	  	  Matcher m = p.matcher(emailtxt.getText());
	  	  	  if(m.find() && m.group().equals(emailtxt.getText())) {
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

