package com.salesken.student;

import co.student.clients.studentsearch.StudentsearchClient;
import co.student.clients.json.jackson.JacksonJsonpMapper;
import co.student.clients.transport.StudentsearchTransport;
import co.student.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.studentsearch.client.RestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StudentSearchConfiguration
{
    @Bean
    public RestClient getRestClient() {
        RestClient restClient = RestClient.builder(
                new HttpHost("localhost", 9200)).build();
        return restClient;
    }

    @Bean
    public  StudentsearchTransport getStudentsearchTransport() {
        return new RestClientTransport(
                getRestClient(), new JacksonJsonpMapper());
    }


    @Bean
    public StudentsearchClient getStudentsearchClient(){
        StudentsearchClient client = new StudentsearchClient(getStudentsearchTransport());
        return client;
    }

}
