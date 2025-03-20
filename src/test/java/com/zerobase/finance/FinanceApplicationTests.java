package com.zerobase.finance;

import com.zerobase.finance.dto.ScrapingDataDto;
import com.zerobase.finance.utils.ScrappingUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Encoders;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

@SpringBootTest
class FinanceApplicationTests {

	@Test
	void contextLoads() {
		String test =Encoders.BASE64.encode(Jwts.SIG.HS512.key().build().getEncoded());
		System.out.println(test);
	}

	@Test
	void scrappingURLTest() throws IOException {
//		List<ScrapingDataDto> test = ScrappingUtil.getScrapingData("Of3215");
//		System.out.println(test.get(0).getDate());
	}

}
