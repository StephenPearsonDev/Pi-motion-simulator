# Pi motion trajectory simulator

## Overview

This project visualises π's irrationality using Java and OOP principles. It is a university project - the task demanded a simple program to show the rendering a non-repeating spiral. The main equation in the requirements for plotting the path is: 𝑃(𝜃) = 𝑒^(𝑖𝜃) + 𝑒^(𝑖𝜋𝜃)

The idea is that it produces a complex, never-repeating pattern.

## Features to match task requirments

- Visualises pi's irrationality with geometric trajectories
- Adjustable speed, line thickness, and frame rate
- Zoom, pan, and lockable camera positions
- Color-coded segments for better pattern recognition
- Toggle double buffering for performance testing
- Includes help documentation

## Running requirements

- Java 11 or higher
- Maven 3.6 or higher
- Git

## Installation & Running

Clone the repository and build/run with Maven:

```bash
# Clone the repository
git clone https://github.com/StephenPearsonDev/Pi-motion-simulator.git
cd Pi-motion-simulator

# Build the project
mvn clean package

# Run the application
java -jar target/pi-motion-simulator-1.0.jar

### Manual installation & running

Download repository as a zip file.

#### Import into your IDE:

**IntelliJ IDEA:**
Open > Select extracted folder > Open as Project

**Eclipse:**
Import > Existing Maven Projects > Browse to extracted folder > Finish

