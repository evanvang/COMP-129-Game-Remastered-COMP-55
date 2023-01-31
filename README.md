# 55starterfiles README ADJUSTED

COMP 129 REMASTERED CHANGES README UPDATED:
In this remastered version for the class COMP 129 (Software Engineering) I am adding an additional feature.
In the orginal game, it always begins after the user clicks on the Play button starting from Level 1. This cannot be changed currently and so you must always begin from level 1 when booting up the game. 
The feature to be added is for the user to have the ability to choose the level from which they want to start from. Rather than always having to start from the 1st level even after beating the game, Level Selection will be added to give the user more freedom in how the game will be played as opposed to keeping it more linear.

PSEUDOCODE:
1. Create a new class called LevelsPane.java to house the page that will contain the level selection 
2. Change the background image and add the GButtons for levels 1, 2, and 3. Also changing the mousePressed response.
3. Add a switchToLevelsMenu function in MainApplication.java to call on it when mousePressed occurs as well as a private variable LevelsPane levels
4. Add GButton (Level Selection) in MenuPane so user can choose it from the opening menu
5. 
6. 

You will use this project as your base.
Make sure that you understand the two files provided here

## UML Class Diagram for files provided
![](media/55GroupProjectUML.jpg)

## UML Sequence Diagram for files provided
![](media/55GroupProjectSequenceDiagram.png)
