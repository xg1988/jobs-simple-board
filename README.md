## 간단한 게시판 만들기

### 링크 첨부

1. 사용 기술
    * Spring Boot 2.7.16, Java 17
    * Spring Security
    * JPA (H2, MySQL)
    * Thymeleaf
    * JUnit5 테스트 코드 작성
2. 기능
   * 로그인, 회원가입
   * 게시글 CRUD, 댓글, 파일
   * 게시판 타입 관리
3. 목표
   * 간단한 게시판을 구현하면서 여러가지 기술을 적용 하여 구현하는 샘플을 만들어본다. 
4. 아이디어
   * DynamoDB를 활용한 댓글 게시판
   * Redis를 활용한 동시성 처리
5. kafka 활용
   * kafka 설치, 실행, 설정(server.properties 변경)
   * spring-kafka config, producer, consumer 설정
   * 글, 댓글 등록시에 producer에 전송 확인