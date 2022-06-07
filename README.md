# NabWeather

# 1 Project
  - NavWeather is an Android testing assignment of Nab - National Australia Bank.

# 2 Setting and run project
  - NavWeather was written in Android Native - Kotlin language. So running it localy need Android Studio set up. You can download and install latest Android studio here: https://developer.android.com/studio. Recommended version is Android Studio Chipmunk | 2021.2.1
  - After installing Android studio, click build and run app.

# 3 App architecture
  - NavWeather was built on MVVM architecture. 
  - It is also applied the latest Architecture Components: **ViewModel**, **LiveData**, **DataBinding**, **Navigation**, **Hilt**, as well as latest Kotlin libraries: **Coroutines**, **Flow**.

# 4 Secure solutions
  - The OpenWeatherMap API calls required an API-Key plan text for authorization, which is unsecure while we have to keep this API-Key on client side. To secure it, I used FirebaseRemoteConfig to store the API-Key. Whenever the app is started, it requests Firebase to get the API-Key, means the app is never keep it localy. The FirebaseRemoteConfig was not designed for this job, but I see it is usefull in the case. https://firebase.google.com/docs/remote-config
  - The API call puts the API-key on its URL query param, which looks unsecure, but luckily the SSL connection helped us handle it.
  - ProGuard was also applied to shrinking the source code, preventing decompile source code.
  - The UI is simple, but also applied Spacing Meterial Design. https://material.io/design/layout/spacing-methods.html

# 5 Check list
  - Programming language: Kotlin
  - Architecture: MVVM
  - Applied LiveData
  - UI looks like attachment
  - Write Unit Test
  - Acceptance Test
  - Exception handling
  - Secure Android app
  - Readme file

For a discussion, please drop an email to mine: nguyenthonguyen.uit@gmail.com, skype: thonguyen252. I am happy to listen and discuss.
