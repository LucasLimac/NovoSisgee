package br.cefetrj.sisgee.model.dao;

import br.cefetrj.sisgee.model.entity.Empresa;
import java.util.List;

public class EmpresaDAO extends GenericDAO<Empresa> {

	public EmpresaDAO() {
		super(Empresa.class, PersistenceManager.getEntityManager());
	}
	
	public Empresa buscarByCnpj(String cnpj){
		return (Empresa) manager.createQuery(
		    "SELECT e FROM Empresa e WHERE e.cnpjEmpresa LIKE :cnpj")
		    .setParameter("cnpj", cnpj)
		    .getSingleResult();
	}
	
	public List<Empresa> buscarByNomeList(String nomeX){
		return (List<Empresa>) manager.createQuery(
		    "SELECT e FROM Empresa e WHERE LOWER (e.razaoSocial) LIKE LOWER (:nomeX)")
                    
		    .setParameter("nomeX", nomeX+"%")
		    .getResultList();
	}
        
        public Empresa buscarByNome(String nomeX){
		return (Empresa) manager.createQuery(
		    "SELECT e FROM Empresa e WHERE LOWER (e.razaoSocial) LIKE LOWER (:nomeX)")
                    
		    .setParameter("nomeX", nomeX)
		    .getSingleResult();
	}

}
