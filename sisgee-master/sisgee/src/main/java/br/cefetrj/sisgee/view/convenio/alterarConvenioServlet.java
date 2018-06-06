/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cefetrj.sisgee.view.convenio;

import br.cefetrj.sisgee.control.ConvenioServices;
import br.cefetrj.sisgee.model.entity.Convenio;
import br.cefetrj.sisgee.view.utils.ServletUtils;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Lucas Lima
 */
public class alterarConvenioServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       
        Locale locale = ServletUtils.getLocale(request);
        ResourceBundle messages = ResourceBundle.getBundle("Messages", locale);
        
        String numero = (String)request.getSession().getAttribute("numero");
        
        String emailPessoa = request.getParameter("emailPessoa");
        String telefonePessoa = request.getParameter("telefonePessoa");
        
        Date dataAssinaturaConvenioEmpresa = (Date)request.getAttribute("dataAssinaturaConvenioEmpresa");
        Date dataAssinaturaConvenioPessoa = (Date)request.getAttribute("dataAssinaturaConvenioPessoa");
        
        String emailEmpresa = request.getParameter("emailEmpresa");
        String telefoneEmpresa = request.getParameter("telefoneEmpresa");
        String contatoEmpresa = request.getParameter("contatoEmpresa");

        Convenio convenio = ConvenioServices.buscarConvenioByNumeroConvenio(numero);
        if (convenio.getEmpresa() != null) {
            convenio.getEmpresa().setContatoEmpresa(contatoEmpresa);
            convenio.getEmpresa().setTelefoneEmpresa(telefoneEmpresa);
            convenio.getEmpresa().setEmailEmpresa(emailEmpresa);
            convenio.setDataAssinatura(dataAssinaturaConvenioEmpresa);
            
            convenio.setNumeroConvenio();
        } else {
            convenio.getPessoa().setTelefone(telefonePessoa);
            convenio.getPessoa().setEmail(emailPessoa);
            convenio.setDataAssinatura(dataAssinaturaConvenioPessoa);
            
            convenio.setNumeroConvenio();
        }

        ConvenioServices.alterarConvenio(convenio);
        String msg = messages.getString("br.cefetrj.sisgee.incluir_cadastro_empresa_servlet.msg_convenio_cadastrado");
                request.setAttribute("msg", msg);
                request.setAttribute("numeroConvenioGerado", convenio.getNumeroConvenio());
                request.getRequestDispatcher("/form_empresa_sucesso.jsp").forward(request, response);
    }

}
