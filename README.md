 # 키즈토피아

 ![KidsTopia](https://teamsparta.notion.site/image/https%3A%2F%2Fprod-files-secure.s3.us-west-2.amazonaws.com%2F83c75a39-3aba-4ba4-a792-7aefe4b07895%2F229a2f81-ed93-4c8c-a162-d7622d53dc88%2FUntitled.png?table=block&id=6fff48e4-0ee1-4940-9e24-649bd89905b8&spaceId=83c75a39-3aba-4ba4-a792-7aefe4b07895&width=2000&userId=&cache=v2)

## 🖥️ 프로젝트 소개
키즈 토피아는 Youtube Data API v3를 사용하여 키즈 카테고리의 비디오를 검색 및 저장할 수 있는 키즈 앱입니다.

## 🕰️ 개발 기간 (24.05.13일 - 24.05.23일 )
+ 24.05.13 : 프로젝트 S.A 작성, Figma 와이어프레임 작성, 깃허브 Organization, Repository 생성,
+ 24.05.14 - 24.05.17 : 각 프래그먼트 기능 구현
+ 24.05.18 - 24.05.21 : 스피너,다이얼로그 생성 및 뷰모델 전환
+ 24.05.21 - 24.05.23 : UI추가 잡업 및 코드 수정, README 작성, 발표 자료 제작
<br><br> 
### 👨‍👨‍👧‍👦 맴버 구성
+ 남형주(팀장) - 발표 자료 작성, 타임 매니저(팀 일정관리)
+ 임희진(부팀장) - Notion 작성 및 팀 계획/회고 기록, 발표 자료 작성
+ 마해인(팀원) - 발표 자료 작성, 발표 자료 취합
+ 임봉규(팀원) - 발표 자료 작성, 영상 제작, Git 관리자
+ 김주현(팀원) - 발표, README.md 작성, 발표 자료 작성

### ⚙️ 개발환경
+ Programming Language : Kotlin
+ IDE : Android Studio
+ Compile SDK Version : 34
+ Minimum SDK Version : 26
+ Target SDK Version : 34
+ Version Code : 1
+ Version Name : 1.0

<br>

- **프로젝트 핵심 기술**
    - 아키텍처 : Modern Android App Architecture
    - 디자인 패턴(앱 아키텍처 패턴) : MVVM
    - Retrofit, Navigation, Fragment, ViewBinding, RecyclerView, Youtube Data API, XML, LiveData, Dialog, Gson, Glide, Coroutine, LifeCycle
- **추가 구현 기능**
    - Room, DataStore

<br>

### 📖 프로젝트 구조

```
C:.
├─java
│  └─com
│      └─kidstopia
│          └─kidstopia
│              ├─model
│              │  └─database
│              ├─presentation
│              │  ├─activity
│              │  ├─adapter
│              │  ├─fragment
│              │  └─network
│              ├─repository
│              └─viewmodel
└─res
    ├─anim
    ├─drawable
    ├─font
    ├─layout
    ├─menu
    ├─mipmap-anydpi
    ├─mipmap-anydpi-v26
    ├─mipmap-hdpi
    ├─mipmap-mdpi
    ├─mipmap-xhdpi
    ├─mipmap-xxhdpi
    ├─mipmap-xxxhdpi
    ├─values
    ├─values-night
    └─xml  
```

### 🔎 페이지별 기능 
 - <a href = "">상세보기 - WIKI 이동</a><br>

### Trouble Shooting
1.bottom navigation을 가리는 것이 무엇인지 알 수 없는 문제 - <a href = "https://github.com/Kid-Topia/KidsTopia/wiki/bottom-navigation%EC%9D%84-%EA%B0%80%EB%A6%AC%EB%8A%94-%EA%B2%83%EC%9D%B4-%EB%AC%B4%EC%97%87%EC%9D%B8%EC%A7%80-%EC%95%8C-%EC%88%98-%EC%97%86%EB%8A%94-%EB%AC%B8%EC%A0%9C">상세보기 - WIKI 이동</a><br><br>
2.SearchFragment → DetailFragment로 이동 후 다시 돌아올 때, 검색 시 보던 화면과 데이터가 유지되지 않는 문제 - <a href = "https://github.com/Kid-Topia/KidsTopia/wiki/SearchFragment-%E2%86%92-DetailFragment%EB%A1%9C-%EC%9D%B4%EB%8F%99-%ED%9B%84-%EB%8B%A4%EC%8B%9C-%EB%8F%8C%EC%95%84%EC%98%AC-%EB%95%8C,-%EA%B2%80%EC%83%89-%EC%8B%9C-%EB%B3%B4%EB%8D%98-%ED%99%94%EB%A9%B4%EA%B3%BC-%EB%8D%B0%EC%9D%B4%ED%84%B0%EA%B0%80-%EC%9C%A0%EC%A7%80%EB%90%98%EC%A7%80-%EC%95%8A%EB%8A%94-%EB%AC%B8%EC%A0%9C">상세보기 - WIKI 이동</a><br><br>
3.Search 엔드포인트 값의 문제 - <a href = "https://github.com/Kid-Topia/KidsTopia/wiki/Search-%EC%97%94%EB%93%9C%ED%8F%AC%EC%9D%B8%ED%8A%B8-%EA%B0%92%EC%9D%98-%EB%AC%B8%EC%A0%9C">상세보기 - WIKI 이동</a><br><br>
4.ViewModel 구현 중 문제 및 API Key 오류 - <a href = "https://github.com/Kid-Topia/KidsTopia/wiki/ViewModel-%EA%B5%AC%ED%98%84-%EC%A4%91-%EB%AC%B8%EC%A0%9C-%EB%B0%8F-API-Key-%EC%98%A4%EB%A5%98">상세보기 - WIKI 이동</a><br><br>
5.DetailFragment Room 관련 수정 후 toast 띄울 수 없는 문제 - <a href = "https://github.com/Kid-Topia/KidsTopia/wiki/DetailFragment-Room-%EA%B4%80%EB%A0%A8-%EC%88%98%EC%A0%95-%ED%9B%84-toast-%EB%9D%84%EC%9A%B8-%EC%88%98-%EC%97%86%EB%8A%94-%EB%AC%B8%EC%A0%9C">상세보기 - WIKI 이동</a><br>
6.DetailFragment description TextView 잘림 문제 - <a href = "https://github.com/Kid-Topia/KidsTopia/wiki/DetailFragment-description-TextView-%EC%9E%98%EB%A6%BC-%EB%AC%B8%EC%A0%9C">상세보기 - WIKI 이동</a><br><br>
7.Migation 할 시에 주의점 및 문제점 - <a href = "https://github.com/Kid-Topia/KidsTopia/wiki/Migation-%ED%95%A0-%EC%8B%9C%EC%97%90-%EC%A3%BC%EC%9D%98%EC%A0%90-%EB%B0%8F-%EB%AC%B8%EC%A0%9C%EC%A0%90">상세보기 - WIKI 이동</a><br><br>
8.Spinner사용 이후 아이템 클릭 후 뒤로가기 했을 때 어플 꺼지는 문제 - <a href = "https://github.com/Kid-Topia/KidsTopia/wiki/Spinner%EC%82%AC%EC%9A%A9-%EC%9D%B4%ED%9B%84-%EC%95%84%EC%9D%B4%ED%85%9C-%ED%81%B4%EB%A6%AD-%ED%9B%84-%EB%92%A4%EB%A1%9C%EA%B0%80%EA%B8%B0-%ED%96%88%EC%9D%84-%EB%95%8C-%EC%96%B4%ED%94%8C-%EA%BA%BC%EC%A7%80%EB%8A%94-%EB%AC%B8%EC%A0%9C">상세보기 - WIKI 이동</a><br><br>
9.DetailFragment 들어갔다 나왔을 때 기존 프래그먼트가 새로고침 되지 않고 그대로 유지 되지 않는 문제 - <a href = "https://github.com/Kid-Topia/KidsTopia/wiki/DetailFragment-%EB%93%A4%EC%96%B4%EA%B0%94%EB%8B%A4-%EB%82%98%EC%99%94%EC%9D%84-%EB%95%8C-%EA%B8%B0%EC%A1%B4-%ED%94%84%EB%9E%98%EA%B7%B8%EB%A8%BC%ED%8A%B8%EA%B0%80-%EC%83%88%EB%A1%9C%EA%B3%A0%EC%B9%A8-%EB%90%98%EC%A7%80-%EC%95%8A%EA%B3%A0-%EA%B7%B8%EB%8C%80%EB%A1%9C-%EC%9C%A0%EC%A7%80-%EB%90%98%EC%A7%80-%EC%95%8A%EB%8A%94-%EB%AC%B8%EC%A0%9C">상세보기 - WIKI 이동</a><br><br>
10.MyVideo 좋아요 누른 영상이 바로 반영되지 않는 문제 - <a href = "https://github.com/Kid-Topia/KidsTopia/wiki/MyVideo-%EC%A2%8B%EC%95%84%EC%9A%94-%EB%88%84%EB%A5%B8-%EC%98%81%EC%83%81%EC%9D%B4-%EB%B0%94%EB%A1%9C-%EB%B0%98%EC%98%81%EB%90%98%EC%A7%80-%EC%95%8A%EB%8A%94-%EB%AC%B8%EC%A0%9C">상세보기 - WIKI 이동</a><br><br>
11.DetailFragment에서 핸드폰 네비게이션 바와 영역이 겹치는 문제 - <a href = "https://github.com/Kid-Topia/KidsTopia/wiki/DetailFragment%EC%97%90%EC%84%9C-%ED%95%B8%EB%93%9C%ED%8F%B0-%EB%84%A4%EB%B9%84%EA%B2%8C%EC%9D%B4%EC%85%98-%EB%B0%94%EC%99%80-%EC%98%81%EC%97%AD%EC%9D%B4-%EA%B2%B9%EC%B9%98%EB%8A%94-%EB%AC%B8%EC%A0%9C">상세보기 - WIKI 이동</a><br><br>
