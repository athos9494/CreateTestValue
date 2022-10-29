package com.mystic.CreateTestValue;

import com.mystic.CreateTestValue.utils.S3Util;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CreateTestValueApplicationTests {
    @Test
    void contextLoads() {
        String url = " http://mystic-test-bucket.s3.ap-southeast-1.amazonaws.com/image/demo/c2218ee94bd04494a2eb68bedcee0e35%E9%87%91%E8%9E%8D%E5%BC%80%E6%94%BE%E5%B9%B3%E5%8F%B0%E6%8E%A5%E5%85%A5%E8%A7%84%E8%8C%83%28%E5%95%86%E6%88%B7%E7%89%88%29V1.3.doc?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20221016T142043Z&X-Amz-SignedHeaders=host&X-Amz-Expires=3599&X-Amz-Credential=AKIA45K5AWB2H3L4LL42%2F20221016%2Fap-southeast-1%2Fs3%2Faws4_request&X-Amz-Signature=d25221dcb89c0e2dca9aa484c8865324492c56e6c54abbc90f7a91f23e4f3368";
        System.out.println(S3Util.readInternetFile(url).length());
    }
}
