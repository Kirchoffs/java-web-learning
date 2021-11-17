<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<body>
<%
    String TAMARIX = "TAMARIX";
    out.println("<h1>" + TAMARIX + "</h1>");
%>

<hr/>

<%
    String[] hello = new String[] {
        "Hello",
        "你好",
        "こんにちは"
    };
%>
<% for (int i = 0; i < hello.length; i++) { %>
    <h1>
        <%= hello[i] %>
    </h1>
<% } %>

<hr/>

<%-- EL expression --%>
<% for (int i = 0; i < hello.length; i++) { %>
    <h1>
        ${hello[i]}
    </h1>
<% } %>

<%!
    static {
        System.out.println("Start...");
    }

    void f() {
        System.out.println("f...");
    }

    private int global = 13681644;
    private int error = 1 / 0;
%>
</body>
</html>
