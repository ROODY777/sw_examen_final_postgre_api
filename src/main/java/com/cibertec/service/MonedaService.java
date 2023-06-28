package com.cibertec.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.cibertec.model.Moneda;

import com.cibertec.repository.MonedaRepository;



import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MonedaService {

	private final MonedaRepository bancoRepository;

	
	
	public void save(Moneda banco) {
		bancoRepository.save(banco);
	}
	
	public Moneda registrarCuenta(Moneda bancos) {
		return bancoRepository.save(bancos);
	}
	
	public List<Moneda> findAll() {
		return bancoRepository.findAll();
	}
	
	public Optional<Moneda> findById(Long id) {
		return bancoRepository.findById(id);
	}
	
	public void deleteById(Long id) {
		bancoRepository.deleteById(id);
	}
	
	
	
}
