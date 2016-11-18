<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!doctype html>
<html>
	<head>
		<link href="<c:url value="/resources/css/bootstrap.css" />" rel="stylesheet">
		<title>Анализатор текстов</title>
	</head>
	<body>
		<div class="container">
			<div class="col-sm-6 col-sm-offset-3">
				<c:url var="url" value="/index"/>
				<form:form action="${url}" method="POST" commandName="form">
					<div class="form-group">
						<label for="text1" class="control-label">Текст 1</label>
						<form:input id="text1" type="text" path="text1" class="form-control"/>
						<form:errors path="text1" class="has-error" />
					</div>
					<div class="form-group">
						<label for="text2" class="control-label">Текст 2</label>
						<form:input id="text2" type="text" path="text2" class="form-control"/>
						<form:errors path="text2" class="has-error" />
					</div>
					<div class="form-group">
						<label for="text3" class="control-label">Текст 3</label>
						<form:input id="text3" type="text" path="text3" class="form-control"/>
						<form:errors path="text3" class="has-error" />
					</div>
					<div class="form-group">
						<label for="text4" class="control-label">Текст 4</label>
						<form:input id="text4" type="text" path="text4" class="form-control"/>
						<form:errors path="text4" class="has-error" />
					</div>
					<div class="form-group">
						<label for="text5" class="control-label">Текст 5</label>
						<form:input id="text5" type="text" path="text5" class="form-control"/>
						<form:errors path="text5" class="has-error" />
					</div>
					<div class="form-group">
						<div class="col-sm-5 col-sm-offset-4">
							<input class="btn btn-primary" type="submit" value="Поиск пересечений"/>
						</div>
					</div>
				</form:form>
			</div>	
			<c:if test="${!empty form.results}">
				<div class="col-sm-8 col-sm-offset-2">
					<table class="table table-hover">
						<thead>
							<tr>
								<th>Слово</th>
								<th>Количество текстов</th>
								<th>Номера текстов</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${form.results}" var="result">
								<tr>
									<td>${result.word}</td>
									<td>${result.count}</td>
									<td>${result.textNumbers}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</c:if>
		</div>
		<script src="<c:url value="/resources/js/jquery.js" />"></script>
		<script src="<c:url value="/resources/js/bootstrap.js" />"></script>
	</body>
</html>
