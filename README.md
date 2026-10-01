# Simple 24-Point Card Game 🎴

> **Play it live on GitHub Pages:**  
> 👉 [https://amitjoshi2724.github.io/cute-24-game/](https://amitjoshi2724.github.io/cute-24-game/)

---

## 📖 Overview

A pixel-perfect, faithful web recreation of the classic **24-Point Card Game** created ~11 years ago in **Java Swing** (`DriverProgrammingExercise22_13.java`).

The user interface, card assets, typography (`Times New Roman 24px`), buttons, layout, and dialog behaviors have been meticulously replicated in standard **HTML5**, **CSS3**, and **Vanilla JavaScript** to look and feel identical to the original desktop application.

---

## 🎮 How to Play

1. **The Goal:** Make an arithmetic expression that evaluates to **`24`** using all **4 cards** shown on screen.
2. **Card Values:**
   - **Ace:** `1`
   - **Numbers 2–10:** `2` through `10`
   - **Jack:** `11`
   - **Queen:** `12`
   - **King:** `13`
3. **Allowed Operations:**
   - Addition (`+`), Subtraction (`-`), Multiplication (`*`), Division (`/`)
   - Parentheses: `(` and `)`
4. **Input:**
   - Type your expression into the input box (e.g. `(11 - 8) * (11 - 3)` or `(J - 8) * (J - 3)` or `(13 - 1) * 2`).
   - You can also click on any card to insert its number.
   - Press **Enter** or click **Verify**.
5. **Results:**
   - If correct: A classic Swing `JOptionPane` dialog pops up displaying **`Correct`** with victory fanfare!
   - If incorrect: A dialog displays **`Incorrect result`**.
   - In both cases, dismissing the dialog deals 4 new cards, matching the original Java `refresh.doClick()` workflow.
   - Click **Refresh** at any time in the top-right corner to deal a fresh hand.

---

## ✨ Features & Faithfulness

- **100% Identical Visuals:** Matches the original Java Swing Aqua / Metal layout:
  - 800px × 472px game area
  - Exact `rgb(238, 238, 238)` panel background
  - Times New Roman 24px plain font for labels, buttons, and inputs
  - Crisp pixelated rendering of the original 71×96 card sprites scaled to 160×240
  - Matching button bevels, borders, active states, and padding
- **Authentic Shunting-Yard Expression Evaluator:** Uses the exact two-stack (operator stack and operand stack) evaluation algorithm from the original Java source.
- **Multiset Card Validation:** Verifies that exactly 4 cards are used and match the current hand.
- **Classic JOptionPane Dialog:** Custom modal dialog replicating Java Swing's message box with keyboard support (Enter/Space to dismiss).
- **Sound Effects:** Plays `tada.wav` on success.
- **Responsive Frame:** Centers the retro app in a desktop window frame that scales seamlessly to mobile and tablet screens without breaking layout.
- **Zero Build Dependencies:** Pure static files ready for instant deployment on GitHub Pages.

---


---

## 📲 Install as an App & Play Offline (Mac, iOS & Android)

**Cute 24 Game** is built as a full **Progressive Web App (PWA)** powered by a dedicated **Service Worker (`sw.js`)** and Web App Manifest (`manifest.json`). 

Once installed to your desktop or smartphone:
- ✈️ **100% Offline Playable:** All game logic, card graphics, Shunting-Yard parser, and retro UI styles are pre-cached directly to persistent device storage via the **Cache Storage API**. You can play in Airplane Mode with zero Wi-Fi or cellular data anytime, anywhere.
- 🖥️ **Native Standalone Window:** Launches in its own dedicated window without browser address bars, URL fields, or tabs.
- 🔄 **Zero-Hassle Background Updates:** When you connect to Wi-Fi, the Service Worker automatically fetches and updates any new changes pushed to GitHub.

### 🍎 Mac (macOS Desktop App)

You can install **Cute 24 Game** directly as a native macOS desktop application with its own dedicated window, Dock icon, and full offline support:

#### Method A: Safari (macOS Sonoma / Sequoia or newer)
1. Open **[https://amitjoshi2724.github.io/cute-24-game/](https://amitjoshi2724.github.io/cute-24-game/)** in **Safari**.
2. In the top menu bar, click **File** > **Add to Dock...** (or click the **Share** button in the Safari toolbar and select **Add to Dock**).
3. Name it **Cute 24 Game** and click **Add**.
4. The game is saved to your `Applications` folder and pinned to your **macOS Dock**.
5. Launch it like any native Mac app—it runs in its own window without browser tabs or address bars, supports full keyboard controls, and is 100% playable offline!

#### Method B: Google Chrome, Brave, or Microsoft Edge
1. Open **[https://amitjoshi2724.github.io/cute-24-game/](https://amitjoshi2724.github.io/cute-24-game/)** in **Chrome**, **Brave**, or **Edge**.
2. Click the **Install Cute 24 Game** icon in the right side of the address/URL bar (or go to **Settings (⋮)** > **Save and share** > **Install Cute 24 Game...**).
3. Click **Install**.
4. The game opens in its own standalone desktop window and is added to your Mac's **Launchpad**, **Spotlight**, and `~/Applications/Chrome Apps` folder.

### 🍏 iPhone & iPad (iOS Safari)
1. Open **[https://amitjoshi2724.github.io/cute-24-game/](https://amitjoshi2724.github.io/cute-24-game/)** in **Safari**.
2. Tap the **Share** button at the bottom of the screen (the square with an arrow pointing upward).
3. Scroll down the share sheet and tap **"Add to Home Screen"**.
4. Tap **Add** in the top-right corner. A dedicated 24 Game icon will appear on your home screen.
5. Tap the new icon once while online to let the Service Worker cache all assets—after that, it is permanently playable offline!

### 🤖 Android (Google Chrome)
1. Open **[https://amitjoshi2724.github.io/cute-24-game/](https://amitjoshi2724.github.io/cute-24-game/)** in **Google Chrome**.
2. Tap the **three-dots menu (⋮)** in the top-right corner.
3. Tap **"Install app"** (or **"Add to Home screen"**).
4. Tap **Install** on the prompt. The game will install directly to your home screen and app drawer as an offline-ready arcade app.

## 🛠️ Original Java Source

The original Java Swing source file is preserved in this repository:
- [`DriverProgrammingExercise22_13.java`](DriverProgrammingExercise22_13.java)
