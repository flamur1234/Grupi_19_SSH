package home.model;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import services.dbConnection;

public class MenaxhoStudentModel  {

    private SimpleIntegerProperty nrId;
    private SimpleStringProperty firstName;
    private SimpleStringProperty lastName;
    private SimpleIntegerProperty mosha;
    private SimpleStringProperty gjinia;
    private SimpleStringProperty fakulteti;
    private SimpleStringProperty departamenti;
    private SimpleStringProperty email;
    private SimpleStringProperty telefon;


    public MenaxhoStudentModel(Integer nrId, String firstName, String lastName, Integer mosha, String gjinia, String fakulteti, String departamenti,  String email, String telefon) {
        this.nrId = new SimpleIntegerProperty(nrId);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.mosha = new SimpleIntegerProperty(mosha);
        this.gjinia = new SimpleStringProperty(gjinia);
        this.fakulteti = new SimpleStringProperty(fakulteti);
        this.departamenti = new SimpleStringProperty(departamenti);
        this.email = new SimpleStringProperty(email);
        this.telefon = new SimpleStringProperty(telefon);

    }

    public int getNrId() {
        return nrId.get();
    }

    public void setNrId(int nrId) {
        this.nrId = new SimpleIntegerProperty(nrId);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName = new SimpleStringProperty(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName = new SimpleStringProperty(lastName);
    }
    
    public int getMosha() {
        return mosha.get();
    }

    public void setMosha(int mosha) {
        this.mosha = new SimpleIntegerProperty();
    }
    
    public String getGjinia() {
        return gjinia.get();
    }

    public void setGjinia(String gjinia) {
        this.gjinia = new SimpleStringProperty(gjinia);
    }
    
    public String getFakulteti() {
        return fakulteti.get();
    }

    public void setFakulteti(String fakulteti) {
        this.fakulteti = new SimpleStringProperty(fakulteti);
    }
    
    public String getDepartamenti() {
        return departamenti.get();
    }

    public void setDepartamenti(String departamenti) {
        this.departamenti = new SimpleStringProperty(departamenti);
    }
    
    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email = new SimpleStringProperty(email);
    }
   
    public final SimpleStringProperty telefonProperty() {
 	   return telefon;
 	}
    
 public String getTelefoni() {
     return telefon.get();
 }

 public void setTelefoni(String nrTel) {
     this.telefon = new SimpleStringProperty(nrTel);
 }
 public static boolean deleteStudent(int nrId) throws IOException, JSONException {
// 	String query = "Delete from student where nid=?";
 	
	 	URL url1 = new URL("http://localhost:8000/api/deleteStudent/"+nrId);
		HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
		conn.setUseCaches(false);
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setRequestMethod("DELETE");
		conn.getInputStream();
		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		JSONObject obj = new JSONObject(in.readLine());
		System.out.println(obj);
		if (obj.get("status").equals("success")) {
			return true;
		}
		else {
			return false;
		}
 }
 
 public static boolean updateStudent(int nid, String eid, String mid, String gid, int aid, String fid, String did,String eeid, String tid) throws IOException, JSONException {
// 	String query = "UPDATE student SET eid=?, mid=?, aid=?, gid=?, fid=?, did=?, eeid=?, tid=? WHERE nid=?";
 	
	URL url1 = new URL("http://localhost:8000/api/updateStudent/"+nid+"/"+eid+"/"+mid+"/"+aid+"/"+gid+"/"+fid+"/"+did+"/"+eeid+"/"+tid);
	HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
	conn.setUseCaches(false);
	conn.setDoInput(true);
	conn.setDoOutput(true);
	conn.setRequestMethod("PUT");
	conn.getInputStream();
	BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	JSONObject obj = new JSONObject(in.readLine());
	if (obj.get("status").equals("success")) {
		return true;
	}
	else {
		return false;
	}
 }
 
	public static List<MenaxhoStudentModel> getStudents() throws IOException, JSONException {
		List<MenaxhoStudentModel> studentList = new ArrayList();
		
//		String query = "Select * from student where kf=1";
		
		URL url1 = new URL("http://localhost:8000/api/students/1");
		HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
		conn.setUseCaches(false);
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setRequestMethod("GET");
		conn.getInputStream();
		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		JSONArray obj = new JSONArray(in.readLine().toString());
		System.out.println(obj);
		
		
		for(int i = 0; i<obj.length(); i++) {
			JSONObject jsonObject = new JSONObject(obj.getJSONObject(i).toString());
			MenaxhoStudentModel book = new MenaxhoStudentModel(jsonObject.getInt("nid"), jsonObject.getString("eid"), jsonObject.getString("mid"), jsonObject.getInt("aid"), jsonObject.getString("gid"),jsonObject.getString("fid"), jsonObject.getString("did"), jsonObject.getString("eeid"), jsonObject.getString("tid"));
			studentList.add(book);

		}
		
		return studentList;
	}

}