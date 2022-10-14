package com.dft.api;

import com.dft.api.autoconfigure.SdkConfig;
import com.dft.api.salesorder.SalesOrderList;
import com.dft.api.salesorder.SalesOrderRow;
import com.dft.api.variant.Variant;
import com.dft.api.variant.VariantList;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@Log4j2
@RunWith(SpringRunner.class)
@SpringBootTest(classes= SdkConfig.class)
@Disabled
class KatanaSdkTest {

    KatanaSDK katanaSDK = new KatanaSDK("eba290d0-4adc-497d-a201-33427b3145ca");

    @Test
    public void getVariantTest() {
        Mono<VariantList> monoVariant = katanaSDK.getVariantBySku("AB-2068619");
        StepVerifier.create(monoVariant).thenConsumeWhile(variantList -> {
            log.debug("variant: {}", variantList);
            return true;
        }).verifyComplete();
    }

    @Test
    public void getVariantByIdTest() {
        Mono<Variant> monoVariant = katanaSDK.getVariantById(14196959);
        StepVerifier.create(monoVariant).thenConsumeWhile(variant -> {
            log.debug("variant: {}", variant);
            return true;
        }).verifyComplete();
    }

    @Test
    public void getOrderByNumberTest() {
        Mono<SalesOrderList> monoOrderList = katanaSDK.getSalesOrderByNumber("60976DE");
        StepVerifier.create(monoOrderList).thenConsumeWhile(orderList -> {
            log.debug("orderList: {}", orderList);
            return true;
        }).verifyComplete();
    }

    @Test
    public void getSalesOrderRowTest() {
        Mono<SalesOrderRow> monoRow = katanaSDK.getSalesOrderRow(25016761);
        StepVerifier.create(monoRow).thenConsumeWhile(orderRow -> {
            log.debug("orderRow: {}", orderRow);
            return true;
        }).verifyComplete();
    }
}
