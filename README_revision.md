# 주요 기능
## 사용자 인증 및 권한 관리
- JWT 기반 STATELESS 인증: 서버 세션을 사용하지 않고 JWT을 활용하여 구현했습니다.

- 역할 기반 접근 제어: Spring Security를 사용하여 일반 사용자(USER)와 관리자(ADMIN)의 권한을 분리했습니다.
```
/api/admin/**: 관리자 전용 기능 (콘텐츠 승인, 사용자 관리 등)*
api/auth/**: 누구나 접근 가능한 인증 엔드포인트
```
- 비밀번호 암호화: BCryptPasswordEncoder를 적용하여 사용자 비밀번호를 안전하게 해싱 저장합니다.

## 콘텐츠 관리 시스템 로직
- 게시글 CRUD: 게시글 작성, 조회, 수정, 삭제 기능을 제공합니다.
- Entity와 DTO를 분리하여 데이터 노출 최소화 및 API 하위 호환성을 확보했습니다.

## 보안 설정
- 보안 필터 체인: 서비스 API, H2 데이터베이스 콘솔, 모니터링 시스템 각각에 최적화된 독립적 보안 정책을 적용했습니다.
- Actuator 보호: 애플리케이션 상태 모니터링 엔드포인트를 관리자 권한으로 보호하여 시스템 정보 유출을 방지합니다.

## 예외 처리 및 유효성 검사
- Global Exception Handling: @RestControllerAdvice를 통해 전역적으로 예외를 관리하며, 일관된 에러 응답 포맷(ResponseCode)을 클라이언트에 제공합니다.
- JWT 유효성 검증: 토큰 만료, 변조, 지원되지 않는 형식 등을 세분화하여 처리하고 적절한 오류 메시지를 반환합니다.

## Redis 활용
- 분산 환경을 고려한 토큰 관리: JWT Stateless 특성을 유지하면서도, 로그아웃된 토큰 관리 및 Refresh Token 저장을 위해 Redis를 활용하여 보안성을 높였습니다.
- 효율적인 조회수 시스템 구현: 데이터베이스의 직접적인 쓰기 부하를 줄이기 위해 조회수 카운팅 로직을 구현했습니다.

# 실행 환경 구성
## application.yml
1. server.port 설정
2. spring.data.redis.port, host 설정

## 계정
1. user, user   | USER
2. user2, user2 | USER
3. admin, admin | ADMIN