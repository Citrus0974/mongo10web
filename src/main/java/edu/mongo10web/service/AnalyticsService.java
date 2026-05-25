package edu.mongo10web.service;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.bson.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnalyticsService {
    private final MongoTemplate mongoTemplate;

    public AnalyticsService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    //Товары дороже определенной цены, отсортированные по убыванию
    public List<Document> getExpensiveProducts(int minCost) {
        Query query = new Query(Criteria.where("cost").gt(minCost))
                .with(Sort.by(Sort.Direction.DESC, "cost"));
        return mongoTemplate.find(query, Document.class, "product");
    }

    //Поставки с критическими статусами (DISPOSED или GONE)
    public List<Document> getUrgentSupplies() {
        Query query = new Query(
                new Criteria().orOperator(
                        Criteria.where("status").is("DISPOSED"),
                        Criteria.where("status").is("GONE")));
        return mongoTemplate.find(query, Document.class, "supply");
    }

    //Подсчет количества товаров каждого производителя
    public List<Document> getProductsCountByManufacturer() {
        GroupOperation groupOp = Aggregation.group("manufacturer").count().as("totalProducts");
        Aggregation aggregation = Aggregation.newAggregation(groupOp);
        return mongoTemplate.aggregate(aggregation, "product", Document.class).getMappedResults();
    }

    //АГРЕГАЦИЯ


    //Общее количество товара на складе и сумма затрат по каждому продукту (только для статусов PLACED и RESERVED)
    public List<Document> getProductStockMetrics() {
        MatchOperation match = Aggregation.match(Criteria.where("status").in("PLACED", "RESERVED"));
        GroupOperation group = Aggregation.group("product") // Группировка по DBRef ID продукта
                .sum("quantity").as("totalQuantity");

        // получить человеческое название товара из Product
        LookupOperation lookup = LookupOperation.newLookup()
                .from("product")
                .localField("_id")
                .foreignField("_id")
                .as("productDetails");

        Aggregation aggregation = Aggregation.newAggregation(match, group, lookup);
        return mongoTemplate.aggregate(aggregation, "supply", Document.class).getMappedResults();
    }

    //Средняя стоимость товаров внутри каждой категории
    public List<Document> getAverageCostByCategory() {
        GroupOperation group = Aggregation.group("category").avg("cost").as("avgCost");
        LookupOperation lookup = LookupOperation.newLookup()
                .from("category")
                .localField("_id")
                .foreignField("_id")
                .as("categoryDetails");

        Aggregation aggregation = Aggregation.newAggregation(group, lookup);
        return mongoTemplate.aggregate(aggregation, "product", Document.class).getMappedResults();
    }

    //Сортировка производителей по объемам поставок по убыванию
    public List<Document> getTopManufacturersBySupplyVolume() {
        // производитель из товара
        LookupOperation lookupProduct = LookupOperation.newLookup()
                .from("product")
                .localField("product")
                .foreignField("_id")
                .as("prod");

        UnwindOperation unwind = Aggregation.unwind("prod");

        // Группировка по производителю товара и сумма поставок
        GroupOperation group = Aggregation.group("prod.manufacturer")
                .sum("quantity").as("totalSuppliedVolume");

        SortOperation sort = Aggregation.sort(Sort.by(Sort.Direction.DESC, "totalSuppliedVolume"));

        Aggregation aggregation = Aggregation.newAggregation(lookupProduct, unwind, group, sort);
        return mongoTemplate.aggregate(aggregation, "supply", Document.class).getMappedResults();
    }
}
