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

### 2. 프로젝트 설정 메모

-	프로젝트설정 파일 수정

	-	기존의 .properties 파일은 지우고 yml 파일 생성하여 사용할 수 있다.

		-	yml 장점 : 가독성, 하나의 파일에서 여러개의 설정파일 관리가능

		-	DemoProjectApplicationTests 에 **@ActiveProfiles("test")** 사용

		-	application.yml

			-	profile: test 를 통해 설정을 따로 분리할 수 있다. (ActiveProfiles에 따라 설정 변경가능)
			-	--- : 구분자, 맨위 설정이 디폴트

	-	기존의 .properties 를 사용하는 경우 (백기선-REST API 3챕터: 테스트용 DB와 설정 분리하기)

		-	DemoProjectApplicationTests 에 **@ActiveProfiles("test")** 사용

		-	테스트 패키지 기준 application.properties과 경로에 적용할 테스트용 properties을 생성

		-	이름을 동일하게 설정하면 컴파일 시 테스트용 properties 가 덮어씌워지므로 주의!

		-	오버라이딩 하고싶은 것만 application-test.properties 에 남기면 됨

		-	test/resources/application-test.properties

-	querydsl 설정

	-	querydsl 이란? (인프런-김영한님 강의 소개 참고)

		-	sql를 java로 type-safe 하게 개발할 수 있게 해줌
		-	기존 쿼리 실행은 쿼리가 실제 실행될 때 문법오류가 발견되지만 querydsl을 사용함으로 컴파일 시점에 발견할 수 있음
		-	최신 백엔드 기술은 스프링 부트 + 스프링 데이터 JPA 를 조합하여 사용함
			-	하지만 해결하자 못하는 한계점이 있음 (복잡한쿼리, 동적쿼리)
			-	이를 해결하도록 도와줌!!

	-	build.gradle 추가한 부분 (기억보다 기록을 참고 https://jojoldu.tistory.com/372\)

		```java
		/*
		querydsl 플러그인
		아래 플러그인이 있어야 querydsl의 도메인 모델인 QClass들이 생성됨
		*/
		plugins{
		    ...
		    id "com.ewerk.gradle.plugins.querydsl" version "1.0.10"
		    ...
		}


		// querydsl 의존성
		dependencies{
		    ...
		    compile("com.querydsl:querydsl-jpa:4.2.2")
		    compile("com.querydsl:querydsl-apt:4.2.2")
		    ...
		}


		/*
		querydsl 설정
		위에서 설정한 플러그인을 사용하는 task 생성
		gradle 탭 - other - comileQuerydsl 실행 시 프로젝트의 Entity들의 QClass가 생성됨
		*/
		def querydslSrcDir = 'src/main/generated' // QClass 생성 위치


		querydsl {
		    library = "com.querydsl:querydsl-apt"
		    jpa = true
		    querydslSourcesDir = querydslSrcDir
		}
		sourceSets {
		    main {
		        java {
		            srcDirs = ['src/main/java', querydslSrcDir]
		        }
		    }
		}
		compileQuerydsl{
		    options.annotationProcessorPath = configurations.querydsl
		}
		configurations {
		    querydsl.extendsFrom compileClasspath
		}
		```

	-	예시

		```java
		@RequiredArgsConstructor
		public class MemberRepositoryImpl implements MemberRepositoryCustom {


		    private final JPAQueryFactory queryFactory;


		    @Override
		    public List<Member> findAllCustomMember() {
		        return queryFactory.selectFrom(member).fetch();
		    }
		}
		```

-	spring security 설정

	-	build.gradle 추가
		-	implementation 'org.springframework.boot:spring-boot-starter-security'
	-	SecurityConfig 파일 추가 (인프런 - 백기선님 강의 참고 )

		-	csrf (Cross-site request forgery)
			-	시큐리티는 기본적으로 csrf 에 대해 방어기능을 제공해줌
			-	사용하려면 토큰 생성이 필요하므로 일단 해제하였음
		-	시큐리티를 적용할 위치 지정 가능
		-	로그인 진행할 url 지정 가능 (이 때 인증은 시큐리티가 진행해줌)
		-	로그인 성공 후 처리할 url 지정가능

			-	인증성공 후 SecurityContextHolder.getContext().getAuthentication(); 를 통해 인증한 정보 가져올 수 있음
			-	인증성공 후 (... @AuthenticationPrincipal User user ...) 를 통해 파라미터로 바로 주입받을 수 있음

			-	User를 받지않고 프로젝트에서 사용할 사용자어댑터 클래스를 사용해서 받을 수도 있음 (백기선님 강의참조)

		-	로그아웃 url 지정가능

			-	JSESSIONID : 서버에서 생성한 세션을 구분하기 위해 세션ID를 쿠키에 넣음. 그래서 로그아웃 시 이 쿠키를 삭제함

		-	PasswordEncoder 를 통해 쉽게 비밀번호 암호화 처리

			-	PasswordEncoderFactories.createDelegatingPasswordEncoder();

		-	필터 적용 가능 (sso, 커스텀 등)

			-	.addFilterBefore(ssoFilter, BasicAuthenticationFilter.class)

	-	UserDetailService 구현

		-	로그인 처리는 UserDetailService를 구현 후 진행할 수있다.
		-	UserDetailService는 loadUserByUsername 메서드를 override 해야해는데, 이 메서드에서 조회한 사용자 정보와 로그인에 사용한 정보와 비교하여 인증을 처리함 (비교과 인증부여는 시큐리티가...)

		-	public class MemberService implements UserDetailsService

	-	권한 enum 부분 메모

		```java
		@ElementCollection(fetch = FetchType.EAGER)
		@Enumerated(EnumType.STRING)
		private Set<MemberRoles> roles;
		```

		-	권한은 다음과 같이 enum 으로 정의하였는데, JPA 사용 시 주의해야 함
		-	@ElementCollection(fetch = FetchType.EAGER) : 권한을 여러개를 가질 수 있으니(여러개의 enum을 가질 수 있음), Set이나 List는 기본으로 LAZY 임. 권한은 거의 사용자 조회 시 필요한 정보이니 EAGER로 설정(즉시)
		-	@Enumerated : 기본 값이 ORDINAL 인데, enum 순서를 기준으로 저장하기 때문에 운영 중 추가나 변경에 혼란이 발생할 수 있다. 꼭 STRING 으로 사용할 것!

-	기타 gradle 추가한 설정

	-	쿼리 파라미터 확인

		-	jpa 사용 시 파라미터가 ? 로 표시된다. 외부 라이브러리를 통해 쉽게 확인가능하다.
		-	implementation 'com.github.gavlyukovskiy:p6spy-spring-boot-starter:1.5.7'

	-	jsp 사용

		-	부트는 jsp를 권장하지 않음 (임베디드 서블릿 컨테이너를 사용하는데 제약이 있는 것 같음)
		-	부트 자동설정에 포함된 템플릿 엔진은 FreeMarker, Groovy, Thymeleaf, Mustache
		-	사용하려면 의존성 추가가 필요하다. (*다음부턴 Thymeleaf 를 써보자 ㅠ*\)
		-	compile 'org.apache.tomcat.embed:tomcat-embed-jasper:8.5.27'

	-	jsp 내 jstl 사용

		-	jsp 내에서 사용할 수 있는 태그  
		-	compile 'jstl:jstl:1.2'

	-	modelmapper

		-	객체 값들을 편하게 복사시켜줌
		-	compile group: 'org.modelmapper', name: 'modelmapper', version: '2.3.1'

### 3. 사용 기술 메모

-	REST

	-	그런 REST API로 괜찮은가? : https://www.youtube.com/watch?v=RP_f5dMoHFc
	-	(인프런)백기선-스프링 기반 REST API 개발 : https://www.inflearn.com/course/spring_rest-api/dashboard

	-	REST 란?

		-	REpresentional State Transfer
		-	인터넷 상의 시스템 간의 상호운용성(Interoperability)을 제공하는 방법 중 하나
		-	시스템 제각각의 독립적인 진화를 보장하기 위한 방법
		-	REST API : REST 아키텍쳐 스타일을 따르는 API
			-	Client-Server
			-	Cache
			-	Uniform Interface
				-	Indentification of resources
				-	manipulation of resource through represenations
				-	**self-descriptive message**
				-	**hypermisa as the engine of application state (HATEOAS)**
			-	Layered System
			-	Code-On-Demand (optional)

	-	사실 게시판같은 작은 프로젝트를 구현하면서 REST API를 사용할 일은 많이 없을 것 같음

		-	다양한 클라이언트, 모바일 등을 사용하다면 유용하게 사용할 듯 싶음

		-	예시를 위해 추가함

		-	get, post, put

			-	HTTP 메서드
			-	GET : 서버에 리소스를 요청할 때 사용
			-	POST : 클라이언트에서 서버로 데이터가 포함된 요청을 보낼 때 사용
			-	DELETE, PUT : DELETE는 데이터 삭제, PUT은 이미 존재하는 데이터의 업데이트 요청을 의미하며 기술적으로는 POST와 큰 차이는 없다.
			-	REST API 구현 시 클라이언트의 사용이 쉽도록 url은 같더라도 HTTP 메서드 타입에 따라 다른 동작을 하게끔 구현하면 좋다고함

	-	백기선님 강의에서 진행한 resource, 링크생성, rest docs 문서 생성 등은 생략하였음

		-	REST API 개발하게 된다면 해당 소스 참조해서 진행할 것 (혹은 강의 복습!)
		-	https://github.com/cotgyu/REST-API_InflearnCourse

-	ExceptionHandler

	-	요청을 처리하다가 에러가 발생하거나 자바에서 지원하는 예외가 발생했을 때 정의한 핸들러로 그 예외를 어떻게 처리할지? 어떤응답을 만들지?를 정의할 수 있다.

		-	@ExceptionHandler 어노테이션을 사용해서 정의한다.
		-	처리하고 싶은 예외를 메서드 아규먼트로 선언

			-	@ExceptionHandler(value = RuntimeException.class)

		-	에러메세지, 에러페이지 등 설정 가능

		-	@ControllerAdvice 를 통해 컨트롤러 영역에 적용을 설정할 수 있음

	-	https://cotmulgyu.blogspot.com/search?q=exception

-	ErrorController

	-	부트는 기본으로 에러페이지를 제공한다.
	-	부트설정에서 오류처리에 대한 세부설정도 가능하다. ( server.error.~~~ )
	-	부트에서 제공하는 ErrorController를 구현하면 에러 발생 이후 이동할 페이지나 처리를 커스텀할 수 있다.

		```java
		@Controller
		public class CustomErrorPageController implements ErrorController {
		    private static final String ERROR_PAGE = "/error";
		    @Override
		    public String getErrorPath() {
		        return ERROR_PAGE;
		    }
		    @RequestMapping("/error")
		    public String errorPage(HttpServletRequest request, Model model){
		        ...
		        return "exception";
		    }
		}
		```

### 4. 참고

-	테스트 시 lombok 정상 작동안할 때

	-	인텔리제이 : Annotation Processors - Enable annotation processing 체크
