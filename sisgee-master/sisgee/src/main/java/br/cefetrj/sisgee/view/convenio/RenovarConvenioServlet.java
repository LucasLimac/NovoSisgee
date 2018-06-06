/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cefetrj.sisgee.view.convenio;

import br.cefetrj.sisgee.control.ConvenioServices;
import br.cefetrj.sisgee.model.entity.Convenio;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Lucas Lima
 */
@WebServlet(name = "RenovarConvenioServlet", urlPatterns = {"/RenovarConvenioServlet"})
public class RenovarConvenioServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        
        int pos = req.getParameter("convenio").indexOf("/");
        //Substring iniciando em 0 até posição do caracter especial
        
        String numeroConvenio = req.getParameter("convenio").substring(0,pos);
        
        Convenio convenio = ConvenioServices.buscarConvenioByNumeroConvenio(numeroConvenio);
        
        if(convenio.getEmpresa()!=null){
            req.setAttribute("isEmpresa", "sim");
            if(convenio.getEmpresa().isAgenteIntegracao()){
                req.setAttribute("simAgenteIntegracao", "sim");
            }else{
                req.setAttribute("naoAgenteIntegracao", "sim");
            }
            req.setAttribute("cnpj", convenio.getEmpresa().getCnpjEmpresa());
            req.setAttribute("razao", convenio.getEmpresa().getRazaoSocial());
            
               
        }else{
            req.setAttribute("isPessoa", "sim");
            req.setAttribute("cpf", convenio.getPessoa().getCpf());
            req.setAttribute("nome", convenio.getPessoa().getNome());
            
        }
        req.getSession().setAttribute("numero", numeroConvenio);
        
        req.setAttribute("convenioRenovar", convenio);
  
        req.getRequestDispatcher("form_renovar_convenio2.jsp").forward(req, resp);
        
    }

}
