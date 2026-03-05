# playbook.md — memo 프로젝트 작업 레시피

## 기본 커맨드
- 포맷: `./gradlew spotlessApply`
- 검증(정적분석+테스트): `./gradlew check`
- 테스트만: `./gradlew test`

## Red/Green/Refactor 규율
- 한 번에 테스트 1개만 진행한다.
- 테스트 실패(Red) 확인 후 최소 구현(Green).
- Green 이후에만 리팩토링.

## 경계 규칙(2모듈)
- interfaces: Controller/DTO/Validation/Error mapping
- domain: Entity/Repository/Service
- domain은 interfaces에 의존하지 않는다.

## 레시피: 저장된 데이터 테스트용 Seed
- 테스트 데이터는 `MemoRepository.save(...)`로 준비한다.
- 테스트 편의를 위한 seed 엔드포인트/배치/CLI는 만들지 않는다.

## 네이밍 규칙(클래스/패키지)
### 1) 공통 규칙
- 클래스명은 **UpperCamelCase**를 사용한다.
- 축약어는 가능한 피한다. (예: `Mgr`, `Hdlr` 금지)
- 의미 없는 접두/접미(`My`, `New`, `Impl`, `Util2`) 금지.
- 도메인 용어는 팀이 합의한 단어를 사용한다. (동의어 혼용 금지: 예 `Note/Memo` 중 `Memo`만 사용한다.)

### 2) 패키지 규칙(2모듈)
- domain 모듈: `com.gayeon.memo.domain.*`
    - `domain.memo` 같은 도메인별 하위 패키지는 기능이 추가되어 꼭 필요한 경우에만 사용한다.
- interfaces 모듈: `com.gayeon.memo.interfaces.*`
    - `interfaces.api`(controller/dto), `interfaces.error`(exception handler) 가 생성될 수 있지만 꼭 필요한 경우에만 추가한다.
- 패키지명은 **소문자**만 사용하고, 단어 구분은 점(.)으로 한다.
- 불필요한 계층 증식 금지(예: `controller.impl.v1.internal` 같은 구조 금지)

### 3) Suffix 규칙(역할이 드러나게)
#### interfaces
- Controller: `*Controller`
    - 예: `MemoController`
- Request DTO: `*RequestDto`
    - 예: `CreateMemoRequestDto`, `UpdateMemoRequestDto`
- Response DTO: `*ResponseDto`
    - 예: `MemoResponseDto`
- Exception Handler: `*ExceptionHandler` 또는 `GlobalExceptionHandler`

#### domain
- Entity: 도메인 명사 그대로(가능하면 suffix 없이)
    - 예: `Memo`
- Repository(Spring Data): `*Repository`
    - 예: `MemoRepository`
- Use-case/Service: `*Service`
    - 예: `MemoService`
- Domain Exception: `*Exception`
    - 예: `MemoNotFoundException`, `InvalidMemoTextException`

### 4) HTTP/API 네이밍
- 엔드포인트는 복수형 리소스 권장:
    - 생성/목록: `POST /memos`, `GET /memos`
    - 단건: `GET /memos/{id}`, `PUT /memos/{id}`, `DELETE /memos/{id}`
- HTTP status 규칙(현재 프로젝트 기준)
    - 생성: `201`
    - 조회/수정: `200`
    - 삭제: `204`
    - validation 실패: `400`
    - 리소스 없음: `404`

### 5) 테스트 네이밍
- 테스트 클래스: `*Test`
- 테스트 메서드 이름은 “무엇을 보장하는지”가 드러나게:
    - 예: `getMemo_returns200_whenExists()`
    - 예: `updateMemo_returns404_whenNotExists()`

### 6) 금지 네이밍
- `*Manager`, `*Helper`, `*Util`은 원칙적으로 금지(정말 필요하면 plan에 근거를 적고 도입).
- `*Impl` 금지(인터페이스/구현 분리가 필요하면 설계부터 다시).
