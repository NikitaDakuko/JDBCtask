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
            <td> Approval ID </td>
            <td> Detail ID </td>
            <td> Order Status </td>
            <td> Products </td>
            <td> Total amount </td>
            <td> Actions </td>
        </tr>

        <c:forEach items="${records}" var="record">
            <tr>
                <td> ${record.getId()} </td>
                <td> ${record.getOrderDetail().getId()} </td>
                <td> ${record.getOrderDetail().getOrderStatus()} </td>
                <td> ${record.getOrderDetail().getProducts()} </td>
                <td> ${record.getOrderDetail().getTotalAmount()} </td>
                <td>
                    <a href="editProduct?id=${record.getId()}">
                        <button onclick=> Edit </button>
                    </a>
                    <a href="deleteProduct?id=${record.getId()}">
                        <button onclick=> Delete </button>
                    </a>
                </td>
            </tr>
        </c:forEach>
    </table>
    <a href="createOrderApproval">
        <button onclick=> Add order approval </button>
    </a>
</body>
</html>