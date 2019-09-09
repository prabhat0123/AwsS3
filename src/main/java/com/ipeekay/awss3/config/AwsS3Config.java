package com.ipeekay.awss3.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class AwsS3Config {

	@Bean
	public AmazonS3 amazonS3Client(AWSCredentialsProvider credentialsProvider,
			@Value("${cloud.aws.region.static}") String region) {
		log.debug("Setting up the client");

		return AmazonS3ClientBuilder.standard().withCredentials(credentialsProvider).withRegion(region).build();
	}

}
