package com.cibertec.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cibertec.model.Moneda;
import com.cibertec.service.MonedaService;


import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class MonedaController {

	
	private final MonedaService bancoService;
	

	@PostMapping("/moneda")
	public ResponseEntity<?> create(@Validated  @RequestBody Moneda cuentaBancariaUsuario, BindingResult result)
	{
		Moneda cuentaBancariaUsuarioNew = null;
		Map<String, Object> response = new HashMap<>();

	 	
		if(result.hasErrors()) {
			
			
			List<String> errors = result.getFieldErrors()
					 .stream()
					 .map(err -> "El campo '"+ err.getField() + "'" + err.getDefaultMessage())
					 .collect(Collectors.toList());
			
			response.put("errors",errors);
		    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);

		}
		
		try {
			//roody comente 13_03_2023
		
			
			cuentaBancariaUsuarioNew = bancoService.registrarCuenta(cuentaBancariaUsuario);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
		    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		response.put("mensaje", "La moneda ha sido creado con éxito!");
		response.put("cliente", cuentaBancariaUsuarioNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED); 
	}
	
	
	@GetMapping("/moneda")
	public List<Moneda> findAll() {
	return	bancoService.findAll();
	
	}
	
	@GetMapping("/moneda/{id}")
	public Moneda findById(@PathVariable Long id) {
	return	bancoService.findById(id).get();
	
	}
	
	
	@DeleteMapping("/moneda/{id}")
	public void deleteById(@PathVariable Long id) {
		bancoService.deleteById(id);
	}
	
	
	@PutMapping("/moneda")
	public void update(@RequestBody Moneda banco) {
		bancoService.save(banco);
	}
	
	
}
