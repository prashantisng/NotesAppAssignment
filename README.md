# Notes App Assignment

A premium Android Notes application demonstrating modern development practices including **Dagger Hilt** for dependency injection, **Room** for local persistence, and **Material 3** for user interface design.

---

## 🚀 Features

- **Full CRUD Operations:** Create, Read, Update, and Delete notes.
- **Real-Time Search:** Instant filtering of notes by title or content as you type.
- **Reactive UI:** Automatic list updates powered by Room database `Flow` streams.
- **Premium Material 3 UI:** Featuring a card-based layout, floating action buttons, clean typography, empty-state illustration feedback, and rounded outlined input dialogs.
- **Accidental Delete Protection:** Confirms deletion using a clean dialog.

---

## 🏗️ Architecture & Technology Stack

The project adheres to the recommended Android Architecture guidelines:

- **Core Language:** Kotlin
- **Dependency Injection:** [Dagger Hilt](https://developer.android.com/training/dependency-injection/hilt-android) (injecting Database and DAO classes)
- **Local Persistence:** [Room Database](https://developer.android.com/training/data-storage/room) (SQLite abstraction layer)
- **Asynchronous Flow:** [Kotlin Coroutines](https://developer.android.com/kotlin/coroutines) & [Flow](https://developer.android.com/kotlin/flow)
- **UI Components:** [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) & [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)
- **View Access:** [View Binding](https://developer.android.com/topic/libraries/data-binding/view-binding)

---

## 📂 Project Structure

```
com.expensetrack.notesappassignment/
│
├── data/
│   ├── NoteEntity.kt       # Room Entity representing a single note
│   ├── NotesDao.kt         # Data Access Object defining DB queries/operations
│   └── NotesDatabase.kt    # Room Database class
│
├── di/
│   └── DatabaseModule.kt   # Hilt module providing Database & DAO instances
│
├── App.kt                  # Application class initialized with @HiltAndroidApp
├── NotesAdapter.kt         # ListAdapter for RecyclerView binding with DiffUtil
├── NotesViewModel.kt       # ViewModel handling business logic and DB flow subscription
└── MainActivity.kt         # Launcher activity observing state and managing UI dialogs
```

---

## 🛠️ Build & Setup Instructions

### Prerequisites
- Android Studio installed.
- Android SDK 36 platform installed.

### Configuration Fixes Applied
1. **JDK Environment:** Added configuration in `gradle.properties` to force Gradle to use Android Studio's JetBrains Runtime (`jbr`) JDK, avoiding missing tool (`jlink`) compilation issues from default IDE plugins.
2. **Hilt Application Bootstrap:** Registered the `App` class correctly within `AndroidManifest.xml` via `android:name=".App"`.

