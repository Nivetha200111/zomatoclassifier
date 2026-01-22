# Goal Wheel - Food Interpretation UI Concept

A UI concept demo that explores **goal-based food interpretation** — where the same dish changes meaning based on your fitness goal.

> "Filters remove options. Goals reinterpret options."

## Concept

Traditional food apps use filters (calories < 500, protein > 30g). This demo explores a different approach: **Goal-based interpretation**.

The same butter chicken might be:
- 🔥 **Cutting**: "Calorie-dense — budget carefully" (Score: 35)
- 💪 **Bulking**: "Excellent calorie density for surplus" (Score: 88)
- 🏃 **Performance**: "Good carb-protein combo for glycogen" (Score: 75)

No dish is "good" or "bad" — it depends on context.

## Two Implementations

### 1. Web Demo (Next.js)
Located in `/lpz` - A Next.js + Tailwind CSS implementation.

```bash
cd lpz
npm install
npm run dev
```

Open http://localhost:3000

### 2. Android Demo (Kotlin + Jetpack Compose)
Located in root `/app` - A native Android implementation.

Open in Android Studio → Sync Gradle → Run

## Key Interactions

### Goal Wheel
Radial selector to quickly switch between 5 fitness goals:
- 🔥 Cutting
- 💪 Bulking  
- 🏃 Performance
- 🧠 Low-GI
- 🩺 Recovery

### Goal Scrubber (WOW Factor)
Drag horizontally to morph the dish interpretation in real-time:
- Score animates smoothly
- Label changes dynamically
- Reasons crossfade

This interaction makes users feel: *"Food meaning is continuous, not binary."*

## Tech Stack

**Web:**
- Next.js 14+ (App Router)
- Tailwind CSS
- TypeScript

**Android:**
- Kotlin
- Jetpack Compose (Material 3)
- ViewModel + StateFlow

## Not Included (By Design)

- ❌ No AI features
- ❌ No backend/APIs
- ❌ No medical/nutrition claims
- ❌ No real food data

This is a **UI concept demo** — scores and reasons are illustrative only.

## License

MIT
