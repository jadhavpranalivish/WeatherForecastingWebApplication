<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Weather App - Pranali Jadhav</title>
    <link rel="stylesheet" href="style.css" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" />
</head>

<body>
    <div class="appContainer">
        <h1 class="title">🌤️ Weather Forecast</h1>
       <%--  <form action="${pageContext.request.contextPath}/MyServlet" method="post" class="searchInput">
            <input type="text" placeholder="Enter City Name" name="city" value="${city != null ? city : ''}" required />
            <button id="searchButton"><i class="fa-solid fa-magnifying-glass"></i></button>
        </form>--%>

         <form action="<%= request.getContextPath() %>/MyServlet" method="post" class="searchInput">
    <input type="text" placeholder="Enter City Name" name="city" 
           value="<%= (request.getAttribute("city") != null) ? request.getAttribute("city") : "" %>" required />
    <button id="searchButton"><i class="fa-solid fa-magnifying-glass"></i></button>
</form>
         
        <div class="weatherCard">
            <div class="weatherIcon">
                <img src="" alt="Weather" id="weather-icon">
            </div>
            <h2 class="temperature">${temperature}°C</h2>
            <p class="condition">${weatherCondition}</p>

            <div class="cityDetails">
                <h2>${city}</h2>
                <p class="date">${date}</p>
                 <!-- Live Clock -->
                 <p id="live-time" style="font-weight:bold; font-size:16px; margin-top:5px;"></p>
            </div>

            <div class="extraInfo">
                <div>
                    <i class="fa-solid fa-droplet"></i>
                    <p>Humidity</p>
                    <h3>${humidity}%</h3>
                </div>
                <div>
                    <i class="fa-solid fa-wind"></i>
                    <p>Wind</p>
                    <h3>${windSpeed} km/h</h3>
                </div>
            </div>
        </div>
    </div>

<input type="hidden" id="wc" value="${weatherCondition}">
    <script src="myScript.js"></script>

    <% if (request.getAttribute("errorMessage") != null) { %>
        <div class="toast">${request.getAttribute("errorMessage")}</div>
        <script>
            setTimeout(() => {
                document.querySelector(".toast").style.display = "none";
            }, 4000);
        </script>
    <% } %>
</body>
</html>
