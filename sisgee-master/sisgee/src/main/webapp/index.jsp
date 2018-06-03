<!DOCTYPE html>
<html lang="en">
    <head>
        <%@include file="import_head.jspf"%>
        <title><fmt:message key = "br.cefetrj.sisgee.resources.form.sisgee"/></title>
        <link rel="stylesheet" href="node_modules/bootstrap/dist/css/bootstrap.min.css" >
        <link rel="stylesheet" href="node_modules/bootstrap-datepicker/dist/css/bootstrap-datepicker.min.css" >
        <script src="node_modules/bootstrap/dist/js/bootstrap.min.js" ></script>
        <script src="node_modules/jquery/dist/jquery.min.js" ></script>


    </head>
    <body  onunload="window.opener.atualizaAgenteIntegracao()" >
        <%@include file="import_navbar.jspf"%>
        <div class="container" style="margin: 0px;" >
            <p>
                <c:choose>
                    <c:when test="${ not empty msg }">
                    <div class="alert alert-success" role="alert">
                        <h2>${ msg }</h2>
                    </div>
                </c:when>
                    <c:when test="${ not empty erroBuscar }">
                    <div class="alert alert-danger" role="alert">
                        <h2>${ erroBuscar }</h2>
                    </div>
                </c:when>
                <c:otherwise>

                </c:otherwise>
            </c:choose>
        </p>
    </div>

    <div id="myCarousel" class="carousel slide " style="margin: 0px; padding: 0px;" data-ride="carousel">
        <!-- Indicators -->
        <ol class="carousel-indicators">
            <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
            <li data-target="#myCarousel" data-slide-to="1"></li>
            
        </ol>

        <!-- Wrapper for slides -->
        <div class="carousel-inner" style="margin: 0px; padding: 0px;">



            <div class="carousel-item">

                <img  src="imgs/carousel2.jpeg" class="img-fluid d-block" alt="Sisgee">
                <div class="carousel-caption" style="background-color: #000; border-radius: 10px; padding: 5px;">
                    <h3 ><fmt:message key="br.cefetrj.sisgee.index.carousel1" /></h3>
                    <p ><fmt:message key="br.cefetrj.sisgee.index.carousel1.a" /></p>
                </div>
            </div>

            <div class="carousel-item active">
                <img  src="imgs/carousel1.jpg" class="img-fluid d-block" alt="Convênio">
                <div class="carousel-caption" style="background-color: #000; border-radius: 10px; padding: 5px;">
                    <h3><fmt:message key="br.cefetrj.sisgee.index.carousel2" /></h3>
                    <p><fmt:message key="br.cefetrj.sisgee.index.carousel2.a" /></p>
                </div>
            </div>
        </div>

        <!-- Left and right controls -->
        <a class="left carousel-control-prev" href="#myCarousel" data-slide="prev">
            <span class="carousel-control-prev-icon"></span>
            <span class="sr-only">Anterior</span>
        </a>
        <a class="right carousel-control-next" href="#myCarousel" data-slide="next">
            <span class="carousel-control-next-icon"></span>
            <span class="sr-only">Próximo</span>
        </a>
    </div>
        
        <!-- Marketing messaging and featurettes
      ================================================== -->
      <!-- Wrap the rest of the page in another container to center all the content. -->

      <div class="container marketing mt-5 ">

        <!-- Three columns of text below the carousel -->
        <div class="row">
          <div class="col-lg-4">
            <img class="rounded-circle" src="https://www.cieers.org.br/resources/img/pages/aprendiz-legal.png" alt="Generic placeholder image" width="140" height="100">
            <h2>Aprendiz Legal</h2>
            <p><fmt:message key="br.cefetrj.sisgee.index.programas1" /></p>
          </div><!-- /.col-lg-4 -->
          <div class="col-lg-4">
            <img class="rounded-circle" src="https://editalconcursosbrasil.com.br/wp-content/uploads/2016/08/estagio-ciee-1.png" alt="Generic placeholder image" width="140" height="140">
            <h2>CIEE</h2>
            <p><fmt:message key="br.cefetrj.sisgee.index.programas2" /></p>
          </div><!-- /.col-lg-4 -->
          <div class="col-lg-4">
            <img class="rounded-circle" src="http://www.mudes.org.br/Imagens/logo.png" alt="Generic placeholder image" width="140" height="140">
            <h2>Mudes</h2>
            <p><fmt:message key="br.cefetrj.sisgee.index.programas3" /></p>
          </div><!-- /.col-lg-4 -->
        </div><!-- /.row -->



    <%@include file="import_footer.jspf"%>
    <%@include file="import_finalbodyscripts.jspf"%>



</body>

</html>
