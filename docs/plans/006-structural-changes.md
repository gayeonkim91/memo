# 006 — 구조 일관성 및 네이밍 정리 (Structural only)

## 목표(Goal)
- 생성자/게터·세터 사용 규칙을 프로젝트 전반에 일관되게 맞춘다.
- 불필요한 코드를 제거하고, 네이밍을 프로젝트 규칙에 맞춘다.
- DTO 네이밍을 명확히 하고 재사용으로 인한 모호함을 제거한다.

## 비목표(Non-goals)
- 기능/행동 변경 금지(응답 코드/에러/DB 스키마 포함)
- 엔드포인트 추가/변경 금지
- 새 의존성 추가 금지
- 테스트 의미 변경 금지(테스트 수정은 “컴파일/리팩토링 필요 최소”만)

## 제약(Constraints)
- 변경은 domain/interfaces 내부 리팩토링만
- 변경 사항은 구조 변경만 포함(행동/스펙 변경 금지)

## 완료 조건(DoD)
- [x] `./gradlew spotlessApply` 통과
- [x] `./gradlew check` 통과
- [x] 기존 테스트 전부 통과
- [x] 변경 요약 + 파일별 변경 목록 작성

## 규칙(Decision)
- 엔티티/도메인 모델: 기본은 불변(필드 private), public setter 금지.
    - 상태 변경은 `updateText()` 같은 의도 있는 메서드로만 한다.
- 생성자 규칙(범위 제한):
    - **엔티티 + DTO에만 적용**한다.
    - 정적 팩토리는 도입하지 않는다.
    - JPA 엔티티는 `protected` 기본 생성자를 유지한다.
- DTO 규칙:
    - 가능하면 `record` 우선(필요 시 setter 허용).
    - DTO 이름은 `*RequestDto`, `*ResponseDto` suffix를 사용한다.
    - 이번 plan에서 DTO 대상은 아래 클래스들로 한정한다.
        - `MemoCreateRequest` -> `CreateMemoRequestDto`
        - Update에서 재사용 중인 DTO는 분리하여 `UpdateMemoRequestDto`를 새로 만들고 update는 이것을 사용
        - `MemoResponse` -> `MemoResponseDto`

## 진행 단계(Steps)
- [x] (go) Step 1: 현재 불일치 지점 목록화(파일/클래스)
- [x] Step 2: 작은 단위로 일관성 적용(컴파일 → 테스트 반복)
    - DTO 리네임/분리
    - 엔티티/DTO 생성자/세터 정리
    - 네이밍 정리
- [x] Step 3: 리팩토링
- [x] Step 4: 품질 게이트 실행 및 요약
