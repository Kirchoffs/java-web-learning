<%@ page contentType="text/html;charset=utf-8" language="java" %>
<html>
<body>
<h2>Login</h2>
<div style="text-align: center">
    <form action="${pageContext.request.contextPath}/login" method="post">
        username: <input type="text" name="username" /> <br/>
        <br/>
        password: <input type="text" name="password" /> <br/>
        <br/>
        hobby:
        <input type="checkbox" name="hobbies" value="game" />game
        <input type="checkbox" name="hobbies" value="sport" />sport
        <input type="checkbox" name="hobbies" value="singing" />singing
        <input type="checkbox" name="hobbies" value="dancing" />dancing
        <input type="checkbox" name="hobbies" value="reading" />reading
        <input type="submit" />
    </form>
</div>
</body>
</html>
