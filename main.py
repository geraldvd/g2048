from p2048 import *
from tkinter import *

# Initialize game
game = P2048()
game.print()

# Define key actions
def leftKey(event):
    game.move(Move.LEFT)
    game.print()

def rightKey(event):
    game.move(Move.RIGHT)
    game.print()

def upKey(event):
    game.move(Move.UP)
    game.print()

def downKey(event):
    game.move(Move.DOWN)
    game.print()


# Start Tkinter app
root = Tk()
root.bind('<Left>', leftKey)
root.bind('<Right>', rightKey)
root.bind('<Up>', upKey)
root.bind('<Down>', downKey)
root.title("P2048 Game")

color_alternate = True
for i in range(3):
    for j in range(3):
        if color_alternate:
            color_alternate = False
            c = "beige"
        else:
            color_alternate = True
            c = "lightgray"
        Label(root, text="5", bg=c, width=10, height=5).grid(row=i, column = j)

root.mainloop()