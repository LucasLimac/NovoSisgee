package br.cefetrj.sisgee.model.dao;

import br.cefetrj.sisgee.model.entity.AgenteIntegracao;

public class AgenteIntegracaoDAO extends GenericDAO<AgenteIntegracao> {

	public AgenteIntegracaoDAO() {
		super(AgenteIntegracao.class, PersistenceManager.getEntityManager());
	}
	
	public AgenteIntegracao buscarByCnpj(String cnpj){
		return (AgenteIntegracao) manager.createQuery(
		   "SELECT e FROM AgenteIntegracao e WHERE e.cnpjAgenteIntegracao LIKE :cnpj")
		   .setParameter("cnpj", cnpj)
		   .getSingleResult();
	}
	
	public AgenteIntegracao buscarByNome(String nome){
		return (AgenteIntegracao) manager.createQuery(
		   "SELECT e FROM AgenteIntegracao e WHERE e.nomeAgenteIntegracao LIKE :nome")
		   .setParameter("nome", nome)
		   .getSingleResult();
	}

}
