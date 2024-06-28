# 📆투두앱 백엔드 서버 만들기 - 로그인 추가 버전

Spring 과제로 투두앱 백엔드 서버를 만들었습니다. <br/>

기존 백엔드 서버에 기능을 추가 및 개선사항을 추가하였습니다.

## 목차
- [요구 사항](#요구-사항)
- [개선 코드](#개선-코드)

## 요구 사항

<details>
<summary> Review </summary><div>
  
** 회원 가입 API **
  > - 회원 가입시 닉네임은 최소 3자 이상, 알파벳 대소문자(a~z, A~Z), 숫자(0~9)를 구성해야 합니다.
  > - 회원 가입시 비밀번호는 최소 4자 이상 구성해야 합니다.
  > - 회원 가입시 비밀번호와 닉네임에 같은 값이 포함된 경우 회원가입에 실패합니다.
  > - 비밀번호 확인 시 비밀번호와 정확하게 일치하지 않으면 실패합니다.
  > - DB에 존재하는 닉네임을 입력한 채 회원가입시 "중복된 닉네임입니다."라는 에러 메시지가 출력됩니다.
  > - 회원 가입 전에 같은 닉네임이 DB에 존재하는지 유효성 검증을 할 수 있습니다.

** 로그인 API **
  > - 로그인시 이메일과 닉네임, 비밀번호를 요구하였습니다.
  > - 로그인시 이메일을 기준으로 잡고 닉네임과 비밀번호 검증을 합니다.
>   - 하나라도 DB와 일치하지 않는다면 "닉네임 또는 패스워드를 확인해주세요" 라는 에러 메시지가 출력됩니다.

**게시글 작성 API**
  > - 게시글로 제목은 500자까지, 내용은 5000자까지만 입력 가능합니다.

</div></details>

<details>
<summary> Improvement </summary><div>

** Service 패키지 개선 **
  > - 인터페이스와 구현체를 분리하여 추상화하였습니다.

** CustomException **
  > - RuntimeException을 상속받아 CustomException을 구현하여 상황에 맞게 사용하였습니다.

** Spring AOP 적용 **
  > - 간단한 Spring AOP를 사용하여 부가기능을 추가하였습니다.

** 다양한 조건의 동적 쿼리 **
  > - 다음의 조건을 만족하는 동적 쿼리를 작성하였습니다.
>   - (포함) 조건은 주어진 값이 포함되어있다면 조회, (정확히 일치) 조건은 값이 정확히 일치해야 조회합니다.
>   - 제목 (포함)
>   - 태그 (포합)
>   - 카테고리 (정확히 일치)
>   - 게시글 상태 (정확히 일치)
>   - N일전 게시글

** 테스트 코드 **
  > - Controller, Service, Repository에 해당하는 간단한 테스트 코드를 작성하였습니다.

** AWS **
  > - S3를 이용해 이미지 업로드 기능을 구현하였습니다.

</div></details>

<details>
<summary> 개발 예정 </summary><div>
  
**로그인 API**
  > - JWT Cookie

**댓글 작성**
  > - 게시글에 종아요 기능

**테스트 코드**
  > - Controller, Service, Repository에 해당하는 모든 부분을 테스트 코드로 작성하기

**AWS**
  > - S3를 이용해 이미지 업로드 기능을 todo에서 가능하도록 만들기
  > - EC2를 이용해 애플리케이션 .jar 파일 배포하기 

</div></details>


## 개선 코드

<details>
<summary> Review </summary><div>

- 회원 가입 API

```Kotlin

// UserServiceImpl.kt
// 회원가입 검증
        if (userRepository.existsByEmail(request.email)) {
            throw IllegalStateException("중복된 이메일입니다.")
        }
        if (request.nickname.length < 3) {
            throw IllegalStateException("닉네임은 최소 3자 이상 필요합니다.")
        } else if (nicknameLower.contains(passwordLower)) {
            throw IllegalStateException("닉네임에 비밀번호가 포함될 수 없습니다.")
        } else if (userRepository.existsByNickname(request.nickname)) {
            throw IllegalStateException("중복된 닉네임입니다.")
        } else if (!isValidNickname(request.nickname)) {
            throw IllegalStateException("닉네임에 최소 4자 이상 필요하고, 알파벳 대소문자(a~z, A~Z), 숫자(0~9)로 구성되어야 합니다.")
        }

// 닉네임 유효성 검증
fun isValidNickname(nickname: String): Boolean {
        val regex = Regex("^(=*[a-zA-Z0-9]){4,}\$")
        return regex.matches(nickname)
}

// 비밀번호 확인
    override fun passwordCheck(request: PasswordRequest) {
        val userId = getUserIdFromToken()

        val user = userRepository.findById(userId) ?: throw ModelNotFoundException("User", null)

        if (!passwordEncoder.matches(request.password, user.password)) {
            throw IllegalStateException("비밀번호가 일치하지 않습니다.")
        }
    }

// 닉네임 확인
override fun isNicknameAvailable(nickname: String): Boolean {
        val alreadyUsed = userRepository.findByNickname(nickname)
        if (!isValidNickname(nickname)) {
            throw IllegalStateException("닉네임에 최소 4자 이상 필요하고, 알파벳 대소문자(a~z, A~Z), 숫자(0~9)로 구성되어야 합니다.")
        } else if (userRepository.existsByNickname(nickname)) {
            throw IllegalStateException("중복된 닉네임입니다.")
        }
        return alreadyUsed == null
    }

```

- 로그인 API

```Kotlin

// UserServiceImpl.kt
// 닉네임, 비밀번호 검증
    override fun login(request: UserRequest): LoginResponse {
        val user = userRepository.findByEmail(request.email) ?: throw ModelNotFoundException("User", null)

        if (request.nickname != user.nickname || !passwordEncoder.matches(request.password, user.password)) {
            throw InvalidCredentialException()
        }

        return LoginResponse(
            accessToken = jwtPlugin.generateAccessToken(
                subject = user.id.toString(),
                email = user.email,
            )
        )
    }

// InvalidCredentialException.kt
// CustomeException
data class InvalidCredentialException(
    override val message: String? = "닉네임 또는 패스워드를 확인해주세요."
) : RuntimeException()

```

- 게시글 작성 API

```Kotlin

// CreateTodoCardRequest.kt
// 글자 수 제한
    @field:NotBlank
    @field:Size(min = 1, max = 500)
    val title: String,

    @field:NotBlank
    @field:Size(min = 1, max = 5000)
    val content: String,

```

</div></details>

<details>
<summary> Improvement </summary><div>

- CustomException

```Kotlin

// IllegalArgumentException.kt
// 작성자 또는 패스워드 일치 확인
data class IllegalArgumentException(
    override val message: String? = "Writer or Password does not match."
) : RuntimeException()

// IllegalStateException.kt
// 이메일 중복 확인
data class IllegalStateException(
    override val message: String? = "Email is already in use"
) : RuntimeException()

// InvalidCredentialException.kt
// 닉네임 또는 패스워드 일치 확인
data class InvalidCredentialException(
    override val message: String? = "닉네임 또는 패스워드를 확인해주세요."
) : RuntimeException()

// ModelNotFoundException.kt
// model 확인
data class ModelNotFoundException(
    val modelName: String, val userid: Long?
) : RuntimeException("Model $modelName not found with id $userid")

```

- Spring AOP 적용

```Kotlin

// StopWatch 구현
class StopWatchAspect {
...
}

// StopWatch 구현 확인
class TestAop {
    @Around("execution(* org.todoapplication.todoapplication.domain.todo.todocard.service.TodoCardService.getTodoCardById(..))")
    fun thisIsAdvice(joinPoint: ProceedingJoinPoint) {
        println("AOP START!!!")
        joinPoint.proceed()
        println("AOP END!!!")
    }
}

```

- 다양한 조건의 동적 쿼리

```Kotlin

// TodoCardRepositoryImpl.kt
// 단순 검색
override fun search(searchCondition: Map<String, String>): List<TodoCard> {
...
}

//조건 확인
private fun allCond(searchCondition: Map<String, String>): BooleanBuilder {
...
}

// 제목 조건 검색
private fun titleLike(title: String?): com.querydsl.core.types.dsl.BooleanExpression {
...
}

// 카테고리 조건 검색 
private fun categoryEq(category: String?): com.querydsl.core.types.dsl.BooleanExpression {
...
}

// 태그 조건 검색
private fun tagLike(tag: String?): com.querydsl.core.types.dsl.BooleanExpression {
...
}

// 게시글 상태 조건 검색
private fun stateEq(stateCode: String?): com.querydsl.core.types.dsl.BooleanExpression {
...
}

// N일 전 게시글 검색
private fun withInDays(daysAgo: String?): com.querydsl.core.types.dsl.BooleanExpression {
...
}

```

- 테스트 코드

```Kotlin

// TodoCardControllerTest.kt
// 조회 테스트
class TodoCardControllerTest @Autowired constructor(
    private val mockMvc: MockMvc,
    private val jwtPlugin: JwtPlugin,
    @MockkBean private val todoCardService: TodoCardService,
) : DescribeSpec({
...
}

// UserRepositoryTest.kt
// 회원가입 테스트
class UserRepositoryTest {
...
}

// UserRepositoryTest.kt
// 회원가입 테스트
class CourseServiceTest : BehaviorSpec({
...
}
```

</div></details>

<details>
<summary> AWS </summary><div>

- S3

```Kotlin

// FileUploadController.kt

@RestController
@RequestMapping("/api")
class FileUploadController {
...
   fun uploadFile(@RequestPart(value = "file") file: MultipartFile): String {
   }
}

// AwsConfig.kt

class AwsConfig {
    @Value("\${amazon.aws.accesskey}")
    private val awsAccessKey: String? = null

    @Value("\${amazon.aws.secretkey}")
    private val awsSecretKey: String? = null

    @Value("\${amazon.aws.region}")
    private val awsRegion: String? = null
...
}

```

</div></details>

## 환경 설정<br>
![Kotlin](https://img.shields.io/badge/kotlin-%237F52FF.svg?style=for-the-badge&logo=kotlin&logoColor=white)
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJIDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white) 
![Jdk17](https://img.shields.io/badge/jdk17-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white"/)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![Swagger](https://img.shields.io/badge/-Swagger-%23Clojure?style=for-the-badge&logo=swagger&logoColor=white)
