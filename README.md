# Aven — Mindful Self-Improvement & Productivity Companion


> **Aven** is a privacy-first, offline-first personal wellness, focus, and self-improvement companion built for Android. Designed to help users overcome doomscrolling and digital distractions, Aven combines mindful app blocking, habit building, structured task tracking, mood journaling, and smart hydration management into a modern, unified experience.

---

## 🌟 Why Aven?

In an era dominated by attention-economy apps and intrusive background tracking, **Aven** offers an intentional alternative:
* **Privacy-First & 100% Local**: No cloud telemetry, no background data spying, and no user tracking. All data is processed and stored on-device using Room Database and local encrypted preferences.
* **All-in-One Wellness Dashboard**: Instead of juggling five separate single-purpose apps, Aven integrates screen time discipline, task management, habit streaks, journaling, and hydration into a cohesive **Bento Grid** dashboard.
* **Mindful Screen Time Blocker**: Encourages healthy boundary setting during work/study hours without compromising device security or sending usage logs to remote servers.

---

## 🚀 Key Features

### 📱 1. Bento Grid Dashboard
* **At-a-Glance Insights**: Modern, adaptive dashboard surfacing real-time summaries for daily water intake, active habits, journal entries, and pending tasks.
* **Dynamic Time-Aware Greetings**: Personalized greeting headers that adapt dynamically based on the time of day.

### 🛡️ 2. Mindful App Blocker
* **Digital Distraction Shield**: Restricts addictive social media and entertainment apps during specified focus windows.
* **Mindful Interventions**: Prompts users with a pause screen before launching blocked apps, replacing compulsive scrolling habits with intentional awareness.

### 📝 3. Task Tracker
* **Categorized Task Management**: Automatically separates active tasks from completed ones.
* **Collapsible Completed Tasks**: Keeps the user interface clean by housing finished tasks under an expandable/collapsible section with animated transitions.
* **Instant Actions**: One-tap completion toggles and swift task deletion.

### 🔥 4. Habit Builder
* **Streak & Frequency Tracking**: Helps users build consistent daily routines with habit streak tracking.
* **Custom Habit Creation**: Define personalized daily or weekly goals with flexible schedules.

### 📔 5. Mood Journaling
* **Reflective Writing**: Express daily thoughts, wins, and reflections in a structured format.
* **Mood Tagging**: Capture emotional states alongside journal entries (e.g., Happy, Calm, Energetic, Neutral) for self-reflection over time.

### 💧 6. Smart Hydration Tracker & Reminders
* **Visual Goal Tracker**: Interactive progress bar tracking daily water intake in glasses against custom goals.
* **Automated Background Reminders**: Powered by Android **WorkManager** to trigger timely hydration alerts during scheduled active hours.

---

## 🛠️ Architecture & Tech Stack

Aven is engineered following official **Android Modern App Architecture** recommendations, adhering to Clean Architecture principles, Unidirectional Data Flow (UDF), and SOLID principles.

```
┌─────────────────────────────────────────────────────────┐
│                      UI Layer                           │
│  [ Jetpack Compose ]  ────>  [ StateFlow / Material3 ]  │
└──────────────────────────┬──────────────────────────────┘
                           │ State & Events
┌──────────────────────────▼──────────────────────────────┐
│                    ViewModel Layer                      │
│ [ TaskVM ]  [ HabitVM ]  [ JournalVM ]  [ WaterVM ]      │
└──────────────────────────┬──────────────────────────────┘
                           │ Coroutine Calls
┌──────────────────────────▼──────────────────────────────┐
│                    Repository Layer                     │
│         [ OfflineAnchorRepository / Interface ]          │
└──────────────┬──────────────────────────┬───────────────┘
               │                          │
┌──────────────▼──────────────┐  ┌────────▼───────────────┐
│        Room Database        │  │   WorkManager / Prefs │
│ (DAOs + Entities via KSP)   │  │ (Background Reminders)│
└─────────────────────────────┘  └───────────────────────┘
```

### 💻 Tech Stack Summary

| Layer | Technology |
| :--- | :--- |
| **Language** | Kotlin 2.0+ |
| **UI Framework** | Jetpack Compose (Material Design 3) |
| **Design System** | Custom Dynamic Material3 Theme (Dark/Light mode support, Edge-to-Edge) |
| **Architecture** | MVVM + Repository Pattern + Unidirectional Data Flow (UDF) |
| **Asynchronous Programming** | Kotlin Coroutines & `StateFlow` |
| **Database & Persistence** | Room Database (KSP) + SharedPreferences |
| **Dependency Injection** | Manual Dependency Injection via `AppContainer` & ViewModel Factory |
| **Navigation** | Jetpack Navigation Compose |
| **Background Scheduling** | Android WorkManager (`androidx.work`) + Notification Channels |
| **Target SDK / Min SDK** | Target SDK 37 / Min SDK 26 (Android 8.0+) |

---

## 📁 Project Structure

```
com.example.anchor
├── data
│   ├── local
│   │   ├── converters/      # TypeConverters for Room DB
│   │   ├── daos/            # Data Access Objects (Habit, Task, Journal, Water)
│   │   ├── entities/        # Room Database Entities (HabitEntity, TaskEntity, etc.)
│   │   └── AnchorDatabase.kt# Main Room Database Configuration
│   └── repository/          # Repository Abstraction & Offline-First Implementation
├── ui
│   ├── pages
│   │   ├── Onboarding/      # Login & First-time setup screens
│   │   ├── Screens/         # Dashboard, Habit, Journal, Task & Blocker screens
│   │   └── water/           # Hydration tracker & WorkManager reminder setup
│   ├── theme/               # Material3 Color schemes, Typography & Shapes
│   ├── viewmodels/          # Feature ViewModels managing StateFlows
│   ├── AppViewModelProvider.kt # Factory for ViewModel instantiations
│   └── AppContainer.kt      # Centralized Dependency Container
├── universalFunctions
│   ├── NotificationHelper.kt# Android Notification Channel creation & triggers
│   └── universalVariables.kt# User Preferences & Reminder Settings
└── MainActivity.kt          # Main Activity & Compose NavHost configuration
```

---

## 🔐 Privacy & Security Highlights

> [!IMPORTANT]
> **Privacy-First Guarantee**
> - **Zero Remote Analytics**: No third-party SDKs, telemetry, or network calls.
> - **Local Data Ownership**: 100% of user data remains on the user's physical device.
> - **On-Device App Blocking**: Safe local monitoring without cloud logging or invasive external permissions.

---

## ⚡ Getting Started

### Prerequisites
* **Android Studio**: Ladybug / Jellyfish or newer.
* **JDK**: Version 11 or 17.
* **Android Device / Emulator**: Running API level 26 (Android 8.0) or higher.

### Installation & Build

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/your-username/Aven.git
   cd Aven
   ```

2. **Open in Android Studio**:
   Open Android Studio, select **Open**, and navigate to the cloned project root.

3. **Build the Project**:
   Run Gradle build from Android Studio or via CLI:
   ```bash
   ./gradlew assembleDebug
   ```

4. **Run on Device**:
   Select your target emulator or physical device by enabling USB debugging and click **Run (Shift + F10)**.

---

## 📈 Roadmap

- [x] **Core Bento Grid Dashboard**: Unified status overview.
- [x] **Smart Hydration Engine**: Background notifications via WorkManager.
- [x] **Structured Tasks**: Active vs. Collapsible Completed sections.
- [x] **Local Privacy Focus**: Room Database & offline preferences.
- [ ] **Data Export / Import**: Backup personal routines to local encrypted JSON.
- [ ] **Analytics & Visual Progress Graphs**: Weekly/monthly trend analysis for habits & mood.
- [ ] **Home Screen Widgets**: Quick-action widgets for logging water and ticking off tasks.

---

## 👤 Developer Profile

Developed by a **Rahul Rajasekhar** **Computer Science Student** passionate about native Android engineering, clean code architecture, Jetpack Compose, and building user-centric, privacy-conscious applications.

---

<p center="align">
  <i>Built with ❤️ using Kotlin & Jetpack Compose</i>
</p>
