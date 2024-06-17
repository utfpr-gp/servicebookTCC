<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<c:if test="${empty jobs}">
    <div class="container">
        <div class="row">
            <div class="col s12 spacing-buttons">
                <div class="none-profission">
                    <p class="center text-form-dados">
                        Nenhum pedido executado encontrado!
                    </p>
                    <p class="center">
                        Um novo pedido pode chegar aqui a qualquer momento.
                    </p>
                </div>
            </div>
        </div>
    </div>
</c:if>
<div class="row">
    <c:forEach var="job" items="${jobs}">
        <a href="minha-conta/profissional/detalhes-servico/${job.jobRequest.id}">
            <div class="col s12 m6">
                <div class="card">
                    <div class="card-title">
                        <p class="label_especialidade center">${job.jobRequest.expertise.name}</p>
                    </div>

                    <div class="card-image">
                        <c:if test="${empty job.jobRequest.jobImages}">
                            <div class="black-text text-darken-2 col-image-job-request">
                                <span class="icone-default"><i class="material-icons right">dashboard</i></span>
                            </div>
                        </c:if>

                        <c:if test="${not empty job.jobRequest.jobImages}">
                            <div class="carousel carousel-slider center">
                                <c:forEach var="image" items="${job.jobRequest.jobImages}">
                                    <div class="carousel-item white white-text" href="#${image.id}">
                                        <img src="${image.path}" class="avatar" style="height: 300px; position: relative;">
                                        <div class="carousel-fixed-item center">
                                            <div class="left">
                                                <a href="Previo" class="movePrevCarousel middle-indicator-text waves-effect waves-light content-indicator">
                                                    <i class="material-icons left middle-indicator-text">chevron_left</i>
                                                </a>
                                            </div>
                                            <div class="right">
                                                <a href="Siguiente" class="moveNextCarousel middle-indicator-text waves-effect waves-light content-indicator">
                                                    <i class="material-icons right middle-indicator-text">chevron_right</i>
                                                </a>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>

                        </c:if>

                        <span class="card-title"> <i class="material-icons small blue-color-text icon_default">location_on</i>
                             ${job.jobRequest.user.address.neighborhood}
                        </span>
                    </div>

                    <div class="card-content">
                        <span class="left description-job-card blue-text"><strong> Solicitado
                            em: ${job.jobRequest.dateCreated} </strong></span>
                        <span class="right description-job-card blue-text"><strong> Realizado em: ${job.todoDate} </strong></span>
                        <br>
                        <p class="left grey-text text-darken-4"> ${job.jobRequest.description} </p>
                        <br>
                        <p class="description-job-card right grey-text text-darken-4"> ${job.jobRequest.textualDate} </p>
                    </div>

                    <div class="card-footer progress" style="position: relative; height: 30px;">
                        <div class="determinate"
                             style="width: ${job.jobRequest.totalCandidates / job.jobRequest.quantityCandidatorsMax * 100}%; height: 100%;">
                        </div>
                        <div class="progress-text"
                             style="position: absolute; right: 10px; top: 50%; transform: translateY(-50%); white-space: nowrap;">
                                ${job.jobRequest.totalCandidates} de ${job.jobRequest.quantityCandidatorsMax}
                        </div>
                    </div>
                </div>
            </div>
        </a>

    </c:forEach>
</div>

<div class="container col s12 center-align">
    <t:pagination-tab-ajax pagination="${pagination}"></t:pagination-tab-ajax>
</div>
<style>
    .carousel{
        height: 200px !important;
    }
    .carousel .carousel-fixed-item.center {
        position: absolute;
        top: 50%;
        width: 100%;
        transform: translateY(-50%);
        justify-content: space-between;
    }

    .carousel .carousel-fixed-item .left,
    .carousel .carousel-fixed-item .right {
        z-index: 1;
    }

    .carousel .carousel-fixed-item .left {
        margin-left: 10px;
    }

    .carousel .carousel-fixed-item .right {
        margin-right: 10px;
    }

</style>
<script>
    $(document).ready(function(){
        $('.carousel').carousel();
    });
</script>