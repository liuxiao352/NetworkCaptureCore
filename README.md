# 预览
<img width="300" height="630" src="preview.gif"/>
<img width="300" height="630" src="preview2.gif"/>

# 接入
项目build.gradle:
```groovy
repositories {
    maven { url 'https://jitpack.io' }
}
```
app模块的build.gradle:
```groovy
dependencies {
    debugImplementation 'com.github.liuxiao352:NetworkCaptureCore:v1.0.7'
}
```

# 使用
1、给OkHttp添加`NetworkCaptureInterceptor`拦截器<br/>  

2、在WebViewClient的`onPageStarted`方法里调用`webView.evaluateJavascript(vConsoleJs, null);`<br/>  

获取`NetworkCaptureInterceptor`和`vConsoleJs`的方法:

```java
 public class NetworkCaptureUtils {

  @Nullable
  public static Interceptor getNetworkCaptureInterceptor() {
    try {
      Class<?> clazz =
          Class.forName("com.liuxiao352.networkcapturecore.interceptor.NetworkCaptureInterceptor");
      return (Interceptor) clazz.getDeclaredConstructor().newInstance();
    } catch (Exception e) {
      return null;
    }
  }

  @Nullable
  public static String getVConsoleJs() {
    try {
      Class<?> clazz =
          Class.forName("com.liuxiao352.networkcapturecore.utils.NetworkCaptureWebTools");
      return Objects.requireNonNull(clazz.getMethod("getVConsoleJs").invoke(null)).toString();
    } catch (Exception e) {
      return null;
    }
  }
}
```
