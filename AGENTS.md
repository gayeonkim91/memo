# AGENTS.md — memo 프로젝트 에이전트 규칙

## 우선순위
1) docs/plans/<작업>.md (plan)
2) 이 파일(AGENTS.md)
3) docs/agent/playbook.md
4) docs/agent/AGENTS.details.md (plan에서 명시된 경우에만)

## 절대 규칙
- plan 기준으로만 작업한다. plan 범위 밖 수정 금지.
- plan에 명시되지 않으면 동작(behavior)을 바꾸지 않는다.
- 새 의존성 추가 금지(필요하면 plan에 근거와 함께 명시 후 진행).

## 진행 규율(최소)
- plan에 "go/시작"이 있으면: 테스트 1개 단위로 Red → Green → Refactor.

## 품질 게이트(필수)
- `./gradlew spotlessApply`
- `./gradlew check`
- (필요 시) `./gradlew test`
  금지: 테스트 skip/ignore, 규칙 끄기(lint disable), “경고 무시”로 통과시키기

## 범위/안전
- plan에 허용된 파일/패키지/모듈만 수정한다.
- plan에 없으면 DB 스키마/마이그레이션, 트랜잭션 경계 변경 금지.

## 산출물
- 변경 요약(3~8줄)
- 파일별 변경 목록
- 실행한 커맨드와 결과
- plan 체크박스(done/next) 갱신
