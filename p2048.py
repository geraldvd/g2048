import numpy as np
from enum import Enum

class Move(Enum):
    UP = 1
    DOWN = 2
    LEFT = 3
    RIGHT = 4

class P2048:
    def __init__(self, maxNum=2048, gridDim=4):
        # Setup class parameters
        self.gridDim = gridDim
        self.maxNum = maxNum
        self.grid = np.zeros((gridDim, gridDim), np.uint64)
        self.score = 0

        # Initialize board
        self.addRandom24()
        self.addRandom24()


    def move(self, m):
        # Perform 2048 move
        # 1. move everything to right direction
        # 2. check adjecent equal numbers
        # 3. double value and add a zero
        # 4. Add another random 2 or 4

        # Flip such that every move can be considered "UP"
        if m == Move.UP:
            pass
        elif m == Move.DOWN:
            self.grid = np.flipud(self.grid)
        elif m == Move.LEFT:
            self.grid = np.transpose(self.grid)
        elif m == Move.RIGHT:
            self.grid = np.transpose(self.grid)
            self.grid = np.flipud(self.grid)

        # Perform move (for each column separately)
        for j in range(self.gridDim):
            # Move all numbers up
            for i in range(self.gridDim - 1):
                keepGoing = True
                while self.grid[i, j] == 0 and keepGoing:
                    # Shift row in direction of move
                    self.grid[i:self.gridDim - 1, j] = self.grid[i + 1:, j]
                    self.grid[self.gridDim - 1, j] = 0

                    # Check whether there is another 0
                    if self.grid[i, j] != 0 or np.sum(self.grid[i + 1:, j]) == 0 or i == self.gridDim - 1:
                        keepGoing = False

            # Check and add adjacent numbers
            skip_next = False
            for i in range(self.gridDim - 1):
                if not skip_next:
                    if self.grid[i,j] == self.grid[i+1,j]:
                        self.grid[i,j] = 2 * self.grid[i,j]
                        self.grid[i+1, j] = 0
                        skip_next = True
                else:
                    skip_next = False



        # Flip back
        if m == Move.UP:
            pass
        elif m == Move.DOWN:
            self.grid = np.flipud(self.grid)
        elif m == Move.LEFT:
            self.grid = np.transpose(self.grid)
        elif m == Move.RIGHT:
            self.grid = np.flipud(self.grid)
            self.grid = np.transpose(self.grid)

        # Add extra random number
        self.addRandom24()

    def addRandom24(self):
        # find eligible locations (i.e., zeros)
        locations = np.where(self.grid==0)

        # Pick random x- and y-coordinates
        n = np.arange(len(locations[0]))
        n = np.random.choice(n)

        # Add number
        self.grid[locations[0][n], locations[1][n]] = np.random.choice(np.array([2,4]))

    def print(self):
        for i in range(0,self.gridDim):
            for j in range(0,self.gridDim):
                print(self.grid[i,j], end='    ')
            print('\n')

    def getValue(self, i, j):
        return self.grid[i,j]