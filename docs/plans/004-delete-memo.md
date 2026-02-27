# 004 - 메모 삭제 (DELETE /memo/{id})

## 목표(Goal)
- `DELETE /memo/{id}` 요청시 메모를 삭제한다
- 존재하면 메모를 삭제하고 `204 No Content`
- 요청한 메모가 존재하지 않으면 `404 Not Found`

## 비목표(Non-goals)
- Soft Delete ( `deleted_at` 같은 필드 추가)
- 삭제 이벤트/감사 로그
- 로그인/인증/권한
- 응답 표준화(에러 바디 스펙 확장)

## 제약(Constraints)
- 2모듈만 사용: domain / interfaces
- 불필요한 추상화/유틸/프레임워크 도입 금지
- 새 의존성 추가 금지(필요 시 근거를 본 plan에 명시)

## 완료 조건(DoD)
- [x] `./gradlew spotlessApply` 통과
- [x] `./gradlew check` 통과
- [x] 테스트 2개가 존재하고 통과한다
    - [x] 존재하는 id 삭제 성공 → 204, DB 반영 확인
    - [x] 없는 id 삭제 → 404
- [x] plan에 없는 엔드포인트 추가 없음

## 변경 허용 범위(Scope)
- domain: `com.gayeon.memo.domain.*`
- interfaces: `com.gayeon.memo.interfaces.*`

## 진행 단계(Steps) - TDD: 테스트 1개 단위
- [x] (go) Step 1: "존재하는 id 삭제 성공" 테스트 추가(실패 Red 확인)
- [x] Step 2: 최소 구현으로 통과(Green)
- [x] Step 3: "없는 id -> 404" 테스트 추가(실패 Red 확인)
- [x] Step 4: 최소 구현으로 통과(Green)
- [x] Step 5: 리팩토링(동작 변경 없이)
- [x] Step 6: 품질 게이트 실행 및 요약

## 메모/결정
- `MemoRepository.findById(id)`에서 없으면 404
- 컨트롤러에서는 DTO 검증만 하고 비즈니스 로직은 domain service에서 담당한다

## 데이터 준비(Seed)
- 성공/404 케이스 테스트에서는 `MemoRepository.save(...)`로 데이터를 미리 저장한다
