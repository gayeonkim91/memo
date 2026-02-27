# 003 — 메모 수정 (PUT /memo/{id})

## 목표(Goal)
- `PUT /memo/{id}` JSON `{ "text": "<string>" }`
- 존재하면 text를 수정하고 `200 OK` + `{ "id": <long>, "text": "<string>" }`
- 존재하지 않으면 `404 Not Found`
- blank text는 `400 Bad Request`

## 비목표(Non-goals)
- PATCH(부분 수정)
- 동시성 제어(ETag/버전 필드)
- 감사로그/수정자/수정시간 필드
- 응답 표준화(에러 바디 스펙 확장)
- 로그인/인증

## 제약(Constraints)
- 2모듈만 사용: domain / interfaces
- 불필요한 추상화/유틸/프레임워크 도입 금지
- 새 의존성 추가 금지(필요 시 근거를 본 plan에 명시)

## 완료 조건(DoD)
- [x] `./gradlew spotlessApply` 통과
- [x] `./gradlew check` 통과
- [x] 테스트 3개가 존재하고 통과한다
    - [x] 존재하는 id 수정 성공 → 200 + body, DB 반영 확인
    - [x] 없는 id 수정 → 404
    - [x] blank text → 400
- [x] plan에 없는 엔드포인트 추가 없음

## 변경 허용 범위(Scope)
- domain: `com.gayeon.memo.domain.*`
- interfaces: `com.gayeon.memo.interfaces.*`

## 진행 단계(Steps) — TDD: 테스트 1개 단위
- [x] (go) Step 1: "존재하는 id 수정 성공" 테스트 추가(실패 Red 확인)
- [x] Step 2: 최소 구현으로 통과(Green)
- [x] Step 3: "없는 id → 404" 테스트 추가(실패 Red 확인)
- [x] Step 4: 최소 구현으로 통과(Green)
- [x] Step 5: "blank → 400" 테스트 추가(실패 Red 확인)
- [x] Step 6: 최소 구현으로 통과(Green)
- [x] Step 7: 리팩토링(동작 변경 없이)
- [x] Step 8: 품질 게이트 실행 및 요약

## 메모/결정
- `MemoRepository.findById(id)`에서 없으면 404
- 컨트롤러에서는 DTO 검증만 하고 비즈니스 로직은 domain service에서 담당한다

## 데이터 준비(Seed)
- 성공/404 케이스 테스트에서는 `MemoRepository.save(...)`로 데이터를 미리 저장한다
