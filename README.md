# Divide and Conquer - Multiplayer Drawing Game

A real-time multiplayer drawing game built with Java Sockets, where players compete to claim territory by coloring cells on a shared game board. This project demonstrates networking, concurrent programming, and interactive GUI development.

![Game Screenshot](path_to_screenshot.png) <!-- You should add a screenshot of your game -->

## 🎮 Game Overview

Players connect to a central server and compete to claim territory on a shared grid. Each player uses their unique color to fill cells, and a cell is claimed when a certain percentage is filled. The game showcases:

- Real-time multiplayer interaction
- Socket-based client-server architecture
- Concurrent programming with multi-threading
- Interactive GUI with Java Swing

## 🛠️ Technical Features

### Networking
- Custom socket-based communication protocol
- Real-time synchronization between multiple clients
- Robust client-server architecture
- Support for up to 4 players

### Concurrency
- Multi-threaded server handling multiple client connections
- Synchronized game state management
- Real-time updates across all connected clients

### User Interface
- Interactive drawing mechanics
- Real-time visual feedback
- Dynamic game board updates
- Player status indicators

## 🏗️ Architecture

### Client Side
- MVC architecture for clean separation of concerns
- Event-driven drawing system
- Efficient pixel-based territory calculation
- Robust error handling and connection management

### Server Side
- Thread-safe game state management
- Broadcast system for game updates
- Client session management
- Game logic validation

## 🚀 Getting Started

### Prerequisites
- Java JDK 17 or higher
- Any Java IDE (IntelliJ IDEA recommended)

### Running the Game

1. Start the server:

```bash
java main.server.controller.Server1
```

2. Launch clients (up to 4 players):

```bash
java main.client.controller.Client1
```

## 🎯 Future Enhancements

1. Game Room Creation
   - Custom game rooms with join codes
   - Flexible player count settings

2. Enhanced Synchronization
   - Improved multi-threading
   - More sophisticated server-side locking
   - Better client state synchronization

3. User Experience
   - Splash screen and options menu
   - Improved game logic and performance
   - More sophisticated drawing tools

4. Development Infrastructure
   - Comprehensive test suite
   - CI/CD pipeline integration
   - Better documentation

## 👥 Team

- @jiadil
- @MarinEstrada
- @jaysqvl
- @k-lee07
- @harpreetdubb

## 📄 🤝 License & Contributing

This project is licensed under the GNU General Public License v3.0. Contributions are welcome!