package com.karmiel.savedata;

import com.karmiel.savedata.dto.QuantityDto;
import com.karmiel.savedata.model.Quantity;
import com.karmiel.savedata.repository.QuantityRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.support.GenericMessage;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(properties = "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration")
@Import(TestChannelBinderConfiguration.class)
class SaveDataAppTest {
    @Autowired(required = false)
    InputDestination producer;
    @Autowired(required = false)
    OutputDestination consumer;
    @MockBean
    QuantityRepository repository;

    @Test
    void saveDataToDBTest() {
        // given
        LocalDateTime startTest = LocalDateTime.now();
        QuantityDto quantityDto = new QuantityDto("A01", 42.);
        ArgumentCaptor<Quantity> quantityArgumentCaptor = ArgumentCaptor.forClass(Quantity.class);
        when(repository.save(quantityArgumentCaptor.capture())).thenReturn(mock(Quantity.class));

        // when
        String bindingName = "receiveNewData-in-0";
        producer.send(new GenericMessage<>(quantityDto), bindingName);

        // then
        verify(repository).save(any(Quantity.class));
        Quantity capturedQuantity = quantityArgumentCaptor.getValue();
        assertEquals("A01", capturedQuantity.getContainerId());
        assertEquals(42., capturedQuantity.getQuantity());
        assertThat(capturedQuantity.getSensorDate())
                .isAfter(startTest)
                .isBefore(LocalDateTime.now());
    }
}