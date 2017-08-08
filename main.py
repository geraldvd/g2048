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
app = Tk()
frame = Frame(app, width=100, height=100)
app.bind('<Left>', leftKey)
app.bind('<Right>', rightKey)
app.bind('<Up>', upKey)
app.bind('<Down>', downKey)
frame.pack()
app.mainloop()