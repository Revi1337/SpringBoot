# Servlet Container Initialize

## How To Initialize Servlet Container

1. 서블릿 컨테이너를 초기화하는 기능을 제공하는 인터페이스인 `ServletContainerInitializer` 의 구현체 생성 후 `onStartup` 메서드를 `@Override`.
   ```java
   public class MyContainerV1 implements ServletContainerInitializer {
       @Override
       public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException {
           System.out.println("MyContainerV1.onStartup");
           System.out.println("MyContainerV1 c = " + c);
           System.out.println("MyContainerV1 ctx = " + ctx);
       }
   }
   ```

2. `resources/META-INF/services/jakarta.servlet.ServletContainerInitializer` 파일에 `ServletContainerInitializer` 의 구현체를 등록. Format 은 `패키지.구현체명`.
   ```
   org.example.container.MyContainerV1
   ```

   **서블릿 컨테이너는 실행 시점에 초기화 메서드인 `onStartup()` 을 호출해주는데, 여기서 애플리케이션에 필요한 기능들을 초기화하거나 등록해주는 것임.**

## 2. Two Ways to Register Servlet

1. `@WebServlet` 어노테이션을 달아주는 방법

   > 본 방법은 ServletContainer 초기화에 관계없이 서블릿을 등록하는 방법.

    1. 새로운 클래스를 생성하고 `HttpServlet` 을 상속
       ```java
       public class TestServlet extends HttpServlet {
       }
       ```
   
    2. 해당 클래스에서 HttpServlet 의 `protected service()` 메서드를 @Override
       ```java
       public class TestServlet extends HttpServlet {
          @Override
          protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
               System.out.println("TestServlet.service");
               resp.getWriter().println("getWriter() Called");
          }
       }
       ```
       
    3. 해당 클래스에 @WebServlet 어노테이션을 달아주고, urlPatterns 속성에 서블릿을 등록할 url 패턴을 등록.
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
       
   <br>
   
2. 프로그래밍 방식 (직접 코드로 등록)

   > 본 방법은 ServletContainer 가 초기화된 후, Application 이 초기화될 때 서블릿을 등록하는 방법.  
   > Application 초기화는 ServletContainer 가 지원하는 조금 더 유연한 초기화 기능이라고 보면된다.
   > Application 초기화는 ServletContainer 가 초기화된 후 호출된다.

    1. ServletContainer 의 초기화 Application 의 초기화 순으로 연쇄적으로 일어나기 때문에, 먼저 ServletContainer 를 초기화시켜주어야 한다.
      따라서 `ServletContainerInitializer` 의 구현체 생성 후 `onStartup` 메서드를 `@Override` 해준다.
      그리고 `jakarta.servlet.ServletContainerInitializer` 에 등록시켜주어야 한다.
       ```java
       public class MyContainerInitV2 implements ServletContainerInitializer {
          @Override
          public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException {
               System.out.println("MyContainerInitV2.onStartup");
               System.out.println("MyContainerInitV2.c = " + c);
               System.out.println("MyContainerInitV2.ctx = " + ctx);
          }
       }
       ```
       
    2. `HttpServlet` 를 상속한 클래스를 만들어준다. 
       ```java
       public class HelloServlet extends HttpServlet {
           @Override
           protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
               System.out.println("HelloServlet.service");
               resp.getWriter().println("hello servlet");
           }
       }
       ```

    3. Application 을 초기화시켜주려면 하나의 인터페이스가 필요하기 때문에 하나의 `Interface` 를 만들어주고, ServletContext 를 인자로 받고 리턴값이 없는 껍데기 메서드를 만들어준다.
       ```java
       public interface AppInit {
           void onStartup(ServletContext servletContext);
       }
       ```
   
   4. 만든 Interface 를 구현하는 구현체를 만들어 껍데기 메서드를 impl 해주고, `addMapping` 메서드로 서블릿 컨테이너에 서블릿을 등록해준다.
       ```java
       public class AppInitV1Servlet implements AppInit{
           @Override
           public void onStartup(ServletContext servletContext) {
                System.out.println("AppInitV1Servlet.onStartup");
                ServletRegistration.Dynamic helloServlet =
                servletContext.addServlet("helloServlet", new HelloServlet());
                helloServlet.addMapping("/hello-servlet");
           }
       }
       ```
      
   5. Application 을 초기화시켜주기 위해서는 `@HandlesTypes` 어노테이션에 초기화 인터페이스를 지정해주어야한다. 이는  서블릿이 지원하는 초기화 인터페이스를 구현한 클래스에 달아주고 속성값으로
      사전에 작성한 `Interface` 의 `class` 를 넣어주어주면 된다.  
      이러면, `Servlet Container` 가 초기화되면서 `AppInit` 인터페이스의 구현체들의 class 정보가 `ServletContainerInitializer` 클래스의 `onStartup` 메서드의 첫번쨰 매개변수인 `c` 에 넘어오게 된다.  
      `c` 로 넘어온 매개변수가 한개인지 두개인지 모르기때문에 `iter` 로 돌리면서 객체로 만들어주고
      `AppInit` 에서 수행하는 `onStartup` 메서드를 통해 서블릿을 서블릿컨테이너에 등록시켜주면 된다.  
      이렇게 `c` 로 넘어온 클래스정보들을 하나하나 초기화시켜준게 `Application` 을 초기화한다 라고한다.
      
       ```java
       @HandlesTypes(AppInit.class)
       public class MyContainerInitV2 implements ServletContainerInitializer {
          @Override
          public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException {
              System.out.println("MyContainerInitV2.onStartup");
              System.out.println("MyContainerInitV2.c = " + c);
              System.out.println("MyContainerInitV2.ctx = " + ctx);
      
              for (Class<?> appInitClass : c) {
              try {
                  AppInit appInit = (AppInit) appInitClass.getDeclaredConstructor().newInstance();
                  appInit.onStartup(ctx);
              } catch (Exception e) {
                  throw new RuntimeException(e);
              }
          }
       }
       ```


