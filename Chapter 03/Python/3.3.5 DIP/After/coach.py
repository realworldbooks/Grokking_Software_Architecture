class Coach:
    def __init__(self, players):
        self.team = players

    def execute_game_plan(self):
        for player in self.team:
            player.perform_action()