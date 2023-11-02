package com.chosu.jobssimpleboard.kafka;

import com.chosu.jobssimpleboard.kafka.KafkaCustomProducer;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = { "spring.config.location=classpath:application-aws.yml" })
public class KafkaTest {

    @Autowired
    KafkaCustomProducer kafkaCustomProducer;

    @DisplayName("1. kafka test")
    @Test
    void test_1() throws InterruptedException, JSONException {
        for(int i =0 ; i< 10; i++){

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("test", "test");
            jsonObject.put("name", "test");
            jsonObject.put("type", "test");
            jsonObject.put("id", "test");
            jsonObject.put("ctnt", "testtesttesttesttesttesttesttest");
            kafkaCustomProducer.send(jsonObject.toString());
            Thread.sleep(100);
        }
    }
}
