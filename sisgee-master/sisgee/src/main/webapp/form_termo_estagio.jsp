<!DOCTYPE html>
<html lang="en">
    <head>
        <%@include file="import_head.jspf"%>

        <style type="text/css">

            div.container {
                margin-bottom: 2em;
            }
            form {
                margin-top: 50px;
            }
            fieldset.form-group {
                border:1px solid #999999;
            }
            fieldset legend.col-form-legend {
                font-weight: bold;
            }
            div.form-row {
                padding: 0px 15px;
            }

        </style>

        <title>
            <c:choose>
                <c:when test="${ not empty termoEstagio }">
                    <fmt:message key = "br.cefetrj.sisgee.resources.form.registroTermoAditivo"/>
                </c:when>
                <c:otherwise>
                    <fmt:message key = "br.cefetrj.sisgee.resources.form.registroTermoEstagio"/>
                </c:otherwise>
            </c:choose>
        </title>
    </head>
    <body>
        <%@include file="import_navbar.jspf"%>	


        <div class="container">
            <c:if test="${ not empty msg }">
                <div class="alert alert-warning" role="alert">
                    ${ msg }
                </div>
            </c:if>
            <c:if test="${ not empty msg2 }">
                <div class="alert alert-warning" role="alert">
                    ${ msg2 }
                </div>
            </c:if>

            <p class="tituloForm">

            <h5>		
                <c:choose>
                    <c:when test="${ not empty termoEstagio }">
                        <fmt:message key = "br.cefetrj.sisgee.resources.form.registroTermoAditivo"/>
                    </c:when>
                    <c:otherwise>
                        <fmt:message key = "br.cefetrj.sisgee.resources.form.registroTermoEstagio"/>
                    </c:otherwise>
                </c:choose>
            </h5>		
        </p>		

        <c:choose>
            <c:when test="${ not empty termoEstagio }">
                <form action="FormTermoAditivoServlet" method="post">
                </c:when>
                <c:otherwise>
                    <form action="FormTermoEstagioServlet" method="post">
                    </c:otherwise>
                </c:choose>

                <fieldset class="form-group" ${ not empty termoEstagio ? 'disabled' : '' }>
                    <legend class="col-form-legend col-lg"><fmt:message key = "br.cefetrj.sisgee.resources.form.dadosEmpresaConveniada"/></legend>
                    <div class="form-group col-md-6">
                        <label for="numeroConvenio"><fmt:message key = "br.cefetrj.sisgee.resources.form.numeroConvenio"/></label>
                        <input type="hidden" id="idEmpresa" name="idEmpresa" value="${ param.idEmpresa }">
                        <input type="text" class="form-control col-sm-4 ${ not empty numeroConvenioMsg ? 'is-invalid': 'is-valid' }" id="numeroConvenio" name="numeroConvenio" maxlength="10" value="${ param.numeroConvenio }" >
                        <c:if test="${ not empty numeroConvenioMsg }">
                            <div class="invalid-feedback">${ numeroConvenioMsg }</div>
                        </c:if>
                    </div>

                    <div class="form-row">
                        <div class="form-group col-md-3">
                            <label for="isAgenteIntegracao"><fmt:message key = "br.cefetrj.sisgee.resources.form.agenteIntegracao"/></label>
                        </div>

                        <div class="custom-controls-stacked d-block my-3">							
                            <label class="custom-control custom-radio">
                                <input id="agenteSim" class="custom-control-input isAgenteChk ${ not empty isAgenteIntegracaoMsg ? 'is-invalid' : '' }" type="radio" name="isAgenteIntegracao" value="sim" ${ not empty isAgenteIntegracaoMsg ? '' : param.isAgenteIntegracao == 'sim' ? 'checked' : '' }> 
                                <span class="custom-control-indicator"></span> 
                                <span class="custom-control-description" ><fmt:message key = "br.cefetrj.sisgee.resources.form.sim"/></span>
                            </label>						

                            <label class="custom-control custom-radio">
                                <input id="agenteNao" class="custom-control-input isAgenteChk ${ not empty isAgenteIntegracaoMsg ? 'is-invalid' : '' }" type="radio" name="isAgenteIntegracao" value="nao" ${ not empty isAgenteIntegracaoMsg ? '' : param.isAgenteIntegracao == 'nao' ? 'checked' : '' }> 
                                <span class="custom-control-indicator"></span> 
                                <span class="custom-control-description"><fmt:message key = "br.cefetrj.sisgee.resources.form.nao"/></span>
                            </label>
                            <c:if test="${ not empty isAgenteIntegracaoMsg }">
                                <div class="invalid-feedback">${ isAgenteIntegracaoMsg }</div>
                            </c:if>
                        </div>
                    </div>							

                    <div class="form-row notAI AI" ${ empty param.isAgenteIntegracao ? "style='display:none'" : param.isAgenteIntegracao == "sim" ? "style='display:none'" : "" }>
                        <div class="form-group col-md-4">
                            <label for="cnpjEmpresa"><fmt:message key = "br.cefetrj.sisgee.resources.form.cnpj"/></label>
                            <div class="input-group">						  
                                <input type="text" class="form-control cnpjEmpresaNotAI cnpjEmpresa" placeholder="Digite o CNPJ" id="cnpjEmpresa1" name="cnpjEmpresa" value="${ param.cnpjEmpresa }">
                                <span class="input-group-btn">
                                    <button class="btn btn-primary" type="button" id="btnBuscarEmpresaNotAI"><fmt:message key = "br.cefetrj.sisgee.resources.form.buscar"/></button>
                                </span>
                            </div>
                        </div>
                        <div class="form-group col-md-6">
                            <label for="nomeEmpresa"><fmt:message key = "br.cefetrj.sisgee.resources.form.razaoSocial"/></label>
                            <input type="text" class="form-control nomeEmpresaNotAI nomeEmpresa" id="nomeEmpresa" name="nomeEmpresa" value="${ param.nomeEmpresa }" readonly>
                        </div>
                        <div class="form-group col-md-2" style="padding-top: 1.9em">
                            <button type="button" class="btn btn-primary addEmpresa">+</button>
                        </div>
                    </div>

                    <div class="form-row isAI AI" ${ empty param.isAgenteIntegracao ? "style='display:none'" : param.isAgenteIntegracao == "nao" ? "style='display:none'" : "" }>
                        <div class="form-group col-md-6">
                            <label for="idAgenteIntegracao"><fmt:message key = "br.cefetrj.sisgee.resources.form.selectAgenteIntegracao"/></label>
                            <select id="idAgenteIntegracao" name="idAgenteIntegracao" class="form-control">
                                <option value="" selected>---</option>
                                <c:forEach items="${ agentesIntegracao }" var="agenteIntegracao">
                                    <option value="${ agenteIntegracao.idAgenteIntegracao }">${ agenteIntegracao.nomeAgenteIntegracao }</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group col-md-2" style="padding-top: 1.9em">
                            <button type="button" class="btn btn-primary addAgenteIntegracao">+</button>
                        </div>
                    </div>
                    <div class="form-row isAI AI" ${ empty param.isAgenteIntegracao ? "style='display:none'" : param.isAgenteIntegracao == "nao" ? "style='display:none'" : "" }>
                        <div class="form-group col-md-5">
                            <label for="cnpjEmpresa"><fmt:message key = "br.cefetrj.sisgee.resources.form.cnpjEmpresaAgenteIntegracao"/></label>
                            <div class="input-group">
                                <input type="text" class="form-control cnpjEmpresaIsAI cnpjEmpresa" placeholder="Digite o CNPJ" id="cnpjEmpresa2" name="cnpjEmpresa" value="${ param.cnpjEmpresa }">
                                <span class="input-group-btn">
                                    <button class="btn btn-primary" type="button" id="btnBuscarEmpresaIsAI"><fmt:message key = "br.cefetrj.sisgee.resources.form.buscar"/></button>
                                </span>
                            </div>
                        </div>
                        <div class="form-group col-md-5">
                            <label for="nomeEmpresa"><fmt:message key = "br.cefetrj.sisgee.resources.form.razaoSocialEmpresaAgenteIntegracao"/></label>
                            <input type="text" class="form-control nomeEmpresaIsAI nomeEmpresa" name="nomeEmpresa" value="${ param.nomeEmpresa }" readonly>
                        </div>
                        <div class="form-group col-md-2" style="padding-top: 1.9em">
                            <button type="button" class="btn btn-primary addEmpresa">+</button>
                        </div>
                    </div>							
                </fieldset>


                <fieldset class="form-group dadosAluno" ${ not empty termoEstagio ? 'disabled' :'' }>
                    <%@include file="import_busca_aluno.jspf"%>
                </fieldset>


                <c:if test="${ not empty periodoMsg }">
                    <div class="alert alert-danger" role="alert">${ periodoMsg }</div>
                </c:if>
                <fieldset class="form-group" ${ isVisualizacao eq true ? 'disabled' :'' }>
                    <legend class="col-form-legend col-lg"><fmt:message key = "br.cefetrj.sisgee.resources.form.vigenciaEstagio"/></legend>
                    <div class="form-row">
                        <div class="form-group col-md-6">

                            <label for="dataInicioTermoEstagio"><fmt:message key = "br.cefetrj.sisgee.resources.form.dataInicio"/></label>
                            <input type="text" class="form-control col-sm-4 ${ not empty dataInicioMsg ? 'is-invalid': not empty periodoMsg ? 'is-invalid' : 'is-valid' }" id="dataInicioTermoEstagio"  name="dataInicioTermoEstagio" value="${ param.dataInicioTermoEstagio }" ${ not empty termoEstagio ? 'disabled' : '' } >
                            <c:if test="${ not empty dataInicioMsg }">
                                <div class="invalid-feedback">${ dataInicioMsg }</div>
                            </c:if>
                        </div>
                        <div class="form-group col-md-6">
                            <label for="dataFimTermoEstagio"><fmt:message key = "br.cefetrj.sisgee.resources.form.dataTermino"/></label>
                            <input type="text" class="form-control col-sm-4 ${ not empty dataFimMsg ? 'is-invalid': not empty periodoMsg ? 'is-invalid' : 'is-valid' }" id="dataFimTermoEstagio"   name="dataFimTermoEstagio" value="${ param.dataFimTermoEstagio }" ${ empty termoEstagio ? '' : empty updVigencia ? 'disabled' : '' } >
                            <c:if test="${ not empty dataFimMsg }">
                                <div class="invalid-feedback">${ dataFimMsg }</div>
                            </c:if>
                        </div>
                    </div>
                </fieldset>


                <fieldset class="form-group" ${ isVisualizacao eq true ? 'disabled' :'' }>
                    <legend class="col-form-legend col-lg"><fmt:message key = "br.cefetrj.sisgee.resources.form.cargaHorariaAluno"/></legend>
                    <div class="form-row">
                        <div class="form-group col-md-4">
                            <label for="cargaHorariaTermoEstagio"><fmt:message key = "br.cefetrj.sisgee.resources.form.horasDia"/></label>
                            <input type="text" maxlength="1" class="form-control col-sm-2 ${ not empty cargaHorariaMsg ? 'is-invalid': 'is-valid' }" id="cargaHorariaTermoEstagio" name="cargaHorariaTermoEstagio" value="${ param.cargaHorariaTermoEstagio }" ${ empty termoEstagio ? '' : empty updCargaHoraria ? 'disabled' : '' }>
                            <c:if test="${ not empty cargaHorariaMsg }">
                                <div class="invalid-feedback">${ cargaHorariaMsg }</div>
                            </c:if>
                        </div>
                    </div>
                </fieldset>


                <fieldset class="form-group" ${ isVisualizacao eq true ? 'disabled' :'' }>
                    <legend class="col-form-legend col-lg"><fmt:message key = "br.cefetrj.sisgee.resources.form.valorBolsaEstagio"/></legend>
                    <div class="form-row">
                        <div class="form-group col-md-4">
                            <label for="valorBolsa">Valor (R$)</label>
                            <input type="text" class="form-control col-sm-6 ${ not empty valorBolsaMsg ? 'is-invalid': 'is-valid' }" id="valorBolsa" name="valorBolsa" value="${ param.valorBolsa }" ${ empty termoEstagio ? '' : empty updValorBolsa ? 'disabled' : '' }>
                            <c:if test="${ not empty valorBolsaMsg }">
                                <div class="invalid-feedback">${ valorBolsaMsg }</div>
                            </c:if>
                        </div>
                    </div>
                </fieldset>


                <fieldset class="form-group" ${ isVisualizacao eq true ? 'disabled' :'' } ${ empty termoEstagio ? '' : empty updEndereco ? 'disabled' : '' }>
                    <legend class="col-form-legend col-lg"><fmt:message key = "br.cefetrj.sisgee.resources.form.localEstagio"/></legend>
                    <div class="form-row">
                        <div class="form-group col-md-12">

                            <label for="enderecoTermoEstagio"><fmt:message key = "br.cefetrj.sisgee.resources.form.endereco"/></label>
                            <input type="text" maxlength="255" class="form-control ${ not empty enderecoMsg ? 'is-invalid': not empty enderecoMsg ? 'is-invalid' : 'is-valid' }" id="enderecoTermoEstagio" name="enderecoTermoEstagio" value="${ param.enderecoTermoEstagio }">
                            <c:if test="${ not empty enderecoMsg }">
                                <div class="invalid-feedback">${ enderecoMsg }</div>
                            </c:if>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group col-md-2">
                            <label for="numeroEnderecoTermoEstagio"><fmt:message key = "br.cefetrj.sisgee.resources.form.numero"/></label>
                            <input type="text" maxlength="10" class="form-control ${ not empty numeroEnderecoMsg ? 'is-invalid': not empty numeroEnderecoMsg ? 'is-invalid' : 'is-valid' }" id="numeroEnderecoTermoEstagio" name="numeroEnderecoTermoEstagio" value="${ param.numeroEnderecoTermoEstagio }">
                            <c:if test="${ not empty numeroEnderecoMsg }">
                                <div class="invalid-feedback">${ numeroEnderecoMsg }</div>
                            </c:if>
                        </div>
                        <div class="form-group col-md-4">
                            <label for="complementoEnderecoTermoEstagio"><fmt:message key = "br.cefetrj.sisgee.resources.form.complemento"/></label>
                            <input maxlength="150" type="text" class="form-control ${ not empty complementoEnderecoMsg ? 'is-invalid': not empty complementoEnderecoMsg ? 'is-invalid' : 'is-valid' }" id="complementoEnderecoTermoEstagio" name="complementoEnderecoTermoEstagio" value="${ param.complementoEnderecoTermoEstagio }">
                            <c:if test="${ not empty complementoEnderecoMsg }">
                                <div class="invalid-feedback">${ complementoEnderecoMsg }</div>
                            </c:if>
                        </div>
                        <div class="form-group col-md-6">
                            <label for="bairroEnderecoTermoEstagio"><fmt:message key = "br.cefetrj.sisgee.resources.form.bairro"/></label>
                            <input type="text" maxlength="150" class="form-control ${ not empty bairroEnderecoMsg ? 'is-invalid': not empty bairroEnderecoMsg ? 'is-invalid' : 'is-valid' }" id="bairroEnderecoTermoEstagio" name="bairroEnderecoTermoEstagio" value="${ param.bairroEnderecoTermoEstagio }">
                            <c:if test="${ not empty bairroEnderecoMsg }">
                                <div class="invalid-feedback">${ bairroEnderecoMsg }</div>
                            </c:if>
                        </div>
                    </div>
                    <div class="form-row">					
                        <div class="form-group col-md-6">
                            <label for="cidadeEnderecoTermoEstagio"><fmt:message key = "br.cefetrj.sisgee.resources.form.cidade"/></label>
                            <input type="text" maxlength="150" class="form-control ${ not empty cidadeEnderecoMsg ? 'is-invalid': not empty cidadeEnderecoMsg ? 'is-invalid' : 'is-valid' }" id="cidadeEnderecoTermoEstagio" name="cidadeEnderecoTermoEstagio" value="${ param.cidadeEnderecoTermoEstagio }">
                            <c:if test="${ not empty cidadeEnderecoMsg }">
                                <div class="invalid-feedback">${ cidadeEnderecoMsg }</div>
                            </c:if>
                        </div>
                        <div class="form-group col-md-2">
                            <label for="estadoEnderecoTermoEstagio"><fmt:message key = "br.cefetrj.sisgee.resources.form.estado"/></label>
                            <select name = "estadoEnderecoTermoEstagio" id="estadoEnderecoTermoEstagio" class="form-control ${ not empty estadoEnderecoMsg ? 'is-invalid': not empty estadoEnderecoMsg ? 'is-invalid' : 'is-valid' }">
                                <option value="" selected>---</option>
                                <c:forEach items="${ uf }" var="uf">
                                    <option value="${ uf }">${ uf }</option>
                                </c:forEach>							
                            </select>
                            <c:if test="${ not empty estadoEnderecoMsg }">
                                <div class="invalid-feedback">${ estadoEnderecoMsg }</div>
                            </c:if>
                        </div>
                        <div class="form-group col-md-4">
                            <label for="cepEnderecoTermoEstagio"><fmt:message key = "br.cefetrj.sisgee.resources.form.cep"/></label>
                            <input type="text" class="form-control ${ not empty cepEnderecoMsg ? 'is-invalid': not empty cepEnderecoMsg ? 'is-invalid' : 'is-valid' }" id="cepEnderecoTermoEstagio" name="cepEnderecoTermoEstagio" value="${ param.cepEnderecoTermoEstagio }">
                            <c:if test="${ not empty cepEnderecoMsg }">
                                <div class="invalid-feedback">${ cepEnderecoMsg }</div>
                            </c:if>
                        </div>
                    </div>
                </fieldset>


                <div class="form-row" >
                    <div class="form-group col-md-3" ${ isVisualizacao eq true ? 'disabled' :'' } >
                        <label for="eEstagioObrigatorio"><fmt:message key = "br.cefetrj.sisgee.resources.form.estagioObrigatorio"/></label>
                    </div>

                    <div class="custom-controls-stacked d-block my-3" ${ isVisualizacao eq true ? 'disabled' :'' }>
                        <label class="custom-control custom-radio"> 
                            <input id="estagioSim" name="eEstagioObrigatorio" type="radio" class="custom-control-input ${ not empty eEstagioObrigatorioMsg ? 'is-invalid' : '' }" value = "sim" ${ not empty termoEstagio ? 'disabled' :'' } ${ not empty eEstagioObrigatorioMsg ? '' : param.eEstagioObrigatorio == 'sim' ? 'checked' : '' }> 
                            <span class="custom-control-indicator"></span> 
                            <span class="custom-control-description" ><fmt:message key = "br.cefetrj.sisgee.resources.form.sim"/></span>
                        </label> 
                        <label class="custom-control custom-radio"> 
                            <input id="estagioNao" name="eEstagioObrigatorio" type="radio" class="custom-control-input ${ not empty eEstagioObrigatorioMsg ? 'is-invalid' : '' }" value = "nao" ${ not empty termoEstagio ? 'disabled' :'' } ${ not empty eEstagioObrigatorioMsg ? '' : param.eEstagioObrigatorio == 'nao' ? 'checked' : '' }> 
                            <span class="custom-control-indicator"></span> 
                            <span class="custom-control-description"><fmt:message key = "br.cefetrj.sisgee.resources.form.nao"/></span>
                        </label>
                    </div>				
                </div>

                <fieldset ${ isVisualizacao eq true ? 'disabled' :'' }>
                    <div class="form-group col-md-8">
                        <label for="idProfessorOrientador"><fmt:message key = "br.cefetrj.sisgee.resources.form.professorOrientador"/></label>
                        <select name="idProfessorOrientador" id="idProfessorOrientador" class="form-control ${ not empty idProfessorMsg ? 'is-invalid': not empty idProfessorMsg ? 'is-invalid' : 'is-valid' }" ${ empty termoEstagio ? '' : empty updProfessor ? 'disabled' : '' }>
                            <option value="" selected>---</option>
                            <c:forEach items="${ professores }" var="professor">
                                <option value="${ professor.idProfessorOrientador }">${ professor.nomeProfessorOrientador }</option>
                            </c:forEach>					
                        </select>
                        <c:if test="${ not empty idProfessorMsg }">
                            <div class="invalid-feedback">${ idProfessorMsg }</div>
                        </c:if>				
                    </div>
                </fieldset>

                <c:if test="${ not empty termoEstagio }">
                    <input type="hidden" name="idTermoEstagio" value="${ termoEstagio.idTermoEstagio }" />
                    <input type="hidden" name="updVigencia" value="${ updVigencia }" />
                    <input type="hidden" name="updCargaHoraria" value="${ updCargaHoraria }" />
                    <input type="hidden" name="updProfessor" value="${ updProfessor }" />
                    <input type="hidden" name="updValorBolsa" value="${ updValorBolsa }" />
                    <input type="hidden" name="updEndereco" value="${ updEndereco }" />
                </c:if>

                <button type="submit" class="btn btn-primary" ${ isVisualizacao eq true ? 'disabled' :'' }><fmt:message key = "br.cefetrj.sisgee.resources.form.salvar"/></button>
                <c:choose>
                    <c:when test="${ not empty termoEstagio }">
                        <button type="button" class="btn btn-secondary" onclick="javascript:location.href = 'form_termo_aditivo.jsp'"><fmt:message key = "br.cefetrj.sisgee.resources.form.cancelar"/></button>
                    </c:when>
                    <c:otherwise>
                        <button type="button" class="btn btn-secondary" onclick="javascript:location.href = 'index.jsp'"><fmt:message key = "br.cefetrj.sisgee.resources.form.cancelar"/></button>
                    </c:otherwise>
                </c:choose>	
            </form>

            <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="myModalLabel"></h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body"></div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-primary" data-dismiss="modal"><fmt:message key = "br.cefetrj.sisgee.resources.form.fechar"/></button>
                        </div>
                    </div>
                </div>
            </div>

    </div>
    <%@include file="import_footer.jspf"%>
    <%@include file="import_finalbodyscripts.jspf"%>
    <%@include file="import_scripts.jspf"%>
    <script>
        $(document).ready(function () {
            $('#cargaHorariaTermoEstagio').mask('9');
            $('#valorBolsa').mask('000.000,00', {reverse: true});
            $('#dataInicioTermoEstagio').mask('99/99/9999');
            $('#dataFimTermoEstagio').mask('99/99/9999');
            $('#cnpjEmpresa1').mask('99.999.999/9999-99');
            $('#cnpjEmpresa2').mask('99.999.999/9999-99');
            $('#cepEnderecoTermoEstagio').mask('99.999-999');
        });
    </script>
</body>
</html>
