<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="home" value="active" scope="request" />

<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>${tableName}</title>
</head>
<body>
      <table>
        <c:forEach items="${records}" var="record">
           <tr>
           <td> test </td>
            <td> ${record.getId} </td>
            <td> ${record.getName} </td>
            <td> ${record.getPrice} </td>
            <td> ${record.getQuantity} </td>
            <td> ${record.getAvailability} </td>
           </tr>
        </c:forEach>
      </table>
</body>
</html>