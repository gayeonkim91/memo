# AGENTS.details.md — memo 프로젝트 컨벤션

## 1) Tidy First: 구조 변경 vs 행위 변경
- 구조 변경(Structural): 리네임, 함수 추출, 파일 이동, 중복 제거 등 “동작 동일”
- 행위 변경(Behavioral): 기능 추가/수정, 버그 수정 등 “동작 변경”
  규칙:
- 구조 변경과 행위 변경을 같은 커밋에 섞지 않는다.
- 둘 다 필요하면: 구조 변경(테스트 통과 확인) → 커밋 → 행위 변경 커밋

## 2) 아키텍처(2모듈)
모듈:
- domain
- interfaces

의존성 방향(강제):
- `interfaces` → `domain`만 허용
- `domain`은 `interfaces`를 참조/의존하면 안 된다.

### 2.1 domain = core 모듈(순수 도메인 아님)
포함 가능:
- `@Entity` (JPA 엔티티)
- `JpaRepository`
- `@Service` (유스케이스/도메인 서비스)
- 트랜잭션/정합성 규칙
  금지:
- `@RestController`, Web/DTO 계층 코드
- HTTP 타입 의존 (`HttpServletRequest`, `ResponseEntity` 등)

### 2.2 interfaces = Web Adapter
포함 가능:
- `@SpringBootApplication` (메인 클래스)
- `@RestController`
- DTO/Validation
- 예외 매핑(도메인 예외 → HTTP 에러)
  금지:
- 비즈니스 규칙을 컨트롤러에 구현
- 영속성 로직/엔티티 매핑을 컨트롤러에 직접 구현

패키지 네이밍 권장:
- domain: `com.gayeon.memo.domain.*`
- interfaces: `com.gayeon.memo.interfaces.*`
  불필요한 계층 증식 금지

## 3) 테스트 컨벤션
- 새 기능: 유스케이스 단위 테스트 우선 → 이후 컨트롤러 테스트(필요 시)
- 레거시/기존 동작 보호: characterization test(현상 유지 테스트)부터
- 테스트 이름에 “무엇을 보장하는지”가 드러나야 한다.
