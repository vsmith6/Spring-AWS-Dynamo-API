package com.dynamoapp.repository;

import com.dynamoapp.model.Job;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JobRepository {

    @Autowired
    private DynamoDbClient dynamoDbClient;

    private static final String TABLE_NAME = "Job";

    public void save(Job job) {
        Map<String, AttributeValue> item = new HashMap<>();
        item.put("id", AttributeValue.builder().s(job.getId()).build());
        item.put("name", AttributeValue.builder().s(job.getName()).build());
        item.put("dateApplied", AttributeValue.builder().s(job.getDateApplied()).build());
        item.put("result", AttributeValue.builder().s(job.getResult()).build());

        PutItemRequest putItemRequest = PutItemRequest.builder()
                .tableName(TABLE_NAME)
                .item(item)
                .build();

        dynamoDbClient.putItem(putItemRequest);
    }

    public Job getById(String id) {
        Map<String, AttributeValue> key = new HashMap<>();
        key.put("id", AttributeValue.builder().s(id).build());

        GetItemRequest getItemRequest = GetItemRequest.builder()
                .tableName(TABLE_NAME)
                .key(key)
                .build();

        Map<String, AttributeValue> item = dynamoDbClient.getItem(getItemRequest).item();
        return new Job(item.get("id").s(), item.get("name").s(), item.get("dateApplied").s(), item.get("result").s());
    }
 

    public void update(Job job) {
        Map<String, AttributeValue> key = new HashMap<>();
        key.put("id", AttributeValue.builder().s(job.getId()).build());

        Map<String, AttributeValueUpdate> updatedValues = new HashMap<>();
        updatedValues.put("name", AttributeValueUpdate.builder()
                .value(AttributeValue.builder().s(job.getName()).build())
                .action(AttributeAction.PUT)
                .build());
        updatedValues.put("dateApplied", AttributeValueUpdate.builder()
                .value(AttributeValue.builder().s(job.getDateApplied()).build())
                .action(AttributeAction.PUT)
                .build());
        updatedValues.put("result", AttributeValueUpdate.builder()
                .value(AttributeValue.builder().s(job.getResult()).build())
                .action(AttributeAction.PUT)
                .build());

        UpdateItemRequest updateItemRequest = UpdateItemRequest.builder()
                .tableName(TABLE_NAME)
                .key(key)
                .attributeUpdates(updatedValues)
                .build();

        dynamoDbClient.updateItem(updateItemRequest);
    }

    public void delete(String id) {
        Map<String, AttributeValue> key = new HashMap<>();
        key.put("id", AttributeValue.builder().s(id).build());

        DeleteItemRequest deleteItemRequest = DeleteItemRequest.builder()
                .tableName(TABLE_NAME)
                .key(key)
                .build();

        dynamoDbClient.deleteItem(deleteItemRequest);
    }

    public List<Job> findAll() {
        ScanRequest scanRequest = ScanRequest.builder()
                .tableName(TABLE_NAME)
                .build();

        ScanResponse scanResponse = dynamoDbClient.scan(scanRequest);
        List<Job> jobs = new ArrayList<>();
        for (Map<String, AttributeValue> item : scanResponse.items()) {
            jobs.add(new Job(item.get("id").s(), item.get("name").s(), item.get("dateApplied").s(), item.get("result").s()));
        }
        return jobs;
    }
}
