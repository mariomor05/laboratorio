
class Pedone:

    
#la classe pedone rappresenta il personaggio che i giocatori muoveranno sulla mappa,
#  ogni pedone avra' una posizione (coordinate_x e coordinate_y)
#  e un'icona_personaggio che lo rappresenta sulla mappa, inoltre avra' un metodo sposta che gli permette di cambiare posizione
    def __init__(self, coordinate_x, coordinate_y,icona_personaggio):
        self.coordinate_x = coordinate_x
        self.coordinate_y = coordinate_y
        self.icona_personaggio= icona_personaggio
        self.nuemro_muri = 10

    def sposta(self, x, y):
        self.coordinate_x = x
        self.coordinate_y = y
