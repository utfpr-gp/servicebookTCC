<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <!-- Funciona apenas com caminho absoluto porque é renderizado antes da tag base -->
    <link href="${pageContext.request.contextPath}/assets/resources/styles/professional/professional.css" rel="stylesheet">
</head>

<t:template title="Meu email">
    <jsp:body>

        <main>
            <div class="container">
                <div class="row">
                    <div class="col s12 l6 offset-l3 spacing-buttons">
                        <c:if test="${not empty msg}">
                            <div class="card-panel green lighten-1 msg-view center-align">
                                <span class="white-text">${msg}</span>
                            </div>
                        </c:if>
                        <c:if test="${not empty msgError}">
                            <div class="card-panel red msg-view center-align">
                                <span class="white-text">${msgError}</span>
                            </div>
                        </c:if>
                        <c:if test="${not empty errors}">
                            <div class="card-panel red msg-view center-align">
                                <c:forEach var="e" items="${errors}">
                                    <span class="white-text">${e.getDefaultMessage()}</span><br>
                                </c:forEach>
                            </div>
                        </c:if>
                    </div>
                </div>
                <div class="row">
                    <h3 class="center secondary-color-text">
                        Vamos validar o seu email?
                    </h3>
                    <h5 class="center secondary-color-text">
                        Por medidas de segurança, o email só pode ser alterado com a validação do mesmo.
                        <br>
                        <div id="email-div">

                        </div>
                    </h5>

                    <form method="post" action="minha-conta/valida-email/${user.id}">
                        <input type="hidden" id="email" name="email" value="${user.email}"/>
                        <div class="row">
                            <div class="input-field col s10 m8 l6 xl6 offset-s1 offset-m2 offset-l3 offset-xl3">
                                <input id="code" name="code" type="text" class="validate" required>
                                <label for="code">Código</label>
                            </div>
                        </div>
                        <div class="row col s12">
                            <div class="sendEmail col s6 center">
                                <button class="waves-effect waves-light btn" type="button" id="resend-email-button">
                                    Reenviar email
                                </button>
                            </div>
                            <div class="center col s6">
                                <button id="validate-button" class="waves-effect waves-light btn" type="submit">Salvar</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </main>

    </jsp:body>
</t:template>
<script>
    const urlParams = new URLSearchParams(window.location.search);
    const email = urlParams.get('email');

    $(document).ready(function() {
        $('#edit-button').click(function(){
            console.log($(this))
            $('#email-form input').prop('disabled', false);
            $(this).hide();
            $('#save-button').show();
        });
    });

    $('#resend-email-button').click(function () {
        $.get("minha-conta/reenvia-codigo-verificacao-email/${user.id}", {email: email}).done(function () {
            swal({
                title: "Sucesso!",
                text: "Foi enviado um email para " + email + ".",
                icon: "success",
            });
        });
    });
</script>