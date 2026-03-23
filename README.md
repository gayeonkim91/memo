# Memo

간단한 메모 CRUD 웹 애플리케이션입니다.  
Spring Boot 기반으로 구현되어 있으며, 작은 규모의 기능을 **plan 기반 개발**, **TDD**, **agentic coding workflow**로 연습하기 위한 프로젝트입니다.

## Features

- 메모 생성
- 메모 단건 조회
- 메모 목록 조회
- 메모 수정
- 메모 삭제
- 메모 텍스트 검증
  - `null` 불가
  - 공백 문자열 불가
  - 최대 2000자

## Tech Stack

- Java 17
- Spring Boot 3.5.7
- Spring Web
- Spring Data JPA
- H2 Database
- Gradle Kotlin DSL
- JUnit 5
- Spotless
- Checkstyle

## Project Structure

이 프로젝트는 2개 모듈로 구성됩니다.

```text
memo
├── domain
│   └── src/main/java/com/gayeon/memo/domain
│       ├── Memo.java
│       ├── MemoRepository.java
│       └── MemoService.java
├── interfaces
│   ├── src/main/java/com/gayeon/memo
│   │   ├── MemoApplication.java
│   │   └── interfaces
│   │       ├── MemoController.java
│   │       ├── CreateMemoRequestDto.java
│   │       ├── UpdateMemoRequestDto.java
│   │       └── MemoResponseDto.java
│   └── src/test/java/com/gayeon/memo
│       ├── MemoApplicationTests.java
│       └── interfaces/MemoControllerTest.java
├── docs
│   ├── agent
│   └── plans
└── AGENTS.md
```

### Module Responsibilities

#### `domain`
도메인 중심 모듈입니다.

포함 내용:
- JPA Entity
- Repository
- Service

#### `interfaces`
웹 진입점 모듈입니다.

포함 내용:
- Spring Boot 애플리케이션 시작점
- REST Controller
- Request/Response DTO
- HTTP 요청/응답 처리

## API

### 1. 메모 생성

**POST** `/memo`

#### Request

```json
{
  "text": "hello memo"
}
```

#### Response `201 Created`

```json
{
  "id": 1,
  "text": "hello memo"
}
```

#### Validation Error `400 Bad Request`
- `text`가 `null`
- `text`가 공백 문자열
- `text` 길이가 2000자를 초과하는 경우

---

### 2. 메모 단건 조회

**GET** `/memo/{id}`

#### Response `200 OK`

```json
{
  "id": 1,
  "text": "hello memo"
}
```

#### Not Found `404 Not Found`

---

### 3. 메모 목록 조회

**GET** `/memos`

#### Response `200 OK`

```json
[
  {
    "id": 2,
    "text": "second memo"
  },
  {
    "id": 1,
    "text": "first memo"
  }
]
```

- 최신 메모가 먼저 오도록 `id` 내림차순으로 반환합니다.

---

### 4. 메모 수정

**PUT** `/memo/{id}`

#### Request

```json
{
  "text": "updated memo"
}
```

#### Response `200 OK`

```json
{
  "id": 1,
  "text": "updated memo"
}
```

#### Error
- `400 Bad Request`: 잘못된 `text`
- `404 Not Found`: 대상 메모가 없는 경우

---

### 5. 메모 삭제

**DELETE** `/memo/{id}`

#### Response `204 No Content`

#### Not Found `404 Not Found`

## Getting Started

### 1. Clone

```bash
git clone https://github.com/gayeonkim91/memo.git
cd memo
```

### 2. Run

```bash
./gradlew bootRun
```

애플리케이션 실행 후 기본 접속 주소:

- App: `http://localhost:8080`
- H2 Console: `http://localhost:8080/h2-console`

### 3. H2 Connection Info

```text
JDBC URL: jdbc:h2:mem:memo
Username: sa
Password:
```

## Test

### 전체 테스트 실행

```bash
./gradlew test
```

### 품질 게이트 실행

```bash
./gradlew check
```

### 포맷 적용

```bash
./gradlew spotlessApply
```

## Development Rules

이 프로젝트는 단순 CRUD 예제이지만, 개발 방식은 비교적 엄격하게 관리합니다.

핵심 원칙:
- 작업은 `docs/plans/<작업>.md` 기준으로 진행
- plan 범위 밖 수정 금지
- 구조 변경과 행위 변경을 섞지 않음
- 새 의존성 추가는 plan 근거가 있을 때만 허용
- 품질 게이트(`spotlessApply`, `check`) 통과 필수

관련 문서:
- `AGENTS.md`
- `docs/agent/playbook.md`
- `docs/agent/AGENTS.detail.md`
- `docs/plans/*`

## Why this project?

이 저장소는 단순히 메모 CRUD를 만드는 데 목적이 있지 않습니다.  
작은 범위의 애플리케이션을 대상으로 다음을 연습하기 위한 프로젝트입니다.

- 멀티모듈 구조 설계
- REST API 설계와 테스트
- 작은 단위의 TDD
- plan 기반 작업 방식
- AI/agent와 협업 가능한 개발 문서 구조 정리

## Current Notes

현재 구현은 실용적인 최소 구조를 목표로 합니다.

예를 들어:
- 검증은 Controller에서 직접 수행
- 예외 처리 계층은 아직 크게 분리하지 않음
- 인증/인가 없음
- 외부 DB 연동 없음
- H2 in-memory DB 사용

즉, 이 프로젝트는 **확장 가능한 출발점**에 가깝습니다.

## License

개인 학습 및 실험용 프로젝트입니다.
