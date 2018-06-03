package br.cefetrj.sisgee.model.entity;

import java.io.Serializable;
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
import javax.persistence.OneToOne;

/**
 *
 * @author Lucas Lima
 * @since 2.0
 *
 */
@Entity
public class Convenio implements Serializable {

    @Id
    @GeneratedValue
    private Integer idConvenio;

    @Column(length = 10, nullable = false)
    private String numeroConvenio;
    
    @Column(length = 5, nullable = false)
    private String numero;
    
    @Column(length = 4, nullable = false)
    private String ano;

    @Column(nullable = false)
    private String dataAssinatura;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn()
    private Empresa empresa;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn()
    private Pessoa pessoa;

    @OneToMany(mappedBy = "convenio")
    private List<TermoEstagio> termoEstagios;

    public Convenio() {
    }

    public Convenio(String numeroConvenio) {
	this.numeroConvenio = numeroConvenio;
    }     
    
    public Convenio(String ano, String numero, String dataAssinatura, Empresa empresa) {
        this.ano=ano;
        this.numero=numero;
        this.dataAssinatura = dataAssinatura;
        this.empresa = empresa;
        this.pessoa=null;

    }

    public Convenio(String ano, String numero, String dataAssinatura, Pessoa pessoa) {
        this.ano=ano;
        this.numero=numero;
        this.dataAssinatura = dataAssinatura;
        this.pessoa = pessoa;
        this.empresa= null;

    }
    
    public String getDataFinal(){
        int x = Integer.parseInt(dataAssinatura.substring(6, dataAssinatura.length()));
        String y = String.valueOf(x + 5);
        String z = dataAssinatura.substring(0,5) + "/" +  y;

        return z;
    }
    

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getAno() {
        return ano;
    }

    public void setAno() {
        this.ano = this.dataAssinatura.substring(6, this.dataAssinatura.length());
    }

    
    public String getDataAssinatura() {
        return dataAssinatura;
    }

    public void setDataAssinatura(String dataAssinatura) {
        this.dataAssinatura = dataAssinatura;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public List<TermoEstagio> getTermoEstagios() {
        return termoEstagios;
    }

    public void setTermoEstagios(List<TermoEstagio> termoEstagios) {
        this.termoEstagios = termoEstagios;
    }

    public Integer getIdConvenio() {
        return idConvenio;
    }

    public void setIdConvenio(Integer idConvenio) {
        this.idConvenio = idConvenio;
    }

    public String getNumeroConvenio() {
        return numero+"/"+ano;
    }
    

    public void setNumeroConvenio() {
        this.numeroConvenio = numero+"/"+ano;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public List<TermoEstagio> getTermoEstagio() {
        return termoEstagios;
    }

    public void setTermoEstagio(List<TermoEstagio> termoEstagios) {
        this.termoEstagios = termoEstagios;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idConvenio == null) ? 0 : idConvenio.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Convenio other = (Convenio) obj;
        if (idConvenio == null) {
            if (other.idConvenio != null) {
                return false;
            }
        } else if (!idConvenio.equals(other.idConvenio)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return numeroConvenio;
    }

}
