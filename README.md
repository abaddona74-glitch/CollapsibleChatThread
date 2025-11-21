# CollapsibleChatThread

This repository contains the Collapsible Chat Thread Android app (Jetpack Compose) implementing a threaded comments UI where tapping a comment collapses/expands its entire reply branch.

## Overview

- Platform: Android (Kotlin + Jetpack Compose)
- Purpose: Threaded discussion UI with infinite nesting and branch collapse/expand behavior.

## What is included

- `app/` — Android app source code (Kotlin/Compose).
- `docs/FIGMA_SERVER_GUIDE.md` — guide for using Figma exports and design tokens (document name may vary).
- `scripts/install_and_run.bat` — helper to build and install the debug APK to a connected Windows machine/device.
- `app/src/main/java/.../ui/components/ThreadedComments.kt` — threaded comment rendering with collapse/expand behavior and connector lines.

## Assignment compliance
The implementation targets the assignment requirements:

- Infinite nesting & recursive rendering: `CommentItem` renders nested replies recursively.
- Collapse / expand whole branch: tapping a comment with replies toggles the entire reply branch. The plus/minus affordance also toggles the branch.
- Reply count when collapsed: shown as `Show X replies` for collapsed branches.
- Vertical and horizontal connector lines: drawn using `drawBehind` in `ThreadedComments.kt`.
- Dark theme: Material3 color scheme and theme tokens are used; UI supports dark mode.
- No third-party libraries: only standard Android Jetpack and Compose libraries are used.

## Build & Run (Windows)
Prereqs: JDK 11+, Android SDK, `adb` in PATH. Connect device via USB or TCP/IP.

1. Build debug APK:

```
call gradlew.bat assembleDebug
```

2a. Install & launch using the helper script (Windows):

```
scripts\install_and_run.bat
```

2b. Or manually install:

```
adb install -r app\build\outputs\apk\debug\app-debug.apk
adb shell am start -n com.maxmudbek.collapsiblechatthread/.MainActivity
```

## Notes for reviewers
- There are no Python integration files in this repository. If a reviewer believes otherwise, point to the file path and it will be clarified.
- If a pull request is preferred for review instead of direct commits to `main`, a branch and PR can be created upon request.

## Next steps (optional)
- Provide a short screen recording demonstrating the collapse/expand flow for submission.
- Tidy up documentation or rename guide files to match project conventions.
