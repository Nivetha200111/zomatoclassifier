# 🎯 Goal Wheel - Context-Aware Food Interpretation

A UI concept demo exploring **goal-based food interpretation** — where the same dish changes meaning based on your fitness goal and situation.

> *"Lost 25 kgs. Built this from the lessons. The same food can be your best friend or worst enemy — depending on your goal."*

---

## 💡 The Concept

Traditional food apps use filters: *calories < 500, protein > 30g*

**Goal Wheel takes a different approach:** Instead of filtering foods out, it *reinterprets* them based on your current goal.

### Same Butter Chicken, Different Meanings:

| Goal | Score | Interpretation |
|------|-------|----------------|
| 🔥 **Cutting** | 35 | "Calorie-dense — budget carefully" |
| 💪 **Bulking** | 88 | "Excellent calorie density for surplus" |
| 🏃 **Performance** | 75 | "Good carb-protein combo for glycogen" |
| 🧠 **Low-GI** | 42 | "Naan spikes glucose, fat slows absorption" |
| 🩺 **Recovery** | 72 | "Decent protein, carbs help replenish" |

**No dish is "good" or "bad" — it depends on context.**

---

## ✨ Key Features

### 🎡 Goal Wheel
Radial selector to quickly switch between 5 fitness goals with smooth animations.

### 🎚️ Goal Scrubber
Drag horizontally to **morph the dish interpretation in real-time**:
- Score animates smoothly between goals
- Label changes dynamically
- Reasons crossfade

*"Food meaning is continuous, not binary."*

### 🔄 Context Toggles
Situational lenses that modify how goals are interpreted:
- 🏋️ **Post-workout** — Prioritizes protein & fast carbs
- 🌙 **Late night** — Penalizes heavy/fried foods
- 💼 **Office lunch** — Avoids food coma triggers

### 💡 Healthier Swaps
Context-aware suggestions for better alternatives:
- *"Butter Chicken → Grilled Chicken Salad for cutting"*
- *"Post-workout? Try Tandoori Prawns for lean protein"*

### 📸 Real Food Images
Beautiful food photography from Unsplash for an authentic feel.

---

## 🛠️ Tech Stack

### Web (Next.js)
```
├── Next.js 14+ (App Router)
├── Tailwind CSS
├── TypeScript
└── Framer Motion animations
```

### Android (Kotlin)
```
├── Jetpack Compose (Material 3)
├── ViewModel + StateFlow
├── Coil for image loading
└── Compose Animation APIs
```

---

## 🚀 Getting Started

### Web Demo

```bash
# From project root
npm install
npm run dev
```

Open [http://localhost:3000](http://localhost:3000)

### Android Demo

1. Open project in **Android Studio**
2. Sync Gradle
3. Run on emulator or device

> 💡 **Tip:** Use a smaller emulator (Pixel 4a) for better performance than Pixel 9 Pro.

---

## 📱 Screenshots

| Restaurant Menu | Goal Scrubber | Context Toggles |
|-----------------|---------------|-----------------|
| Browse dishes with goal scores | Drag to morph interpretation | Switch situational context |

---

## 🎯 Design Philosophy

1. **Goals > Filters** — Don't remove options, reinterpret them
2. **Context Matters** — Post-workout butter chicken ≠ Late night butter chicken
3. **Continuous, Not Binary** — Food isn't "healthy" or "unhealthy"
4. **Situationally Intelligent** — The same goal means different things in different contexts

---
## Here you go : lick on the link for sample - https://youtube.com/shorts/V5oHO14ZCT4?feature=share

## ⚠️ Disclaimer

This is a **UI concept demo** for exploring interaction patterns.

- ❌ No AI/ML features
- ❌ No backend/APIs  
- ❌ No medical/nutrition claims
- ❌ Scores are illustrative only

**Not intended as actual dietary advice.**

---

## 📄 License

MIT

---

*Built with 💪 after losing 25 kgs. Fitness changed my perspective on food — this app explores that idea.*
