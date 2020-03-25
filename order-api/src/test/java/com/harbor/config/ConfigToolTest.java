package com.harbor.config;

import com.alibaba.druid.filter.config.ConfigTools;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by harbor on 2020/3/24.
 */
@Slf4j
public class ConfigToolTest {

    @Test
    public void test() throws Exception {



        String[] keys = ConfigTools.genKeyPair(512);

        log.info("privateKey: {}", keys[0]);
        log.info("publicKey: {}", keys[1]);

        String plainText = "123";
        String cipherText = ConfigTools.encrypt(keys[0], plainText);

        log.info("Cipher text: {}", cipherText);

//        String decryptText = ConfigTools.decrypt(keys[1], cipherText);
        String decryptText = ConfigTools.decrypt(ConfigTools.getPublicKey(keys[1]), cipherText);

        log.info("Plain text: {}", decryptText);

        Assert.assertEquals(plainText, decryptText);

        ConfigTools.main(new String[]{plainText});

    }

}
