package com.metricalab.saying.controllers;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.metricalab.saying.pojo.AvgQualitySaying;
import com.metricalab.saying.pojo.NumSaying;
import com.metricalab.saying.pojo.SayingDTO;
import com.metricalab.saying.pojo.SayingResponse;
import com.metricalab.saying.service.ISayingService;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.PATCH })
@RestController
@RequestMapping("/api")
public class Saying {

	private static final Logger log = Logger.getLogger(Saying.class.getName());

	// Si no se configura al iniciar la aplicación se puede
	// seleccionar la implementación a utilizar mediante un Qualifier.
	// @Qualifier("inMemory")
	// @Qualifier("dataBase")
	@Autowired
	ISayingService sayingService;

	@GetMapping("/saying/best")
	public ResponseEntity<SayingDTO> bestSaying() {
		log.log(Level.INFO, "Llamada al endpoint /saying/best ");
		return new ResponseEntity<>(sayingService.getBestSaying(), HttpStatus.OK);
	}

	@GetMapping("/saying/{number}/{order}")
	public ResponseEntity<List<SayingDTO>> saying(@PathVariable final Integer number,
			@PathVariable final String order) {
		// Logger con expresion lambda
		log.log(Level.INFO, () -> String.format(
				"Llamada al endpoint /saying/{number}/{order}. Se quieren recuperar %s refranes con ordenación %s .",
				number, order));
		return new ResponseEntity<>(sayingService.getSaying(number, order), HttpStatus.OK);
	}

	@GetMapping("/saying/totalNum")
	public ResponseEntity<NumSaying> numSaying() {
		log.log(Level.INFO, "Llamada al endpoint /saying/totalNum");

		return new ResponseEntity<>(new NumSaying(sayingService.getNumSaying()), HttpStatus.OK);
	}

	@GetMapping("/saying/random")
	public ResponseEntity<SayingDTO> sayingRandom() {
		log.log(Level.INFO, "Llamada al endpoint /saying/random");
		return new ResponseEntity<>(sayingService.getRandomSaying(), HttpStatus.OK);
	}

	@GetMapping("/saying/sort/{order}")
	public ResponseEntity<List<SayingDTO>> orderSaying(@PathVariable final String order) {
		log.log(Level.INFO, "Llamada al endpoint /saying/sort");
		return new ResponseEntity<>(sayingService.sortSaying(order), HttpStatus.OK);
	}

	@PostMapping("/saying")
	public ResponseEntity<SayingResponse> addSaying(@Valid @RequestBody final SayingDTO saying) {
		log.log(Level.INFO, "Llamada al endpoint /saying (POST)");
		final String response = sayingService.addSaying(saying) != null ? " Saying insertado " : " Error al insertar ";
		return new ResponseEntity<>(new SayingResponse(response), HttpStatus.OK);
	}

	@DeleteMapping("/saying/{id}")
	public ResponseEntity<SayingResponse> deleteSaying(@PathVariable final Long id) {
		log.log(Level.INFO, "Llamada al endpoint /saying/{id} (DELETE)");
		sayingService.deleteSaying(id);
		return new ResponseEntity<>(new SayingResponse("Saying con id: " + id + " borrado"), HttpStatus.OK);
	}

	@GetMapping("/saying/{id}")
	public ResponseEntity<SayingDTO> findById(@PathVariable final Long id) {
		log.log(Level.INFO, "Llamada al endpoint /saying/{id} (GET)");
		return new ResponseEntity<>(sayingService.getSayingById(id), HttpStatus.OK);
	}

	@GetMapping("/saying/user/{user}")
	public ResponseEntity<List<SayingDTO>> findByUser(@PathVariable final String user) {
		log.log(Level.INFO, "Llamada al endpoint /saying/user/{user} (GET)");
		return new ResponseEntity<>(sayingService.getSayingByOrigin(user), HttpStatus.OK);

	}

	@GetMapping("/saying/findText/{text}")
	public ResponseEntity<List<SayingDTO>> searchSaying(@PathVariable final String text) {
		log.log(Level.INFO, "Llamada al endpoint /saying/findText/{text}");
		return new ResponseEntity<>(sayingService.getContainsSaying(text), HttpStatus.OK);

	}

	@GetMapping("/saying/findUser/{user}")
	public ResponseEntity<List<SayingDTO>> searchUser(@PathVariable final String user) {
		log.log(Level.INFO, "Llamada al endpoint /saying/findUser/{user}");
		return new ResponseEntity<>(sayingService.getContainsOrigin(user), HttpStatus.OK);
	}

	@GetMapping("/saying/findUserOrder/{user}/{order}")
	public ResponseEntity<List<SayingDTO>> searchUserOrder(@PathVariable final String user,
			@PathVariable final String order) {
		log.log(Level.INFO, () -> String.format(
				"Llamada al endpoint /saying/{user}/{order}. Se quieren recuperar %s refranes con ordenación %s .",
				user, order));
		return new ResponseEntity<>(sayingService.getContainsOriginOrder(user, order), HttpStatus.OK);

	}
	
	
	@GetMapping("/saying/avgQuality")
	public ResponseEntity<AvgQualitySaying> avgQualitySaying() {
		log.log(Level.INFO, "Llamada al endpoint /saying/avgQuality ");
		return new ResponseEntity<>(new AvgQualitySaying(sayingService.getAvgQualitySaying()), HttpStatus.OK);
	}
}
