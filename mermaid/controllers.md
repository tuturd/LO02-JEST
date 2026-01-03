# Architecture des Controllers et Router

## Vue d'ensemble du système

```mermaid
graph TB
    subgraph "Couche Model"
        Game[Game<br/>Singleton]
        Player[Player]
        VirtualPlayer[VirtualPlayer]
    end
    
    subgraph "Couche Controller"
        GC[GameController<br/>Interface]
        PR[PlayerRouter<br/>Router léger]
        Console[ConsoleController<br/>Implémentation]
        GUI[GUIController<br/>Implémentation]
    end
    
    subgraph "Couche View"
        Terminal[Console<br/>Scanner/System.out]
        MainWin[MainWindow<br/>Swing GUI]
    end
    
    Game -->|"gameController<br/>(90% opérations)"| GC
    Game -->|"playerRouter<br/>(3 méthodes)"| PR
    
    GC -.->|implements| Console
    GC -.->|implements| GUI
    
    PR -->|"route selon<br/>InterfaceType"| Console
    PR -->|"route selon<br/>InterfaceType"| GUI
    
    Console -->|affiche| Terminal
    GUI -->|affiche| MainWin
    
    Player -->|"getPreferredInterface()<br/>InterfaceType enum"| PR
    VirtualPlayer -->|"getPreferredInterface()<br/>InterfaceType enum"| PR
    
    style Game fill:#e1f5ff
    style GC fill:#fff4e1
    style PR fill:#ffe1f5
    style Console fill:#e1ffe1
    style GUI fill:#e1ffe1
```

## Flux d'exécution typique

### 1. Création d'une offre (makeOffer)
```mermaid
sequenceDiagram
    participant Game
    participant PlayerRouter
    participant Player
    participant ConsoleController
    participant GUIController
    
    Game->>Player: makeOffer(c1, c2, router)
    Player->>Player: getPreferredInterface()<br/>→ InterfaceType.CONSOLE
    Player->>PlayerRouter: askMakeOffer(this, jest, c1, c2)
    PlayerRouter->>PlayerRouter: getControllerForPlayer(player)<br/>→ vérifie InterfaceType
    
    alt Player veut CONSOLE
        PlayerRouter->>ConsoleController: askMakeOffer(player, jest, c1, c2)
        ConsoleController->>Player: retourne choix (1 ou 2)
    else Player veut GUI
        PlayerRouter->>GUIController: askMakeOffer(player, jest, c1, c2)
        GUIController->>Player: retourne choix (1 ou 2)
    end
    
    Player->>Game: retourne Card choisie
```

### 2. Message global (displayMessage)
```mermaid
sequenceDiagram
    participant Game
    participant GameController
    participant ConsoleController
    participant GUIController
    
    Game->>GameController: displayMessage("Partie sauvegardée", INFORMATION)
    
    alt Mode CONSOLE
        GameController->>ConsoleController: displayMessage(msg, type)
        ConsoleController->>ConsoleController: System.out.println(msg)
    else Mode GUI
        GameController->>GUIController: displayMessage(msg, type)
        GUIController->>GUIController: Affiche dans log + popup si INFORMATION/ERROR
    end
```

## Routing selon le type d'interface joueur

```mermaid
graph TB
    subgraph "PlayerRouter.getControllerForPlayer()"
        Start[Joueur demande interaction]
        Check{"player.getPreferredInterface()"}
        
        Start --> Check
        Check -->|InterfaceType.GUI| ReturnGUI[Retourne guiController]
        Check -->|InterfaceType.CONSOLE| ReturnConsole[Retourne consoleController]
    end
    
    ReturnGUI --> GUICtrl[GUIController]
    ReturnConsole --> ConsoleCtrl[ConsoleController]
    
    GUICtrl --> SwingUI[Interface graphique Swing]
    ConsoleCtrl --> Terminal[Terminal Scanner]
    
    style Check fill:#fff9c4
    style ReturnGUI fill:#e1f5ff
    style ReturnConsole fill:#e1ffe1
```
