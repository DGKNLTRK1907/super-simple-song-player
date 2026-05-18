# Super Simple Song Player (SSSP) 🎵

A robust, core-Java desktop audio player application built using the **Java Swing** framework and the **Java Sound API**. This project demonstrates clean implementation of event-driven desktop GUIs, asynchronous UI updating via timers, and precise audio manipulation including dynamic playlist sequencing and decibel-mapped volume control.

---

## 📌 Project Status: Under Active Development 🚀
This repository represents an **ongoing educational project** designed to master desktop application architecture, threading, and hardware API communication in Java. 
* **Current Focus:** Refactoring core event listeners, polishing real-time thread safety, and expanding format support.
* **Next Major Milestone:** Transitioning the layout architecture from Java Swing to **JavaFX** for an enhanced MVVM-based graphical interface.

---

## 🚀 Core Technical Features

* **Logarithmic Volume Control:** Implements precision audio attenuation by mapping a linear JSlider (0-100) to Java's `FloatControl.MASTER_GAIN` using standard decibel conversion formulas (dB = 20 * log10(volume)). Includes built-in boundary safety checks against hardware limits.
* **Asynchronous Progress Tracking:** Uses a `javax.swing.Timer` ticking at 100ms intervals to poll the `Clip` microsecond position, dynamically updating a `JProgressBar` with formatted `mm:ss / mm:ss` runtime statistics without blocking the main UI thread.
* **Automated Playlist & Loop Sequencing:** Attaches a custom `LineListener` to the `Clip` lifecycle. Detects `LineEvent.Type.STOP` when a track concludes, enabling either seamless single-track looping or automatic pipeline progression via an `ArrayList` playlist mechanism.
* **Dynamic Time Scrubbing:** Features responsive playback modification handlers allowing users to instantaneously skip forward (`10>`) or rewind (`<10`) exactly 10 seconds through core microsecond position mutations.
* **On-the-Fly UI Customization:** Implements decoupled frame communication through a standalone `SettingsPopUp` context, utilizing `JColorChooser` dialogs to dynamically re-skin main panel backgrounds and slider components at runtime.

---

## 🛠️ Architecture & Class Breakdown

The codebase is structured modularly across four primary components within the `Project` package:

1. **`Main.java`:** The clean bootstrap entry point that initializes the application pipeline by invoking the primary file chooser dialogue.
2. **`FilePopUp.java`:** A continuous-loop initialization handler utilizing `JFileChooser`. Restricts input stream loading to validated `.wav` targets, safely catching `UnsupportedAudioFileException` states, and boots up the core audio architecture.
3. **`AudioPlayer.java`:** The central engine of SSSP. Manages the active `Clip` lifecycle, explicit layout component positioning, core state metrics (play/stop, reset, loop toggles), and asynchronous event loops.
4. **`SettingsPopUp.java`:** An independent control interface implementing `ActionListener` to append new audio files to the runtime playlist track array or pass target `Color` values back to structural UI setters.

---

## 💻 Tech Stack

* **Language:** Java SE (JDK 11+ compatible)
* **GUI Framework:** Java Swing / AWT
* **Audio Library:** Java Sound API (`javax.sound.sampled`)
* **Development Paradigm:** Object-Oriented Programming (OOP) & Event-Driven Architecture

---

## 📥 How to Run

1. Clone this repository to your local workstation:
   ```bash
   git clone [https://github.com/dogukanuluturk/super-simple-song-player.git](https://github.com/dogukanuluturk/super-simple-song-player.git)
