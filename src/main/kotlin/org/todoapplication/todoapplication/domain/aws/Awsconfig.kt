package org.todoapplication.todoapplication.domain.aws

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AwsConfig {
    @Value("\${amazon.aws.accesskey}")
    private val awsAccessKey: String? = null

    @Value("\${amazon.aws.secretkey}")
    private val awsSecretKey: String? = null

    @Value("\${amazon.aws.region}")
    private val awsRegion: String? = null

    @Bean
    fun amazonS3(): AmazonS3 {
        val awsCredentials = BasicAWSCredentials(awsAccessKey, awsSecretKey)
        return AmazonS3ClientBuilder.standard()
            .withCredentials(AWSStaticCredentialsProvider(awsCredentials))
            .withRegion(awsRegion)
            .build()
    }
}