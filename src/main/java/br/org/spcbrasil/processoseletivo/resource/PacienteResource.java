package br.org.spcbrasil.processoseletivo.resource;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.org.spcbrasil.processoseletivo.model.Paciente;
import br.org.spcbrasil.processoseletivo.repository.PacienteRepository;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/pacientes")
@CrossOrigin(origins="*")
public class PacienteResource {

	@Autowired
	private PacienteRepository pacienteRepository;
	
	@ApiOperation(value = "Retorna a lista de pacientes cadastrados")
	@GetMapping(produces = "application/json")
	public @ResponseBody Iterable<Paciente> listarPacientes(){
		Iterable<Paciente> listaPacientes = pacienteRepository.findAll();
		return listaPacientes;
	}
	
	@ApiOperation(value = "Retorna o paciente pelo ID informado na url")
	@GetMapping(value="/{id}", produces = "application/json")
	public Optional<Paciente> buscarPaciente(@PathVariable(value="id") Long id){
		Optional<Paciente> paciente = pacienteRepository.findById(id);
		return paciente;
	}

    @ApiOperation(value = "Retorna a quantidade de pacientes")
	@GetMapping(value="/total", produces = "application/json")
	public int totalPacientes(){
		int total = pacienteRepository.findAll().size();
		return total;
	}

    @ApiOperation(value = "Retorna média de idade dos homens")
	@GetMapping(value="/homens/mediaidade", produces = "application/json")
	public double buscarHomens(){
		double media = pacienteRepository.findMediaIdadeHomes();
		return media;
	}

    @ApiOperation(value = "Retorna quantidade de mulheres com altura entre 1,60 e 1,70 e peso acima de 70kg")
	@GetMapping(value="/mulheres", produces = "application/json")
	public Optional<Paciente> buscarMulheres(@RequestParam(value = "alturamin", required = false) double alturaMin,
        @RequestParam(value = "alturamax", required = false) double alturaMax,
        @RequestParam(value = "pesomin", required = false) double pesoMin,
        @RequestParam(value = "pesomax", required = false) double pesoMax){

        String sexo = "feminino";        

        Optional<Paciente> paciente = pacienteRepository.findBySexo(sexo, alturaMin, alturaMax, pesoMin, pesoMax);
		
        return paciente;
	}

    @ApiOperation(value = "Retorna o nome do paciente mais velho")
	@GetMapping(value="/maisvelho", produces = "application/json")
	public String maisVelho(){
		Paciente paciente = pacienteRepository.findByIdadeMax();
		return paciente.getNome();
	}

    @ApiOperation(value = "Retorna o nome da mulher mais baixa")
	@GetMapping(value="/mulherbaixa", produces = "application/json")
	public String mulherBaixa(){

        String sexo = "feminino";

		Paciente paciente = pacienteRepository.findBySexoAndAlturaMin(sexo);
		return paciente.getNome();
	}
	
	@ApiOperation(value = "Cadastrar novo paciente")
	@PostMapping
	public Paciente adicionarPaciente(@Valid @RequestBody Paciente paciente) {
		return pacienteRepository.save(paciente); 
	}

    @ApiOperation(value = "Atualizar cadastro do paciente por Id")
	@PutMapping(value = "/{id}")
	public ResponseEntity<Paciente> atualizarPaciente(@PathVariable Long id, @Valid @RequestBody Paciente paciente){
		Paciente pacienteExistente = pacienteRepository.getOne(id);
		
		if(pacienteExistente == null) {
			return ResponseEntity.notFound().build();
		}
		
		BeanUtils.copyProperties(paciente, pacienteExistente, "id");
		
		pacienteExistente = pacienteRepository.save(pacienteExistente);
		
		return ResponseEntity.ok(pacienteExistente);
	}
	
	@ApiOperation(value = "Remover Paciente por Id")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> removerPaciente(@PathVariable Long id){
		Paciente pacienteExistente = pacienteRepository.getOne(id);
		
		if(pacienteExistente == null) {
			return ResponseEntity.notFound().build();
		}
		
		pacienteRepository.delete(pacienteExistente);
		
		return ResponseEntity.noContent().build();
	}
	
}