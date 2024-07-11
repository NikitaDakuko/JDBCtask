import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.nikita.jdbctask.DatabaseConfig;
import org.nikita.jdbctask.dao.ProductDAO;
import org.nikita.jdbctask.dto.ProductDTO;
import org.testcontainers.containers.PostgreSQLContainer;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TestProductDAO {
    static Connection connection = TestDatabaseConfig.getConnection();
    static ProductDAO productDAO = new ProductDAO(connection);

    static ProductDTO testDTO1 = new ProductDTO(1L,"test product", BigDecimal.valueOf(2D), 1, true);
    static ProductDTO testDTO2 = new ProductDTO(2L,"test product2", BigDecimal.valueOf(1D), 2, false);
    static ProductDTO testDTO3 = new ProductDTO(3L,"test product3", BigDecimal.valueOf(1984D), 50, false);
    static ProductDTO testDTO4 = new ProductDTO(4L,"test product4", BigDecimal.valueOf(20D), 900, true);
    static List<ProductDTO> testDTOs = Arrays.asList(testDTO1, testDTO2, testDTO3, testDTO4);

    static PostgreSQLContainer postgreSQLTestContainer = new PostgreSQLContainer<>(
            "postgres:16-alpine"
    );

    @BeforeAll
    static void beforeAll() {
        postgreSQLTestContainer.start();
        recreateDAO();
    }

    @AfterAll
    static void afterAll() {
        postgreSQLTestContainer.stop();
    }

    @AfterEach
    void afterEach(){
        recreateDAO();
    }

    static void recreateDAO(){
        try {
            DatabaseConfig.recreateSchema(connection);
            productDAO.create(testDTOs);
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Test
    public void getAllDAOtest() {
        List<ProductDTO> resultData = productDAO.getAll();
        assertEquals(testDTOs.toString(), resultData.toString());
    }

    @Test
    public void findByIdDAOtest() {
        ProductDTO testDTO = productDAO.findById(testDTO3.getId());
        assertEquals(testDTO3.toString(), testDTO.toString());
    }

    @Test
    public void getMultipleDAOtest() {
        List<ProductDTO> testData = Arrays.asList(testDTO1, testDTO2);

        List<Long> idArray = new ArrayList<>();
        for (ProductDTO p : testData)
            idArray.add(p.getId());

        List<ProductDTO> resultData = productDAO.getMultiple(idArray);

        assertEquals(testData.toString(), resultData.toString());
    }

    @Test
    public void updateDAOtest(){
        ProductDTO testProduct = new ProductDTO(2L, "update test", BigDecimal.valueOf(2), 555, true);

        assertNotEquals(
                productDAO.findById(testProduct.getId()).toString(),
                testProduct.toString());

        productDAO.update(testProduct);
        assertEquals(
                productDAO.findById(testProduct.getId()).toString(),
                testProduct.toString());
    }

    @Test
    public void deleteDAOtest() {
        int currentSize = productDAO.getAll().size();
        productDAO.delete(1L);
        assertEquals(currentSize - 1, productDAO.getAll().size());
    }
}
