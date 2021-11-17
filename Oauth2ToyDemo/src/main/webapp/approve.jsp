<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>Oauth Test</title>
</head>

<body>
<form action="/oauth-servlet" method="post">
    <input type="hidden" name="request_id" value="<%=request.getAttribute("request_id")%>" />
    <input type="hidden" name="response_type" value="<%=request.getAttribute("response_type")%>" />
    <input type="hidden" name="redirect_uri" value="<%=request.getAttribute("redirect_uri")%>" />
    <input type="hidden" name="app_id" value="<%=request.getAttribute("app_id")%>" />

    <input type="hidden" name="request_type" value="approve" />

    Are you sure you want the authorization code？

    <br>
    app_id: <%=request.getAttribute("app_id")%>

    <br><br>
    <input type="checkbox" value="today" name="rscope" checked/>today<br>
    <input type="checkbox" value="history" name="rscope"/>history<br>
    <input type="checkbox" value="pic" name="rscope"/>pic<br>

    <br>

    <input type="submit" value="approve"/> <input type="submit" value="refuse"/>
    <br>
</form>
</body>

</html>
