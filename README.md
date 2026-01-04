# TMDB Movies App

This is a sample Android application demonstrating a **modularized, scalable architecture** using:

- **Clean Architecture**
- **Jetpack Compose UI**
- **MVI Pattern**
- **Multi-module setup** (`data`, `domain`, `presentation`)
- **Koin for Dependency Injection**
- **Kotlin Coroutines + Flow*

## 🧱 Project Structure

Movies/
├── app/ # Main application module (hosts the MainActivity)
├── data/ # Implements repository interfaces, handles API + local db
├── domain/ # Contains business models and use case interfaces
└── presentation/ # (Compose UI, ViewModel, DI


# Android Studio Version Used
Android Studio Narwhal | 2025.1.1 Patch 1


## Common Issues
If you see **"Invalid JDK configuration"**:
- Open Android Studio → `File > Project Structure > SDK Location` and set jdk
- This is the latest stable version as mentioned in requirements sheet. 
- Please run on this version to avoid any Gradle issues
