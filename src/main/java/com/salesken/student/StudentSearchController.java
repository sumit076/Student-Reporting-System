package com.salesken.student;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class StudentSearchController {

    @Autowired
    private StudentSearchQuery studentSearchQuery;

    @PostMapping("/createOrUpdateDocument")
    public ResponseEntity<Object> createOrUpdateDocument(@RequestBody Product product) throws IOException {
          String response = studentSearchQuery.createOrUpdateDocument(product);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getDocument")
    public ResponseEntity<Object> getDocumentById(@RequestParam String productId) throws IOException {
       Product product =  studentSearchQuery.getDocumentById(productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @DeleteMapping("/deleteDocument")
    public ResponseEntity<Object> deleteDocumentById(@RequestParam String productId) throws IOException {
        String response =  studentSearchQuery.deleteDocumentById(productId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/searchDocument")
    public ResponseEntity<Object> searchAllDocument() throws IOException {
        List<Product> products = studentSearchQuery.searchAllDocuments();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
