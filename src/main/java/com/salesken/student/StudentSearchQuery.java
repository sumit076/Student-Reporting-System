package com.salesken.student;

import co.student.clients.studentsearch.studentsearchClient;
import co.student.clients.studentsearch.core.*;
import co.student.clients.studentsearch.core.search.Hit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.studentsearch.core.StudentsearchRestTemplate;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class StudentSearchQuery {

    @Autowired
    private StudentsearchClient studentsearchClient;

    private final String indexName = "products";


    public String createOrUpdateDocument(Product product) throws IOException {

        IndexResponse response = studentsearchClient.index(i -> i
                .index(indexName)
                .id(product.getId())
                .document(product)
        );
        if (response.result().name().equals("Created")) {
            return new StringBuilder("Document has been successfully created.").toString();
        } else if (response.result().name().equals("Updated")) {
            return new StringBuilder("Document has been successfully updated.").toString();
        }
        return new StringBuilder("Error while performing the operation.").toString();
    }

    public Product getDocumentById(String productId) throws IOException {
        Product product = null;
        GetResponse<Product> response = studentsearchClient.get(g -> g
                        .index(indexName)
                        .id(productId),
                Product.class
        );

        if (response.found()) {
            product = response.source();
            System.out.println("Product name " + product.getName());
        } else {
            System.out.println("Product not found");
        }

        return product;
    }

    public String deleteDocumentById(String productId) throws IOException {

        DeleteRequest request = DeleteRequest.of(d -> d.index(indexName).id(productId));

        DeleteResponse deleteResponse = studentsearchClient.delete(request);
        if (Objects.nonNull(deleteResponse.result()) && !deleteResponse.result().name().equals("NotFound")) {
            return new StringBuilder("Product with id " + deleteResponse.id() + " has been deleted.").toString();
        }
        System.out.println("Product not found");
        return new StringBuilder("Product with id " + deleteResponse.id() + " does not exist.").toString();

    }

    public List<Product> searchAllDocuments() throws IOException {

        SearchRequest searchRequest = SearchRequest.of(s -> s.index(indexName));
        SearchResponse searchResponse = studentsearchClient.search(searchRequest, Product.class);
        List<Hit> hits = searchResponse.hits().hits();
        List<Product> products = new ArrayList<>();
        for (Hit object : hits) {

            System.out.print(((Product) object.source()));
            products.add((Product) object.source());

        }
        return products;
    }
}
