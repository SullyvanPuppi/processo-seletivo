package br.org.spcbrasil.processoseletivo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.org.spcbrasil.processoseletivo.model.Paciente;

/**
 * @author Sullyvan Puppi
 * spuppi.com - spuppi@gmail.com
 * 18 de jun de 2019
 *
 */
public interface PacienteRepository extends JpaRepository<Paciente, Long> {

	@Query(nativeQuery = true, value = "SELECT AVG(idade) FROM tb_paciente;")
	double findMediaIdadeHomes();

	@Query(nativeQuery = true, value = "SELECT * FROM tb_paciente WHERE sexo = ':sexo' AND altura >= :alturaMin AND altura <= :alturaMax AND peso >= :pesoMin AND peso <= :pesoMax;")
	Optional<Paciente> findBySexo(String sexo, double alturaMin, double alturaMax, double pesoMin, double pesoMax);

	@Query(nativeQuery = true, value = "SELECT *, min(nascimento) FROM tb_paciente;")
	Paciente findByIdadeMax();

	@Query(nativeQuery = true, value = "SELECT *, min(altura) FROM tb_paciente WHERE sexo = ':sexo';")
	Paciente findBySexoAndAlturaMin(String sexo);

	
	
}
