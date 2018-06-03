package br.cefetrj.sisgee.view.termoaditivo;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.cefetrj.sisgee.control.AlunoServices;
import br.cefetrj.sisgee.control.ProfessorOrientadorServices;
import br.cefetrj.sisgee.control.TermoAditivoServices;
import br.cefetrj.sisgee.model.entity.Aluno;
import br.cefetrj.sisgee.model.entity.ProfessorOrientador;
import br.cefetrj.sisgee.model.entity.TermoAditivo;
import br.cefetrj.sisgee.model.entity.TermoEstagio;
import br.cefetrj.sisgee.view.utils.ServletUtils;
import br.cefetrj.sisgee.view.utils.UF;
import br.cefetrj.sisgee.view.utils.ValidaUtils;

@WebServlet("/TermoAditivoServlet")
public class TermoAditivoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Locale locale = ServletUtils.getLocale(request);
		ResourceBundle messages = ResourceBundle.getBundle("Messages", locale);
		
		
		String vigencia = request.getParameter("vigencia");
		String endereco = request.getParameter("endereco");
		String carga = request.getParameter("cargaHoraria");
		String professor = request.getParameter("professor");
		String valorBolsa = request.getParameter("valor");
		
		String idAluno = request.getParameter("idAlunoAdt");
		
		
		Aluno aluno = null;
		TermoEstagio termoEstagio = null;
		List<TermoAditivo> termosAditivos = null;
		TermoAditivo termoAditivo = null;
		
		boolean isValid = true;
		String campo = "";
		String msg = "";
		
			
		/**
		 * Validação do Id do Aluno, usando métodos da Classe ValidaUtils.
		 * Instanciando o objeto e pegando o TermoEstagio válido (sem data de rescisão)
		 */
		String idAlunoMsg = "";
		campo = "Aluno";
		idAlunoMsg = ValidaUtils.validaObrigatorio(campo, idAluno);
		if (idAlunoMsg.trim().isEmpty()) {
			idAlunoMsg = ValidaUtils.validaInteger(campo, idAluno);
			if (idAlunoMsg.trim().isEmpty()) {				
				Integer idAlunoInt = Integer.parseInt(idAluno);
				aluno = AlunoServices.buscarAluno(new Aluno(idAlunoInt));
				if (aluno != null) {
					List<TermoEstagio> termosEstagio = aluno.getTermoEstagios();	
					for (TermoEstagio termoEstagio2 : termosEstagio) {
						
						if(termoEstagio2.getDataRescisaoTermoEstagio() == null) {
							termoEstagio = termoEstagio2;
							break;
						}
					}					
					
				} else {
					idAlunoMsg = messages.getString("br.cefetrj.sisgee.incluir_termo_aditivo_servlet.msg_AlunoEscolhido");
					request.setAttribute("idAlunoMsg", idAlunoMsg);
				}

			} else {
				idAlunoMsg = messages.getString(idAlunoMsg);
				request.setAttribute("idAlunoMsg", idAlunoMsg);
				isValid = false;
			}
		} else {
			idAlunoMsg = messages.getString(idAlunoMsg);
			request.setAttribute("idAlunoMsg", idAlunoMsg);
			isValid = false;
		}
		
		
		if(termoEstagio != null) {
			//TODO implementar lógica de encaminhamento para a tela de registro
			termosAditivos = termoEstagio.getTermosAditivos();
			if(termosAditivos != null && !termosAditivos.isEmpty()) {
				termoAditivo = termosAditivos.get(termosAditivos.size() - 1);
			}
			
			// se existe algum termo aditivo para o termo estagio
			if(termoAditivo != null) {
				termoEstagio = TermoAditivoServices.termoEstagioAtualizadoByTermoAditivo(termoAditivo);
			}
			
			List<ProfessorOrientador> professores = ProfessorOrientadorServices.listarProfessorOrientador();
			UF[] uf = UF.asList();
			
			request.setAttribute("termoEstagio", termoEstagio);
			//request.setAttribute("termoAditivo", termoAditivo);
			request.setAttribute("professores", professores);
			request.setAttribute("uf", uf);
			
			request.setAttribute("updVigencia", vigencia);
			request.setAttribute("updCargaHoraria", carga);
			request.setAttribute("updProfessor", professor);
			request.setAttribute("updValorBolsa", valorBolsa);
			request.setAttribute("updEndereco", endereco);
			
		}else {
			msg = messages.getString("br.cefetrj.sisgee.form_termo_aditivo_servlet.msg_termo_estagio_invalido");
			request.setAttribute("msg", msg);
			isValid = false;
		}
		
		if (isValid) {
			request.getRequestDispatcher("/form_termo_estagio.jsp").forward(request, response);
		}else {
			request.getRequestDispatcher("/form_termo_aditivo.jsp").forward(request, response);
		}

	}
}
