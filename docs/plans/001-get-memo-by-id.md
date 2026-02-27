# 001 — 메모 단건 조회 (GET /memo/{id})

## 목표(Goal)
- `GET /memo/{id}` 요청 시 메모를 반환한다.
- 존재하면 `200 OK` + `{ "id": <long>, "text": "<string>" }`
- 없으면 `404 Not Found`

## 비목표(Non-goals)
- 목록 조회 (`GET /memos`)
- pagination/sort/search
- 인증/권한
- response 포맷 표준화(에러 바디 스펙 확장 등)
- 캐시/성능 최적화

## 제약(Constraints)
- 2모듈만 사용: domain / interfaces
- 불필요한 추상화/유틸/프레임워크 도입 금지
- 새 의존성 추가 금지(필요 시 근거를 본 plan에 명시)

## 완료 조건(DoD)
- [x] `./gradlew spotlessApply` 통과
- [x] `./gradlew check` 통과
- [x] 테스트 추가:
    - [x] 존재하는 id 조회 → 200 + body
    - [x] 없는 id 조회 → 404
- [x] plan에 없는 엔드포인트 추가 없음

## 변경 허용 범위(Scope)
- domain: `com.gayeon.memo.domain.*`
- interfaces: `com.gayeon.memo.interfaces.*`

## 진행 단계(Steps) — 테스트 1개 단위
- [x] (go) Step 1: 존재하는 id 조회 테스트 추가(실패 Red 확인)
- [x] Step 2: 최소 구현으로 통과(Green)
- [x] Step 3: 없는 id → 404 테스트 추가(실패 Red 확인)
- [x] Step 4: 최소 구현으로 통과(Green)
- [x] Step 5: 리팩토링(동작 변경 없이) — 네이밍/경계/중복 정리
- [x] Step 6: 품질 게이트 실행 및 요약

## 메모/결정
- Repository는 `findById` 기반으로 구현한다.

## 데이터 준비(Seed)
- 존재하는 id 조회 성공 케이스에서는 `MemoRepository.save(..)`로 메모를 미리 저장한 후 조회한다.
