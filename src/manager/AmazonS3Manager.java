package manager;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;

public class AmazonS3Manager {
		private AWSCredentialsProvider credentialsProvider;
		private AmazonS3Client s3;
		private String uploadID;
		private ArrayList<PartETag> tags;
		public AmazonS3Manager() {
	
			init();
		}
	
		private void init() {
			try 
			{
				//read AWS credentials
				credentialsProvider = new ClasspathPropertiesFileCredentialsProvider();
				//get cloudfront object using S3 credentials
				s3 = new AmazonS3Client(credentialsProvider);
				createS3Bucket();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	
		void createS3Bucket() throws InterruptedException 
		{
	
				//you may create using code (hint: extra credits!)
				String bucketName = "myvideohost";
				if(!s3.doesBucketExist(bucketName))
					s3.createBucket(bucketName);
		}
		
		public void initiateUpload(String key)
		{
			InitiateMultipartUploadRequest initRequest = new InitiateMultipartUploadRequest("myvideohost", key);
			InitiateMultipartUploadResult initResponse = s3.initiateMultipartUpload(initRequest);
			uploadID = initResponse.getUploadId();
			tags = new ArrayList<PartETag>();
		}
		public void uploadObject(InputStream inputStream, int partNum, String key, int offset) {
			//upload to S3
			UploadPartRequest uploadPartRequest = new UploadPartRequest();
			
			uploadPartRequest.withBucketName("myvideohost");
			uploadPartRequest.withInputStream(inputStream);
			uploadPartRequest.withPartNumber(partNum);
			uploadPartRequest.withKey(key);	
			uploadPartRequest.withUploadId(uploadID);
			uploadPartRequest.withFileOffset(offset);
			System.out.println(offset);
			
			UploadPartResult result = s3.uploadPart(uploadPartRequest);
			PartETag tag = result.getPartETag();
			tags.add(tag);
		}
		public void endUploading(String key)
		{	
			CompleteMultipartUploadRequest completeMultipartUploadRequest = new CompleteMultipartUploadRequest("myvideohost", key, uploadID,tags);
			s3.completeMultipartUpload(completeMultipartUploadRequest);
		}
		
		
		public void addItem(String key, File file)
		{
			PutObjectRequest putObjectRequest = new PutObjectRequest("myvideohost", key, file);
			putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);
			AccessControlList accessControlLis = new AccessControlList();
			CanonicalGrantee grantee = new CanonicalGrantee("Everyone");
//			EmailAddressGrantee grantee = new EmailAddressGrantee("baihui89@hotmail.com"); 
			accessControlLis.grantPermission(grantee, Permission.Read);
//			putObjectRequest.withAccessControlList(accessControlLis);
			s3.putObject(putObjectRequest);
			
		}
		public List<S3ObjectSummary> readAllFilesFromS3() {
			
			ObjectListing list = s3.listObjects("myvideohost");
			List<S3ObjectSummary> objects = list.getObjectSummaries();
			return objects;
		}
	
		
		public void deleteObject(String fileName) {
		
			//delete a video from S3
			DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest("myvideohost",fileName);			
			s3.deleteObject(deleteObjectRequest);
			System.out.println("Deletion Complete");
		}
}
