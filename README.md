# FAST CAMPUS - INNER CIRCLE 2022 BACK-END COURSE

패스트캠퍼스 Inner Circle - 백엔드 코스 리파지토리

## IntelliJ Project Setting

### File > Project Structure > Product Setting > Project
SDK -> corretto 17 또는 17 버전의 SDK로 설정
<img width="954" alt="Screenshot 2022-12-19 at 9 55 52 AM" src="https://user-images.githubusercontent.com/117892511/208330672-7aa378d9-1734-49f5-b5a6-ea30112af5fe.png">

### Settings > Build, Execution, Deployment > Build Tools > Gradle
Gradle JVM -> Project SDK
<img width="1129" alt="Screenshot 2022-12-19 at 9 56 24 AM" src="https://user-images.githubusercontent.com/117892511/208330733-37ccdcfb-2a75-4b76-814f-7ed17439db1c.png">

### Settings > Build, Execution, Deployment > Compiler > Annotation Processors
Enable annotation processing -> checked
<img width="1129" alt="Screenshot 2022-12-19 at 9 57 01 AM" src="https://user-images.githubusercontent.com/117892511/208330800-3be61f0a-13f6-4341-a5c6-155346f3d9d5.png">

## How to run tests

```
# run unit tests
./gradlew test

# run integration tests
./gradlew integrationTest
```

## How to run application

```
./gradlew bootRun
```

## OpenAPI definition

http://localhost:8080/swagger-ui/index.html
