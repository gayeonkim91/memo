# 000-bootstrap — Create Memo (첫 슬라이스)

## 목표(Goal)
- `POST /memo` JSON `{ "text": "<string>" }`
- DB(H2) `memos`에 저장
- `201 Created`로 `{ "id": <long>, "text": "<string>" }` 반환

## 비목표(Non-goals)
- 로그인/인증
- 목록/조회/수정/삭제
- 페이징/검색
- "blank 금지" 외 추가 검증

## 제약(Constraints)
- 2모듈만 사용: domain / interfaces
- 불필요한 추상화/유틸/프레임워크 도입 금지
- 새 의존성 추가 금지(필요 시 근거를 본 plan에 명시)

## 완료 조건(DoD)
- [x] `./gradlew spotlessApply` 통과
- [x] `./gradlew check` 통과
- [x] 테스트 추가:
    - [x] 메모 생성 성공(201)
    - [x] text blank면 400
- [x] plan에 없는 엔드포인트 추가 없음

## 변경 허용 범위(Scope)
- domain: `com.gayeon.memo.domain.*`
- interfaces: `com.gayeon.memo.interfaces.*`
- (필요 시) gradle 설정 최소 변경

## 진행 단계(Steps) — TDD: 테스트 1개 단위
- [x] (go) Step 1: `POST /memo` 성공 테스트 추가(실패 확인)
- [x] Step 2: 최소 구현으로 통과
- [x] Step 3: blank → 400 테스트 추가(실패 확인)
- [x] Step 4: 최소 구현으로 통과
- [x] Step 5: 리팩토링(동작 변경 없이)
- [x] Step 6: 품질 게이트 실행 + 요약
