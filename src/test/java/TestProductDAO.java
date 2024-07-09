import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.nikita.jdbctask.dao.ProductDAO;
import org.nikita.jdbctask.dto.ProductDTO;
import org.postgresql.util.PGmoney;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestProductDAO {
    static Connection connection = TestDatabaseConfig.getConnection();
    static ProductDAO productDAO = new ProductDAO(connection);

    static ProductDTO testDTO1 = new ProductDTO(1L,"test product", new PGmoney(2), 1, true);
    static ProductDTO testDTO2 = new ProductDTO(2L,"test product2", new PGmoney(1), 2, false);
    static ProductDTO testDTO3 = new ProductDTO(3L,"test product3", new PGmoney(1984), 50, false);
    static ProductDTO testDTO4 = new ProductDTO(4L,"test product4", new PGmoney(20), 900, true);

    static PostgreSQLContainer postgreSQLTestContainer = new PostgreSQLContainer<>(
            "postgres:16-alpine"
    );

    @BeforeAll
    static void beforeAll() {
        postgreSQLTestContainer.start();
        recreateProductDAO();
    }

    @AfterAll
    static void afterAll() {
        postgreSQLTestContainer.stop();
    }

    @AfterEach
    void afterEach(){
        recreateProductDAO();
    }

    static void recreateProductDAO(){
        TestDatabaseConfig.recreateProductsTable(connection);
        productDAO.create(testDTO1);
        productDAO.create(testDTO2);
        productDAO.create(testDTO3);
        productDAO.create(testDTO4);
    }

    @Test
    void createProductDAOtest(){
        assertEquals(4, productDAO.getAll().size());
    }

    @Test
    public void getAllProductDAOtest(){
        List<ProductDTO> testData = new ArrayList<>();
        List<ProductDTO> resultData = productDAO.getAll();
        System.out.println(resultData);
        testData.add(testDTO1);
        testData.add(testDTO2);
        testData.add(testDTO3);
        testData.add(testDTO4);

        for (int i = 0; i<testData.size();i++)
            isEqual(testData.get(i), resultData.get(i));
    }

    @Test
    public void findByIdProductDAOtest(){
        System.out.println(productDAO.getAll());
        ProductDTO testDTO = productDAO.findById(testDTO3.getId());
        isEqual(testDTO3, testDTO);
    }

    @Test
    public void deleteProductDAOtest(){
        int currentSize = productDAO.getAll().size();
        productDAO.delete(1L);
        assertEquals(currentSize - 1, productDAO.getAll().size());
    }

    private void isEqual(ProductDTO dto1, ProductDTO dto2){
        assertEquals(dto1.getId(), dto2.getId());
        assertEquals(dto1.getName(), dto2.getName());
        assertEquals(dto1.getPrice(), dto2.getPrice());
        assertEquals(dto1.getQuantity(), dto2.getQuantity());
        assertEquals(dto1.getAvailability(),dto2.getAvailability());
    }
}
