스프링 부트 기본 프로젝트 기록
------------------------------

-	인프런에서 많은 강의 들었다. 코드를 따라치기도하고, 정리도 하였지만 역시 최고의 복습은 직접 실습하는 것이다.

-	지금까지 배운 것을 활용해서 부트 기본 프로젝트를 생성해보자.

	-	백기선-스프링 프레임워크 핵심기술
	-	백기선-스프링 웹 MVC
	-	백기선-스프링 REST API 개발
	-	김영한-자바 ORM 표준 JPA 프로그래밍 - 기본편
	-	김영한-실전! 스프링 데이터 JPA
	-	김영한-스프링 입문 - 코드로 배우는 스프링 부트, 웹 MVC, DB 접근 기술
	-	++ 개인적으로 학습한 내용들 적용

-	사용할 기술 목표

	-	Spring Boot 2.3.x
	-	java 8++
	-	Spring Security
	-	jpa + queryDSL
	-	gradle
	-	test case
	-	rest api
	-	화면은 고려하지 않음
	-	DB
		-	개발: h2  
		-	운영: mysql

---

### 1. 프로젝트 생성

-	프로젝트 생성 방법은 2가지가 있다.

	-	인텔리제이에서 gradle 프로젝트 생성
	-	spring initailizr 을 통해 프로젝트 생성
		-	https://start.spring.io/
		-	필요 설정 선택 후 GENERATE
		-	다운받아진 zip 파일을 압축풀고 IntelliJ 에서 Open

	```text
	< 선택한 설정 >
	1. Project : Gradle Project
	2. Language : Java
	3. Boot 버전 : 2.3.x (SNAPSHOT, M~ 버전은 아직 정식 릴리즈된 버전이 아님 )
	4. Project Metadata : 맘대로..
	5. Dependencies : 이 후 필요한 것은 따로 추가
	   - Spring Web, Spring Data JPA, Lombok, H2 Database, MySQL Driver
	```

	-	gradle vs maven ?

		-	필요한 라이브러리글을 자동으로 다운받고 빌드까지 관리해줌

		-	요즘은 gradle 이 대세

		-	빌드배포툴로 ANT와 MAVEN의 단점을 보완했다고 함

		-	상속구조를 이용해 멀티모듈 구현 가능

		-	maven의 길고 떨어지는 가독성 개선 (xml 사용안함)

		-	성능 향상을 위한 다양한 기능 제공 (증분 빌드, 작업결과 캐싱, 병렬 실행 등 가능함)

	-	arifact : 빌드될 때의 결과물 (jar 이름)

-	프로젝트설정 파일 생성

	-	기존의 .properties 파일은 지우고 yml 파일 생성 (test 용 따로 생성)
	-	테스트 시 lombok 정상 작동안할 때
		-	인텔리제이 : Annotation Processors - Enable annotation processing 체크
