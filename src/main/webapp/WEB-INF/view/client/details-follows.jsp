<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <!-- Funciona apenas com caminho absoluto porque é renderizado antes da tag base -->
    <link href="${pageContext.request.contextPath}/assets/resources/styles/client/client.css" rel="stylesheet">
</head>

<t:template title="Meus Profissionais Favoritos">
    <jsp:body>
        <main class="container">
            <div class="row">
                <div class="col m4 l3 hide-on-med-and-down">
                    <t:side-panel userInfo="${userInfo}"></t:side-panel>
                </div>

                <div class="col s12 l9">
                    <div class="row">
                        <div class="col s12">
                            <h2 class="secondary-color-text">Meus profissionais favoritos</h2>
                            <div class="section">
                                <c:if test="${empty professionals}">
                                    <div class="center">
                                        <i class="material-icons" style="font-size: 120px;">mood_bad</i>
                                        <div class="flow-text">Não há resultados!</div>
                                    </div>
                                </c:if>
                                <c:forEach var="p" items="${professionals}">
                                    <!-- cartão -->
                                    <t:professional-card professional="${p}"></t:professional-card>
                                    <!-- fim cartão -->
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>
    </jsp:body>
</t:template>
