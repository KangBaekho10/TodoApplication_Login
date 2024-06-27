import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.ObjectMetadata
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile


@RestController
@RequestMapping("/api")
class FileUploadController {
    @Autowired
    private val amazonS3: AmazonS3? = null

    @Value("\${amazon.aws.bucketName}")
    private val bucketName: String? = null

    @PostMapping("/upload")
    fun uploadFile(@RequestPart(value = "file") file: MultipartFile): String {
        try {
            val metadata = ObjectMetadata()
            metadata.contentType = file.contentType
            metadata.contentLength = file.size
            amazonS3!!.putObject(bucketName, file.originalFilename, file.inputStream, metadata)
            return "File uploaded successfully: " + file.originalFilename
        } catch (e: Exception) {
            return "Failed to upload file: " + e.message
        }
    }
}