from stable_baselines3.common.env_checker import check_env
from env import Maze

env = Maze()
check_env(env, warn=True)