<%@ page
	contentType="text/html;charset=UTF-8"
	language="java"
	import="com.google.appengine.api.users.*"
%>
<%
	UserService userService = UserServiceFactory.getUserService();
	String logInOutUrl;
	String logInOutLabel;
	String destUrl = request.getRequestURL().toString();
	if(userService.isUserLoggedIn())
	{
		logInOutUrl = userService.createLogoutURL(destUrl);
		logInOutLabel = "Log Out";
	}
	else
	{
		logInOutUrl = userService.createLoginURL(destUrl);
		logInOutLabel = "Log In";
	}
%>
<html>
	<head>
		<title>Secret Santa</title>
		<link rel="stylesheet" type="text/css" href="style.css"/>
	</head>
	<body>
		<div class="nav">
			Home | 
			<a href="help.html">Help</a> |
			<a href="<%= logInOutUrl %>"><%= logInOutLabel %></a>
		</div>
		<script language="javascript" src="santaui/santaui.nocache.js"></script>
		<div id="output"></div>
	</body>
</html>
