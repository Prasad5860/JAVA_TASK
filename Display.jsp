<%@ page language="java" import="java.util.*,Controller1.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Ticket Details</title>
</head>
<body>
    <h1>Ticket Details</h1>
    <%
        List<Passenger_Data> ticketList = (List<Passenger_Data>) request.getAttribute("ticketList");
        double totalPrice = (double) request.getAttribute("totalPrice");
     %>
     <h3>PNR Number: <%=  ticketList.get(0).getTicketNumber()%></h3>
     <p><b>From Station: </b><%= ticketList.get(0).getFromStation() %></p>
      <p><b>To Station: </b><%= ticketList.get(0).getToStation() %></p>
       <p><b>DOJ: </b><%= ticketList.get(0).getDate() %></p>
        <p><b>TrainNumber: </b><%= ticketList.get(0).getTrainNumber()%></p>
         <p><b>TrainName: </b><%= ticketList.get(0).getTrainName() %></p>
         <p><b>Class: </b><%= ticketList.get(0).getTicketClass() %></p>
         
      <h2>Passenger Details</h2>
         
    <%
        // Display ticket details
        for (Passenger_Data passenger : ticketList) {
    %>
            <p>Passenger Name: <%= passenger.getPassengerName() %></p>
            <p>Age: <%= passenger.getPassengerAge() %></p>
            <p>Gender: <%= passenger.getPassengerGender() %></p>
            <p>Fare Price: <%= passenger.getFarePrice() %></p>
    <%
        }
    %>
    <h4>Total Ticket Cost: <%= totalPrice %></h4>
</body>
</html>
