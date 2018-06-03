package br.cefetrj.sisgee.view.termoestagio;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.cefetrj.sisgee.control.AgenteIntegracaoServices;
import br.cefetrj.sisgee.control.AlunoServices;
import br.cefetrj.sisgee.control.EmpresaServices;
import br.cefetrj.sisgee.control.ProfessorOrientadorServices;
import br.cefetrj.sisgee.model.entity.AgenteIntegracao;
import br.cefetrj.sisgee.model.entity.Aluno;
import br.cefetrj.sisgee.model.entity.Empresa;
import br.cefetrj.sisgee.model.entity.ProfessorOrientador;
import br.cefetrj.sisgee.model.entity.TermoEstagio;
import br.cefetrj.sisgee.view.utils.ServletUtils;
import br.cefetrj.sisgee.view.utils.UF;
import br.cefetrj.sisgee.view.utils.ValidaUtils;

/**
 * Servlet para tratar os dados da tela de cadastro de Termo
 * de Estágio.
 * 
 * @author Paulo Cantuária
 * @since 1.0
 *
 */
@WebServlet("/FormTermoEstagioServlet")
public class FormTermoEstagioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	/**
	 * Método doGet: carrega as listas necessárias para seleção dos atributos de relacionamento e redireciona para a tela de Registro de Termo de Estágio
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request = carregarListas(request); 
		
		request.getRequestDispatcher("/form_termo_estagio.jsp").forward(request, response);

	}
	
	/**
	 * Método doPost: Valida os campos da tela de Registro de Termo de Estágio. 
	 * Retorna para a tela caso não passe em alguma validação 
	 * ou encaminha para o servlet de inclusão de Termo.
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		Locale locale = ServletUtils.getLocale(request);
		ResourceBundle messages = ResourceBundle.getBundle("Messages", locale);
		
		String dataInicioTermoEstagio = request.getParameter("dataInicioTermoEstagio");
		String dataFimTermoEstagio = request.getParameter("dataFimTermoEstagio");		
		String cargaHorariaTermoEstagio = request.getParameter("cargaHorariaTermoEstagio");
		String valorBolsa = request.getParameter("valorBolsa");
		String enderecoTermoEstagio = request.getParameter("enderecoTermoEstagio");
		String numeroEnderecoTermoEstagio = request.getParameter("numeroEnderecoTermoEstagio");
		String complementoEnderecoTermoEstagio = request.getParameter("complementoEnderecoTermoEstagio");
		String bairroEnderecoTermoEstagio = request.getParameter("bairroEnderecoTermoEstagio");
		String cepEnderecoTermoEstagio = request.getParameter("cepEnderecoTermoEstagio");
		String cidadeEnderecoTermoEstagio = request.getParameter("cidadeEnderecoTermoEstagio");
		String estadoEnderecoTermoEstagio = request.getParameter("estadoEnderecoTermoEstagio");
		String eEstagioObrigatorio = request.getParameter("eEstagioObrigatorio");
		String idProfessorOrientador = request.getParameter("idProfessorOrientador");		
		String idAluno = request.getParameter("idAluno");
		String numeroConvenio = request.getParameter("numeroConvenio");
		String idEmpresa = request.getParameter("idEmpresa");
		String isAgenteIntegracao = request.getParameter("isAgenteIntegracao");
		String idAgenteIntegracao = request.getParameter("idAgenteIntegracao");
			
		boolean isValid = true;
		String msg = "";
		String campo = "";
		Integer tamanho = 0;		
		
		/**
		 * Validação da Data de início do estágio usando os métodos da Classe ValidaUtils
		 * Campo obrigatório
		 */		
		Date dataInicio = null;
		String dataInicioMsg = "";
		campo = "Data de Início";
		
		dataInicioMsg = ValidaUtils.validaObrigatorio(campo, dataInicioTermoEstagio);
		if (dataInicioMsg.trim().isEmpty()) {
			dataInicioMsg = ValidaUtils.validaDate(campo, dataInicioTermoEstagio);
			if (dataInicioMsg.trim().isEmpty()) {				
				try {
					SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
					dataInicio = format.parse(dataInicioTermoEstagio);
					request.setAttribute("dataInicio", dataInicio);
				} catch (Exception e) {
					//TODO trocar saída de console por Log
					System.out.println("Data em formato incorreto, mesmo após validação na classe ValidaUtils");
					isValid = false;
				}
			}else {
				dataInicioMsg = messages.getString(dataInicioMsg);
				request.setAttribute("dataInicioMsg", dataInicioMsg);
				isValid = false;
				//TODO Fazer log
				System.out.println(dataInicioMsg);
			}
		} else {
			dataInicioMsg = messages.getString(dataInicioMsg);
			request.setAttribute("dataInicioMsg", dataInicioMsg);
			isValid = false;
			//TODO Fazer log
			System.out.println(dataInicioMsg);
		}
		
		/**
		 * Validação da Data de fim do estágio usando os métodos da Classe ValidaUtils
		 */
		Date dataFim = null;
		campo = "Data de Término";
		Boolean hasDataFim = false;
		if (!dataFimTermoEstagio.trim().isEmpty()) {
			String dataFimMsg = "";
			dataFimMsg = ValidaUtils.validaDate(campo , dataFimTermoEstagio);
			if (dataFimMsg.trim().isEmpty()) {
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
				try {
					dataFim = format.parse(dataFimTermoEstagio);
					request.setAttribute("dataFim", dataFim);
					hasDataFim = true;
				} catch (Exception e) {
					//TODO trocar saída de console por Log
					System.out.println("Data em formato incorreto, mesmo após validação na classe ValidaUtils");
					isValid = false;
				}
			} else {
				dataFimMsg = messages.getString(dataFimMsg);
				request.setAttribute("dataFimMsg", dataFimMsg);
				isValid = false;
				//TODO Fazer log
				System.out.println(dataFimMsg);
			} 
		}
		request.setAttribute("hasDataFim", hasDataFim);
		
		
		/**
		 * Validação do período (entre o início e fim do estágio) usando o método validaDatas da Classe ValidaUtils
		 */
		String periodoMsg = "";
		if(!(dataFimTermoEstagio == null || dataFimTermoEstagio.isEmpty())) {
			periodoMsg = ValidaUtils.validaDatas(dataInicio, dataFim);
			if(!periodoMsg.trim().isEmpty()) {
				periodoMsg = messages.getString(ValidaUtils.validaDatas(dataInicio, dataFim));
				request.setAttribute("periodoMsg", periodoMsg);
				isValid = false;
				//TODO Fazer log
				System.out.println(periodoMsg);
			}	
		}	
		
		/**
		 * Validação da carga horária usando os métodos da Classe ValidaUtils
		 * Campo obrigatório e valor menor que 255 (No banco), valor menor que 24, por ser horas diárias.
		 */
		String cargaHorariaMsg = "";
		campo = "Horas por dia";
		tamanho = 6;		
		cargaHorariaMsg = ValidaUtils.validaObrigatorio(campo , cargaHorariaTermoEstagio);
		if (cargaHorariaMsg.trim().isEmpty()) {
			cargaHorariaMsg = ValidaUtils.validaInteger(campo, cargaHorariaTermoEstagio);
			if (cargaHorariaMsg.trim().isEmpty()) {
				Integer cargaHoraria = Integer.parseInt(cargaHorariaTermoEstagio);
				if (cargaHorariaMsg.trim().isEmpty()) {
					cargaHorariaMsg = ValidaUtils.validaTamanho(campo, tamanho, cargaHoraria);
					if (cargaHorariaMsg.trim().isEmpty()) {
					request.setAttribute("cargaHoraria", cargaHoraria);
					}else {
						cargaHorariaMsg = messages.getString(cargaHorariaMsg);
						cargaHorariaMsg = ServletUtils.mensagemFormatada(cargaHorariaMsg, locale, tamanho);
						request.setAttribute("cargaHorariaMsg", cargaHorariaMsg);
					}
				} else {
					cargaHorariaMsg = messages.getString(cargaHorariaMsg);
					request.setAttribute("cargaHorariaMsg", cargaHorariaMsg);
					isValid = false;
					//TODO Fazer log
					System.out.println(cargaHorariaMsg);
					
				}
			} else {
				cargaHorariaMsg = messages.getString(cargaHorariaMsg);
				request.setAttribute("cargaHorariaMsg", cargaHorariaMsg);
				isValid = false;
				//TODO Fazer log
				System.out.println(cargaHorariaMsg);
			}
		} else {
			cargaHorariaMsg = messages.getString(cargaHorariaMsg);
			request.setAttribute("cargaHorariaMsg", cargaHorariaMsg);
			isValid = false;
			//TODO Fazer log
			System.out.println(cargaHorariaMsg);
		}
				
		/**
		 * Validação do valor da bolsa usando os métodos da Classe ValidaUtils
		 * Campo obrigatório e valor float.
		 */
		String valorBolsaMsg = "";
		campo = "Valor";
		valorBolsaMsg = ValidaUtils.validaObrigatorio(campo, valorBolsa);
		if (valorBolsaMsg.trim().isEmpty()) {
                        valorBolsa = valorBolsa.replaceAll("[.|,]", "");
			valorBolsaMsg = ValidaUtils.validaFloat(campo, valorBolsa);
			if (valorBolsaMsg.trim().isEmpty()) {
				Float valor = Float.parseFloat(valorBolsa);
				request.setAttribute("valor", valor);
				/*NumberFormat nf = NumberFormat.getInstance(new Locale("pt", "BR"));
				Number n;
				float valor = 0;
				try {
					n = nf.parse(valorBolsa);
					valor = n.floatValue();
				} catch (ParseException e) {
				}*/				
			} else {
				valorBolsaMsg = messages.getString(valorBolsaMsg);
				request.setAttribute("valorBolsaMsg", valorBolsaMsg);
				isValid = false;
				//TODO Fazer log
				System.out.println(valorBolsaMsg);
			}
		} else {
			valorBolsaMsg = messages.getString(valorBolsaMsg);
			request.setAttribute("valorBolsaMsg", valorBolsaMsg);
			isValid = false;
			//TODO Fazer log
			System.out.println(valorBolsaMsg);
		}			
		
		/**
		 * Validação do endereço do TermoEstagio usando métodos da Classe ValidaUtils.
		 * Campo obrigatório e tamanho máximo de 255 caracteres.
		 */
		String enderecoMsg = "";
		campo = "Endereço";
		tamanho = 255;
		enderecoMsg = ValidaUtils.validaObrigatorio(campo, enderecoTermoEstagio);
		if(enderecoMsg.trim().isEmpty()) {
			enderecoMsg = ValidaUtils.validaTamanho(campo, tamanho, enderecoTermoEstagio);
			if(enderecoMsg.trim().isEmpty()) {
				request.setAttribute("enderecoTermoEstagio", enderecoTermoEstagio);
			}else {
				enderecoMsg = messages.getString(enderecoMsg);
				enderecoMsg = ServletUtils.mensagemFormatada(enderecoMsg, locale, tamanho);
				request.setAttribute("enderecoMsg", enderecoMsg);
				isValid = false;
				//TODO Fazer log
				System.out.println(enderecoMsg);
			}
		}else {
			enderecoMsg = messages.getString(enderecoMsg);
			request.setAttribute("enderecoMsg", enderecoMsg);
			isValid = false;
			//TODO Fazer log
			System.out.println(enderecoMsg);
		}
		
		
		/**
		 * Validação do número do endereço do TermoEstagio usando os métodos da Classe ValidaUtils.
		 * Campo obrigatório e tamanho máximo de 10 caracteres.
		 */
		String numeroEnderecoMsg = "";
		campo = "Número";
		tamanho = 10;
		numeroEnderecoMsg = ValidaUtils.validaObrigatorio(campo , numeroEnderecoTermoEstagio);
		if(numeroEnderecoMsg.trim().isEmpty()) {
			numeroEnderecoMsg = ValidaUtils.validaTamanho(campo, tamanho, numeroEnderecoTermoEstagio);
			if(numeroEnderecoMsg.trim().isEmpty()) {
				request.setAttribute("numeroEnderecoTermoEstagio", numeroEnderecoTermoEstagio);
			}else {				
				numeroEnderecoMsg = messages.getString(numeroEnderecoMsg);
				numeroEnderecoMsg = ServletUtils.mensagemFormatada(numeroEnderecoMsg, locale, tamanho);
				request.setAttribute("numeroEnderecoMsg", numeroEnderecoMsg);
				isValid = false;
				//TODO Fazer log
				System.out.println(numeroEnderecoMsg);
			}
		}else {
			numeroEnderecoMsg = messages.getString(numeroEnderecoMsg);
			request.setAttribute("numeroEnderecoMsg", numeroEnderecoMsg);
			isValid = false;
			//TODO Fazer log
			System.out.println(numeroEnderecoMsg);
		}		
		
		/**
		 * Validação do complemento do endereço do TermoEstagio usando os métodos da Classe ValidaUtils.
		 * Campo obrigatório e tamanho máximo de 150 caracteres.
		 */		
		String complementoEnderecoMsg = "";
		campo = "Complemento";
		tamanho = 150;
		complementoEnderecoMsg = ValidaUtils.validaObrigatorio(campo, complementoEnderecoTermoEstagio);
		if(complementoEnderecoMsg.trim().isEmpty()) {
			complementoEnderecoMsg = ValidaUtils.validaTamanho(campo, tamanho, complementoEnderecoTermoEstagio);
			if(complementoEnderecoMsg.trim().isEmpty()) {
				request.setAttribute("complementoEnderecoTermoEstagio", complementoEnderecoTermoEstagio);
				System.out.println("Tamanho complemento válido");
			}else {				
				complementoEnderecoMsg = messages.getString(complementoEnderecoMsg);
				complementoEnderecoMsg = ServletUtils.mensagemFormatada(complementoEnderecoMsg, locale, tamanho);
				request.setAttribute("complementoEnderecoMsg", complementoEnderecoMsg);
				isValid = false;
				//TODO Fazer log
				System.out.println(complementoEnderecoMsg);
				System.out.println("Tamanho complemento inválido");
			}
		}else {
			complementoEnderecoMsg = messages.getString(complementoEnderecoMsg);
			request.setAttribute("complementoEnderecoMsg", complementoEnderecoMsg);
			isValid = false;
			//TODO Fazer log
			System.out.println(complementoEnderecoMsg);
		}		
		
		/**
		 * Validação do bairro do endereço do TermoEstagio usando métodos da Classe ValidaUtils.
		 * Campo obrigatório e tamanho máximo de 150 caracteres.
		 */
		String bairroEnderecoMsg = "";
		campo = "Bairro";
		tamanho = 150;
		bairroEnderecoMsg = ValidaUtils.validaObrigatorio(campo, bairroEnderecoTermoEstagio);
		if(bairroEnderecoMsg.trim().isEmpty()) {
			bairroEnderecoMsg = ValidaUtils.validaTamanho(campo, tamanho, bairroEnderecoTermoEstagio);
			if(bairroEnderecoMsg.trim().isEmpty()) {
				request.setAttribute("bairroEnderecoTermoEstagio", bairroEnderecoTermoEstagio);
			}else {				
				bairroEnderecoMsg = messages.getString(bairroEnderecoMsg);
				bairroEnderecoMsg = ServletUtils.mensagemFormatada(bairroEnderecoMsg, locale, tamanho);
				request.setAttribute("bairroEnderecoMsg", bairroEnderecoMsg);
				isValid = false;
				//TODO Fazer log
				System.out.println(bairroEnderecoMsg);
			}
		}else {
			bairroEnderecoMsg = messages.getString(bairroEnderecoMsg);
			request.setAttribute("bairroEnderecoMsg", bairroEnderecoMsg);
			isValid = false;
			//TODO Fazer log
			System.out.println(bairroEnderecoMsg);
		}			
				
		/**
		 * Validação do cep do endereço do TermoEstagio usando métodos da Classe ValidaUtils.
		 * Campo obrigatório e tamanho máximo de 15 caracteres.
		 */
		String cepEnderecoMsg = "";	
		campo = "CEP";
		tamanho = 15;
		cepEnderecoMsg = ValidaUtils.validaObrigatorio(campo, cepEnderecoTermoEstagio);
		if(cepEnderecoMsg.trim().isEmpty()) {
			cepEnderecoMsg = ValidaUtils.validaTamanho(campo, tamanho, cepEnderecoTermoEstagio);
			if(bairroEnderecoMsg.trim().isEmpty()) {
				request.setAttribute("cepEnderecoTermoEstagio", cepEnderecoTermoEstagio);
			}else {				
				cepEnderecoMsg = messages.getString(cepEnderecoMsg);	
				cepEnderecoMsg = ServletUtils.mensagemFormatada(bairroEnderecoMsg, locale, tamanho);
				request.setAttribute("cepEnderecoMsg", cepEnderecoMsg);
				isValid = false;
				//TODO Fazer log
				System.out.println(cepEnderecoMsg);
			}
		}else {
			cepEnderecoMsg = messages.getString(cepEnderecoMsg);
			request.setAttribute("cepEnderecoMsg", cepEnderecoMsg);
			isValid = false;
			//TODO Fazer log
			System.out.println(cepEnderecoMsg);
		}			
		
		
		/**
		 * Validação da Cidade do endereço do TermoEstagio, usando métodos da Classe ValidaUtils.
		 * Campo obrigatório e tamanho máximo de 150 caracteres.
		 */
		String cidadeEnderecoMsg = "";
		campo = "Cidade";
		tamanho = 150;
		cidadeEnderecoMsg = ValidaUtils.validaObrigatorio(campo, cidadeEnderecoTermoEstagio);
		if(cidadeEnderecoMsg.trim().isEmpty()) {
			cidadeEnderecoMsg = ValidaUtils.validaTamanho(campo, tamanho, cidadeEnderecoTermoEstagio);
			if(cidadeEnderecoMsg.trim().isEmpty()) {
				request.setAttribute("cidadeEnderecoTermoEstagio", cidadeEnderecoTermoEstagio);
			}else {
				cidadeEnderecoMsg = messages.getString(cidadeEnderecoMsg);	
				cidadeEnderecoMsg = ServletUtils.mensagemFormatada(cidadeEnderecoMsg, locale, tamanho);
				request.setAttribute("cidadeEnderecoMsg", cidadeEnderecoMsg);
				isValid = false;
				//TODO Fazer log
				System.out.println(cidadeEnderecoMsg);
			}
		}else {
			cidadeEnderecoMsg = messages.getString(cidadeEnderecoMsg);	
			request.setAttribute("cidadeEnderecoMsg", cidadeEnderecoMsg);
			isValid = false;
			//TODO Fazer log
			System.out.println(cidadeEnderecoMsg);
		}					
		
		/**
		 * Validação do Estado do endereço do TermoEstagio, usando métodos da Classe ValidaUtils.
		 * Campo obrigatório e contido na Enum de UFs.
		 */
		String estadoEnderecoMsg = "";
		campo = "Estado";
		estadoEnderecoMsg = ValidaUtils.validaObrigatorio(campo, estadoEnderecoTermoEstagio);
		if(estadoEnderecoMsg.trim().isEmpty()) {
			estadoEnderecoMsg = ValidaUtils.validaUf(campo, estadoEnderecoTermoEstagio);
			if(estadoEnderecoMsg.trim().isEmpty()) {
				request.setAttribute("estadoEnderecoTermoEstagio", estadoEnderecoTermoEstagio);
			}else {
				estadoEnderecoMsg = messages.getString(estadoEnderecoMsg);
				request.setAttribute("estadoEnderecoMsg", estadoEnderecoMsg);
				isValid = false;
				//TODO Fazer log
				System.out.println(estadoEnderecoMsg);
			}
		}else {			
			estadoEnderecoMsg = messages.getString(estadoEnderecoMsg);
			request.setAttribute("estadoEnderecoMsg", estadoEnderecoMsg);
			isValid = false;
			//TODO Fazer log
			System.out.println(estadoEnderecoMsg);
		}					
		
		/**
		 * Validação do campo Estágio Obrigatório, usando métodos da Classe ValidaUtils.
		 * Deve ser campo booleano
		 */
		String eEstagioObrigatorioMsg = "";
		campo = "Estágio obrigatório";
		eEstagioObrigatorioMsg = ValidaUtils.validaObrigatorio(campo, eEstagioObrigatorio);
		if(eEstagioObrigatorioMsg.trim().isEmpty()) {
			Boolean obrigatorio;
			if(eEstagioObrigatorio.equals("sim")) {				
				obrigatorio = true;
				request.setAttribute("obrigatorio", obrigatorio);
			} else if(eEstagioObrigatorio.equals("nao")) {
				obrigatorio = false;
				request.setAttribute("obrigatorio", obrigatorio);
			}else {
				eEstagioObrigatorioMsg = "Valor inválido";
				request.setAttribute("eEstagioObrigatorioMsg", eEstagioObrigatorioMsg);
				isValid = false;
				//TODO Fazer log
				System.out.println(eEstagioObrigatorioMsg);
			}
				
			}else {
				eEstagioObrigatorioMsg = messages.getString(eEstagioObrigatorioMsg);
				request.setAttribute("eEstagioObrigatorioMsg", eEstagioObrigatorioMsg);
				isValid = false;
				//TODO Fazer log
				System.out.println(eEstagioObrigatorioMsg);
			}			
		
				
		
		/**
		 * Validação do Id do Professor Orientador, usando métodos da Classe ValidaUtils.
		 * Consultando a lista de Professores para validar 
		 */
		String idProfessorMsg = "";
		campo = "Professor Orientador";
		Boolean hasProfessor = false;
		if (!(idProfessorOrientador.trim().isEmpty() || idProfessorOrientador == null)) {
			idProfessorMsg = ValidaUtils.validaInteger(campo, idProfessorOrientador);
			if (idProfessorMsg.trim().isEmpty()) {
				Integer idProfessor = Integer.parseInt(idProfessorOrientador);
				List<ProfessorOrientador> listaProfessores = ProfessorOrientadorServices.listarProfessorOrientador();
				if (listaProfessores != null) {
					if (listaProfessores.contains(new ProfessorOrientador(idProfessor))) {
						request.setAttribute("idProfessor", idProfessor);
						hasProfessor = true;
					} else {
						idProfessorMsg = messages.getString("br.cefetrj.sisgee.form_termo_estagio_servlet.professor_invalido");
						isValid = false;
						//TODO Fazer log
						System.out.println(idProfessorMsg);
					}
				} else {
					idProfessorMsg = messages.getString("br.cefetrj.sisgee.form_termo_estagio_servlet.lista_professores_vazia");
					isValid = false;
					//TODO Fazer log
					System.out.println(idProfessorMsg);
				}
			} else {
				idProfessorMsg = messages.getString(idProfessorMsg);
				request.setAttribute("idProfessorMsg", idProfessorMsg);
				isValid = false;
				//TODO Fazer log
				System.out.println(idProfessorMsg);
			}
		}
		request.setAttribute("hasProfessor", hasProfessor);
		
		/**
		 * Validação do Id do Aluno, usando métodos da Classe ValidaUtils.
		 * Valor obrigatório e Integer
		 */
		Boolean alunoExiste = false;
		String idAlunoMsg = "";
		campo = "Aluno";
		idAlunoMsg = ValidaUtils.validaObrigatorio(campo, idAluno);
		if (idAlunoMsg.trim().isEmpty()) {
			idAlunoMsg = ValidaUtils.validaInteger(campo, idAluno);
			if (idAlunoMsg.trim().isEmpty()) {
				Integer idAlunoInt = Integer.parseInt(idAluno);
				List<Aluno> listaAlunos = AlunoServices.listarAlunos();
				if (listaAlunos != null) {
					if (listaAlunos.contains(new Aluno(idAlunoInt))) {
						request.setAttribute("idAluno", idAlunoInt);
						alunoExiste = true;
					} else {
						idAlunoMsg = messages.getString("br.cefetrj.sisgee.form_termo_estagio_servlet.aluno_invalido");
						isValid = false;
						//TODO Fazer log
						System.out.println(idAlunoMsg);
					}
				} else {
					idAlunoMsg = messages.getString("br.cefetrj.sisgee.form_termo_estagio_servlet.lista_alunos_vazia");
					isValid = false;
					//TODO Fazer log
					System.out.println(idAlunoMsg);
				}

			} else {
				idAlunoMsg = messages.getString(idAlunoMsg);
				request.setAttribute("idAlunoMsg", idAlunoMsg);
				isValid = false;
				//TODO Fazer log
				System.out.println(idAlunoMsg);
			}
		} else {
			idAlunoMsg = messages.getString(idAlunoMsg);
			request.setAttribute("idAlunoMsg", idAlunoMsg);
			isValid = false;
			//TODO Fazer log
			System.out.println(idAlunoMsg);
		}
		
		/**
		 * Validação do Nº de Convênio
		 * Campo obrigatório, tamanho máximo 10
		 * 
		 */
		String numeroConvenioMsg = "";
		campo = "Número do convênio";
		tamanho = 10;
		numeroConvenioMsg = ValidaUtils.validaObrigatorio(campo, numeroConvenio);
		if (numeroConvenioMsg.trim().isEmpty()) {
			numeroConvenioMsg = ValidaUtils.validaTamanho(campo, tamanho, numeroConvenio);
			if (numeroConvenioMsg.trim().isEmpty()) {
				//convenio = ConvenioServices.buscarConvenioByNumero(numeroConvenio);
				request.setAttribute("numeroConvenio", numeroConvenio);
			} else {
				numeroConvenioMsg = messages.getString(numeroConvenioMsg);
				numeroConvenioMsg = ServletUtils.mensagemFormatada(numeroConvenioMsg, locale, tamanho);
				request.setAttribute("numeroConvenioMsg", numeroConvenioMsg);
				isValid = false;
				//TODO Fazer log
				System.out.println(numeroConvenioMsg);
			}
		} else {
			numeroConvenioMsg = messages.getString(numeroConvenioMsg);
			request.setAttribute("numeroConvenioMsg", numeroConvenioMsg);
			isValid = false;
			//TODO Fazer log
			System.out.println(numeroConvenioMsg);
		}	
		
		/**
		 * Validação do idEmpresa campo obrigatório, inteiro e já existente no banco
		 */
		Empresa empresa = null;
		String empresaMsg = "";
		campo = "Empresa";
		Integer idEmp;		
		empresaMsg = ValidaUtils.validaObrigatorio(campo, idEmpresa);
		if (empresaMsg.trim().isEmpty()) {
			empresaMsg = ValidaUtils.validaInteger(campo, idEmpresa);
			if (empresaMsg.trim().isEmpty()) {
				idEmp = Integer.parseInt(idEmpresa);
				empresa = EmpresaServices.buscarEmpresa(idEmp);
				if(empresa != null) {
					request.setAttribute("idEmp", idEmp);
				}else {
					empresaMsg = messages.getString("br.cefetrj.sisgee.form_termo_estagio_servlet.empresa_invalida");
					request.setAttribute("empresaMsg", empresaMsg);
					isValid = false;
				}
			} else {
				empresaMsg = messages.getString(empresaMsg);
				request.setAttribute("empresaMsg", empresaMsg);
				isValid = false;
			}
		} else {
			empresaMsg = messages.getString(empresaMsg);
			request.setAttribute("empresaMsg", empresaMsg);
			isValid = false;
		}
		
		
		/**
		 * Validação do idAgenteIntegração campo obrigatório se usuário selecionou 
		 * que Empresa Conveniada é agente de integração, inteiro e já existente no banco
		 */
		request.setAttribute("isAgenteIntegracao", isAgenteIntegracao);
		String isAgenteIntegracaoMsg = "";
		campo = "É agente de integração";
		isAgenteIntegracaoMsg = ValidaUtils.validaObrigatorio(campo, isAgenteIntegracao);
		if (isAgenteIntegracaoMsg.trim().isEmpty()) {		
			if(isAgenteIntegracao.equals("sim")) {
				AgenteIntegracao agenteIntegracao = null;
				String agenteIntegracaoMsg = "";
				campo = "Agente de Integração";
				Integer idAI;
				agenteIntegracaoMsg = ValidaUtils.validaObrigatorio(campo, idAgenteIntegracao);
				if (agenteIntegracaoMsg.trim().isEmpty()) {
					agenteIntegracaoMsg = ValidaUtils.validaInteger(campo, idAgenteIntegracao);
					if (agenteIntegracaoMsg.trim().isEmpty()) {
						idAI = Integer.parseInt(idAgenteIntegracao);
						agenteIntegracao = AgenteIntegracaoServices.buscarAgenteIntegracao(idAI);
						if(agenteIntegracao != null) {
							request.setAttribute("idAI", idAI);
							request.setAttribute(isAgenteIntegracao, isAgenteIntegracao);
						}else {
							agenteIntegracaoMsg = messages.getString("br.cefetrj.sisgee.form_termo_estagio_servlet.agente_integracao_invalido");
							request.setAttribute("agenteIntegracaoMsg", agenteIntegracaoMsg);
							isValid = false;
						}
					} else {
						agenteIntegracaoMsg = messages.getString(agenteIntegracaoMsg);
						request.setAttribute("agenteIntegracaoMsg", agenteIntegracaoMsg);
						isValid = false;
					}
				} else {
					agenteIntegracaoMsg = messages.getString(agenteIntegracaoMsg);
					request.setAttribute("agenteIntegracaoMsg", agenteIntegracaoMsg);
					isValid = false;
				}
			}
		
		}
		else {
			isAgenteIntegracaoMsg = messages.getString(isAgenteIntegracaoMsg);
			System.out.println("msg AI: " + isAgenteIntegracaoMsg);
			request.setAttribute("isAgenteIntegracaoMsg", isAgenteIntegracaoMsg);
			isValid = false;
		}
		
		
		/**
		 * *************************************************************************
		 * Se aluno já possui estágio aberto, não pode cadastrar outro
		 * *************************************************************************
		 */
		if(alunoExiste) {
			Integer idAlunoInt = Integer.parseInt(idAluno);
			Aluno a = new Aluno(idAlunoInt);
			Aluno aluno = AlunoServices.buscarAluno(a);
			Boolean hasTermoAberto = false;
			
			List<TermoEstagio> termosEstagio = aluno.getTermoEstagios();	
			for (TermoEstagio t : termosEstagio) {				
				if(t.getDataRescisaoTermoEstagio() == null) {
					hasTermoAberto = true;
					break;
				}
			}
			
			if(hasTermoAberto) {
				String msg2 = messages.getString("br.cefetrj.sisgee.form_termo_estagio_servlet.msg_aluno_has_termo_aberto");
				request.setAttribute("msg2", msg2);
				isValid = false;
			}
		}
		
		
		
		/**
		 * Teste da variável booleana após validação. Redirecionamento para a inclusão
		 * ou devolver para o formulário com as mensagens.
		 */
		if (isValid) {
			request.getRequestDispatcher("/IncluirTermoEstagioServlet").forward(request, response);
		} else {
			msg = messages.getString("br.cefetrj.sisgee.form_termo_estagio_servlet.msg_atencao");
			request.setAttribute("msg", msg);
			request = carregarListas(request);

			request.getRequestDispatcher("/form_termo_estagio.jsp").forward(request, response);
		}
	}	
	
	private static HttpServletRequest carregarListas(HttpServletRequest request) {
		
		List<AgenteIntegracao> agentesIntegracao = AgenteIntegracaoServices.listarAgenteIntegracao();
		List<Empresa> empresas = EmpresaServices.listarEmpresas();
		List<Aluno> alunos = AlunoServices.listarAlunos();
		List<ProfessorOrientador> professores = ProfessorOrientadorServices.listarProfessorOrientador();
		UF[] uf = UF.asList();
		
		request.setAttribute("agentesIntegracao", agentesIntegracao);
		request.setAttribute("empresas", empresas);
		request.setAttribute("alunos", alunos);
		request.setAttribute("professores", professores);
		request.setAttribute("uf", uf);
		
		return request;		
	}	
}
