from py4j.java_gateway import JavaGateway
from py4j.protocol import get_return_value, get_command_part
import numpy as np
import gymnasium as gym
from gymnasium import spaces

gateway = JavaGateway(auto_convert=True)
win = gateway.entry_point
maze = gateway.entry_point.maze

gridX = win.getNumSquaresX()
gridY = win.getNumSquaresY()

gridArr = np.array([gridX, gridY], dtype=np.float32).reshape((1, 2))
    
class Maze(gym.Env):
    global win, gridX, gridY, gridArr
    metadata = { "render_modes": ["Console"]}
    LEFT = 0
    RIGHT = 1
    DOWN = 3
    UP = 4
    
    def __init__(self, grid_size=gridArr, render_mode="Console"):
        super(Maze, self).__init__()
        self.render_mode = render_mode
        
        posX = win.getX()
        posY = win.getY()
        
        self.grid_size = grid_size
        self.agent_pos = [posX, posY]
        
        n_actions = 4
        self.action_space = spaces.Discrete(n_actions)
        self.observation_space = spaces.Box(
            low=np.zeros((1, 2), dtype=np.float32), high=gridArr, shape=(1, 2), dtype=np.float32 
        )
        
    def reset(self, seed=None, options=None):
        super().reset(seed=seed, options=options)
        win.resetMaze()
        posX = maze.getX()
        posY = maze.getY()
        self.agent_pos = np.array([posX, posY], dtype=np.float32)
        return self.agent_pos.reshape(1, 2), {}
    
    def step(self, action):
        if (action == self.LEFT):
            curX = win.getX()
            win.setX(curX-1)
        if (action == self.RIGHT):
            curX = win.getX()
            win.setX(curX+1)
        if (action == self.UP):
            curY = win.getY()
            win.setY(curY-1)
        if (action == self.DOWN):
            curY = win.getY()
            win.setY(curY+1)
        
        posX = win.getX()
        posY = win.getY()
        self.agent_pos = np.array([posX, posY])
        
        if (action > 4 or action < 0):
            raise ValueError(f"Incorrect action {action}")
        
        self.agent_pos = np.clip(self.agent_pos, 0, self.grid_size)
        
        terminated = bool(maze.checkFinish() or maze.getPossibles())
        truncated = False
        
        reward = 1 if maze.checkFinish() else 0
        
        info = {}
        
        return (
                self.agent_pos.reshape(1, 2).astype(np.float32),
                reward,
                terminated,
                truncated,
                info
            )
        
    def render(self):
        if self.render_mode == "console":
            print("x: " + self.agent_pos[0] + " y: " + self.agent_pos[1])
            
    def close(self):
        pass