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

import br.cefetrj.sisgee.control.EmpresaServices;
import br.cefetrj.sisgee.control.ConvenioServices;
import br.cefetrj.sisgee.control.PessoaServices;
import br.cefetrj.sisgee.model.entity.Convenio;
import br.cefetrj.sisgee.model.entity.Empresa;
import br.cefetrj.sisgee.model.entity.Pessoa;
import br.cefetrj.sisgee.view.utils.ServletUtils;
import br.cefetrj.sisgee.view.utils.ValidaUtils;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.servlet.http.HttpSession;
import javax.swing.text.MaskFormatter;

/**
 *
 * @author Usuario
 */
@WebServlet("/BuscaConvenioBotaoServlet")
public class BuscaConvenioBotaoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String numeroConvenio = request.getParameter("numeroConvenio");
        String nome = request.getParameter("nomeConvenio");
        String idConvenio = "";
        Empresa empresa = null;

        Empresa empresaNome = null;
        Pessoa pessoaNome = null;

        Locale locale = ServletUtils.getLocale(request);
        ResourceBundle messages = ResourceBundle.getBundle("Messages", locale);

        boolean isValid = false;
        Integer tamanho = 0;

        String agenteIntegracao = "NÃO";
        String CPF = "", CNPJ = "";

        Convenio convenio = null;
        List<Convenio> convenios = new ArrayList();
        List<Empresa> empresas = new ArrayList();
        List<Pessoa> pessoas = new ArrayList();

        /**
         * Buscar pelo numero do Convenio
         */
        if (numeroConvenio != null) {
            convenio = ConvenioServices.buscarConvenioByNumeroConvenio(numeroConvenio.trim());
            if (convenio != null) {
                empresaNome = convenio.getEmpresa();
                if (empresaNome != null) {
                    CNPJ = formatString(empresaNome.getCnpjEmpresa(), "##.###.###/####-##");
                    if (empresaNome.isAgenteIntegracao()) {
                        agenteIntegracao = "SIM";
                    }
                }

                pessoaNome = convenio.getPessoa();
                if (pessoaNome != null) {
                    pessoaNome.getNome();
                    CPF = formatString(pessoaNome.getCpf(), "###.###.###-##");
                }
            }
        }
        /**
         * Buscar pelo nome da Empresa/Pessoa
         */
        if (nome != null) {
            if (!nome.equals("")) {

                pessoas = PessoaServices.buscarPessoaByNomeList(nome.trim());

                empresas = EmpresaServices.buscarEmpresaByNomeList(nome.trim());

                if (pessoas != null) {
                    for (Pessoa x : pessoas) {
                        convenio = ConvenioServices.buscarConvenioByPessoa(x);
                        convenios.add(convenio);
                        break;
                    }
                }

                if (empresas != null) {
                    for (Empresa x : empresas) {
                        convenio = ConvenioServices.buscarConvenioByEmpresa(x);
                        convenios.add(convenio);
                        break;
                    }
                }

                if (convenios != null) {
                    empresaNome = convenios.get(0).getEmpresa();
                    if (empresaNome != null) {
                        CNPJ = formatString(empresaNome.getCnpjEmpresa(), "##.###.###/####-##");
                        if (empresaNome.isAgenteIntegracao()) {
                            agenteIntegracao = "SIM";
                        }
                    }

                    pessoaNome = convenios.get(0).getPessoa();
                    if (pessoaNome != null) {
                        pessoaNome.getNome();
                        CPF = formatString(pessoaNome.getCpf(), "###.###.###-##");
                    }
                }
            }
        }

        //JSON
        if (empresaNome != null) {
            JsonObject model = Json.createObjectBuilder()
                    .add("idConvenio", convenio.getNumero())
                    .add("razaoSocial", empresaNome.getRazaoSocial())
                    .add("tipoConvenio", "Pessoa Jurídica")
                    .add("isAgenteIntegracao", agenteIntegracao)
                    .add("cnpjEcpf", CNPJ)
                    .add("nomeEmpresaPessoa", empresaNome.getRazaoSocial())
                    .add("nomeConvenio", empresaNome.getRazaoSocial())
                    .add("numeroConvenio", convenio.getNumeroConvenio())
                    .build();

            StringWriter stWriter = new StringWriter();
            JsonWriter jsonWriter = Json.createWriter(stWriter);
            jsonWriter.writeObject(model);
            jsonWriter.close();
            String jsonData = stWriter.toString();

            response.setContentType("application/json");
            response.getWriter().print(jsonData);
        } else if (pessoaNome != null) {
            JsonObject model = Json.createObjectBuilder()
                    .add("idConvenio", convenio.getNumero())
                    .add("razaoSocial", pessoaNome.getNome())
                    .add("tipoConvenio", "Pessoa Física")
                    .add("isAgenteIntegracao", "NÃO")
                    .add("cnpjEcpf", CPF)
                    .add("nomeEmpresaPessoa", pessoaNome.getNome())
                    .add("nomeConvenio", pessoaNome.getNome())
                    .add("numeroConvenio", convenio.getNumeroConvenio())
                    .build();

            StringWriter stWriter = new StringWriter();
            JsonWriter jsonWriter = Json.createWriter(stWriter);
            jsonWriter.writeObject(model);
            jsonWriter.close();
            String jsonData = stWriter.toString();

            response.setContentType("application/json");
            response.getWriter().print(jsonData);
        } else {
            JsonObject model = Json.createObjectBuilder()
                    .add("razaoSocial", "")
                    .add("tipoConvenio", "")
                    .add("isAgenteIntegracao", "")
                    .add("nomeAgenciada", "")
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

    public static String formatString(String value, String pattern) {
        MaskFormatter mf;
        try {
            mf = new MaskFormatter(pattern);
            mf.setValueContainsLiteralCharacters(false);
            return mf.valueToString(value);
        } catch (ParseException ex) {
            return null;
        }
    }

}
