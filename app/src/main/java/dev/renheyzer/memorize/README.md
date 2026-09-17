# Memorize – Memory Improvement App

**Description**: An Android application designed to boost memory and focus through interactive daily exercises and games.

### 🛠 Technology Stack
* **Architecture:** Clean Architecture, MVI (Model-View-Intent)
* **Navigation & UI Logic:** Decompose
* **Dependency Injection:** Manual DI
* **UI Framework:** Jetpack Compose
* **Concurrency:** Kotlin Coroutines
* **Backend & Auth:** Firebase

### 🏗 Architecture
> **Why Decompose?** > Decompose is utilized for lifecycle-aware componentization and navigation. It decouples UI from business logic and provides a highly scalable, Kotlin Multiplatform (KMP) ready foundation.

[ВСТАВИТЬ ВАШУ МАКРО-СХЕМУ С CORE INFRASTRUCTURE СЮДА]

#### Feature-Level Architecture (MVI Data Flow Example)


### ✨ Features

**1. Authentication (Firebase Auth)**
* Secure Registration (Email, Password, Password Confirmation).
* Email Verification flow.
* Login.

**2. Memory Games**
* **Numbers Mode:** Generates a random sequence of numbers (Binary or Natural). The user must memorize the sequence within a specific timeframe and recall it accurately.
* **Cards Mode:** Displays a shuffled deck of playing cards for a limited time. The user must recall and arrange the cards in the exact original order.

**3. Statistics & Progress**
* [Здесь допишите, что именно делает статистика: например, "Tracks daily performance and user progression over time".]