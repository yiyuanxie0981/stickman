## Instruction :
There are three levels in this game, each level contains different entities. <br/>
Player will be able to move character by pressing left and right keyboard button, 
press up to jump and "S" for saving game and "Q" for restoring game.

## Character:
Assumption:
1. Character has 3 lives in each level for 3 level of challenges.
2. Player will be able to move around, save game, retrieve game, go to next level etc.

## Screen Instruction:

There is game time, total score and current level score shown on the screen.<br/>
1. Game time: time will start record when the game begins.
2. Total score: this score will add up the current level and the previous levels score.
3. Current level score: record the current score<br />
Score: 
1. A initially score will be given based on target time in json file.<br />
2. Score will add up 100 when player kills a enemy.
3. The minimal score is 0, which means the least score a player can get in a level is 0.
4. Losing lives will not impact the score.

## Keyboard Instruction:
1. Up: Jump
2. Left: Move left
3. Right: Move right
4. S: Saving game
5. Q: Reloading game

## Code Style:
This project mainly use Java Style Guide, the link will be as shown:
https://google.github.io/styleguide/javaguide.html
For example:

1. File name

<small>The source file name consists of the case-sensitive name of the top-level class it contains (of which there is exactly one), plus the .java extension.</small>

2. Special escape sequences:

<small>For any character that has a special escape sequence (\b, \t, \n, \f, \r, \", \' and \\), that sequence is used rather than the corresponding octal (e.g. \012) or Unicode (e.g. \u000a) escape.</small>

3. Source file structure:

<small> License or copyright information, if present
        Package statement
        Import statements
        Exactly one top-level class</small>
4. Package statement

<small>The package statement is not line-wrapped. The column limit (Section 4.4, Column limit: 100) does not apply to package statement</small>

5. More information will be the link above.