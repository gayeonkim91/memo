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

## 레시피: GET 테스트용 Seed
- 테스트 데이터는 `MemoRepository.save(...)`로 준비한다.
- 테스트 편의를 위한 seed 엔드포인트/배치/CLI는 만들지 않는다.
