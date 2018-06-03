/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cefetrj.sisgee.view.convenio;

import br.cefetrj.sisgee.control.ConvenioServices;
import br.cefetrj.sisgee.control.EmpresaServices;
import br.cefetrj.sisgee.control.PessoaServices;
import br.cefetrj.sisgee.model.entity.Convenio;
import br.cefetrj.sisgee.model.entity.Empresa;
import br.cefetrj.sisgee.model.entity.Pessoa;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Lucas Lima
 */
public class BuscarConvenioServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String nome = "";
        String numero = "";
        request.setAttribute("filtro", null);

        numero = request.getParameter("numeroConvenio");
        nome = request.getParameter("razaoSocial");

        String idEmpresa = "";
        request.setAttribute("selecao", null);

        Convenio convenio = null;
        List<Convenio> convenios = new ArrayList();
        List<Empresa> empresas = new ArrayList();
        Empresa empresa = null;
        List<Pessoa> pessoas = new ArrayList();

        boolean isValid = true;

        /**
         * Buscar pelo numero do Convenio
         */
        if (numero != null) {
            if (!numero.equals("")) {
                convenio = ConvenioServices.buscarConvenioByNumeroConvenio(numero.trim());
            } 
            if (convenio != null) {
                convenios.add(convenio);

            }
        }else{
            isValid=false;
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

                    }

                }

                if (empresas != null) {

                    for (Empresa x : empresas) {
                        convenio = ConvenioServices.buscarConvenioByEmpresa(x);
                        convenios.add(convenio);

                    }

                }
            } 
        }else{
            isValid=false;
        }
        
        
        if (!convenios.isEmpty()) {
            isValid=true;

            request.setAttribute("filtro", convenios);

        }

        if (isValid) {
            

            request.getRequestDispatcher("form_renovar_convenio.jsp").forward(request, response);
        } else {
            request.setAttribute("erroBuscar", "Não foi encontrado nenhum convênio com os parâmetros passados.");
            request.getRequestDispatcher("index.jsp").forward(request, response);

        }

    }

}
