package fr.uca.cdr.skillful_network.jwt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.uca.cdr.skillful_network.model.entities.User;
import fr.uca.cdr.skillful_network.security.services.UserPrinciple;

@Component
public class JwtProvider {

	private static final String SCRIPT_URL = Paths.get("src/main/resources/data/script/scriptToken.py").toAbsolutePath()
			.toString();
	private static final String DECRYPT = "decrypt";
	private static final String ENCRYPT = "encrypt";
	@Value("${pyCmd}")
	private String PY_CMD;

//	Méthode qui permet de générer un token à partir de l'id, l'email et le mot de passe de l'utilisateur connecté
	public String generateJwtToken(Authentication authentication) {

		System.out.println("absolutePath : " + SCRIPT_URL);
		UserPrinciple userPrincipal = (UserPrinciple) authentication.getPrincipal();

		String code = userPrincipal.getId() + " " + userPrincipal.getEmail() + " " + userPrincipal.getPassword();
		String encryptCmd = PY_CMD + " " + SCRIPT_URL + " " + ENCRYPT + " " + code;

		return ExecutePyScript(encryptCmd);
	}

//	Méthode qui permet de décrypter un token 
	public String decryptJwtToken(String frontToken) {

		String decryptCmd = PY_CMD + " " + SCRIPT_URL + " " + DECRYPT + " " + frontToken;
		System.out.println("jwt récupéré dans decrypt : " + frontToken);

		return ExecutePyScript(decryptCmd);
	}

//	Méthode qui permet de définir l'état du token : valide ou invalide
	public boolean validateToken(String scriptResponse) {
		boolean validate = true;
		if (scriptResponse.equals("-3")) {
			validate = false;
			System.out.println("validateToken : Le token n'est pas fourni en argument");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le token n'est pas fourni en argument");
		}

		if (scriptResponse.equals("-4")) {
			validate = false;
			System.out.println("validateToken : Le token n'est pas conforme");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le token n'est pas conforme");
		}

		if (scriptResponse.equals("-5")) {
			validate = false;
			System.out.println("validateToken : Le token a expiré");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le token a expiré");
		}

		if (scriptResponse.equals("-6")) {
			validate = false;
			System.out.println(
					"validateToken : Il manque des arguments ou il y a un problème avec les modules jwt python");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Il manque des arguments ou il y a un problème avec les modules jwt python");
		}
//		Dans le cas où on arrive à catcher l'erreur lors de l'encodage du token dans le script python : TODO
		if (scriptResponse.equals("-7")) {
			validate = false;
			System.out.println(
					"validateToken : Il y a un problème lors de l'encodage du token, erreur liée avec les modules jwt python");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Il y a un problème lors de l'encodage du token, erreur liée avec les modules jwt python");
		}

		System.out.println("validateToken : Token valide");
		return validate;
	}

//  Méthode qui permet de récupérer l'email de l'utilisateur à partir de la réponse json fournie par le decryptage du token
	public String getEmailFromToken(String jsonDecryptResponse) throws JsonMappingException, JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = mapper.readValue(jsonDecryptResponse, Map.class);
		String email = (String) map.get("email");
		System.out.println("Email récupéré depuis le Json : " + email);
		return email;
	}

//	Méthode qui permet de récupérer un objet User à partir des informations utilisateurs de la réponse json fournie par le decryptage du token
	public User getUserFromJson(String jsonDecryptResponse) throws JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = mapper.readValue(jsonDecryptResponse, Map.class);

		Long id = Long.parseLong((String) map.get("id"));
		String email = (String) map.get("email");
		String password = (String) map.get("password");

		System.out.println("id : " + id + "\nemail : " + email + "\npassword : " + password);

		User userFromJson = new User();
		userFromJson.setId(id);
//		Attention : l'email sera mis en lowercase : vérifier que tous les emails le soient aussi en BDD
		userFromJson.setEmail(email);
		userFromJson.setPassword(password);

		System.out.println("User from json : \n" + userFromJson.toString());
		return userFromJson;
	}

//	Méthode qui sert à executer le script python en fonction de la commande donnée en argument et renvoyer sa réponse
	public String ExecutePyScript(String cmd) {
		String line = "";
		String scriptResponse = "";
		try {
			Process p = Runtime.getRuntime().exec(cmd);
			BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			while ((line = in.readLine()) != null) {
				// On récupère l'utilisateur
				scriptResponse = line;
				System.out.println(scriptResponse);
			}
			in.close();
			p.destroy();
		} catch (IOException ie) {
			ie.printStackTrace();
		}

		return scriptResponse;

	}
}