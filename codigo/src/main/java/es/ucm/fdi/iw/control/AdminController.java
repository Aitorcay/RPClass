package es.ucm.fdi.iw.control;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.itextpdf.text.DocumentException;

import es.ucm.fdi.iw.LocalData;
import es.ucm.fdi.iw.model.User;

/**
 * Admin-only controller
 * @author aitorcay
 */

@Controller()
@RequestMapping("admin")
public class AdminController {
	
	private static final Logger log = LogManager.getLogger(AdminController.class);
	
	@Autowired 
	private EntityManager entityManager;
	
	@Autowired
	private LocalData localData;
	
	/**
	 * Vista del perfil del usuario
	 * 
	 * @param id		id del usuario loggeado
	 * @param model		modelo que contendrá la información
	 * @param session	sesión asociada al usuario
	 * @return			vista a mostrar
	 */
	@GetMapping("/{id}")
	public String getUser(@PathVariable long id, Model model, HttpSession session) {
		
		// ANTES User u = entityManager.find(User.class, id);
		User u = entityManager.find(User.class, ((User)session.getAttribute("u")).getId());

		model.addAttribute("user", u);
		return "admin";
	}
	
	/**
	 * Vista de error
	 * 
	 * @param model	modelo que contendrá la información
	 * @return		vista informando del error
	 */
	@GetMapping("/error")
	public String error(Model model) {
		return "error";
	}
	
	/**
	 * Descarga el fichero plantilla para la creación de clases
	 * 
	 * @param id					id del usuario loggeado
	 * @param model					modelo que contendrá la información
	 * @param session				sesión asociada al usuario
	 * @return						fichero con la plantilla
	 * @throws IOException
	 * @throws DocumentException
	 */
	@GetMapping("/{id}/downloadFile")
	@Transactional
	public StreamingResponseBody downloadFile(@PathVariable("id") long id, @RequestParam("fileName") String fileName, Model model, HttpSession session) throws IOException, DocumentException {	
		
		File f = localData.getFile("templates", fileName);
		InputStream in = new BufferedInputStream(new FileInputStream(f));
		return new StreamingResponseBody() {
			@Override
			public void writeTo(OutputStream os) throws IOException {
				FileCopyUtils.copy(in, os);
			}
		};
	}	
	
	/**
	 * Descarga el fichero plantilla para la creación de pruebas
	 * 
	 * @param id					id del usuario loggeado
	 * @param model					modelo que contendrá la información
	 * @param session				sesión asociada al usuario
	 * @return						fichero con la plantilla
	 * @throws IOException
	 * @throws DocumentException
	 */
	@GetMapping("/{id}/contestTemplate")
	@Transactional
	public StreamingResponseBody getContestTemplate(@PathVariable("id") long id, Model model, HttpSession session) throws IOException, DocumentException {	
		
		File f = localData.getFile("templates", "plantilla - prueba.json");
		InputStream in = new BufferedInputStream(new FileInputStream(f));
		return new StreamingResponseBody() {
			@Override
			public void writeTo(OutputStream os) throws IOException {
				FileCopyUtils.copy(in, os);
			}
		};
	}		
}
