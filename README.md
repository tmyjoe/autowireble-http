# Autowireble-http
Autowireble-http is a HTTP client library for Spring IoC Container inspired by [Retrofit](http://square.github.io/retrofit/).

By Autowireble-http, you can easily get autowireble components to execute http request
only by just defining a interface.

This is still version 0.1.0, so there are a lot of things to be done.
Any feedback is always welcome, as well as Pull requests.

## Usage
### Step1
Create an interface with AutowirebleHttp annotation.

```Java
package com.tmyjoe.httpcomponent

@AutowirebleHttp
public interface GithubService {
  @GET("/repos/:owner/:repo")
  @Scheme("https")
  @Host("api.github.com")
  HttpResponse getRepository(@PathParam String owner, @PathParam(":repo") repositoryName);
}
```

### Step2
Register a bean spring IoC container like below.
```xml
  <bean class="com.tmyjoe.autowireblehttp.spring.AutoHttpBeanConfigurer">
      <!-- Set a base package name to scan interface with AutowirebleHttp annotation.-->
      <constructor-arg value="com.tmyjoe.httpcomponent"/>
  </bean>
```

### Step3
Now, you can autowire the interface defined in Step1
```Java
@Service
public class GithubRepositoryService {

  @autowire
  private GithubService githubService;

  public List<GithubRepo> getRepository(Owner owner, String repoName) {
    try(HttpResponse response = gihubService.getRepository(owner.name(), repoName)) {
      return toRepositoryList(response.getEntity());
    }
  }
}

```
## License
```
Copyright [2015/10/25] [Tomoya Honjo]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
