# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a German golf stroke tracking application called "Nerd Golf Tracker" built with Java and Gradle. The application follows a command-interpreter pattern where golfers input text commands to track strokes, change holes, and view scoring information.

## Development Commands

### Build & Test
- `./gradlew` - Run default tasks: clean, build, cucumber (full CI pipeline)
- `./gradlew build` - Compile and package the application
- `./gradlew test` - Run unit tests only
- `./gradlew cucumber` - Run BDD acceptance tests
- `./gradlew clean` - Clean build artifacts

### Running the Application
- `java -jar build/libs/nerd-golf-tracker.jar` - Run the built JAR
- `./run.sh` - Convenience script to run the application

### Testing
- Unit tests: Located in `src/unittests/java/`
- BDD tests: Located in `src/cucumber/` with German feature files
- Test command: `./gradlew test` for unit tests, `./gradlew cucumber` for acceptance tests

## Architecture Overview

### Core Components
- **Main.java**: Entry point, initializes console and starts main loop
- **Tracker.java**: Central coordinator managing scorecard and command interpretation
- **EinfacherInterpreter.java**: Command interpreter using command pattern
- **BefehleSammler.java**: Command registry collecting all available commands
- **EinfacheScorecard.java**: Golf scorecard implementation tracking holes and strokes

### Design Patterns
- **Command Pattern**: Commands implement `Befehl` interface with `kommando()`, `operation()`, and `beschreibung()`
- **Strategy Pattern**: Operations implement `Operation` interface with `fuehreAus(scorecard)` method
- **Interpreter Pattern**: Text commands mapped to operations via interpreter

### Package Structure
```
de.itagile.golf/
├── befehl/          # Command implementations (SchlagBefehl, LochwechselBefehl, HilfeBefehl)
├── konsole/         # Console I/O handling with encoding support
├── operation/       # Business logic operations (Schlag, Lochwechsel, Hilfe, etc.)
└── util/            # Utility classes
```

### Key Interfaces
- `Befehl`: Command interface defining command string, operation, and description
- `Operation`: Business operation interface with `fuehreAus(scorecard)` method
- `Interpreter`: Command interpretation interface
- `Scorecard`: Golf scoring interface

### Command Flow
1. User input → Konsole → Tracker
2. Tracker → EinfacherInterpreter → Command lookup
3. Command → Operation execution → Scorecard update
4. Result → Console output

## Language & Localization
- **Primary Language**: German (class names, variables, feature files)
- **Encoding**: UTF-8 configured in gradle.properties for proper umlaut handling
- **Console Encoding**: Special handling in KonsoleEncoding.java for Windows compatibility

## Testing Strategy
- **Unit Tests**: JUnit 5 with Hamcrest matchers and Mockito
- **BDD Tests**: Cucumber with German feature files using Gherkin syntax
- **CI/CD**: GitHub Actions with Java 11 and 17 matrix testing

## Special Considerations
- Windows encoding issues with umlauts handled via KonsoleEncoding
- Gradle wrapper configured for cross-platform development
- Main class configured in build.gradle for JAR execution
- Default tasks include cucumber for comprehensive validation