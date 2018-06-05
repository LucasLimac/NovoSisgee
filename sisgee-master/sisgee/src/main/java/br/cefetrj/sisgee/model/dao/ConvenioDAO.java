package br.cefetrj.sisgee.model.dao;

import br.cefetrj.sisgee.model.entity.Convenio;
import br.cefetrj.sisgee.model.entity.Empresa;
import br.cefetrj.sisgee.model.entity.Pessoa;
import java.util.List;

public class ConvenioDAO extends GenericDAO<Convenio> {
	
	public ConvenioDAO() {
		super(Convenio.class, PersistenceManager.getEntityManager());
	}
	
	public Convenio buscarByNumeroEmpresa(String numero, Empresa emp){
		return (Convenio) manager.createQuery(
		    "SELECT c FROM Convenio c WHERE c.numeroConvenio LIKE :numero AND c.empresa = :empresa")
		    .setParameter("numero", numero)
		    .setParameter("empresa", emp)
		    .getSingleResult();
	}
        
        
        public Convenio buscarByNumero(String numeroConvenio){
            System.out.println("ENTROU BUSCAR NUMERO CONVENIO DAO");
		return (Convenio) manager.createQuery(
		    "SELECT a FROM Convenio a WHERE a.numero LIKE :numeroConvenio")
		    .setParameter("numeroConvenio", numeroConvenio)
		    .getSingleResult();
	}
        
        
        public Convenio buscarByEmpresa(Empresa emp){
		return (Convenio) manager.createQuery(
		    "SELECT a FROM Convenio a WHERE a.empresa = :emp")
		    .setParameter("emp", emp)
		    .getSingleResult();
	}
        
        public Convenio buscarByPessoa(Pessoa pess){
		return (Convenio) manager.createQuery(
		    "SELECT a FROM Convenio a WHERE a.pessoa = :pess")
		    .setParameter("pess", pess)
		    .getSingleResult();
	}
}
