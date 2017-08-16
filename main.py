from p2048 import *
from tkinter import *

# Initialize game
game = P2048()

# Define key actions
def leftKey(event):
    game.move(Move.LEFT)
    refreshGraphics()

def rightKey(event):
    game.move(Move.RIGHT)
    refreshGraphics()

def upKey(event):
    game.move(Move.UP)
    refreshGraphics()

def downKey(event):
    game.move(Move.DOWN)
    refreshGraphics()

# Tile settings
tile_colors = {0: 'white', 2: 'honeydew', 4: 'moccasin',
               8: 'peach puff', 16: 'powder blue',
               32: 'sky blue', 64: 'dark turquoise',
               128: 'sea green', 256: 'dark olive green',
               512: 'maroon', 1024: 'deep pink', 2048: 'goldenrod'}


def refreshGraphics():
    # Update tiles
    for i in range(game.gridDim):
        for j in range(game.gridDim):
            v = game.grid[i,j]
            if v != 0: s = str(v)
            else: s = ''
            tiles[i][j].config(text=s, bg=tile_colors[v])

    # Update score
    score_label.config(text="Score: " + str(game.score))


# Start Tkinter app
master = Tk()
master.bind('<Left>', leftKey)
master.bind('<Right>', rightKey)
master.bind('<Up>', upKey)
master.bind('<Down>', downKey)
master.title("P2048 Game")

# Score
score_label = Label(master, text="Score: " + str(game.score))
score_label.pack()


# Initialize tiles
tile_frame = LabelFrame(master, text="Game Board")
tiles = []
for i in range(game.gridDim):
    r_tiles = []
    for j in range(game.gridDim):
        r_tiles.append(Label(tile_frame, text='', font=("Helvetica", 20), bg=tile_colors[0], width=10, height=5))
        r_tiles[-1].grid(row=i, column=j)
    tiles.append(r_tiles)
tile_frame.pack()


# Run Tkinter main loop
refreshGraphics()
master.mainloop()