# Towny

Towny is a java dwarf fortress-style game, where you manage a group of villagers stranded in the wilderness.
Your villagers will struggle to survive with their limited food and resources. Orcs, bandits and monsters will
periodically attack your village, or ambush villagers in underground caves. Slowly but surely you and your villagers
tame the land. With you help they build a community and thrive.

## Running the game
Use gradle to build te project. The build script will detect your operating system and package the correct binaries into
a runnable JAR.
### Mac OS
Running the game on Mac requires you to put `-XstartOnFirstThread -Djava.awt.headless=true` in the VM options in your
launch config.

## TODO

  **Bugs**
 - Combat system is very buggy
 - Selecting an icon when the buildsquare is visible selects the icon, but does not remove the old buildsquare.
 
 **Planned improvements**

 - Selectionsquare for mining orders
 - Better building and crafting UI
 - Villager management UI
 - World generator rework
 - Redo animation system
 - Finish farming implementation
 
 **Planned features / ideas**
 - Monster attacks
 - More crafting recipes
 - Farming, food and hunger system
 - Happiness and social system
 - Health system
 - Skills
 - NPC factions
 - Trading
 - More building materials and recipes
 - Beds and sleeping mechanic
 - Day/night cycle
