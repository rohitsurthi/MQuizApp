# MQuiz - A quiz app

I built a small quiz app that fetches 10 MCQ questions from a JSON endpoint, caches them locally, and runs a clean one question at a time flow with answer reveal and auto advance.

## what is does
- **Splash → Load**: On launch I refresh from the API and write to Room. UI always reads from DB. 
- **Question flow**: Shows one question (text + 4 options). On tap, it shows correct vs selected and **auto advances after 2 seconds**.
- **Skip**: Jumps to the next question immediately.
- **Results**: Displays Correct/Total, streaks and Skipped. Has a restart button.
- **Theme**: Support both Dark and Light Theme based on System
- **Steaks**: Display a current streak for continuous correct answers with streak comments

## Tech
- **Kotlin**,
- **Jetpack Compose**
- **Coroutines / Flow**
- **Room**
- **Retrofit**
- **DI**

/
## Architecture
 - **Model-View-ViewModel(MVVM)** Data -> Repository -> ViewModel -> UI
 - **Single Source of Truth**: UI observes Room via ViewModel. Network writes into DB, UI never binds to raw network responses.
 
 ## UI / UX 
- Used lottie to provide better user experience
- Gestures : Swipe right for next question
 
## data model
```
data class Question(
    val id: Int,
    val question: String,
    val options: List<String>,
    val correctOptionIndex: Int
)
```

**API**:  
`https://gist.githubusercontent.com/dr-samrat/53846277a8fcb034e482906ccc0d12b2/raw`

## Note / Use
- **Offline‑first**: The app always renders from DB for stability and instant loads after the first run.
- **Manual DI**: 
- **State handling**: One `QuizUiState` exposes `isLoading`, `errorMessage`, `questions`, `currentIndex`, `selectedIndex`, `isAnswerRevealed`, `correctAnswers`, and `skipped`. // etc
- **Auto‑advance UI**: After revealing, I show a small “Next in Ns” chip and move forward at 2s.

## Permission
```xml
<uses-permission android:name="android.permission.INTERNET" />
```

## Run
1) Clone/open in Android Studio  
2) Run on device/emulator  
3) Flow: **Splash → Questions → Results → Restart**
