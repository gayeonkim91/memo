# skills.md — 반복 작업 레시피(초기)

## 기본 커맨드
- 포맷: `./gradlew spotlessApply`
- 검증(정적분석+테스트): `./gradlew check`
- 테스트만: `./gradlew test`

## Red/Green/Refactor 규율
- 한 번에 테스트 1개만 진행한다.
- 테스트가 실패(Red)하는 것을 반드시 확인한다.
- 통과(Green)시키는 최소 코드만 작성한다.
- Green 이후에만 리팩토링한다.

## 경계 규칙(2모듈)
- interfaces: Controller/DTO/Validation/Error mapping
- domain: Entity/Repository/Service
- domain은 interfaces에 의존하지 않는다.
