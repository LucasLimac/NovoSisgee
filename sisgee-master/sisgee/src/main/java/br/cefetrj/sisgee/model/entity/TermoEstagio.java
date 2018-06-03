package br.cefetrj.sisgee.model.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * 
 * @author padu
 * @since 1.0
 */
@Entity
public class TermoEstagio {

	@Id
	@GeneratedValue
	private Integer idTermoEstagio;

	@Column(nullable = false)
	private Date dataInicioTermoEstagio;

	private Date dataFimTermoEstagio;

	private Date dataRescisaoTermoEstagio;
		
	@Column(nullable = false)
	private Integer cargaHorariaTermoEstagio;

	@Column(nullable = false)
	private Float valorBolsa;

	@Column(length = 255, nullable = false)
	private String enderecoTermoEstagio;

	@Column(length = 10, nullable = false)
	private String numeroEnderecoTermoEstagio;

	@Column(length = 150, nullable = false)
	private String complementoEnderecoTermoEstagio;

	@Column(length = 150, nullable = false)
	private String bairroEnderecoTermoEstagio;

	@Column(length = 15, nullable = false)
	private String cepEnderecoTermoEstagio;

	@Column(length = 150, nullable = false)
	private String cidadeEnderecoTermoEstagio;

	@Column(length = 2, nullable = false)
	private String estadoEnderecoTermoEstagio;

	@Column(nullable = false)
	private Boolean eEstagioObrigatorio;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	private Aluno aluno;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	private Convenio convenio;

	@ManyToOne(fetch = FetchType.EAGER)
	private ProfessorOrientador professorOrientador;

	@OneToMany(mappedBy = "termoEstagio")
	private List<TermoAditivo> termosAditivos;

	public TermoEstagio() {}
	
	
	
	public TermoEstagio(Date dataInicioTermoEstagio, Date dataFimTermoEstagio, Integer cargaHorariaTermoEstagio,
			Float valorBolsa, String enderecoTermoEstagio, String numeroEnderecoTermoEstagio,
			String complementoEnderecoTermoEstagio, String bairroEnderecoTermoEstagio, String cepEnderecoTermoEstagio,
			String cidadeEnderecoTermoEstagio, String estadoEnderecoTermoEstagio, Boolean eEstagioObrigatorio,
			Aluno aluno, Convenio convenio, ProfessorOrientador professorOrientador) {
		
		this.dataInicioTermoEstagio = dataInicioTermoEstagio;
		this.dataFimTermoEstagio = dataFimTermoEstagio;
		this.cargaHorariaTermoEstagio = cargaHorariaTermoEstagio;
		this.valorBolsa = valorBolsa;
		this.enderecoTermoEstagio = enderecoTermoEstagio;
		this.numeroEnderecoTermoEstagio = numeroEnderecoTermoEstagio;
		this.complementoEnderecoTermoEstagio = complementoEnderecoTermoEstagio;
		this.bairroEnderecoTermoEstagio = bairroEnderecoTermoEstagio;
		this.cepEnderecoTermoEstagio = cepEnderecoTermoEstagio;
		this.cidadeEnderecoTermoEstagio = cidadeEnderecoTermoEstagio;
		this.estadoEnderecoTermoEstagio = estadoEnderecoTermoEstagio;
		this.eEstagioObrigatorio = eEstagioObrigatorio;
		this.aluno = aluno;
		this.convenio = convenio;
		this.professorOrientador = professorOrientador;
	}



	public Integer getIdTermoEstagio() {
		return idTermoEstagio;
	}

	public void setIdTermoEstagio(Integer idTermoEstagio) {
		this.idTermoEstagio = idTermoEstagio;
	}

	public Date getDataInicioTermoEstagio() {
		return dataInicioTermoEstagio;
	}

	public void setDataInicioTermoEstagio(Date dataInicioTermoEstagio) {
		this.dataInicioTermoEstagio = dataInicioTermoEstagio;
	}

	public Date getDataFimTermoEstagio() {
		return dataFimTermoEstagio;
	}

	public void setDataFimTermoEstagio(Date dataFimTermoEstagio) {
		this.dataFimTermoEstagio = dataFimTermoEstagio;
	}

	public Date getDataRescisaoTermoEstagio() {
		return dataRescisaoTermoEstagio;
	}

	public void setDataRescisaoTermoEstagio(Date dataRescisaoTermoEstagio) {
		this.dataRescisaoTermoEstagio = dataRescisaoTermoEstagio;
	}


	public Integer getCargaHorariaTermoEstagio() {
		return cargaHorariaTermoEstagio;
	}

	public void setCargaHorariaTermoEstagio(Integer cargaHorariaTermoEstagio) {
		this.cargaHorariaTermoEstagio = cargaHorariaTermoEstagio;
	}

	public Float getValorBolsa() {
		return valorBolsa;
	}

	public void setValorBolsa(Float valorBolsa) {
		this.valorBolsa = valorBolsa;
	}

	public String getEnderecoTermoEstagio() {
		return enderecoTermoEstagio;
	}

	public void setEnderecoTermoEstagio(String enderecoTermoEstagio) {
		this.enderecoTermoEstagio = enderecoTermoEstagio;
	}

	public String getNumeroEnderecoTermoEstagio() {
		return numeroEnderecoTermoEstagio;
	}

	public void setNumeroEnderecoTermoEstagio(String numeroEnderecoTermoEstagio) {
		this.numeroEnderecoTermoEstagio = numeroEnderecoTermoEstagio;
	}

	public String getComplementoEnderecoTermoEstagio() {
		return complementoEnderecoTermoEstagio;
	}

	public void setComplementoEnderecoTermoEstagio(String complementoEnderecoTermoEstagio) {
		this.complementoEnderecoTermoEstagio = complementoEnderecoTermoEstagio;
	}

	public String getBairroEnderecoTermoEstagio() {
		return bairroEnderecoTermoEstagio;
	}

	public void setBairroEnderecoTermoEstagio(String bairroEnderecoTermoEstagio) {
		this.bairroEnderecoTermoEstagio = bairroEnderecoTermoEstagio;
	}

	public String getCepEnderecoTermoEstagio() {
		return cepEnderecoTermoEstagio;
	}

	public void setCepEnderecoTermoEstagio(String cepEnderecoTermoEstagio) {
		this.cepEnderecoTermoEstagio = cepEnderecoTermoEstagio;
	}

	public String getCidadeEnderecoTermoEstagio() {
		return cidadeEnderecoTermoEstagio;
	}

	public void setCidadeEnderecoTermoEstagio(String cidadeEnderecoTermoEstagio) {
		this.cidadeEnderecoTermoEstagio = cidadeEnderecoTermoEstagio;
	}

	public String getEstadoEnderecoTermoEstagio() {
		return estadoEnderecoTermoEstagio;
	}

	public void setEstadoEnderecoTermoEstagio(String estadoEnderecoTermoEstagio) {
		this.estadoEnderecoTermoEstagio = estadoEnderecoTermoEstagio;
	}	

	public Boolean getEEstagioObrigatorio() {
		return eEstagioObrigatorio;
	}

	public void setEEstagioObrigatorio(Boolean eEstagioObrigatorio) {
		this.eEstagioObrigatorio = eEstagioObrigatorio;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public Convenio getConvenio() {
		return convenio;
	}

	public void setConvenio(Convenio convenio) {
		this.convenio = convenio;
	}

	public ProfessorOrientador getProfessorOrientador() {
		return professorOrientador;
	}

	public void setProfessorOrientador(ProfessorOrientador professorOrientador) {
		this.professorOrientador = professorOrientador;
	}

	public List<TermoAditivo> getTermosAditivos() {
		return termosAditivos;
	}

	public void setTermosAditivos(List<TermoAditivo> termosAditivos) {
		this.termosAditivos = termosAditivos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idTermoEstagio == null) ? 0 : idTermoEstagio.hashCode());
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
		TermoEstagio other = (TermoEstagio) obj;
		if (idTermoEstagio == null) {
			if (other.idTermoEstagio != null)
				return false;
		} else if (!idTermoEstagio.equals(other.idTermoEstagio))
			return false;
		return true;
	}
}
