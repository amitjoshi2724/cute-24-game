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

## 🛠️ Original Java Source

The original Java Swing source file is preserved in this repository:
- [`DriverProgrammingExercise22_13.java`](DriverProgrammingExercise22_13.java)
