OpenCV-Connect-Four-AI
======================

This program takes an input image of a connect four board, uses the OpenCV computer vision library to calculate the positions of all the pieces in the board and their positions, and then uses minimax to approximate the optimal move for the player.

This project requires OpenCV as a dependency. The following project includes a script which can be used to install OpenCV on Ubuntu.
https://github.com/sgjava/install-opencv

Optionally specify a custom image path:
```gradle run -PimagePath=/build/resources/main/connect_four.jpg```
