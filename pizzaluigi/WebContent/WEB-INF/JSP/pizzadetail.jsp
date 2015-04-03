<%@page contentType='text/html' pageEncoding='UTF-8' session='false'%>
<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@taglib uri='http://vdab.be/tags' prefix='vdab'%>
<!doctype html>
<html lang='nl'>
<head>
<vdab:head title='${pizza.naam}'/>
</head>
<body>
	<vdab:menu/>
	<c:choose>
		<c:when test='${empty pizza}'>
			<div class='fout'>${empty fout ? 'Pizza niet gevonden' : fout}</div>
		</c:when>
		<c:otherwise>
			<h1>${pizza.naam}</h1>
			<dl>
				<dt>Nummer</dt>
				<dd>${pizza.id}</dd>
				<dt>Naam</dt>
				<dd>${pizza.naam}</dd>
				<dt>Prijs</dt>
				<dd>${pizza.prijs}</dd>
				<dt>Pikant</dt>
				<dd>${pizza.pikant ? 'ja' : 'nee'}</dd>
			</dl>
		</c:otherwise>
	</c:choose>
</body>
</html>