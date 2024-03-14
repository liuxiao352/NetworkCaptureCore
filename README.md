# 预览
<img width="300" height="630" src="preview.gif"/>

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
    debugImplementation 'com.github.liuxiao352:NetworkCaptureCore:v1.0.1'
}
```

# 使用
给OkHttp添加`NetworkCaptureInterceptor`拦截器,获取拦截器的方法:
```java
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
```
