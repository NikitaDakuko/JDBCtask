package org.nikita.jdbctask.dao;

import org.nikita.jdbctask.DatabaseConfig;
import org.nikita.jdbctask.dto.OrderApprovalDTO;
import org.nikita.jdbctask.dto.OrderProductDTO;
import org.nikita.jdbctask.dto.ProductDTO;
import org.nikita.jdbctask.interfaces.DAO;
import org.nikita.jdbctask.mapper.dto.OrderApprovalDTOMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderApprovalDAO implements DAO<OrderApprovalDTO> {
    private final String tableName = "public.\"orderApproval\"";
    private final OrderDetailDAO orderDetailDAO;
    private final OrderProductDAO orderProductDAO;
    private final OrderApprovalDTOMapper mapper = new OrderApprovalDTOMapper();
    private final Connection connection;

    public OrderApprovalDAO(Connection connection){
        this.connection = connection;
        this.orderDetailDAO = new OrderDetailDAO(connection);
        this.orderProductDAO = new OrderProductDAO(connection);
    }

    public OrderApprovalDAO(){
        this.connection = DatabaseConfig.getConnection();
        this.orderDetailDAO = new OrderDetailDAO(connection);
        this.orderProductDAO = new OrderProductDAO(connection);
    }

    @Override
    public ResultSet create(OrderApprovalDTO orderApproval){
        try {
            long insertedOrderDetailId = returnIds(orderDetailDAO.create(orderApproval.getOrderDetail())).get(0);

            List<Long> productIds = new ArrayList<>();
            for (ProductDTO p: orderApproval.getOrderDetail().getProducts())
                productIds.add(p.getId());

            orderProductDAO.create(new OrderProductDTO(insertedOrderDetailId, productIds));

            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO public.\"orderApproval\"(\n" +
                            "id, \"orderDetailId\")\n" +
                            "VALUES (?, ?)");
            statement.setLong(1, orderApproval.getId());
            statement.setLong(2, insertedOrderDetailId);

            if (statement.executeUpdate()!=1) throw new SQLException();
        } catch (SQLException e) {
            System.out.println("Could not create orderApproval, SQLException: "+ e.getMessage());
        }
        return null;
    }

    @Override
    public OrderApprovalDTO findById(Long id) {
        return mapper.fromResult(defaultFindById(connection, tableName, id));
    }

    @Override
    public List<OrderApprovalDTO> getAll() {
        return mapper.listFromResult(defaultGetAll(connection, tableName));
    }

    @Override
    public void update(OrderApprovalDTO orderApproval){
    }

    @Override
    public void delete(Long id){
        defaultDelete(connection, tableName, id);
    }
}
