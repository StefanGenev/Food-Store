package com.foodstore.repositories;

import com.foodstore.models.Product;
import com.foodstore.models.StoreStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

// Клас за заявки към БД за стоките в магазина

@Repository
public interface StoreStockRepository extends JpaRepository<StoreStock, Long> {
    Optional<StoreStock> findStoreStockById(Long id);

    // Наличност на продукт
    Optional<StoreStock> findStoreStockByProduct(Product product);

    // Наличност на продукт към дадена дата
    Optional<StoreStock> findStoreStockByProductAndAvailabilityDate(Product product, LocalDate availabilityDate);

    // Намира Наличност на продукт по най-близката до актуалната дата
    @Query(value = "SELECT * FROM STORE_STOCKS WHERE ID = :#{#product.id} ORDER BY AVAILABILITY_DATE DESC LIMIT 1", nativeQuery = true)
    Optional<StoreStock> findStoreStockByProductAndMaxAvailabilityDate(@Param("product") Product product);

    // изтрива наличност
    void deleteStoreStockById(Long id);

    // справка наличност по категории към дадена дата
    @Query(value = "SELECT * FROM STORE_STOCKS AS SS_RESULT\n" +
            "WHERE SS_RESULT.ID IN (\n" +
            "   SELECT ID FROM (\n" +
            "       SELECT SS.PRODUCT_ID, SS.ID AS ID\n" +
            "       FROM STORE_STOCKS AS SS\n" +
            "" +
            "       INNER JOIN PRODUCTS AS P\n" +
            "       ON P.ID = SS.PRODUCT_ID\n" +
            "" +
            "       WHERE SS.AVAILABILITY_DATE = :#{#filter.availabilityDate}\n" +
            "       AND P.CATEGORY_ID = :#{#filter.product.category.id}\n" +
            "" +
            "       GROUP BY SS.PRODUCT_ID, SS.ID\n" +
            "   ) AS SS_ID\n" +
            ") ", nativeQuery = true)
    List<StoreStock> findStoreStockByCategoryForDate(@Param("filter") StoreStock filter);

    // справка наличност към днешна дата
    @Query(value = "SELECT * FROM STORE_STOCKS AS SS_RESULT\n" +
            "WHERE SS_RESULT.ID IN (\n" +
            "   SELECT ID FROM (\n" +
            "       SELECT SS.PRODUCT_ID, SS.ID AS ID\n" +
            "       FROM STORE_STOCKS AS SS\n" +
            "" +
            "       INNER JOIN PRODUCTS AS P\n" +
            "       ON P.ID = SS.PRODUCT_ID\n" +
            "" +
            "       WHERE SS.AVAILABILITY_DATE = CURDATE()\n" +
            "" +
            "       GROUP BY SS.PRODUCT_ID, SS.ID\n" +
            "   ) AS SS_ID\n" +
            ") ", nativeQuery = true)
    List<StoreStock> findStoreStockForCurrentDate();

    // Взима докъдето е стигнал брояча на уникалния ключ на таблицата с наличностите
    @Query(value = "SELECT CASE WHEN MAX(ID) IS NULL THEN 0 ELSE MAX(ID) END FROM STORE_STOCKS", nativeQuery = true)
    long getMaxId();

    // взима за дадена дата и продукт
    @Query(value =  "SELECT * \n" +
                    "FROM STORE_STOCKS \n" +
                    "WHERE PRODUCT_ID = :#{#product.id}\n" +
                    "AND AVAILABILITY_DATE = :#{#availabilityDate}\n" +
                    "LIMIT 1\n", nativeQuery = true)
    Optional<StoreStock> getStoreStockByProductAndAvailabilityDate(@Param("product")Product product, @Param("availabilityDate")LocalDate availabilityDate);

    @Query(value = "SELECT * FROM STORE_STOCKS \n" +
            "WHERE ID IN (\n" +
            "\tSELECT ID FROM (\n" +
            "\t\tSELECT ID, MIN(DATEDIFF(AVAILABILITY_DATE, :#{#availabilityDate}))\n" +
            "\t\tFROM STORE_STOCKS\n" +
            "\t\tWHERE PRODUCT_ID = :#{#product.id}\n" +
            "\t) AS IDS\n" +
            ")\n" +
            "LIMIT 1;\n", nativeQuery = true)
    Optional<StoreStock> getClosestToGivenDateStoreStock(@Param("product") Product product, @Param("availabilityDate") LocalDate availabilityDate);
}