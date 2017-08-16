from p2048 import *
from tkinter import *

# Initialize game
game = P2048()

# Define key actions
def leftKey(event):
    game.move(Move.LEFT)
    refreshTiles()

def rightKey(event):
    game.move(Move.RIGHT)
    refreshTiles()

def upKey(event):
    game.move(Move.UP)
    refreshTiles()

def downKey(event):
    game.move(Move.DOWN)
    refreshTiles()

# Tile settings
tile_colors = {0: 'white', 2: 'honeydew', 4: 'moccasin',
               8: 'peach puff', 16: 'powder blue',
               32: 'sky blue', 64: 'dark turquoise',
               128: 'sea green', 256: 'dark olive green',
               512: 'maroon', 1024: 'deep pink', 2048: 'goldenrod'}


def refreshTiles():
    for i in range(game.gridDim):
        for j in range(game.gridDim):
            v = game.getValue(i,j)
            if v != 0: s = str(v)
            else: s = ''
            tiles[i][j].config(text=s, bg=tile_colors[v])


# Start Tkinter app
root = Tk()
root.bind('<Left>', leftKey)
root.bind('<Right>', rightKey)
root.bind('<Up>', upKey)
root.bind('<Down>', downKey)
root.title("P2048 Game")

# Initialize tiles
tiles = []
for i in range(game.gridDim):
    r_tiles = []
    for j in range(game.gridDim):
        r_tiles.append(Label(root, text='', font=("Helvetica", 20), bg=tile_colors[0], width=10, height=5))
        r_tiles[-1].grid(row=i, column=j)
    tiles.append(r_tiles)

# Run Tkinter main loop
refreshTiles()
root.mainloop()