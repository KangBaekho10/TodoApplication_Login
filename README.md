# 📆투두앱 백엔드 서버 만들기 - 로그인 추가 버전

Spring 과제로 투두앱 백엔드 서버를 만들었습니다. <br/>

기본 투두앱 백엔드 서버에 대해 로그인 한 유저만 사용 가능하도록 만들었습니다.

## 목차
- [요구 사항](#요구-사항)
- [WHY?](#why)
- [기획 및 설계](#기획-및-설계)
- [코드 구조](#코드-구조)
- [복습 과제](https://github.com/KangBaekho10/TodoApplication_Login/tree/review)
- [개선 과제](https://github.com/KangBaekho10/TodoApplication_Login/tree/improvement)

## 요구 사항

<details>
<summary>STEP 1</summary><div>
  
**할 일 카드 작성 기능**
  > - `할 일 제목`, `할 일 내용`, `작성일`, `작성자 이름`을 입력받아 저장할 수 있습니다.
  > - 저장된 할 일의 정보를 반환받아 확인할 수 있습니다.

**선택한 할 일 조회 기능**
  > - 선택한 할 일의 정보를 조회할 수 있습니다.
  > - 반환받은 할 일 정보에는 `할 일 제목`, `할 일 내용`, `작성일`, `작성자 이름`을 정보가 들어있습니다.

**할 일 카드 목록 조회 기능**
  > - 등록된 할 일 전체를 조회할 수 있습니다.
  > - 조회된 할 일 목록은 작성일 기준 내림차순으로 정렬되어 있습니다.

**선택한 할 일 수정 기능**
  > - 선택한 할 일의 `할 일 제목`, `할 일 내용`, `작성자 이름`을 수정할 수 있습니다.
  > - 수정된 할 일의 정보를 반환받아 확인할 수 있습니다.

**선택한 할 일 삭제 기능**
  > - 선택한 게시글을 삭제할 수 있습니다.

</div></details>

<details>
<summary>STEP 2</summary><div>

**할 일 카드 완료 기능**
  > - 완료 처리할 할 일 카드는 목록 조회 시 `완료 여부` 필드가 `TRUE`로 내려갑니다.
  > - `완료 여부` 기본 값은 `FALSE`입니다.

**댓글 작성 기능**
  > - `작성자 이름`, `비밀번호`, `댓글`을 입력받아 저장할 수 있습니다.
  > - 응답에서 `비밀번호`는 제외하고 등록된 댓글을 반환합니다.

**댓글 수정 기능**
  > - `작성자 이름`, `비밀번호`를 입력받아 저장된 값과 일치하면 수정할 수 있습니다.
  > - 응답에서 `비밀번호`는 제외하고 수정된 댓글을 반환합니다.

**댓글 삭제 기능**
  > - `작성자 이름`, `비밀번호`를 입력받아 저장된 값과 일치하면 삭제할 수 있습니다.
  > - 응답에서 삭제 메시지와 상태 코드를 반환합니다.

**댓글 조회 기능**
  > - STEP 1에서 만든 할 일 조회 API의 응답에서 댓글을 조회할 수 있습니다.
  > - 연관되지 않은 댓글은 포함되지 않아야 합니다. 

</div></details>

<details>
<summary>STEP 3</summary><div>

**개발 완료**
  > - 회원가입 및 로그인이 가능합니다.
  > - 로그인한 사용자만 투두앱의 기능들을 사용할 수 있습니다.

**개발 예정**
  > - 소셜 로그인 기능

</div></details>

<details>
<summary>STEP 4</summary><div>
  
**개발 예정**
  
  > - Test code
  > - Query DSL

</div></details>

## WHY?

<details>
<summary>질문 사항</summary><div>

Q1. 요청한 사용자가 누구인지, api를 호출할 권한이 있는지를 어떻게 알 수 있을까요? <br/>
> A. 회원가입과 로그인을 진행하면 AccessToken을 발행하게 되고, 이 Token을 통해 Authorize가 확인되면 API를 사용할 수 있게 됩니다.<br/>

Q2. basic authentication과 bearer authentication은 어떤 차이가 있나요? 또한 장단점을 말해주세요.<br/>
> A1. basic authentication의 경우 ID와 Password를 Base64 인코딩한 값을 토큰으로 사용합니다. 간단하지만 정교하지 않습니다.<br/>
> A2. bearer authentication의 경우 JWT 혹은 OAuth를 통한 인증 용도로 사용합니다. 안전하고 확장이 쉽지만, 토큰 노출에 취약합니다.<br/>

</div></details>

## 기획 및 설계

#### 1. Event Storming

![image](https://github.com/KangBaekho10/TodoApplication/assets/166815465/a90fb0c5-b9fc-43c1-8f36-8c2338f96912)

#### 2. Use Case Diagram

![image](https://github.com/KangBaekho10/TodoApplication/assets/166815465/2ae4bff6-4df0-4ae2-be77-4597aa227e6b)

#### 3. API Specification

![image](https://github.com/KangBaekho10/TodoApplication_Login/assets/166815465/739aa128-f739-4e3c-804d-30b862065764)

#### 4. ERD
  
![image](https://github.com/KangBaekho10/TodoApplication_Login/assets/166815465/35b4912f-9e06-4eb7-babd-53ae839f2ed6)

## 코드 구조

할 일 카드에 대한 `TodoCard`와 댓글 대한 `Comment`로 API를 나누었습니다.

Spring의 Layer 구조와 DB에 맞추어 패키지를 `Controller` , `Dto` , `Service` , `Repository`, `Model`로 나누었습니다.

- 동작 과정

```Kotlin
1) Web Layer에 해당하는 'Controller'에서 Client로부터 Request 받는다.

2) Request에 맞는 함수를 'Dto'에서 찾아 Service Layer에 해당하는 'Service'로 넘겨준다.

3) 'Service'에서는 Request에 대한 실제 동작이 이루어진다. (삽입, 수정, 삭제, 조회)

4) 'Service'는 Entity를 통해 동작한 Data를 Repository Layer에 해당하는 'Repository'로 넘겨준다.

5) 'Repository'는 'Model'과 직접 연결되어 있고, 'Model'은 Repository에 의해 넘겨받은 Data를 DB에서 동작한다.

6) 동작한 내용은 다시 역순으로 진행하고, Web Layer를 통해 Client에게 Response 해준다.
```

<details>
<summary> TodoCard </summary><div>

- Controller

```Kotlin

// 단일 카드 조회
fun getTodoCard(@PathVariable userId: Long) : ResponseEntity<TodoCardResponse> {
...
}

// 전체 카드 조회
fun getTodoCardList(): ResponseEntity<List<TodoCardResponse>> {
...
}

// 할 일 카드 생성
fun createTodoCard(@RequestBody createTodoCardRequest: CreateTodoCardRequest): ResponseEntity<TodoCardResponse> {
...
}

// 할 일 카드 수정
fun updateTodoCard(
    @PathVariable userId: Long,
    @RequestBody updateTodoCardRequest: UpdateTodoCardRequest
) : ResponseEntity<TodoCardResponse> {
...
}

// 할 일 카드 삭제
fun deleteTodoCard(@PathVariable userId: Long) : ResponseEntity<Unit> {
...
}

```

- Service

```Kotlin

// 단일 카드 조회
fun getTodoCardById(userId: Long): TodoCardResponse

// 전체 카드 조회
fun getAllTodoCardList(): List<TodoCardResponse>

// 할 일 카드 생성
fun createTodoCard(request: CreateTodoCardRequest): TodoCardResponse

// 할 일 카드 수정
fun updateTodoCard(userId: Long, request: UpdateTodoCardRequest): TodoCardResponse

// 할 일 카드 삭제
fun deleteTodoCard(userId: Long)

```

- Repository

```Kotlin

interface TodoCardRepository: JpaRepository<TodoCard, Long> {}

```

- Model

```Kotlin
...
// 1:N
// DATA에 맞는 DB Column을 지정
class TodoCard (
...
    @OneToMany(mappedBy = "todoCard", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    val comment: MutableList<Comment> = mutableListOf()
...
)

// Column 일치하는 곳에 DATA 삽입
fun TodoCard.toResponse(): TodoCardResponse { 
...
}

```

</div></details>

<details>
<summary> Comment </summary><div>

- Controller

```Kotlin

// 댓글 생성
fun createComment(
  @PathVariable userId: Long,
  @RequestBody commentRequest: CommentRequest
): ResponseEntity<CommentResponse> {
...
}

// 댓글 수정
fun updateComment(
  @PathVariable userId: Long,
  @PathVariable commentId: Long,
  @RequestBody commentRequest: CommentRequest
): ResponseEntity<CommentResponse> {
...
}

// 댓글 삭제
fun updateComment(
  @PathVariable userId: Long,
  @PathVariable commentId: Long,
  @RequestBody commentRequest: CommentRequest
): ResponseEntity<CommentResponse> {
...
}

```

- Service

```Kotlin

// 댓글 조회 (할 일 카드에서 조회 가능)
fun getComment(commentId : Long) : CommentResponse

// userId로 할 일 카드를 지정하여 댓글 생성
fun createComment(userId: Long, request: CommentRequest) : CommentResponse

// userId로 할 일 카드를 지정하여 댓글 수정
fun updateComment(userId: Long, commentId: Long, request: CommentRequest) : CommentResponse

// userId로 할 일 카드를 지정하여 댓글 삭제
fun deleteComment(userId: Long, commentId: Long, request: DeleteCommentRequest)

```

- Repository

```Kotlin

interface CommentRepository: JpaRepository<Comment, Long> {
    fun findByTodoCardUseridAndCommentid(userId: Long, commentId: Long): Comment?
}

```

- Model

```Kotlin

class Comment (
...
// N:1
// DATA에 맞는 DB Column 지정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid")
    val todoCard: TodoCard,
...
)
// Column 일치하는 곳에 DATA 삽입
fun Comment.toResponse(): CommentResponse{
...
}

```

</div></details>

<details>
<summary> User </summary><div>

- Controller

```Kotlin

@PostMapping("/signup")
fun createUser(@RequestBody userRequest: UserRequest): ResponseEntity<UserResponse> {
...
}
// 사용자 생성
...
fun login(@RequestBody userRequest: UserRequest): ResponseEntity<UserResponse>{
...
}
// 사용자 로그인

```

- Service

```Kotlin

fun createUser(request: UserRequest): UserResponse
//사용자 생성

fun login(request: UserRequest): UserResponse
// 사용자 로그인

```

- Repository

```Kotlin

interface UserRepository: JpaRepository<User, Long> {
    fun findByEmailAndPassword(email: String, password: String): User
}

```

- Model

```Kotlin

class User(
)
...
// Column 일치하는 곳에 DATA 삽입
fun User.toResponse(): UserResponse {
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

