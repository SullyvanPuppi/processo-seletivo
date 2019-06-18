package br.org.spcbrasil.processoseletivo.model;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_funcionario")
public class Funcionario extends Pessoa implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3901580258736391753L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cod_funcionario;
	
	private Date data_admissao;

	private double salario;

	public Long getCod_funcionario() {
		return cod_funcionario;
	}

	public void setCod_funcionario(Long cod_funcionario) {
		this.cod_funcionario = cod_funcionario;
	}

	public Date getData_admissao() {
		return data_admissao;
	}

	public void setData_admissao(Date data_admissao) {
		this.data_admissao = data_admissao;
	}

	public double getSalario() {
		return salario;
	}

	public void setSalario(double salario) {
		this.salario = salario;
	}
}
