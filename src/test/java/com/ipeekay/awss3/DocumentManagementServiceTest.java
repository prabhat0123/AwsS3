package com.ipeekay.awss3;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.amazonaws.services.s3.AmazonS3;
import com.ipeekay.awss3.service.DocumentManagementService;

@RunWith(SpringRunner.class)
public class DocumentManagementServiceTest {
	

	@InjectMocks
	private DocumentManagementService documentManagementService;
	
	@Mock
	private AmazonS3 amazonS3;
	
	
	
	@Test
	public void test_uploadToS3_Success() throws IOException {

		ReflectionTestUtils.setField(documentManagementService, "region", "ap-southeast-1");
		ReflectionTestUtils.setField(documentManagementService, "bucketName", "mock-bucket");

		MockMultipartFile mockFileToUpload = new MockMultipartFile("name", "mockFile.png", "image/png",
				"content".getBytes());
		documentManagementService.normalUploadToS3(mockFileToUpload);

		

	}


}
