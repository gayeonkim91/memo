# 005 - 메모 길이 검증

## 목표(Goal)
- Memo의 text 필드 max 길이를 결정하고 검증한다
- `POST /memo` 요청시, text 필드의 길이를 검증하여 500자를 초과하는 경우 `400 Bad Request`
- `PUT /memo` 요청시, text 필드의 길이를 검증하여 500자를 초과하는 경우 `400 Bad Request`

## 비목표(Non-goals)
- 로그인/인증/권한
- createdAt, updatedAt 필드 추가
- 응답 표준화(에러 바디 스펙 확장)
- 생성/수정 이벤트 감사 로그

## 제약(Constraints)
- 2모듈만 사용: domain / interfaces
- 불필요한 추상화/유틸/프레임워크 도입 금지
- 새 의존성 추가 금지(필요 시 근거를 본 plan에 명시)

## 완료 조건(DoD)
- [x] `./gradlew spotlessApply` 통과
- [x] `./gradlew check` 통과
- [x] 테스트 2개가 존재하고 통과한다
    - [x] 500자 초과의 메모 생성 → 400
    - [x] 500자 초과의 메모 수정 → 400
- [x] 엔드포인트 추가 없음

## 변경 허용 범위(Scope)
- domain: `com.gayeon.memo.domain.*`
- interfaces: `com.gayeon.memo.interfaces.*`

## 진행 단계(Steps) — TDD: 테스트 1개 단위
- [x] (go) Step 1: "500자 초과의 메모 생성 → 400" 테스트 추가(실패 Red 확인)
- [x] Step 2: 최소 구현으로 통과(Green)
- [x] Step 3: "500자 초과의 메모 수정 → 400" 테스트 추가(실패 Red 확인)
- [x] Step 4: 최소 구현으로 통과(Green)
- [x] Step 5: 리팩토링(동작 변경 없이)
- [x] Step 6: 품질 게이트 실행 및 요약
