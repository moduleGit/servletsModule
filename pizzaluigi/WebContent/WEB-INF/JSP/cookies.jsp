<%-- <%@page contentType='text/html' pageEncoding='UTF-8' session='false'%> --%>
<%-- <%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%> --%>
<!-- <!doctype html> -->
<!-- <html lang='nl'> -->
<!-- <head> -->
<%-- <c:import url='/WEB-INF/JSP/head.jsp'> --%>
<%-- 	<c:param name='title' value='Cookies' /> --%>
<%-- </c:import> --%>
<!-- </head> -->
<!-- <body> -->
<%-- 	<c:import url='/WEB-INF/JSP/menu.jsp' /> --%>
<!-- 	<h1>Cookies</h1> -->
<!-- 	<form method='post'> -->
<%-- 		<label>Naam<input name='naam' value='${naam}' autofocus /></label> <input --%>
<!-- 			type='submit' value='Onthoud me'> -->
<!-- 	</form> -->

<%-- 	<label>Het aangemaakte cookie noemt: <span>${cookie.naam.value}</span></label>  zelf toegevoegd --%>
<!-- </body> -->
<!-- </html> -->


<%@page contentType='text/html' pageEncoding='UTF-8' %>
<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>
<%@taglib uri='http://vdab.be/tags' prefix='vdab'%>

<c:if test='${not empty sessionScope.locale}'>
<fmt:setLocale value='${sessionScope.locale}'/> <%-- p84 --%>
</c:if>
<fmt:setBundle basename='resourceBundles.teksten' />

<!doctype html>
<html lang='nl'>
<head>
<vdab:head title='Cookies'/>
</head>
<body>
	<vdab:menu/>
	<h1>
		<fmt:message key='cookieVoorbeeld' />
	</h1>
	<form method='post'>
		<label><fmt:message key='naam' /> <input name='naam'
			value='${naam}' autofocus required></label> <input type='submit'
			value="<fmt:message key='onthoudMe'/>">
	</form>
	<c:if test='${not empty naam}'>
		<div>
			<fmt:message key='naamLetters'>
				<fmt:param value='${naam.length()}' />
			</fmt:message>
		</div>
	</c:if>

	<div>
		<c:url value='' var='nlBEURL'> 
<c:param name='locale' value='nl-BE' />
		</c:url>
		<c:url value='' var='enUSURL'>
			<c:param name='locale' value='en-US' />
		</c:url>
		<a href='${nlBEURL}'>Ik spreek Nederlands en woon in België</a> 
		<br>
		<a	href='${enUSURL}'>I speak English and live in the USA</a>
	</div>
</body>
</html>