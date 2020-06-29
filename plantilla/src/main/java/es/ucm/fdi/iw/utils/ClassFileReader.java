package es.ucm.fdi.iw.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import es.ucm.fdi.iw.constants.Constants;
import es.ucm.fdi.iw.constants.ConstantsPdfFile;
import es.ucm.fdi.iw.model.StClass;
import es.ucm.fdi.iw.model.User;

/**
 * Processes a JSON file and creates a new class
 * @author aitorcay
 */

public class ClassFileReader {
	
	private static final Logger log = LogManager.getLogger(ClassFileReader.class);

	/**
	 * Procesa la información de un fichero JSON y crea una nueva clase
	 * 
	 * @param jsonClass	contenido del fichero JSON
	 * @return			clase creada con la información procesada
	 */
	public static StClass readClassFile(String jsonClass) {
		StClass stClass = new StClass();
		User student;
				
		try {
			JSONObject jClass = new JSONObject(jsonClass);
			stClass.setName(jClass.getString(Constants.NOMBRE_CLASE));
			stClass.setTeamList(new ArrayList<>());
			stClass.setClassContest(new ArrayList<>());
			
			JSONArray jStudentsList = jClass.getJSONArray(Constants.ALUMNOS);
			JSONObject jStudent;
			List<User> studentList = new ArrayList<>();
			
			for (int i = 0; i < jStudentsList.length(); i++) {
				jStudent = jStudentsList.getJSONObject(i);
				student= new User();
				
				student.setEnabled((byte) 1);
				student.setRoles(Constants.USER_ROLE);
				student.setFirstName(jStudent.getString(Constants.NOMBRE));
				student.setLastName(jStudent.getString(Constants.APELLIDOS));
				student.createAndSetRandomToken(Constants.TOKEN_LENGTH);
				student.setUsername("ST-" + String.format("%03d" , i));
				student.setPassword(student.getToken());
				student.setElo(1000);
				student.setCorrect(0);
				student.setPerfect(0);
				student.setPassed(0);
				student.setStClass(stClass);
				student.setResultList(new ArrayList<>());
				
				log.info("- Estudiante cargado con éxito -\n{}", student);
				studentList.add(student);
			}
			
			stClass.setStudents(studentList);
			
		} catch (JSONException e) {
			log.warn("Error durante el procesado. Por favor revisa el fichero", e);
		}
		
		return stClass;
	}

}
