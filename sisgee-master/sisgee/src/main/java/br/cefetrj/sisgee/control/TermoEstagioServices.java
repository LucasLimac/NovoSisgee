package br.cefetrj.sisgee.control;

import java.util.Date;
import java.util.List;

import br.cefetrj.sisgee.model.dao.GenericDAO;
import br.cefetrj.sisgee.model.dao.PersistenceManager;
import br.cefetrj.sisgee.model.dao.TermoEstagioDAO;
import br.cefetrj.sisgee.model.entity.AgenteIntegracao;
import br.cefetrj.sisgee.model.entity.Aluno;
import br.cefetrj.sisgee.model.entity.Convenio;
import br.cefetrj.sisgee.model.entity.Empresa;
import br.cefetrj.sisgee.model.entity.TermoEstagio;

/**
 * Serviços de TermoEstagio. 
 * Trata a lógica de negócios
 * associada com a entidade TermoEstagio.
 * 
 * @author Paulo Cantuária, Augusto Jose
 * @since 1.0
 */
public class TermoEstagioServices {
	
	/**
	 * Recupera todos os Termos de Estágio e retorna em uma lista.
	 * 
	 * @return lista com todos os Termos de Estágio
	 */
	public static List<TermoEstagio> listarTermoEstagio(){
		GenericDAO<TermoEstagio> termoEstagioDao = PersistenceManager.createGenericDAO(TermoEstagio.class);
		return termoEstagioDao.buscarTodos();
	}	
	
	public static TermoEstagio buscarTermoEstagio(Integer idTermoEstagio) {
		GenericDAO<TermoEstagio> termoEstagioDao = PersistenceManager.createGenericDAO(TermoEstagio.class);
		return termoEstagioDao.buscar(idTermoEstagio);
	}
	
	/**
	 * 
	 * Metodo para receber uma matriz de com conteudo do banco
	 * @author Marcos Eduardo
	 * @param  obrigatorio boolean do form para filtrar resultado
	 * @param  inicio date do form para filtrar resultado
	 * @param  termino date do form para filtrar resultado
	 * @return author matriz com conteúdo obtido do banco ou null
	 */

	public static List<Object[]> listarTermoEstagioFiltrado(Boolean obrigatorio, Date inicio, Date termino){
		TermoEstagioDAO termoEstagioDAO = new TermoEstagioDAO();
		
		try{
			List<Object[]> author = null;
			
			if(obrigatorio == null) {
				author = termoEstagioDAO.buscarFiltrado( inicio, termino);
			}else {
				author = termoEstagioDAO.buscarFiltrado(obrigatorio , inicio, termino);
			}
			return author;
		}catch(Exception e){
			return null;
		}
	}
	
	public static void incluirTermoEstagio(TermoEstagio termoEstagio, Empresa empresa, AgenteIntegracao agenteIntegracao){
		
		/**
		 * Lógica de negócio
		 * 
		 * É Agente de Integração?
		 * 		Empresa já está ligada ao Agente de Integração?
		 * 			NÃO - Atualizar Empresa.idAgenteIntegracao
		 * 
		 * Convênio já existe para a Empresa selecionada?
		 * 		SIM - Encapsular em termo estagio
		 * 		NÃO - Criar novo convênio e encapsular
		 * 
		 * Registrar termo
		 * 
		 */

		PersistenceManager.getTransaction().begin();
		try{
			
			GenericDAO<Empresa> empresaDao = PersistenceManager.createGenericDAO(Empresa.class);
			Empresa emp = empresaDao.buscar(empresa.getIdEmpresa());
			
			// É Agente de Integração?
			if(agenteIntegracao != null) {
				
				// Empresa já está ligada ao Agente de Integração?
				Boolean atualizarAI = true;
				
				//if(emp.getAgenteIntegracao() != null) {
				//	if(emp.getAgenteIntegracao().getIdAgenteIntegracao() == agenteIntegracao.getIdAgenteIntegracao()) {
				//		atualizarAI = false;
				//	}
				//}
				
				// NÃO - Atualizar Empresa.idAgenteIntegracao
				if(atualizarAI) {
					GenericDAO<AgenteIntegracao> agenteIntegracaoDao = PersistenceManager.createGenericDAO(AgenteIntegracao.class);
					AgenteIntegracao ai = agenteIntegracaoDao.buscar(agenteIntegracao.getIdAgenteIntegracao());
					//emp.setAgenteIntegracao(ai);
					empresaDao.alterar(emp);
				}
			}
			
			// Convênio já existe para a Empresa selecionada?
			Convenio conv = ConvenioServices.buscarConvenioByNumeroEmpresa(termoEstagio.getConvenio().getNumeroConvenio(), emp);
			if(conv != null) {
				// SIM - Encapsular em termo estagio
				termoEstagio.setConvenio(conv);
			}
			else {
				// NÃO - Criar novo convênio e encapsular
				GenericDAO<Convenio> convenioDao = PersistenceManager.createGenericDAO(Convenio.class);
				conv = termoEstagio.getConvenio();
				conv.setEmpresa(emp);
				convenioDao.incluir(conv);
				conv = ConvenioServices.buscarConvenioByNumeroEmpresa(termoEstagio.getConvenio().getNumeroConvenio(), emp);
				System.out.println("Dados conv: " + conv.getIdConvenio() + ", " + conv.getNumeroConvenio() + ", " + conv.getEmpresa().getRazaoSocial());
				termoEstagio.setConvenio(conv);
			}
			
			// encapsula aluno
			GenericDAO<Aluno> alunoDao = PersistenceManager.createGenericDAO(Aluno.class);
			Aluno al = alunoDao.buscar(termoEstagio.getAluno().getIdAluno());
			termoEstagio.setAluno(al);
			
			System.out.println("valor estagioObrigatorio: " + termoEstagio.getEEstagioObrigatorio());
			
			GenericDAO<TermoEstagio> termoEstagioDao = PersistenceManager.createGenericDAO(TermoEstagio.class);		
			termoEstagioDao.incluir(termoEstagio);
			
			PersistenceManager.getTransaction().commit();
		}catch(Exception e){
			//TODO remover saída do console
			System.out.println(e);
			e.printStackTrace();
			PersistenceManager.getTransaction().rollback();
		}

	}
	
	public static void alterarTermoEstagio(TermoEstagio termoEstagio) {
		
		GenericDAO<TermoEstagio> termoEstagioDao = PersistenceManager.createGenericDAO(TermoEstagio.class);		
		
		try {
			PersistenceManager.getTransaction().begin();
			termoEstagioDao.alterar(termoEstagio);
			PersistenceManager.getTransaction().commit();
		} catch (Exception e) {			
			e.printStackTrace();
			PersistenceManager.getTransaction().rollback();
		}
	}
}
