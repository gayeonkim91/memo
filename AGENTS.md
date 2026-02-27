# AGENTS.md — memo 프로젝트 에이전트 규칙

이 저장소에서 코딩 에이전트(Codex CLI / Claude / Cursor Agent 등)는 아래 규칙을 **반드시** 따른다.

---

## 0) 최우선 규칙
1. 작업은 항상 `docs/plans/<작업>.md`(이하 plan) 기준으로 수행한다.
2. plan에 "go" 또는 "시작"이 명시된 경우:
    - plan에서 **체크되지 않은 다음 테스트 1개**를 찾는다.
    - 그 테스트를 먼저 작성/수정하고 **실패(Red)** 를 확인한다.
    - 그 다음 **최소 구현(Green)** 만 해서 통과시킨다.
    - 통과 후에만 **리팩토링(Refactor)** 한다.
3. 한 번에 **테스트 1개 단위**로 진행한다. (여러 테스트를 한 번에 추가하지 말 것)

(참고: 이 흐름은 Kent Beck의 TDD/Tidy First 방식에 맞춘 규율이다.)

---

## 1) 품질 게이트 (Definition of Done에 기본 포함)
아래 커맨드가 모두 통과해야 한다.

- 포맷 자동 적용: `./gradlew spotlessApply`
- 품질/정적 분석/테스트: `./gradlew check`
- (필요 시) 테스트만: `./gradlew test`

금지:
- 규칙을 끄거나(예: lint disable), 테스트를 ignore/skip 처리해서 통과시키기
- "경고는 무시" 같은 타협

---

## 2) Tidy First: 구조 변경 vs 행위 변경 분리
모든 변경은 아래 둘 중 하나로 분류한다.

- **구조 변경(Structural change)**: 리네임, 함수 추출, 파일 이동, 중복 제거 등 "동작은 동일"
- **행위 변경(Behavioral change)**: 기능 추가/수정, 버그 수정 등 "동작이 바뀜"

규칙:
- 구조 변경과 행위 변경을 **같은 커밋에 섞지 않는다.**
- 둘 다 필요하면: 구조 변경을 먼저(테스트 통과 확인) → 커밋 → 행위 변경 커밋

---

## 3) 변경 범위 통제 (Scope Control)
- plan에 명시된 범위(패키지/모듈/파일) 밖 수정 금지
- 새 의존성 추가 금지(필요하면 plan에 근거와 함께 명시 후 진행)
- 불필요한 추상화/유틸/프레임워크 도입 금지
    - "나중에 쓸 수도" 유형(YAGNI)은 금지

---

## 4) 프로젝트 아키텍처 원칙 (2모듈)
이 프로젝트는 **domain / interfaces** 두 모듈로만 구성한다.

### 4.1 의존성 방향 (강제)
- `interfaces` → `domain` 단방향만 허용
- `domain`은 `interfaces`를 참조/의존하면 안 된다.

### 4.2 domain 모듈의 역할 (Core)
- `domain`은 **핵심 비즈니스 + 영속성(JPA) + 유스케이스(Service)** 를 포함한다.
- 즉, `domain`은 "순수 도메인"이 아니라 **core 모듈**로 취급한다.
- 포함 가능:
    - `@Entity` (JPA 엔티티)
    - `JpaRepository`
    - `@Service` (유스케이스/도메인 서비스)
    - 트랜잭션/정합성 규칙
- 금지:
    - `@RestController`, Web/DTO 계층 코드
    - HTTP 요청/응답 타입에 대한 의존 (`HttpServletRequest`, `ResponseEntity` 등)

### 4.3 interfaces 모듈의 역할 (Web Adapter)
- `interfaces`는 **웹/API 어댑터**로만 동작한다.
- 포함 가능:
    - `@SpringBootApplication` (메인 클래스)
    - `@RestController`
    - DTO/Validation
    - 예외 매핑(도메인 예외 → HTTP 에러)
- 금지:
    - 비즈니스 규칙(검증/정합성/상태 전이)을 컨트롤러에 구현
    - JPA 엔티티 매핑/영속성 로직을 컨트롤러에 직접 구현

### 4.4 패키지 네이밍 권장 (단순 유지)
- `domain` 모듈:
    - `com.gayeon.memo.domain.*` (entity/repository/service)
- `interfaces` 모듈:
    - `com.gayeon.memo.interfaces.*` (controller/dto/error)

(패키지 구조는 plan에서 더 구체화할 수 있으나, 불필요한 계층 증식 금지)

---

## 5) 테스트 규칙
- 새 기능: 가능하면 **유스케이스 테스트(단위)** 먼저 → 그 다음 컨트롤러 테스트
- 레거시/기존 동작 보호 필요 시: Characterization test(현상 유지 테스트)부터
- 테스트는 "무엇을 보장하는지"가 이름에 드러나야 한다.

---

## 6) 산출물 형식 (에이전트 답변/PR 요약)
작업 완료 시 아래를 반드시 포함한다.

1) 변경 요약(3~8줄)
2) 파일별 변경 목록
3) 실행한 커맨드(`./gradlew check` 등)와 결과
4) 다음 작업(미완료 체크박스가 있으면 plan에 표시)

---
