package br.cefetrj.sisgee.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * 
 * @author padu
 *
 */
@Entity
public class TermoAditivo {

	@Id
	@GeneratedValue
	private Integer idTermoAditivo;

	private Date dataFimTermoAditivo;

	@Column(nullable = false)
	private Integer cargaHorariaTermoAditivo;

	@Column(nullable = false)
	private Float valorBolsaTermoAditivo;

	@Column(length = 255, nullable = false)
	private String enderecoTermoAditivo;

	@Column(length = 10, nullable = false)
	private String numeroEnderecoTermoAditivo;

	@Column(length = 150, nullable = false)
	private String complementoEnderecoTermoAditivo;

	@Column(length = 150, nullable = false)
	private String bairroEnderecoTermoAditivo;

	@Column(length = 15, nullable = false)
	private String cepEnderecoTermoAditivo;

	@Column(length = 150, nullable = false)
	private String cidadeEnderecoTermoAditivo;

	@Column(length = 2, nullable = false)
	private String estadoEnderecoTermoAditivo;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	private TermoEstagio termoEstagio;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private ProfessorOrientador professorOrientador;
	
	public TermoAditivo() {}
	
	public TermoAditivo(Date dataFimTermoAditivo, Integer cargaHorariaTermoAditivo,
			Float valorBolsaTermoAditivo, String enderecoTermoAditivo, ProfessorOrientador professorOrientador) {
		
		this.dataFimTermoAditivo = dataFimTermoAditivo;
		this.cargaHorariaTermoAditivo = cargaHorariaTermoAditivo;
		this.valorBolsaTermoAditivo = valorBolsaTermoAditivo;
		this.enderecoTermoAditivo = enderecoTermoAditivo;
		this.professorOrientador = professorOrientador;
	}
	
	public ProfessorOrientador getProfessorOrientador() {
		return professorOrientador;
	}

	public void setProfessorOrientador(ProfessorOrientador professorOrientador) {
		this.professorOrientador = professorOrientador;
	}
	
	public Integer getIdTermoAditivo() {
		return idTermoAditivo;
	}

	public void setIdTermoAditivo(Integer idTermoAditivo) {
		this.idTermoAditivo = idTermoAditivo;
	}

	public Date getDataFimTermoAditivo() {
		return dataFimTermoAditivo;
	}

	public void setDataFimTermoAditivo(Date dataFimTermoAditivo) {
		this.dataFimTermoAditivo = dataFimTermoAditivo;
	}

	public Integer getCargaHorariaTermoAditivo() {
		return cargaHorariaTermoAditivo;
	}

	public void setCargaHorariaTermoAditivo(Integer cargaHorariaTermoAditivo) {
		this.cargaHorariaTermoAditivo = cargaHorariaTermoAditivo;
	}

	public Float getValorBolsaTermoAditivo() {
		return valorBolsaTermoAditivo;
	}

	public void setValorBolsaTermoAditivo(Float valorBolsaTermoAditivo) {
		this.valorBolsaTermoAditivo = valorBolsaTermoAditivo;
	}

	public String getEnderecoTermoAditivo() {
		return enderecoTermoAditivo;
	}

	public void setEnderecoTermoAditivo(String enderecoTermoAditivo) {
		this.enderecoTermoAditivo = enderecoTermoAditivo;
	}

	public String getNumeroEnderecoTermoAditivo() {
		return numeroEnderecoTermoAditivo;
	}

	public void setNumeroEnderecoTermoAditivo(String numeroEnderecoTermoAditivo) {
		this.numeroEnderecoTermoAditivo = numeroEnderecoTermoAditivo;
	}

	public String getComplementoEnderecoTermoAditivo() {
		return complementoEnderecoTermoAditivo;
	}

	public void setComplementoEnderecoTermoAditivo(String complementoEnderecoTermoAditivo) {
		this.complementoEnderecoTermoAditivo = complementoEnderecoTermoAditivo;
	}

	public String getBairroEnderecoTermoAditivo() {
		return bairroEnderecoTermoAditivo;
	}

	public void setBairroEnderecoTermoAditivo(String bairroEnderecoTermoAditivo) {
		this.bairroEnderecoTermoAditivo = bairroEnderecoTermoAditivo;
	}

	public String getCepEnderecoTermoAditivo() {
		return cepEnderecoTermoAditivo;
	}

	public void setCepEnderecoTermoAditivo(String cepEnderecoTermoAditivo) {
		this.cepEnderecoTermoAditivo = cepEnderecoTermoAditivo;
	}

	public String getCidadeEnderecoTermoAditivo() {
		return cidadeEnderecoTermoAditivo;
	}

	public void setCidadeEnderecoTermoAditivo(String cidadeEnderecoTermoAditivo) {
		this.cidadeEnderecoTermoAditivo = cidadeEnderecoTermoAditivo;
	}

	public String getEstadoEnderecoTermoAditivo() {
		return estadoEnderecoTermoAditivo;
	}

	public void setEstadoEnderecoTermoAditivo(String estadoEnderecoTermoAditivo) {
		this.estadoEnderecoTermoAditivo = estadoEnderecoTermoAditivo;
	}

	public TermoEstagio getTermoEstagio() {
		return termoEstagio;
	}

	public void setTermoEstagio(TermoEstagio termoEstagio) {
		this.termoEstagio = termoEstagio;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idTermoAditivo == null) ? 0 : idTermoAditivo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TermoAditivo other = (TermoAditivo) obj;
		if (idTermoAditivo == null) {
			if (other.idTermoAditivo != null)
				return false;
		} else if (!idTermoAditivo.equals(other.idTermoAditivo))
			return false;
		return true;
	}

}
