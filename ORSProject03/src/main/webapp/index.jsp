<%@ page import="in.co.rays.project_3.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta name="viewport" content="width=device-width, initial-scale=1">
<style>
.p1 {
    padding-top: 150px;
    text-align: center;
}

/* 🔥 Animated Gradient Background (better than image) */
body {
    margin: 0;
    font-family: 'Poppins', sans-serif;

    background: linear-gradient(-45deg, #2196F3, #21CBF3, #673AB7, #9C27B0);
    background-size: 400% 400%;
    animation: gradientMove 10s ease infinite;
}

@keyframes gradientMove {
    0% { background-position: 0% 50%; }
    50% { background-position: 100% 50%; }
    100% { background-position: 0% 50%; }
}

/* Title Styling */
.title {
    color: white;
    font-size: 50px;
    text-decoration: none;
    font-weight: bold;
}

/* Logo Animation */
.logo {
    animation: float 3s ease-in-out infinite;
}

@keyframes float {
    0% { transform: translateY(0px); }
    50% { transform: translateY(-15px); }
    100% { transform: translateY(0px); }
}

/* Hover effect */
.title:hover {
    color: #FFD700;
    transition: 0.3s;
}
</style>
<body class="img-fluids">
	<div class="p1">
		<h1 align="Center">
			<img src="img/custom.png" width="318" height="120" border="0">
		</h1>
		<h1 align="Center">
			<a href="<%=ORSView.WELCOME_CTL%>" style="color: purple"> <font
				size="10px">Online Result System</font></a>
		</h1>
	</div>
</body>
</html>