<!DOCTYPE html>


<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>${tableName}</title>
</head>
<body>
      <table>
        <c:forEach items="${records}" var="record">
           <tr>
            <td> ${record.getId()} </td>
            <td> ${record.getName()} </td>
            <td> ${record.getPrice()} </td>
            <td> ${record.getQuantity()} </td>
            <td> ${record.getAvailability()} </td>
           </tr>
        </c:forEach>
      </table>

    </c:if>
</body>
</html>