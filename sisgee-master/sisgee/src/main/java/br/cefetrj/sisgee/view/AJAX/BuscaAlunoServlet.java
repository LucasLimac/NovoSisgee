package br.cefetrj.sisgee.view.AJAX;

import java.io.IOException;
import java.io.StringWriter;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.cefetrj.sisgee.control.AlunoServices;
import br.cefetrj.sisgee.model.entity.Aluno;
import br.cefetrj.sisgee.model.entity.Campus;
import br.cefetrj.sisgee.model.entity.Curso;
import br.cefetrj.sisgee.model.entity.Pessoa;
import br.cefetrj.sisgee.model.entity.TermoEstagio;
import java.util.List;

/**
 * Servlet para trazer os dados do Aluno por meio de requisição AJAX.
 * 
 * @author Augusto Jose
 * @since 1.0
 *
 */
@WebServlet("/BuscaAlunoServlet")
public class BuscaAlunoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String matricula = request.getParameter("matricula");
		String idAluno = "";
		String nome = "";
		String nomeCurso = "";
		String nomeCampus = "";
                String idTermoEstagioAtivo = "";
                String tipoAluno = "";
		
		Aluno aluno = AlunoServices.buscarAlunoByMatricula(matricula.trim());
		if(aluno != null) {
			Pessoa pessoa = aluno.getPessoa();
			Curso curso   = aluno.getCurso();
			Campus campus = curso.getCampus();
			tipoAluno     = aluno.getTipoAluno();
                        
			idAluno = Integer.toString(aluno.getIdAluno());
			nome = pessoa.getNome();
			nomeCurso = curso.getNomeCurso();
			nomeCampus = campus.getNomeCampus();
                        List<TermoEstagio> termos = aluno.getTermoEstagios();
                        System.out.println("busca termo aluno ");                        
                        if(termos != null){
                            for (TermoEstagio termo : termos) {
                                if(termo.getDataFimTermoEstagio() == null){
                                    idTermoEstagioAtivo = 
                                           (termo.getIdTermoEstagio() != null ? 
                                            termo.getIdTermoEstagio().toString() : 
                                            "" );
                                    termo.getDataInicioTermoEstagio();
                                    termo.getConvenio().getEmpresa().getCnpjEmpresa();
                                    termo.getConvenio().getEmpresa().getRazaoSocial();
                                   
                                }
                            }
                        }
		}
		
		//JSON
		JsonObject model = Json.createObjectBuilder()
				.add("idAluno", idAluno)
				.add("nome", nome)
				.add("nomeCurso", nomeCurso)
				.add("nomeCampus", nomeCampus)
                                .add("idTermoEstagioAtivo", idTermoEstagioAtivo)
                                .add("tipoAluno", tipoAluno)
				.build();
		
		StringWriter stWriter = new StringWriter();
		JsonWriter jsonWriter = Json.createWriter(stWriter);
		jsonWriter.writeObject(model);
		jsonWriter.close();
		String jsonData = stWriter.toString();
		
		response.setContentType("application/json");
		response.getWriter().print(jsonData);
	}

}
