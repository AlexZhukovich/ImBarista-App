# ImBarista-App

The **I'm Barista** application has a list of coffee drinks with a description of each coffee drink. The app was created for testing workshop and demonstrate many general practises which are usually used in mobile applications.

> Note: If you want to improve basic of Espresso framework and skills of creating custom Hamcrest Matchers you can check the [IntroToAndroidUITesting](https://github.com/AlexZhukovich/IntroToAndroidUITesting) repository.  

**Create Account** and **Login** screen
<p align="center">
  <img src="/art/screenshots/create-account-screen-framed.png" height="500" />
  <img src="/art/screenshots/login-screen-framed.png" height="500" />
</p>

**Coffee drinks**, **Near Me** and **Profile** screens
<p align="center">
  <img src="/art/screenshots/home-coffee-drinks-screen-framed.png" height="500" />
  <img src="/art/screenshots/home-near-me-screen-framed.png" height="500" />
  <img src="/art/screenshots/home-profile-screen-framed.png" height="500" />
</p>

**Coffee drinks details** and **Settings** screens
<p align="center">
  <img src="/art/screenshots/coffee-drink-details-screen-framed.png" height="500" />
  <img src="/art/screenshots/settings-screen-framed.png" height="500" />
</p>

[Application diagram with connections between screens](https://raw.githubusercontent.com/AlexZhukovich/ImBarista-App/add-images-to-project/art/ImBarista-app-mockup.png)

**Features of the application:**
* Demonstrating list of coffee drinks
* User can mark/unmark coffee drink as a favourite
* User can create account/Login into app
* User can interact with a map and find a cafe

# Frameworks and Tools
**Development Frameworks and Tools**:
* Kotlin
* Kotlin coroutines
* Android SDK
* Android Jetpack
* Material Design Components
* Picasso
* RxJava
* RxKotlin
* Retrofit
* Gson
* Koin

**Maps Frameworks and Tools**:
* Google Maps
* TomTom Maps

**Testing Frameworks and Tools**:
* Espresso
* Ui Automator Viewer
* Test Orchestrator
* Robolectric
* JUnit
* MockK

# Structure of the project
The source code split between different modules:
* domain
* data
* remote
* cache
* mobile-ui

<p align="center">
  <img src="/art/architecture/modules.png" height="500" />
</p>

Modules for generating test data:
* commonTestData
* commonAndroidTestData

The project has the main branches:
* master
* workshop-start

The **master** branch has all test cases with solutions.

The **workshop-start** branch has all failed test cases with description for each test case and test classes.   

# Configuration
* TomTom Maps API key should be added as System variable with the `BARISTA_TOMTOM_MAPS_API_KEY` key
* Google Maps API key should be added as System variable with the `BARISTA_GOOGLE_MAPS_API_KEY` key

# App screens with views
**"Login" screen**
<p align="center">
  <img src="art/screens-with-views/login-screen.png" width="800" />
</p>

**"Create Account" screen**
<p align="center">
  <img src="art/screens-with-views/login-screen.png" width="800" />
</p>

**"Home - Coffee drinks" screen**
<p align="center">
  <img src="art/screens-with-views/coffee-drinks-screen.png" width="800" />
</p>

**"Coffee drink details" screen**
<p align="center">
  <img src="art/screens-with-views/coffee-drink-details-screen.png" width="800" />
</p>

**"Home - Near Me" screen**
<p align="center">
  <img src="art/screens-with-views/near-me-screen.png" width="800" />
</p>

**"Home - Profile" screen**
<p align="center">
  <img src="art/screens-with-views/profile-screen.png" width="800" />
</p>

**"Settings" screen**
<p align="center">
  <img src="art/screens-with-views/settings-screen.png" width="800" />
</p>