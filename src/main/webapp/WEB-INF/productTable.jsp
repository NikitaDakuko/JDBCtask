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
        <tr>
            <td> ID </td>
            <td> Name </td>
            <td> Price </td>
            <td> Quantity </td>
            <td> Availability </td>
            <td> Actions </td>
        </tr>

        <c:forEach items="${records}" var="record">
            <tr>
                <td> ${record.getId()} </td>
                <td> ${record.getName()} </td>
                <td> ${record.getPrice()} </td>
                <td> ${record.getQuantity()} </td>
                <td> ${record.getAvailability()} </td>
                <td>
                    <a href="editOrderApproval?id=${record.getId()}">
                        <button onclick=> Edit </button>
                    </a>
                    <a href="deleteOrderApproval?id=${record.getId()}">
                        <button onclick=> Delete </button>
                    </a>
                </td>
            </tr>
        </c:forEach>
    </table>
    <a href="createProduct">
        <button onclick=> Add product </button>
    </a>
</body>
</html>