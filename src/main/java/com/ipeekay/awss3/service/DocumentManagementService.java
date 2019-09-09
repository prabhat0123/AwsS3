package com.ipeekay.awss3.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DocumentManagementService {

	@Autowired
	private AmazonS3 amazonS3Client;

	@Value("${app.awsServices.bucketName}")
	private String bucketName;

	@Value("${cloud.aws.region.static}")
	private String region;

	public void createBucket(String bucketName) {

		if (amazonS3Client.doesBucketExistV2(bucketName)) {
			log.info("Bucket name is not available." + " Try again with a different Bucket name.");
			return;
		}
		amazonS3Client.createBucket(bucketName);

	}

	public String normalUploadToS3(MultipartFile mediaFile) throws IOException {

		try {
			PutObjectRequest putObject = this.createS3PutObject(mediaFile);
			PutObjectResult result = amazonS3Client.putObject(putObject);
			//log.info(result.getVersionId());

			return "https://s3-" + region + ".amazonaws.com/" + bucketName + "/" + mediaFile.getOriginalFilename();
		} catch (AmazonClientException e) {
			log.error("Error uploading file to s3 bucket ", e);
			throw e;
		}

	}

	public void listAllBucket() {
		List<Bucket> buckets = amazonS3Client.listBuckets();
		for (Bucket bucket : buckets) {
			log.info(bucket.getName());
		}
	}

	public void listAllFile() {
		ObjectListing listing = amazonS3Client.listObjects(bucketName);
		List<S3ObjectSummary> summaries = listing.getObjectSummaries();

		while (listing.isTruncated()) {
			listing = amazonS3Client.listNextBatchOfObjects(listing);
			summaries.addAll(listing.getObjectSummaries());
		}
	}

	private PutObjectRequest createS3PutObject(MultipartFile mediaFile) throws IOException {

		try {
			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentType(mediaFile.getContentType());
			metadata.setContentLength(mediaFile.getSize());

			return new PutObjectRequest(bucketName, mediaFile.getOriginalFilename(), mediaFile.getInputStream(),
					metadata);
		} catch (IOException e) {
			log.error("Error reading multipart file {} ", e);
			throw e;
		}

	}

}
