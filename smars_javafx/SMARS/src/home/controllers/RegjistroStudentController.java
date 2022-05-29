package home.controllers;

import home.model.RegjistroStudentModel;
import org.json.JSONArray;
import org.json.JSONException;

import home.model.RegjistroStudentModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import services.dbConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import org.json.JSONArray;
import org.json.JSONObject;


public class RegjistroStudentController implements Initializable {

    @FXML
    private TableView<RegjistroStudentModel> tbData;
    
    @FXML
    public TableColumn<RegjistroStudentModel, Integer> nrId;

    @FXML
    public TableColumn<RegjistroStudentModel, String> firstName;

    @FXML
    public TableColumn<RegjistroStudentModel, String> lastName;

    @FXML
    public TableColumn<RegjistroStudentModel, Integer> mosha;
    
    @FXML
    public TableColumn<RegjistroStudentModel, String> gjinia;
    
    @FXML
    public TableColumn<RegjistroStudentModel, String> fakulteti;
    
    @FXML
    public TableColumn<RegjistroStudentModel, String> departamenti;
    
    @FXML
    public TableColumn<RegjistroStudentModel, String> email;
    
    @FXML
    public TableColumn<RegjistroStudentModel, Integer> telefon;
    
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
    
    
    private ObservableList<RegjistroStudentModel> data;

    Connection dbconn = dbConnection.getConnection();
   

    public void initialize(URL url, ResourceBundle rb) {
        data = FXCollections.observableArrayList();
        try {
    		URL url1 = new URL("http://localhost:8000/api/students/0");
    		HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
    		conn.setUseCaches(false);
    		conn.setDoInput(true);
    		conn.setDoOutput(true);
    		conn.setRequestMethod("GET");
    		conn.getInputStream();
    		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//    		JsonParser parser = new JsonParser();
//    		JSONArray jsonArray = (JSONArray) parser.parseDoc(in.readLine());
    		JSONArray obj = new JSONArray(in.readLine().toString());
    		System.out.println(obj);
    		
    		for(int i = 0; i<obj.length(); i++) {
    			JSONObject jsonObject = new JSONObject(obj.getJSONObject(i).toString());
    			data.add(new RegjistroStudentModel(
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

    		
    		
    		
           nrId.setCellValueFactory(new PropertyValueFactory<RegjistroStudentModel, Integer>("nrId"));
           firstName.setCellValueFactory(new PropertyValueFactory<RegjistroStudentModel, String>("firstName"));
           lastName.setCellValueFactory(new PropertyValueFactory<RegjistroStudentModel, String>("lastName"));
           mosha.setCellValueFactory(new PropertyValueFactory<RegjistroStudentModel, Integer>("mosha"));
           gjinia.setCellValueFactory(new PropertyValueFactory<RegjistroStudentModel, String>("gjinia"));
           fakulteti.setCellValueFactory(new PropertyValueFactory<RegjistroStudentModel, String>("fakulteti"));
           departamenti.setCellValueFactory(new PropertyValueFactory<RegjistroStudentModel, String>("departamenti"));
           email.setCellValueFactory(new PropertyValueFactory<RegjistroStudentModel, String>("email"));
           telefon.setCellValueFactory(new PropertyValueFactory<RegjistroStudentModel, Integer>("telefon"));
           
           nrIdtxt.setDisable(true);
           
           tbData.setItems(data);             
       }catch (Exception e){
           e.printStackTrace();
           System.out.println(e);
       }

	   		tbData.setRowFactory(tv -> {
	   			
	            TableRow<RegjistroStudentModel> row = new TableRow<>();
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
    
   		
	   	   
	   		public void deleteStudent() throws JSONException, IOException, SQLException {
	   			if(RegjistroStudentModel.deleteStudent(Integer.parseInt(nrIdtxt.getText()))) {
	   				showStudent();
	   				clearForm();
	   				
	   			}
	   		}
//	   		
	   		public void konfirmoStudent() throws JSONException, IOException, SQLException {
	   			if(RegjistroStudentModel.konfirmoStudent(Integer.parseInt(nrIdtxt.getText()))) {
	   				showStudent();
	   				clearForm();
	   			}
	   		}
//	   		
	   		public void showStudent() throws JSONException, IOException, SQLException {
	   			List<RegjistroStudentModel> students = RegjistroStudentModel.getStudents();
	   			
	   			ObservableList<RegjistroStudentModel> studentList = FXCollections.observableArrayList();
	   			
	   			for(int i = 0; i < students.size(); i++) {
	   				studentList.add(students.get(i));
	   			}
	   			
	   			tbData.setItems(studentList);
	   		}
//	   		
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

}
