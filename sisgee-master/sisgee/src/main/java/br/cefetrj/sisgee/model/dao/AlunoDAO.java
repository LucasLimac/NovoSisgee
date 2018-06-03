package br.cefetrj.sisgee.model.dao;

import br.cefetrj.sisgee.model.entity.Aluno;

public class AlunoDAO extends GenericDAO<Aluno> {
	
	public AlunoDAO() {
		super(Aluno.class, PersistenceManager.getEntityManager());
	}
	
	public Aluno buscarByMatricula(String matricula){
		return (Aluno) manager.createQuery(
		    "SELECT a FROM Aluno a WHERE a.matricula LIKE :matricula")
		    .setParameter("matricula", matricula)
		    .getSingleResult();
	}

}
