package com.zerobase.finance;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Encoders;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.beans.Encoder;

@SpringBootTest
class FinanceApplicationTests {

	@Test
	void contextLoads() {
		String test =Encoders.BASE64.encode(Jwts.SIG.HS512.key().build().getEncoded());
		System.out.println(test);
	}

}
