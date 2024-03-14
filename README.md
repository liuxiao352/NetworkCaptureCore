# 接入
添加 `NetworkCaptureCore` 依赖到你的库或 APP 项目的 `build.gradle` 文件:
```groovy
implementation 'com.github.liuxiao352:NetworkCaptureCore:v1.0.1'
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
