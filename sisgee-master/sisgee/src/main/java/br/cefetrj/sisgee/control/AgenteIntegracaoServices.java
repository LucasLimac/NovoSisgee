package br.cefetrj.sisgee.control;

import java.util.List;

import br.cefetrj.sisgee.model.dao.AgenteIntegracaoDAO;
import br.cefetrj.sisgee.model.dao.GenericDAO;
import br.cefetrj.sisgee.model.dao.PersistenceManager;
import br.cefetrj.sisgee.model.entity.AgenteIntegracao;

/**
 * Serviços de AgenteIntegracao. 
 * Trata a lógica de negócios
 * associada com a entidade AgenteIntegracao.
 * 
 * @author Paulo Cantuária
 * @since 1.0
 */
public class AgenteIntegracaoServices {
	
	/**
	* Recupera todas as empresas e retorna em uma lista.
	* 
	* @return lista com todas as empresas
	*/
	public static List<AgenteIntegracao> listarAgenteIntegracao(){
		GenericDAO<AgenteIntegracao> agenteIntegracaoDAO = PersistenceManager.createGenericDAO(AgenteIntegracao.class);
		return agenteIntegracaoDAO.buscarTodos();
	}	
	
	public static AgenteIntegracao buscarAgenteIntegracaoByCnpj(String cnpj) {
		AgenteIntegracaoDAO agenteIntegracaoDAO = new AgenteIntegracaoDAO();
		try{
			AgenteIntegracao e = agenteIntegracaoDAO.buscarByCnpj(cnpj);
			return e;
		}catch(Exception e){
			return null;
		}
		
	}
	
	public static AgenteIntegracao buscarAgenteIntegracaoByNome(String nome) {
		AgenteIntegracaoDAO agenteIntegracaoDAO = new AgenteIntegracaoDAO();
		try{
			AgenteIntegracao e = agenteIntegracaoDAO.buscarByNome(nome);
			return e;
		}catch(Exception e){
			return null;
		}
		
	}
	
	public static AgenteIntegracao buscarAgenteIntegracao(Integer idAgenteIntegracao) {
		AgenteIntegracaoDAO agenteIntegracaoDao = new AgenteIntegracaoDAO();
		try{
			AgenteIntegracao e = agenteIntegracaoDao.buscar(idAgenteIntegracao);
			return e;
		}catch(Exception e){
			return null;
		}
		
	}
	
	public static void incluirAgenteIntegracao(AgenteIntegracao agenteIntegracao){
		GenericDAO<AgenteIntegracao> agenteIntegracaoDao = PersistenceManager.createGenericDAO(AgenteIntegracao.class);	
		PersistenceManager.getTransaction().begin();
		try{
			agenteIntegracaoDao.incluir(agenteIntegracao);
			PersistenceManager.getTransaction().commit();
		}catch(Exception e){
			PersistenceManager.getTransaction().rollback();
		}
	}
}

