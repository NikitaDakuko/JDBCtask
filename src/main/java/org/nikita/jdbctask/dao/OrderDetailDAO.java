package org.nikita.jdbctask.dao;

import org.nikita.jdbctask.DatabaseConfig;
import org.nikita.jdbctask.dto.OrderDetailDTO;
import org.nikita.jdbctask.interfaces.DAO;
import org.nikita.jdbctask.mapper.dto.OrderDetailDTOMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OrderDetailDAO implements DAO<OrderDetailDTO> {
    private final String tableName = "public.\"orderDetail\"";
    private final OrderDetailDTOMapper mapper = new OrderDetailDTOMapper();
    private final Connection connection;

    public OrderDetailDAO(Connection connection){
        this.connection = connection;
    }

    public OrderDetailDAO(){
        this.connection = DatabaseConfig.getConnection();
    }

    @Override
    public ResultSet create(OrderDetailDTO orderDetail){
        try {
            PreparedStatement insertDetailsStatement = connection.prepareStatement(
                    "INSERT INTO public.\"orderDetail\"(\n" +
                            "id, \"totalAmount\", \"orderStatus\")\n" +
                            "VALUES (?, ?, ?)\n" +
                            "RETURNING id;");
            insertDetailsStatement.setLong(1, orderDetail.getId());
            insertDetailsStatement.setBigDecimal(2, orderDetail.getTotalAmount());
            insertDetailsStatement.setString(3, orderDetail.getOrderStatus().name());
            return insertDetailsStatement.executeQuery();
        }
        catch (SQLException e) {
            System.out.println("Could not create orderDetail, SQLException: "+ e.getMessage());
        }
        return null;
    }

    @Override
    public OrderDetailDTO findById(Long id) {
        return mapper.listFromResult(defaultFindById(connection, tableName, id)).get(0);
    }

    @Override
    public List<OrderDetailDTO> getAll() {
        return mapper.listFromResult(defaultGetAll(connection, tableName));
    }

    @Override
    public void update(OrderDetailDTO orderDetail){
//        try {
//            PreparedStatement statement = connection.prepareStatement(
//                    "UPDATE " + tableName +
//                            " SET name = ?, price = "+orderApproval.getPrice().val + ", quantity = ?, available = ?" +
//                            " WHERE id = ?");
//            statement.setString(1, orderApproval.getName());
//            statement.setLong(2, orderApproval.getQuantity());
//            statement.setBoolean(3, orderApproval.getAvailability());
//            statement.setLong(4, orderApproval.getId());
//
//            if (statement.executeUpdate()!=1) throw new SQLException();
//        }
//        catch (SQLException e) {
//            System.out.println("Could not update orderDetail with id = " + product.getId() + ", SQLException: "+ e.getMessage());
//        }
    }

    @Override
    public void delete(Long id){
        defaultDelete(connection, tableName, id);
    }
}
