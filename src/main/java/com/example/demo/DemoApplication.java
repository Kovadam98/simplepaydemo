package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Collections;
import java.util.UUID;

@SpringBootApplication
@Slf4j
public class DemoApplication implements CommandLineRunner {

	@Value("${merchant-key}")
	private String merchantKey;

	@Value("${url.start}")
	private String startUrl;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var startRequest = getStartRequest();
		String json = new ObjectMapper().writeValueAsString(startRequest);
		log.info(json);
		log.info(merchantKey);
		log.info("\n");
		String result = postMessage(json);
		log.info("result: {}", result);

	}

	private String postMessage(String message) {
		String signature = signMessageWithKey(message, merchantKey);
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		headers.set("Signature", signature);
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<String> entity = new HttpEntity<>(message, headers);
		return restTemplate.postForObject(startUrl, entity, String.class);
	}

	private String signMessageWithKey(String message, String key) {
		byte[] hash = hmacSha384(key.getBytes(), message.getBytes());
		return toBase64(hash);
	}

	private byte[] hmacSha384(byte[] key, byte[] message) {
		byte[] hmacSha284 = null;
		try {
			Mac mac = Mac.getInstance("HmacSHA384");
			SecretKeySpec secretKeySpec = new SecretKeySpec(key, "HmacSHA384");
			mac.init(secretKeySpec);
			hmacSha284 = mac.doFinal(message);
		}
		catch (Exception exception) {
			log.error(exception.getMessage());
		}
		return hmacSha284;
	}

	private String toBase64(byte[] code) {
		return Base64.getEncoder().encodeToString(code);
	}

	private StartRequest getStartRequest() {
		LocalDateTime localDateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
		return new StartRequest()
				.setSalt("806a7a4830351ef68a05f3e2b077fd93")
				.setMerchant("PUBLICTESTHUF")
				.setOrderRef(UUID.randomUUID().toString())
				.setCurrency("HUF")
				.setCustomerEmail("sdk_test@gmail.com")
				.setLanguage("HU")
				.setSdkVersion("SimplePayV2.1_Payment_PHP_SDK_2.0.7_190701:dd236896400d7463677a82a47f53e36e")
				.setMethods(Collections.singletonList("CARD"))
				.setTotal(5600)
				.setTimeout("2021-09-26T13:00:00+02:00")
				.setUrls(getUrls())
				.setInvoice(getInvoice());
	}

	private Urls getUrls() {
		return new Urls()
				.setSuccess("http://81.182.150.167/api/success")
				.setFail("http://81.182.150.167/api/fail")
				.setCancel("http://81.182.150.167/api/cancel")
				.setTimeout("http://81.182.150.167/api/timeout");
	}

	private Invoice getInvoice() {
		return new Invoice()
				.setName("name")
				.setCompany("")
				.setCountry("country")
				.setState("state")
				.setCity("city")
				.setZip("zip")
				.setAddress("address")
				.setAddress2("address2")
				.setPhone("202222222");
	}
}
