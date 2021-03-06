<%@page contentType='text/html' pageEncoding='UTF-8' session='false'%>
<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@taglib uri='http://vdab.be/tags' prefix='vdab'%>
<!doctype html>
<html lang='nl'>
<head>
<!-- <title>Pizza's</title>
<link rel='shortcut icon' href='images/favicon.ico' type='image/x-icon' />
<meta name='viewport' content='width=device-width,initial-scale=1'>
<link rel='stylesheet' href='styles/default.css'> -->

<vdab:head title="Pizza's"/>
</head>
<body>
	<vdab:menu/>
	<h1>
		Pizza's
		<c:forEach begin='1' end='5'>
			&#9733; <%-- de HTML code van een ster --%>
		</c:forEach>
	</h1>
	<ul class='zebra'>
		<%-- <c:forEach var='entry' items='${pizzas}'>
			<li>${entry.key}:<c:out value='${entry.value.naam}' />
				${entry.value.prijs}&euro; c: voor vertaalslag & p29 
				
				${entry.value.pikant ? "pikant" : "niet pikant"} <c:url
					value='/pizzas/detail.htm' var='detailURL'>
					<c:param name='id' value='${entry.key}' />
				</c:url> <a href='${detailURL}'>Detail</a>
			</li>
		</c:forEach> --%>

		<c:forEach var='pizza' items='${pizzas}'>
			<li>${pizza.id}:<c:out value='${pizza.naam}' /> ${pizza.prijs}
				&euro; ${pizza.pikant ? "pikant" : "niet pikant"}
				<c:url value='/pizzas/detail.htm' var='detailURL'>
					<c:param name='id' value="${pizza.id}" />
				</c:url> 
				<a href="<c:out value='${detailURL}'/>">Detail</a> 
				<c:if
					test='${pizzaIdsMetFoto.contains(pizza.id)}'>
					<c:url value='/pizzafotos/${pizza.id}.jpg' var='fotoURL' />
					<a href='${fotoURL}'>Foto</a>
				</c:if>
			</li>
		</c:forEach>
	</ul>
</body>
</html>