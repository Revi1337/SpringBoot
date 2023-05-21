# Web Server and Servlet Container

External Tomcat 을 WAR 에 올리는 방법

## 1. Install Tomcat
1. https://tomcat.apache.org/download-10.cgi 에서 Tomcat 다운

2. Windows 기준  `startup.bat` 파일 실행

3. 정상적으로 톰켓서버가 올라가있는지 확인
    ```
    > curl -v -s localhost:8080

    C:\Users\revi1>curl -v -s localhost:8080
    *   Trying 127.0.0.1:8080...
    * Connected to localhost (127.0.0.1) port 8080 (#0)
    > GET / HTTP/1.1
    > Host: localhost:8080
    > User-Agent: curl/8.0.1
    > Accept: */*
    >
    < HTTP/1.1 200
    < Content-Type: text/html;charset=UTF-8
    < Transfer-Encoding: chunked
    < Date: Sun, 21 May 2023 07:43:33 GMT
    <
    ...
    ```

   **추가적으로 Log 는 톰켓을 설치한 경로의 log 폴더에서 확인 가능**

   `인코딩이 깨진다면 cmd 에서 chcp 65001 로 인코딩을 바꾼 후 수행하면 됨./`

    ```
    > type C:\Tomcat_Versions\apache-tomcat-10.0.23\logs\catalina.2023-05-21.log
    ```


## 2. Project Configuration

1. 순수 자바프로젝트 생성

2. `build.gradle` 파일에 해당 내용 추가

    ```gradle
    // 빌드 결과물을 Tomcat 같이 WAS 에서 동작하게 `WAR` Suffix 로 만들어주기 위함.
    plugins {
      ...
      id 'war'
    }

    ... 
    // Servlet 을 사용하기위한 Library 의존성
    dependencies {
    ...
    implementation 'jakarta.servlet:jakarta.servlet-api:6.0.0'
    }
    ```

3. 프로젝트 main 디렉터리 바로 하위에 `webapp` 디렉터리 생성하고 `webapp` 디렉터리에 매우 단순한 흐트물(html) 파일생성
    ```html
    <!doctype html>
    <html lang="en">
    <head>
        <title>Document</title>
    </head>
    <body>
      
    </body>
    </html>
    ```

4. servlet` 패키지를 만든 후, 하위에 java 파일 생성.
    1. `HttpServlet` 를 상속. 그리고 접근제한자가 `protected` 인 `service` 메서드 `@Override`

    2. Servlet 이 언제 실행될지 매핑해주는 `@WebServlet` 어노테이션을 부여.

       `http://localhost:8080/test` URL 을 매핑해주겠다는 의미.

    ```java
    @WebServlet(urlPatterns = "/test")
    public class TestServlet extends HttpServlet {

      @Override
      protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
          System.out.println("TestServlet.service");
          resp.getWriter().println("getWriter() Called");
      }
    }
    ```

5. 해당 `Servlet` 을 실행하면 설치한 Tomcat 에다가 WAR 파일로 배포해야함.

## 3. WAR Build & Deploy

1. 현재 프로젝트 Root 에서 `gradle` 로 빌드.
   빌드결과물은 프로젝트 Root 하위 ` /build/libs/이름.war` 이다.

    ```
    > ./gradlew build 
    ```

2. war 파일을 압축을 해제해보면, 아래와 같은 structure 을 갖은 폴더와 파일이 나온다.

    * `index.html`  은 사전에 작성한 html 파일이고. `TestServlet` 클래스가

    * 컴파일된 파일은 `WEB-INF/classess` 안에 `.class` suffix 로 존재한다.

    * library 는 `WEB-INF/lib` 하위 `.jar` suffix 로 존재한다.

    ```
    > jar xvf .\TomcatWARBuildTest-1.0-SNAPSHOT.war
      생성됨: META-INF/
      증가됨: META-INF/MANIFEST.MF
      생성됨: WEB-INF/
      생성됨: WEB-INF/classes/
      생성됨: WEB-INF/classes/org/
      생성됨: WEB-INF/classes/org/example/
      생성됨: WEB-INF/classes/org/example/servlet/
      증가됨: WEB-INF/classes/org/example/servlet/TestServlet.class
      생성됨: WEB-INF/lib/
      증가됨: WEB-INF/lib/jakarta.servlet-api-6.0.0.jar
      증가됨: index.html
    ```
   #### JAR 와 WAR

   ##### JAR

   * `JAR(Java Archive)`는 여러 클래스와 리소스를 묶어서 만든 압축파일

   * JVM 위에서 직접 실행되거나 또는 다른 곳에서 라이브러리로 제공됨

   * 직접 실행하는 경우 `main()` 메서드가 필요하고 MANIFEST.MF 파일에 실행할 메인 메서드가 있는 클래스를 지정해주어야 한다.

   ##### WAR

   * `WAR(Web Application Archive)` 파일은 웹 애플리케이션 서버(WAS)에 배포할 때 사용하는 파일.JAR 파일이 JVM 위에서 실행된다면, WAR 는 WAS 위에서 실행됨.

   * JVM 위에서 직접 실행되거나 또는 다른 곳에서 라이브러리로 제공됨.

   * HTML 같은 정적 리소스와 클래스 파일을 모두 함께 포함하기 때문에 JAR 와 비교해서 구조가 더 복잡하며 WAR 구조를 지켜야 한다.  

   **WAR Structure**
     * WEB-INF : 폴더 하위는 자바 클래스와 라이브러리, 그리고 설정 정보가 들어가는 곳.
        * classes
        * lib
        * web.xml
     * index.html : WEB-INF 를 제외한 나머지 영역은 HTML, CSS 같은 static 리소스가 사용되는 영역


3. WAR 파일 배포 
   1. 톰캣 서버 종료 `./shutdown.bat`
   2. `톰캣폴더/webapps` 하위 모두 삭제
   3. 빌드된 .war 파일을 복사하여 `톰캣폴더/webapps` 하위에 붙여넣는다.
   4. 이름을 `ROOT.war` 로 변경한다.
   5. 톰캣 서버를 실행한다 `./startup.bat`


4. 톰캣이 잘 올라오는지 확인. (Servlet 또한 잘동작되는지 확인한다)
    ```batch
   > curl http://localhost:8080/
    ...
        <p>index.html</p>
    ...
    ```
   ```batch
   > curl http://localhost:8080/test
    getWriter() Called
    ```
   