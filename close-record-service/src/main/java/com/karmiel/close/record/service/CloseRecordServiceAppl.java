package com.karmiel.close.record.service;

import com.karmiel.close.record.service.bislog.CloseRecord;
import com.karmiel.close.record.service.dto.CloseRecordDto;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.util.function.Consumer;

// Alex Zakharov
@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class CloseRecordServiceAppl {
    @Resource
   final CloseRecord service;


    public static void main(String[] args) {
        SpringApplication.run(CloseRecordServiceAppl.class);
    }
@Bean
Consumer<CloseRecordDto> orderDtoConsumer(){
    return data -> {
service.closeRecord(data);
    };
}

}
