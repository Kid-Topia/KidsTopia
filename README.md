 # 키즈토피아

![image](https://github.com/Kid-Topia/KidsTopia/assets/161282085/b0f081bd-2f5d-4e45-813d-003e462e7980)

 ![KidsTopia](https://teamsparta.notion.site/image/https%3A%2F%2Fprod-files-secure.s3.us-west-2.amazonaws.com%2F83c75a39-3aba-4ba4-a792-7aefe4b07895%2F229a2f81-ed93-4c8c-a162-d7622d53dc88%2FUntitled.png?table=block&id=6fff48e4-0ee1-4940-9e24-649bd89905b8&spaceId=83c75a39-3aba-4ba4-a792-7aefe4b07895&width=2000&userId=&cache=v2)

## 🖥️ 프로젝트 소개
키즈 토피아는 Youtube Data API v3를 사용하여 키즈 카테고리의 비디오를 검색 및 저장할 수 있는 키즈 앱입니다.

## 🕰️ 개발 기간 (24.04.22일 - 24.04.28일 )
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
<a href = "">상세보기 - WIKI 이동</a><br>

### Trouble Shooting
1.bottom navigation을 가리는 것이 무엇인지 알 수 없는 문제 - <a href = "">상세보기 - WIKI 이동</a><br>
2.Bottom navigation 클릭한 탭에 알약 모양 배경이 나오는 문제<a href = "">상세보기 - WIKI 이동</a><br>
3.SearchFragment → DetailFragment로 이동 후 다시 돌아올 때, 검색 시 보던 화면과 데이터가 유지되지 않는 문제<a href = "">상세보기 - WIKI 이동</a><br>
4.Search 엔드포인트 값의 문제<a href = "">상세보기 - WIKI 이동</a><br>
5.ViewModel 구현 중 문제 및 API Key 오류<a href = "">상세보기 - WIKI 이동</a><br>
6.DetailFragment Room 관련 수정 후 toast 띄울 수 없는 문제<a href = "">상세보기 - WIKI 이동</a><br>
7.DetailFragment description TextView 잘림 문제<a href = "">상세보기 - WIKI 이동</a><br>
8.Migation 할 시에 주의점 및 문제점<a href = "">상세보기 - WIKI 이동</a><br>
9.Spinner사용 이후 아이템 클릭 후 뒤로가기 했을 때 어플 꺼지는 문제<a href = "">상세보기 - WIKI 이동</a><br>
10.DetailFragment 들어갔다 나왔을 때 기존 프래그먼트가 새로고침 되지 않고 그대로 유지 되지 않는 문제<a href = "">상세보기 - WIKI 이동</a><br>
11.패키지명 수정 시 문제<a href = "">상세보기 - WIKI 이동</a><br>
12.MyVideo 좋아요 누른 영상이 바로 반영되지 않는 문제<a href = "">상세보기 - WIKI 이동</a><br>
13.DetailFragment에서 핸드폰 네비게이션 바와 영역이 겹치는 문제<a href = "">상세보기 - WIKI 이동</a><br>

### 프로젝트 후기
