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

-	spring initailizr 에서 생성하였음

	-	< start.spring.io에서 선택한 설정 >

	```
	1. Project : Gradle Project
	2. Language : Java
	3. Boot 버전 : 2.3.x (SNAPSHOT, M~ 버전은 아직 정식 릴리즈된 버전이 아님 )
	4. Project Metadata : 맘대로..
	5. Dependencies : 이 후 필요한 것은 따로 추가
	   - Spring Web, Spring Data JPA, Lombok, H2 Database, MySQL Driver
	```

	-	gradle vs maven ?

		-	필요한 라이브러리들을 자동으로 다운받고 빌드까지 관리해줌

		-	요즘은 gradle 이 대세

		-	빌드배포툴로 ANT와 MAVEN의 단점을 보완했다고 함

		-	상속구조를 이용해 멀티모듈 구현 가능

		-	maven의 길고 떨어지는 가독성 개선 (xml 사용안함)

		-	성능 향상을 위한 다양한 기능 제공 (증분 빌드, 작업결과 캐싱, 병렬 실행 등 가능함)

### 2. 프로젝트 설정

-	프로젝트설정 파일 수정

	-	기존의 .properties 파일은 지우고 yml 파일 생성

		-	yml 장점 : 가독성, 하나의 파일에서 여러개의 설정파일 관리가능

		-	DemoProjectApplicationTests 에 **@ActiveProfiles("test")** 사용

		-	application.yml

			-	profile: test 를 통해 설정을 따로 분리할 수 있다. (ActiveProfiles에 따라 설정 변경가능)
			-	--- : 구분자, 맨위 설정이 디폴트

	-	properties 의 경우 (백기선-REST API 3챕터: 테스트용 DB와 설정 분리하기)

		-	DemoProjectApplicationTests 에 **@ActiveProfiles("test")** 사용

		-	테스트 패키지 기준 application.properties과 경로에 적용할 테스트용 properties을 생성

		-	이름을 동일하게 설정하면 컴파일 시 테스트용 properties 가 덮어씌워지므로 주의!

		-	오버라이딩 하고싶은 것만 application-test.properties 에 남기면 됨

		-	test/resources/application-test.properties

-	build.gradle 파일 설정 (+ querydsl 설정)

---

-	테스트 시 lombok 정상 작동안할 때

	-	인텔리제이 : Annotation Processors - Enable annotation processing 체크

-	compile 'org.apache.tomcat.embed:tomcat-embed-jasper:8.5.27' : jsp 사용

-	compile 'jstl:jstl:1.2' : jsp 내 jtl 사용

-	json 직렬화 관련 빈생성자 정리

-	modelmapper 정리

-	post put 관련 정리

-	권한 enum 부분 정리

-	REST API

	-	사실 게시판같은 작은 프로젝트를 구현하면서 REST API를 사용할 일은 많이 없을 것 같음

		-	다양한 클라이언트, 모바일 등을 사용하다면 유용하게 사용할 듯 싶음

		-	예시를 위해 추가함.

	-	resource, 링크생성, rest docs 문서 생략

-	TODO

	-	개념 정리
	-	optional 적극 활용
