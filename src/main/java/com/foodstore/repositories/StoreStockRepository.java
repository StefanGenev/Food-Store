package com.foodstore.repositories;

import com.foodstore.models.Product;
import com.foodstore.models.StoreStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// Клас за заявки към БД за стоките в магазина

@Repository
public interface StoreStockRepository extends JpaRepository<StoreStock, Long> {
    Optional<StoreStock> findStoreStockById(Long id);

    Optional<StoreStock> findStoreStockByProduct(Product product);

    void deleteStoreStockById(Long id);

    @Query(value = "SELECT * FROM STORE_STOCKS AS SS_RESULT\n" +
            "WHERE SS_RESULT.ID IN (\n" +
            "   SELECT ID FROM (\n" +
            "       SELECT SS.PRODUCT_ID, SS.ID AS ID, MIN(DATEDIFF(SS.DATE_TIME, DATE(':dateTime'))) \n" +
            "       FROM STORE_STOCKS AS SS\n" +
            "" +
            "       INNER JOIN PRODUCTS AS P\n" +
            "       ON P.ID = SS.PRODUCT_ID\n" +
            "" +
            "       WHERE SS.DATE_TIME <= DATE(':dateTime')\n" +
            "       AND P.CATEGORY_ID = :category_id\n" +
            "" +
            "       GROUP BY SS.PRODUCT_ID, SS.ID\n" +
            "   ) AS SS_ID\n" +
            ") ", nativeQuery = true)
    List<StoreStock> findStoreStockByCategoryForDate(@Param("dateTime") String dateTime, @Param("category_id") String categoryId);
}