# ZeroBase 3차 프로젝트 
배당금 정보 조회 서버 프로젝트

# 사용 스택
Spring data jpa, QueryDsl, H2, JJWT, Spring Security, Redis, Docker

# 구현 기능
- 유저 회원 가입 : DB에 비밀 번호 저장 시 암호화
- 유저 로그인 : JWT 토큰 기반
- 회사 정보 저장하기 : 인증된 사용자인 경우 회사 정버 스크래핑 가능
- 회사 정보 업데이트 : 매일 일정한 시간에 회사 정보 업데이트 가능
- 전체 회사 목록 가져오기 : Page interface에 맞게 데이터 반환
- 배당금 상세 정보 : Redis를 사용한 캐싱으로 한번 불러온 회사는 빠르게 접근 가능
- 자동 완성 : 회사 이름 검색 용, 10개 씩 리턴
- 회사 삭제 : 배당금 정보, 캐시, 회사 전부 삭제. 인증된 사용자만 가능

## [PostMan Documentation](https://documenter.getpostman.com/view/40737812/2sAYkGJy83)

Redis -> Docker로 서버 실행